package hs.lessonReserve.handler.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("login fail handler ★: " + exception.toString());
        String errorMassage;
        if (exception instanceof BadCredentialsException) {
            errorMassage = "아이디 또는 비밀번호가 맞지 않습니다.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMassage = "아이디 또는 비밀번호가 맞지 않습니다.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMassage = "아이디 또는 비밀번호가 맞지 않습니다.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMassage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else {
            errorMassage = "알 수 없는 이유로 로그인에 실패했습니다. 관리자에게 문의하세요.";
        }

//        request.setAttribute("errorMassage", errorMassage);
        response.sendRedirect("/login?errorMassage="+ URLEncoder.encode(errorMassage, "UTF-8"));

    }
}
