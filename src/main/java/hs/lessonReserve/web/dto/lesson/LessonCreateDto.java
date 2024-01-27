package hs.lessonReserve.web.dto.lesson;

import lombok.Data;

@Data
public class LessonCreateDto {

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


}
