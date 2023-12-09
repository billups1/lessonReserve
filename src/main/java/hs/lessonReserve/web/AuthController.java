package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.service.UserService;
import hs.lessonReserve.web.dto.auth.UserJoinDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/student/join")
    public String joinStudentForm(HttpServletRequest request) {
        return "auth/joinStudent";
    }

    @PostMapping("/student/join")
    public String joinStudent(@Validated UserJoinDto UserJoinDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.joinStudent(UserJoinDto);

        return "redirect:/";
    }

    @GetMapping("/teacher/join")
    public String joinTeacherForm() {

        return "auth/joinTeacher";
    }

    @PostMapping("/teacher/join")
    public String joinTeacher(@Validated UserJoinDto UserJoinDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.joinTeacher(UserJoinDto);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String errorMassage, Model model) {
        model.addAttribute("errorMassage", errorMassage);
        return "auth/login";
    }

}
