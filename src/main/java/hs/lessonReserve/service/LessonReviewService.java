package hs.lessonReserve.service;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.LessonReview.LessonReview;
import hs.lessonReserve.domain.LessonReview.LessonReviewRepository;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.domain.apply.ApplyRepository;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.user.Student;
import hs.lessonReserve.handler.ex.CustomApiException;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.web.dto.admin.AdminLessonReviewDto;
import hs.lessonReserve.web.dto.lessonReview.LessonReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LessonReviewService {

    private final LessonReviewRepository lessonReviewRepository;
    private final ApplyRepository applyRepository;

    @Transactional
    public void makeReview(long applyId, LessonReviewDto lessonReviewDto, PrincipalDetails principalDetails) {
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> {
            throw new CustomException("없는 수강신청입니다.");
        });

        Lesson lesson = new Lesson();
        lesson.setId(apply.getLesson().getId());

        LessonReview lessonReview = LessonReview.builder()
                .lesson(lesson)
                .student((Student) principalDetails.getUser())
                .score(lessonReviewDto.getScore())
                .content(lessonReviewDto.getContent())
                .build();

        apply.setApplyStatus(ApplyStatus.REVIEWCOMPLETED);

        lessonReviewRepository.save(lessonReview);

    }

    @Transactional(readOnly = true)
    public Page<AdminLessonReviewDto> adminLessonReviewDtosByLessonId(long lessonId, Pageable pageable) {
        Page<LessonReview> lessonReviewPage = lessonReviewRepository.findAllByLessonIdOrderByIdDesc(lessonId, pageable);
        Page<AdminLessonReviewDto> adminLessonReviewDtos = lessonReviewPage.map(lessonReview -> {
            return new AdminLessonReviewDto(lessonReview);
        });

        return adminLessonReviewDtos;
    }

    @Transactional(readOnly = true)
    public Page<AdminLessonReviewDto> adminLessonReviewDtosBystudentId(Long studentId, Pageable pageable) {
        Page<LessonReview> lessonReviewPage = lessonReviewRepository.findAllByStudentIdOrderByIdDesc(studentId, pageable);
        Page<AdminLessonReviewDto> adminLessonReviewDtos = lessonReviewPage.map(lessonReview -> {
            return new AdminLessonReviewDto(lessonReview);
        });
        return adminLessonReviewDtos;
    }

    public Page<AdminLessonReviewDto> adminLessonReviewDtosByTeacherId(Long teacherId, Pageable pageable) {
        Page<LessonReview> lessonReviewPage = lessonReviewRepository.findAllByTeacherIdOrderByIdDesc(teacherId, pageable);
        Page<AdminLessonReviewDto> adminLessonReviewDtos = lessonReviewPage.map(lessonReview -> {
            return new AdminLessonReviewDto(lessonReview);
        });
        return adminLessonReviewDtos;
    }

    @Transactional
    public void lessonReviewDelete(PrincipalDetails principalDetails, long lessonReviewId) {

        // ADMIN일 경우에만 삭제 가능하도록
        if (!principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
            throw new CustomApiException("삭제 권한이 없습니다.");
        }

        lessonReviewRepository.deleteById(lessonReviewId);
    }


}
