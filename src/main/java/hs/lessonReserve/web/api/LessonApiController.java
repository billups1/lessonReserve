package hs.lessonReserve.web.api;

import com.nimbusds.jose.shaded.gson.JsonObject;
import hs.lessonReserve.config.auth.PrincipalDetails;
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
    public ResponseEntity lessonPayment(String imp_uid, String merchant_uid) {
        System.out.println("imp_uid = " + imp_uid);
        System.out.println("merchant_uid = " + merchant_uid);
        System.out.println("결제 완료");

        return new ResponseEntity<>(new CMRespDto<>(1, "결제 완료", 100), HttpStatus.OK);
    }


    @PostMapping("/api/lesson/apply/paymentComplete")
    public ResponseEntity lessonPaymentComplete(String orderNum, String productId, String userId, String totalPrice, String imp_uid) {
        System.out.println("orderNum = " + orderNum);
        System.out.println("imp_uid = " + imp_uid);
        System.out.println("주문정보 저장 성공");

        return new ResponseEntity<>(new CMRespDto<>(1, "결제 정보 저장" +
                " 완료", null), HttpStatus.OK);
    }

}
