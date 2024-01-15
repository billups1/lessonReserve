package hs.lessonReserve.web.dto.lesson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.user.Teacher;
import hs.lessonReserve.util.CustomFormatter;
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
    private String lessonName;
    private String teacherName;
    private String teacherProfileImageUrl;
    private String lessonTime;
    private String price;
    private String lessonStartDate;
    private String lessonEndDate;
    private String applyEndDate;
    private String applyCreateTime;
    private String applyStatus;
    private long lessonId;
    private long paymentId;

    public StudentMyPageLessonListDto(Object[] objects) {
        this.applyId = (long) objects[0];
        this.lessonName = (String) objects[1];
        this.teacherName = (String) objects[2];
        this.teacherProfileImageUrl = (String) objects[3];
        this.lessonTime = (String) objects[4];
        this.price = CustomFormatter.makePrice((int)objects[5]) + "원";
        this.lessonStartDate = (String) objects[6];
        this.lessonEndDate = (String) objects[7];
        this.applyEndDate = (String) objects[8];
        this.applyCreateTime = (String) objects[9];
        this.applyStatus = (String) objects[10];
        this.lessonId = (long) objects[11];
        this.paymentId = objects[12] != null ? (long) objects[12] : 0;
    }
}
