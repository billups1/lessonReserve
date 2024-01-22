package hs.lessonReserve.web.dto.admin;

import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.util.CustomFormatter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminLessonDto {

    private Long lessonId; //레슨 번호
    private String lessonName; //레슨명
    private String lessonContent; //레슨 설명
    private int maximumStudentsNumber;
    private int price; // 가격
    private String lessonTime; // 레슨 시간
    private String lessonStartDate; // 레슨 시작일
    private String lessonEndDate; // 레슨 종료일
    private String postcode;
    private String roadAddress; // 레슨 장소(주소)
    private String jibunAddress;
    private String detailAddress;
    private String extraAddress;
    private String createTime;


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
        this.createTime = CustomFormatter.make_yyyyMMdd(lesson.getCreateTime());
    }
}
