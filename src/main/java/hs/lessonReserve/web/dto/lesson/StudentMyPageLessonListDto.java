package hs.lessonReserve.web.dto.lesson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.user.Teacher;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StudentMyPageLessonListDto {

    private long applyId;
    @JsonIgnoreProperties({"lessons", "certificates"})
    private Teacher teacher;

    private String name;
    private int maximumStudentsNumber;
    private String lessonTime;
    private int price;

    private String lessonStartDate;
    private String lessonEndDate;
    private String applyEndDate;
    private String applyCreateTime;

    private String applyStatus;
    private ApplyStatus userApplyStatus;
    private long lessonId;

}
