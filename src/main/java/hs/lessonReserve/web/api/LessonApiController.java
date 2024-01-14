package hs.lessonReserve.web.api;

import com.nimbusds.jose.shaded.gson.JsonObject;
import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.payment.Payment;
import hs.lessonReserve.service.LessonService;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import hs.lessonReserve.web.dto.lesson.HomeLessonListDto;
import hs.lessonReserve.web.dto.lesson.LessonSearchCondDto;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LessonApiController {

    private final LessonService lessonService;

    @DeleteMapping("/api/teacher/lesson/{lessonId}")
    public ResponseEntity deleteLesson(@PathVariable long lessonId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        lessonService.deleteLesson(lessonId, principalDetails);

        return new ResponseEntity<>(new CMRespDto<>(1, "강의삭제 완료", null), HttpStatus.OK);
    }

    @GetMapping("/api/lesson/home")
    public ResponseEntity homeLessonList(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @AuthenticationPrincipal PrincipalDetails principalDetails, LessonSearchCondDto lessonSearchCondDto) {
        System.out.println("lessonSearchCondDto = " + lessonSearchCondDto);
        Page<HomeLessonListDto> lessons = lessonService.homeLessonList(principalDetails, lessonSearchCondDto, pageable);
        return new ResponseEntity<>(new CMRespDto<>(1,"홈 레슨리스트 불러오기 완료", lessons), HttpStatus.OK);
    }

    @PostMapping("/api/lesson/apply/payment")
    public ResponseEntity lessonPayment(@AuthenticationPrincipal PrincipalDetails principalDetails, String imp_uid, String merchant_uid, int totalPrice, long lessonId, String pay_method, String pg_provider,
                                        boolean lessonPolicyAgree, boolean pgPolicyAgree) {
        System.out.println("imp_uid = " + imp_uid);
        System.out.println("merchant_uid = " + merchant_uid);
        System.out.println("결제 완료");

        long paymentId = lessonService.paymentValidateAndSave(principalDetails, imp_uid, merchant_uid, totalPrice, lessonId, pay_method, pg_provider, lessonPolicyAgree, pgPolicyAgree);

        return new ResponseEntity<>(new CMRespDto<>(1, "결제 완료", paymentId), HttpStatus.OK);
    }

    @PostMapping("/api/lesson/payment/cancel")
    public ResponseEntity lessonPaymentCancel(@AuthenticationPrincipal PrincipalDetails principalDetails, long paymentId) {
        Payment payment = lessonService.lessonCancel(principalDetails, paymentId);
        return new ResponseEntity<>(new CMRespDto(1, "결제취소 완료", null), HttpStatus.OK);
    }

}
