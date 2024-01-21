package hs.lessonReserve.web.dto.admin;

import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.util.CustomFormatter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminLessonDto {

    private String lessonName;
    private String lessonContent;
    private int maximumStudentsNumber;
    private int price;
    private String lessonTime;
    private String lessonStartDate;
    private String lessonEndDate;
    private String postcode;
    private String roadAddress;
    private String jibunAddress;
    private String detailAddress;
    private String extraAddress;


    public AdminLessonDto(Lesson lesson) {
        this.lessonName = lesson.getName();
        this.lessonContent = lesson.getContent();
        this.maximumStudentsNumber = lesson.getMaximumStudentsNumber();
        this.price = lesson.getPrice();
        this.lessonTime = lesson.getLessonTime();
        this.lessonStartDate = CustomFormatter.make_yyyyMMdd(lesson.getLessonStartDate());
        this.lessonEndDate = CustomFormatter.make_yyyyMMdd(lesson.getLessonEndDate());
        this.postcode = null;
        this.roadAddress = lesson.getRoadAddress();
        this.jibunAddress = null;
        this.detailAddress = null;
        this.extraAddress = null;
    }
}
