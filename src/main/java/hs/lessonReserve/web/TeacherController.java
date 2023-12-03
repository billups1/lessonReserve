package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.service.lesson.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TeacherController {

    private final LessonService lessonService;

    @GetMapping("/teacher/mypage")
    public String teacherMyPageForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        // 스프링 시큐리티에서 권한 막아놓아서 따로 권한 막지 않음

        List<Lesson> teacherMyPageList = lessonService.teacherMyPageList(principalDetails);

        model.addAttribute("lessons", teacherMyPageList);

        return "teacher/teacherMyPage";
    }

}
