package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.domain.apply.ApplyRepository;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.service.apply.ApplyService;
import hs.lessonReserve.service.lesson.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final ApplyService applyService;

    @GetMapping("/student/mypage")
    public String studentMyPageForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails == null) {
            throw new CustomException("로그인 후 접속 가능합니다.");
        }

        List<Apply> studentMyPageList = applyService.studentMyPageList(principalDetails);
        model.addAttribute("applies", studentMyPageList);

        return "student/studentMyPage";
    }

}
