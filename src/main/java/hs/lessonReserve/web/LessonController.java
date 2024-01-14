package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.payment.Payment;
import hs.lessonReserve.service.ApplyService;
import hs.lessonReserve.service.LessonService;
import hs.lessonReserve.service.PaymentService;
import hs.lessonReserve.web.dto.lesson.ApplyLessonDto;
import hs.lessonReserve.web.dto.lesson.LessonPaymentCancelFormDto;
import hs.lessonReserve.web.dto.lesson.LessonPaymentFormDto;
import hs.lessonReserve.web.dto.lesson.MakeLessonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final ApplyService applyService;
    private final PaymentService paymentService;

    @GetMapping("/teacher/lesson/create")
    public String makeLessonFrom() {

        return "lesson/makeLesson";
    }

    @PostMapping("/teacher/lesson/create")
    public String makeLesson(@Validated MakeLessonDto makeLessonDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(makeLessonDto.toString());
        lessonService.makeLesson(makeLessonDto, principalDetails);

        return "redirect:/";
    }

    @GetMapping("/lesson/{lessonId}")
    public String applyLessonForm(@PathVariable long lessonId, Model model) {

        ApplyLessonDto applyLessonDto = lessonService.applyLessonForm(lessonId);

        model.addAttribute("lesson", applyLessonDto);

        return "lesson/applyLesson";
    }

    @PostMapping("/lesson/{lessonId}")
    public String applyLesson(@PathVariable long lessonId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        applyService.applyAvailabilityValidate(lessonId, principalDetails);

        return "redirect:/lesson/payment/"+lessonId;
    }

    @GetMapping("/lesson/payment/{lessonId}") // 레슨id 추가
    public String lessonPayment(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long lessonId, Model model) {
        LessonPaymentFormDto lessonPaymentFormDto = lessonService.lessonPaymentFormDto(principalDetails, lessonId);
        model.addAttribute("dto", lessonPaymentFormDto);
        return "lesson/lessonPayment";
    }

    @GetMapping("/lesson/lessonPolicy")
    public String lessonPolicy() {
        return "lesson/lessonPolicy";
    }

    @GetMapping("/lesson/pgCompanyPolicy")
    public String pgCompanyPolicy() {
        return "lesson/pgCompanyPolicy";
    }

    @GetMapping("/lesson/paymentComplete/{paymentId}")
    public String lessonPaymentComplete(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long paymentId, Model model) {
        String paymentNumber = paymentService.paymentName(principalDetails, paymentId);
        model.addAttribute("paymentNumber", paymentNumber);
        return "lesson/lessonPaymentComplete";
    }

    @GetMapping("/lesson/cancel/{paymentId}")
    public String lessonCancel(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long paymentId, Model model) {
        LessonPaymentCancelFormDto lessonPaymentCancelFormDto = lessonService.lessonPaymentCancelFormDto(principalDetails, paymentId);
        model.addAttribute("dto", lessonPaymentCancelFormDto);
        return "lesson/cancel/lessonCancel";
    }

    @GetMapping("/lesson/cancelComplete/{applyId}")
    public String lessonCancelComplete(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long applyId, Model model) {

        return "lesson/lessonPaymentComplete";
    }

}
