package hs.lessonReserve.service;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.LessonReview.LessonReview;
import hs.lessonReserve.domain.LessonReview.LessonReviewRepository;
import hs.lessonReserve.domain.certificate.Certificate;
import hs.lessonReserve.domain.certificate.CertificateRepository;
import hs.lessonReserve.domain.user.Student;
import hs.lessonReserve.domain.user.Teacher;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.domain.user.UserRepository;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.util.RedisUtil;
import hs.lessonReserve.web.dto.auth.StudentModifyDto;
import hs.lessonReserve.web.dto.auth.UserJoinDto;
import hs.lessonReserve.web.dto.teacher.TeacherIntroduceDto;
import hs.lessonReserve.web.dto.teacher.TeacherModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CertificateRepository certificateRepository;
    private final LessonReviewRepository lessonReviewRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisUtil redisUtil;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void joinTeacher(UserJoinDto userJoinDto) {

        String savedVerificationCode = redisUtil.getData(userJoinDto.getEmail());
        if (!savedVerificationCode.equals(userJoinDto.getVerificationCode())) {
            throw new CustomException("이메일 인증을 완료해 주세요.");
        }

        System.out.println("사진: " + userJoinDto.getProfileImageFile().getOriginalFilename());
        String profileImageFilename = null;
        if (userJoinDto.getProfileImageFile() != null) {
            UUID uuid = UUID.randomUUID();
            profileImageFilename = uuid + userJoinDto.getProfileImageFile().getOriginalFilename();
            Path path = Paths.get(uploadFolder + profileImageFilename);
            try {
                Files.write(path, userJoinDto.getProfileImageFile().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Teacher teacher = Teacher.builder()
                .email(userJoinDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userJoinDto.getPassword()))
                .name(userJoinDto.getName())
                .profileImageUrl(profileImageFilename)
                .build();

        List<MultipartFile> certificateImageFiles = userJoinDto.getCertificateImageFiles();

        if (certificateImageFiles != null) {
            List<Certificate> certificates = new ArrayList<>();
            for (MultipartFile certificateImageFile : certificateImageFiles) {
                UUID uuid = UUID.randomUUID();
                String certificateImageFileName = uuid + certificateImageFile.getOriginalFilename();
                System.out.println(uploadFolder);

                Path path = Paths.get(uploadFolder + certificateImageFileName);
                try {
                    Files.write(path, certificateImageFile.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Certificate certificate = Certificate.builder()
                        .teacher(teacher)
                        .certificatePaperImageUrl(certificateImageFileName)
                        .build();

                certificateRepository.save(certificate);
                certificates.add(certificate);
            }
            teacher.setCertificates(certificates);
        }

        teacher.setRole("ROLE_TEACHER");

        userRepository.save(teacher);
    }

    @Transactional
    public void joinStudent(UserJoinDto userJoinDto) {
        String savedVerificationCode = redisUtil.getData(userJoinDto.getEmail());
        if (!savedVerificationCode.equals(userJoinDto.getVerificationCode())) {
            throw new CustomException("이메일 인증을 완료해 주세요.");
        }

        String profileImageFilename = null;
        if (userJoinDto.getProfileImageFile() != null) {
            UUID uuid = UUID.randomUUID();
            profileImageFilename = uuid + userJoinDto.getProfileImageFile().getOriginalFilename();
            Path path = Paths.get(uploadFolder + profileImageFilename);
            try {
                Files.write(path, userJoinDto.getProfileImageFile().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Student student = Student.builder()
                .email(userJoinDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userJoinDto.getPassword()))
                .name(userJoinDto.getName())
                .profileImageUrl(profileImageFilename)
                .build();

        student.setRole("ROLE_STUDENT");

        userRepository.save(student);
    }

    public TeacherIntroduceDto teacherIntroduceDto(long teacherId) {
        Teacher teacher = (Teacher) userRepository.findById(teacherId).orElseThrow(() -> {
            throw new CustomException("없는 선생님입니다.");
        });

        List<LessonReview> lessonReviews = lessonReviewRepository.mFindByTeacher(teacherId);

        int sum = 0;
        int count = 0;
        for (LessonReview lessonReview : lessonReviews) {
            sum += lessonReview.getScore();
            count++;
        }
        int averageScore = -1;
        if (count != 0) {
            averageScore = sum / count;
        }
        TeacherIntroduceDto teacherIntroduceDto = new TeacherIntroduceDto(teacher, lessonReviews, averageScore);

        return teacherIntroduceDto;
    }

    public StudentModifyDto studentModifyDto(PrincipalDetails principalDetails) {
        try {
            StudentModifyDto studentModifyDto = new StudentModifyDto((Student) principalDetails.getUser());
            return studentModifyDto;
        } catch (ClassCastException e) {
            throw new CustomException("강사님 회원수정 메뉴를 이용해 주세요.");
        }
    }

    @Transactional
    public void studentModify(PrincipalDetails principalDetails, StudentModifyDto studentModifyDto) {

        User user = userRepository.findById(principalDetails.getUser().getId()).orElseThrow(() -> {
            throw new CustomException("없는 유저입니다.");
        });

        user.setName(studentModifyDto.getName());
        user.setPassword(bCryptPasswordEncoder.encode(studentModifyDto.getPassword()));

        if (studentModifyDto.isProfileImageDelete()) { // 기존 사진 삭제
            user.setProfileImageUrl(null);
        } else if (studentModifyDto.getProfileImageFile() != null) { // 사진 새로 등록
            UUID uuid = UUID.randomUUID();
            String profileImageFileName = uuid + studentModifyDto.getProfileImageFile().getOriginalFilename();
            Path path = Paths.get(uploadFolder + profileImageFileName);
            try {
                Files.write(path, studentModifyDto.getProfileImageFile().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            user.setProfileImageUrl(profileImageFileName);
        }
        principalDetails.setUser(user);

    }

    public TeacherModifyDto teacherModifyDto(PrincipalDetails principalDetails) {

        Teacher teacher = (Teacher) principalDetails.getUser();

        ArrayList<String> certificatePaperImageUrls = new ArrayList<>();
        teacher.getCertificates().stream().map(certificate -> {
            return certificatePaperImageUrls.add(certificate.getCertificatePaperImageUrl());
        });
        TeacherModifyDto teacherModifyDto = new TeacherModifyDto(teacher, certificatePaperImageUrls);

        return teacherModifyDto;
    }

    @Transactional
    public void teacherModify(PrincipalDetails principalDetails, TeacherModifyDto teacherModifyDto) {
        System.out.println("받는"+teacherModifyDto);

        User user = userRepository.findById(principalDetails.getUser().getId()).orElseThrow(() -> {
            throw new CustomException("없는 유저입니다.");
        });

        user.setName(teacherModifyDto.getName());
        user.setPassword(bCryptPasswordEncoder.encode(teacherModifyDto.getPassword()));

        if (teacherModifyDto.isProfileImageDelete()) { // 기존 사진 삭제
            user.setProfileImageUrl(null);
        } else if (teacherModifyDto.getProfileImageFile() != null) { // 사진 새로 등록
            UUID uuid = UUID.randomUUID();
            String profileImageFileName = uuid + teacherModifyDto.getProfileImageFile().getOriginalFilename();
            Path path = Paths.get(uploadFolder + profileImageFileName);
            try {
                Files.write(path, teacherModifyDto.getProfileImageFile().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            user.setProfileImageUrl(profileImageFileName);
        }
        principalDetails.setUser(user);

    }
}
