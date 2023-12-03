package hs.lessonReserve.web.dto.lesson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.user.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class HomeLessonListDto {

    @JsonIgnoreProperties({"lessons", "certificates"})
    private Teacher teacher;

    private String name;
    private String content;
    private int maximumStudentsNumber;
    private String lessonTime;
    private int price;

    private String lessonStartDate;
    private String lessonEndDate;
    private String applyEndDate;
    private String applyStatus;
    private boolean userApplyStatus;



}
