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

    @GetMapping("/student/join")
    public String joinStudentForm(HttpServletRequest request) {
        return "auth/joinStudent";
    }

    @PostMapping("/student/join")
    public String joinStudent(UserJoinDto UserJoinDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.joinStudent(UserJoinDto);

        return "redirect:/";
    }

    @GetMapping("/teacher/join")
    public String joinTeacherForm() {

        return "auth/joinTeacher";
    }

    @PostMapping("/teacher/join")
    public String joinTeacher(UserJoinDto UserJoinDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.joinTeacher(UserJoinDto);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

//    @GetMapping("/login/teacher")
//    public String loginTeacherForm() {
//        return "auth/loginTeacher";
//    }

}
