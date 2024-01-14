package hs.lessonReserve.web.dto.lesson;

import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.payment.Payment;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.util.CustomFormatter;
import lombok.Data;

@Data
public class LessonPaymentCancelFormDto {

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
    private long paymentId;

    public LessonPaymentCancelFormDto(User user, Payment payment) {
        this.teacherName = payment.getLesson().getTeacher().getName();
        this.teacherProfileImageUrl = payment.getLesson().getTeacher().getProfileImageUrl();
        this.lessonId = payment.getLesson().getId();
        this.lessonName = payment.getLesson().getName();
        this.lessonRoadAddress = payment.getLesson().getRoadAddress();
        this.lessonStartDate = CustomFormatter.make_yyyyMMdd(payment.getLesson().getLessonStartDate());
        this.lessonEndDate = CustomFormatter.make_yyyyMMdd(payment.getLesson().getLessonEndDate());
        this.lessonPrice = CustomFormatter.makePrice(payment.getLesson().getPrice()) + "원(부가세 포함)";
        this.userEmail = user.getEmail();
        this.userName = user.getName();
        this.userTel = user.getPhone();
        this.userAddress = user.getAddress();
        this.userPostcode = user.getPostcode();
        this.paymentId = payment.getId();
    }
}
