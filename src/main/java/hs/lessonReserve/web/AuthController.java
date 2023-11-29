package hs.lessonReserve.web;

import hs.lessonReserve.service.user.UserService;
import hs.lessonReserve.web.dto.auth.UserJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/join/User")
    public String joinUserForm() {

        return "auth/joinUser";
    }

    @PostMapping("/join/User")
    public String joinUser(UserJoinDto UserJoinDto) {
        userService.join(UserJoinDto);
        return "redirect:/login";
    }

    @GetMapping("/join/teacher")
    public String joinTeacherForm() {

        return "auth/joinTeacher";
    }

}
