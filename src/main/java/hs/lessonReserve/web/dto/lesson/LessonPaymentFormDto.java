package hs.lessonReserve.web.dto.lesson;

import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.util.CustomFormatter;
import lombok.Data;

@Data
public class LessonPaymentFormDto {

    private String teacherName;
    private String teacherProfileImageUrl;
    private long lessonId;
    private String lessonName;
    private String lessonRoadAddress;
    private String lessonStartDate;
    private String lessonEndDate;
    private String lessonPrice;
    private String userEmail;
    private String userName;
    private String userTel;
    private String userAddress;
    private String userPostcode;

    public LessonPaymentFormDto(User user, Lesson lesson) {
        this.teacherName = lesson.getTeacher().getName();
        this.teacherProfileImageUrl = lesson.getTeacher().getProfileImageUrl();
        this.lessonId = lesson.getId();
        this.lessonName = lesson.getName();
        this.lessonRoadAddress = lesson.getRoadAddress();
        this.lessonStartDate = CustomFormatter.make_yyyyMMdd(lesson.getLessonStartDate());
        this.lessonEndDate = CustomFormatter.make_yyyyMMdd(lesson.getLessonEndDate());
        this.lessonPrice = CustomFormatter.makePrice(lesson.getPrice()) + "(부가세 포함)";
        this.userEmail = user.getEmail();
        this.userName = user.getName();
        this.userTel = user.getPhone();
        this.userAddress = user.getAddress();
        this.userPostcode = user.getPostcode();
    }
}
