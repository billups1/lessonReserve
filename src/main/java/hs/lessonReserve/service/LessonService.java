package hs.lessonReserve.service;

import hs.lessonReserve.config.ModelMapperConfig;
import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.lesson.LessonRepository;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.domain.apply.ApplyRepository;
import hs.lessonReserve.domain.user.Teacher;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.web.dto.lesson.HomeLessonListDto;
import hs.lessonReserve.web.dto.lesson.MakeLessonDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ApplyRepository applyRepository;
    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public void makeLesson(MakeLessonDto makeLessonDto, PrincipalDetails principalDetails) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime lessonStartDate = LocalDateTime.parse(makeLessonDto.getLessonStartDate() + " 00:00:00.000", dateTimeFormatter);
        LocalDateTime lessonEndDate = LocalDateTime.parse(makeLessonDto.getLessonEndDate() + " 23:59:59.000", dateTimeFormatter);

        Lesson lesson = Lesson.builder()
                .name(makeLessonDto.getLessonName())
                .content(makeLessonDto.getLessonContent())
                .lessonTime(makeLessonDto.getLessonTime())
                .maximumStudentsNumber(makeLessonDto.getMaximumStudentsNumber())
                .teacher((Teacher) principalDetails.getUser())
                .price(makeLessonDto.getPrice())
                .lessonStartDate(lessonStartDate)
                .lessonEndDate(lessonEndDate)
                .roadAddress(makeLessonDto.getRoadAddress())
                .build();

        lessonRepository.save(lesson);
    }

    @Transactional
    public void deleteLesson(long lessonId, PrincipalDetails principalDetails) {
        List<Apply> applies = applyRepository.findAllByLessonId(lessonId);
        for (Apply apply : applies) {
            apply.setLesson(null);
        }

        long teacherId = principalDetails.getUser().getId();
        System.out.println(lessonId + "/" + teacherId);
        lessonRepository.mDeleteLesson(lessonId, teacherId);
    }

    @Transactional(readOnly = true)
    public Page<HomeLessonListDto> homeLessonList(PrincipalDetails principalDetails, Pageable pageable) {
        Page<Lesson> lessons = lessonRepository.mHomeLessonList(pageable);
        ArrayList<HomeLessonListDto> homeLessonListDtoArrayList = new ArrayList<>();
        for (Lesson lesson : lessons) {
            HomeLessonListDto homeLessonListDto = HomeLessonListDto.builder()
                    .id(lesson.getId())
                    .teacher(lesson.getTeacher())
                    .name(lesson.getName())
                    .maximumStudentsNumber(lesson.getMaximumStudentsNumber())
                    .lessonTime(lesson.getLessonTime())
                    .price(lesson.getPrice())
                    .lessonStartDate(lesson.getLessonStartDate().toString().substring(0, 10))
                    .lessonEndDate(lesson.getLessonEndDate().toString().substring(0, 10))
                    .build();
            homeLessonListDto.setApplyEndDate(lesson.getLessonStartDate().minusDays(3).toString().substring(0,10));
            homeLessonListDto.setApplyStatus(lesson.getApplies().stream().filter(list -> ApplyStatus.APPLY.equals(list.getApplyStatus())).collect(Collectors.toList()).size() + " / " + lesson.getMaximumStudentsNumber());
            List<Apply> applies = lesson.getApplies();
            if (principalDetails != null) {
                for (Apply apply : applies) {
                    if (apply.getStudent().getId() == principalDetails.getUser().getId() && apply.getApplyStatus() == ApplyStatus.APPLY) {
                        homeLessonListDto.setUserApplyStatus(true);
                        break;
                    }
                }
            }
            homeLessonListDtoArrayList.add(homeLessonListDto);
        }

        PageRequest pageRequest = PageRequest.of(lessons.getPageable().getPageNumber(), lessons.getPageable().getPageSize());
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), homeLessonListDtoArrayList.size());
        Page<HomeLessonListDto> homeLessonListDtos = new PageImpl<>(homeLessonListDtoArrayList.subList(start, end), pageRequest, homeLessonListDtoArrayList.size());
        return homeLessonListDtos;
    }

    @Transactional(readOnly = true)
    public List<Lesson> teacherMyPageList(PrincipalDetails principalDetails) {

        long teacherId = principalDetails.getUser().getId();
        List<Lesson> lessons = lessonRepository.mTeacherMyPageList(teacherId);

        for (Lesson lesson : lessons) {
            lesson.setApplyEndDate(lesson.getLessonStartDate().minusDays(3));
            lesson.setApplyStatus(lesson.getApplies().size() + " / " + lesson.getMaximumStudentsNumber());
        }

        return lessons;

    }

    @Transactional(readOnly = true)
    public Lesson applyLessonForm(long lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            throw new CustomException("없는 강의입니다.");
        });

        lesson.setStudentNumber(lesson.getApplies().size());

        return lesson;

    }

    public Lesson findLesson(long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(()->{
            throw new CustomException("없는 강의입니다.");
        });
    }
}
