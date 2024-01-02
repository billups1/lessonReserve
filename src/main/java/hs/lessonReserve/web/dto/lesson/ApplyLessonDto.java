package hs.lessonReserve.web.dto.lesson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.lesson.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyLessonDto {

    private long id;
    private String name;
    private String lessonStartDate;
    private String lessonEndDate;
    private String content;
    private int price;
    private int studentNumber;
    private int maximumStudentsNumber;
    private String roadAddress;
    private long teacherId;
    private String teacherName;
    private String teacherProfileImageUrl;
    @JsonIgnoreProperties({"teacherLessons"})
    private List<ApplyLessonDto> teacherLessons;

    public ApplyLessonDto(Lesson lesson) {
        this.id = lesson.getId();
        this.name = lesson.getName();
        this.lessonStartDate = lesson.getLessonStartDate().toString().substring(0, 10);
        this.lessonEndDate = lesson.getLessonEndDate().toString().substring(0, 10);
        this.content = lesson.getContent();
        this.price = lesson.getPrice();
        this.studentNumber = lesson.getApplies().stream()
                .filter(apply -> ApplyStatus.APPLY.equals(apply.getApplyStatus())).collect(Collectors.toList()).size();
        this.maximumStudentsNumber = lesson.getMaximumStudentsNumber();
        this.roadAddress = lesson.getRoadAddress();
        this.teacherId = lesson.getTeacher().getId();
        this.teacherName = lesson.getTeacher().getName();
        this.teacherProfileImageUrl = lesson.getTeacher().getProfileImageUrl();
        this.teacherLessons = lesson.getTeacher().getLessons().stream().map(teacherLesson ->
                ApplyLessonDto.builder().id(teacherId)
                        .name(teacherLesson.getName())
                        .lessonStartDate(teacherLesson.getLessonStartDate().toString().substring(0, 10))
                        .lessonEndDate(teacherLesson.getLessonEndDate().toString().substring(0, 10))
                        .build()).collect(Collectors.toList());
    }
}
