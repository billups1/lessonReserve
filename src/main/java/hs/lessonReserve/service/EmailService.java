package hs.lessonReserve.service;

import hs.lessonReserve.handler.ex.CustomApiException;
import hs.lessonReserve.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    public void sendValidationMail(String toEmail) {
        Random random = new Random();
        int ValidationCode = random.nextInt(900000) +100000;
        String subject = "[LessonReserve] 회원가입 인증메일";
        String body = "";
        body += "<h3>" + "LessonReserve 회원가입 메일 인증번호입니다." + "</h3>";
        body += "<h1> [" + ValidationCode + "]</h1>";
        body += "<h3>" + "감사합니다." + "</h3>";

        MimeMessage emailForm = createEmailForm(toEmail, subject, body);
        redisUtil.setData(toEmail, Integer.toString(ValidationCode), Duration.ofSeconds(300));

        javaMailSender.send(emailForm);
    }

    public String sendPasswordForgetMail(String toEmail, String name) {
        String newPassword = UUID.randomUUID().toString().substring(0,8);

        String subject = "[LessonReserve] 비밀번호 재설정";
        String body = "";
        body += "<h3>" + "안녕하세요. " + name + " 님! </h3>";
        body += "<h3>" + "LessonReserve 계정 비밀번호를 재설정하였습니다." + "</h3>";
        body += "<h3>" + "아래 신규 비밀번호를 통해 로그인하여 주시고," + "</h3>";
        body += "<h3>" + "즉시 원하시는 비밀번호로 변경하여 주시기 바랍니다." + "</h3>";
        body += "<h1> [" + newPassword + "]</h1>";
        body += "<h3>" + "감사합니다." + "</h3>";

        MimeMessage emailForm = createEmailForm(toEmail, subject, body);

        javaMailSender.send(emailForm);

        return newPassword;
    }

    private MimeMessage createEmailForm(String toEmail, String subject, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);
        } catch (MessagingException e) {
            throw new CustomApiException("메일 발송에 실패했습니다.", e);
        }
        return mimeMessage;
    }

    public boolean emailCodeVerification(String email, String inputCode) {
        String savedCode = redisUtil.getData(email);
        if (savedCode.equals(inputCode)) {
            return true;
        } else {
            return false;
        }
    }
}
