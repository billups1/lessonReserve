package hs.lessonReserve.web.dto.admin;

import com.querydsl.core.annotations.QueryProjection;
import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.lesson.QLesson;
import hs.lessonReserve.util.CustomFormatter;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Data
public class AdminLessonListDto {
    private long id;
    private String lessonName;
    private String teacherName;
    private String lessonCreateTime;
    private String lessonStudentCount;
    private String lessonProgressStatus;
    private String price;
    private String lessonStartDate;
    private String lessonEndDate;

    @QueryProjection
    public AdminLessonListDto(Lesson lesson) {
        this.id = lesson.getId();
        this.lessonName = lesson.getName();
        this.teacherName = lesson.getTeacher().getName();
        this.lessonCreateTime = CustomFormatter.make_yyyyMMdd(lesson.getCreateTime());
        this.lessonStudentCount = lesson.getApplies().stream()
                .filter(a -> ApplyStatus.APPLY.equals(a.getApplyStatus()))
                .filter(a -> a.getStudent() != null)
                .collect(Collectors.toList()).size() + " / " + lesson.getMaximumStudentsNumber();
        if (LocalDateTime.now().isBefore(lesson.getLessonStartDate())) {
            this.lessonProgressStatus = "시작전";
        } else if (LocalDateTime.now().isBefore(lesson.getLessonEndDate()) || LocalDateTime.now().isEqual(lesson.getLessonEndDate())) {
            this.lessonProgressStatus = "진행중";
        } else {
            this.lessonProgressStatus = "종료";
        }
        this.price = CustomFormatter.makePrice(lesson.getPrice());
        this.lessonStartDate = CustomFormatter.make_yyyyMMdd(lesson.getLessonStartDate());
        this.lessonEndDate = CustomFormatter.make_yyyyMMdd((lesson.getLessonEndDate()));
    }

}
