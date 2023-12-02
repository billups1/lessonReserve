package hs.lessonReserve.service.lesson;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.lesson.LessonRepository;
import hs.lessonReserve.domain.lessonStudent.LessonStudent;
import hs.lessonReserve.domain.lessonStudent.LessonStudentRepository;
import hs.lessonReserve.domain.user.Student;
import hs.lessonReserve.domain.user.Teacher;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.web.dto.lesson.MakeLessonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonStudentRepository lessonStudentRepository;

    @Transactional
    public void makeLesson(MakeLessonDto makeLessonDto, PrincipalDetails principalDetails) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime lessonStartDate = LocalDateTime.parse(makeLessonDto.getLessonStartDate()+" 00:00:00.000", dateTimeFormatter);
        LocalDateTime lessonEndDate = LocalDateTime.parse(makeLessonDto.getLessonEndDate()+" 23:59:59.000", dateTimeFormatter);

        Lesson lesson = Lesson.builder()
                .name(makeLessonDto.getLessonName())
                .content(makeLessonDto.getLessonContent())
                .lessonTime(makeLessonDto.getLessonTime())
                .maximumStudentsNumber(makeLessonDto.getMaximumStudentsNumber())
                .teacher((Teacher) principalDetails.getUser())
                .price(makeLessonDto.getPrice())
                .lessonStartDate(lessonStartDate)
                .lessonEndDate(lessonEndDate)
                .build();

        lessonRepository.save(lesson);
    }

    @Transactional
    public void applyLesson(long lessonId, PrincipalDetails principalDetails) {

        if (principalDetails == null) {
            throw new CustomException("로그인 후 수강신청해 주세요.");
        }

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            throw new CustomException("없는 강의입니다.");
        });

        if (lesson.getStudents().size() >= lesson.getMaximumStudentsNumber()) {
            throw new CustomException("수강인원이 모두 찼습니다.");
        }

        LessonStudent lessonStudent = LessonStudent.builder()
                .lesson(lesson)
                .student(principalDetails.getUser())
                .build();

        lessonStudentRepository.save(lessonStudent);

    }

    @Transactional(readOnly = true)
    public List<Lesson> homeLessonList(PrincipalDetails principalDetails) {
        List<Lesson> lessons = lessonRepository.homeLessonList();
        for (Lesson lesson : lessons) {
            lesson.setApplyEndDate(lesson.getLessonStartDate().minusDays(3));
            lesson.setApplyStatus(lesson.getStudents().size() + " / " + lesson.getMaximumStudentsNumber());
            List<LessonStudent> lessonStudents = lesson.getStudents();
            if (principalDetails != null) {
                for (LessonStudent lessonStudent : lessonStudents) {
                    if (lessonStudent.getStudent().getId() == principalDetails.getUser().getId()) {
                        lesson.setUserApplyStatus(true);
                        break;
                    }
                }
            }
        }
        return lessons;
    }


    public Lesson applyLessonForm(long lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            throw new CustomException("없는 강의입니다.");
        });

        lesson.setStudentNumber(lesson.getStudents().size());

        return lesson;

    }
}
