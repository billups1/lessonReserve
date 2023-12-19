package hs.lessonReserve.web.api;

import hs.lessonReserve.handler.ex.CustomApiException;
import hs.lessonReserve.service.EmailService;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailApiController {

    private final EmailService emailService;

    @GetMapping("/api/mail/verificationEmailSend")
    public ResponseEntity verificationEmailSend(String email) {
        emailService.sendValidationMail(email);
        return new ResponseEntity<>(new CMRespDto<>(1,"인증 이메일 발송 성공", null), HttpStatus.OK);
    }

    @GetMapping("/api/mail/emailVerification")
    public ResponseEntity emailCodeVerification(String email, String code) {
        boolean codeVerificationState = emailService.emailCodeVerification(email, code);
        if (codeVerificationState == true) {
            return new ResponseEntity<>(new CMRespDto<>(1, "이메일 인증 성공", null), HttpStatus.OK);
        } else {
            throw new CustomApiException("인증코드가 맞지 않습니다.");
        }
    }

}
