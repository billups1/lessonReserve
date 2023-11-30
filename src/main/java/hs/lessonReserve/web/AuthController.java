package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.service.user.UserService;
import hs.lessonReserve.web.dto.auth.UserJoinDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/join/student")
    public String joinStudentForm(HttpServletRequest request) {
        return "auth/joinStudent";
    }

    @PostMapping("/join/student")
    public String joinStudent(UserJoinDto UserJoinDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.joinStudent(UserJoinDto);

        return "redirect:/login/student";
    }

    @GetMapping("/join/teacher")
    public String joinTeacherForm() {

        return "auth/joinTeacher";
    }

    @PostMapping("/join/teacher")
    public String joinTeacher(UserJoinDto UserJoinDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.joinTeacher(UserJoinDto);

        return "redirect:/login/teacher";
    }

    @GetMapping("/login/student")
    public String loginStudentForm() {
        return "auth/loginStudent";
    }

    @GetMapping("/login/teacher")
    public String loginTeacherForm() {
        return "auth/loginTeacher";
    }

}
