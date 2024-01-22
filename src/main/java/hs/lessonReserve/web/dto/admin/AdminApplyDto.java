package hs.lessonReserve.web.dto.admin;

import com.querydsl.core.annotations.QueryProjection;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.util.CustomFormatter;
import lombok.Data;

@Data
public class AdminApplyDto {

    private Long applyId;
    private Long paymentId;

    private String studentName;
    private Long studentId;
    private String teacherName;
    private Long teacherId;

    private String lessonName;
    private Long lessonId;

    private String price;
    private String impUid;
    private String merchantUid;

    private String paymentMethod; // card, bankTransfer,...
    private String paymentGateway; // kakao, danal,...
    private Boolean lessonPolicyAgree;
    private Boolean pgPolicyAgree;

    private String applyStatus;
    private String paymentStatus;
    private String cancelTime;

    private String paymentCreateTime;

    @QueryProjection
    public AdminApplyDto(Apply apply) {
        this.applyId = apply.getId();
        System.out.println(apply.getId());
        this.paymentId = apply.getPayment() == null ? null : apply.getPayment().getId();

        this.studentName = apply.getStudent() == null ? "(탈퇴한 회원)" : apply.getStudent().getName();
        this.studentId = apply.getStudent() == null ? null : apply.getStudent().getId();

        this.teacherName = apply.getLesson().getTeacher().getName();
        this.teacherId = apply.getLesson().getTeacher().getId();

        this.lessonName = apply.getLesson().getName();
        this.lessonId = apply.getLesson().getId();
        this.price = apply.getPayment() == null ? null : CustomFormatter.makePrice(apply.getPayment().getPrice());
        this.impUid = apply.getPayment() == null ? null : apply.getPayment().getImpUid();
        this.merchantUid = apply.getPayment() == null ? null : apply.getPayment().getMerchantUid();
        this.paymentMethod = apply.getPayment() == null ? null : apply.getPayment().getPaymentMethod();
        this.paymentGateway = apply.getPayment() == null ? null : apply.getPayment().getPaymentGateway();
        this.lessonPolicyAgree = apply.getPayment() == null ? null : apply.getPayment().isLessonPolicyAgree();
        this.pgPolicyAgree = apply.getPayment() == null ? null : apply.getPayment().isPgPolicyAgree();
        this.applyStatus = apply.getApplyStatus() == null ? "-" : apply.getApplyStatus().toString();
        this.paymentStatus = apply.getPayment() == null ? null : apply.getPayment().getStatus();
        this.cancelTime = apply.getPayment() == null ? "-" : CustomFormatter.make_yyyyMMddHHmmss(apply.getPayment().getCancelTime());
        this.paymentCreateTime = apply.getPayment() == null ? "-" : CustomFormatter.make_yyyyMMddHHmmss(apply.getPayment().getCreateTime());
    }
}
