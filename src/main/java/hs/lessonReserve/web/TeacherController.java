package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.LessonReview.LessonReview;
import hs.lessonReserve.domain.LessonReview.LessonReviewRepository;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.user.Teacher;
import hs.lessonReserve.service.LessonReviewService;
import hs.lessonReserve.service.LessonService;
import hs.lessonReserve.service.UserService;
import hs.lessonReserve.web.dto.auth.StudentModifyDto;
import hs.lessonReserve.web.dto.lesson.HomeLessonListDto;
import hs.lessonReserve.web.dto.teacher.TeacherIntroduceDto;
import hs.lessonReserve.web.dto.teacher.TeacherModifyDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TeacherController {

    private final LessonService lessonService;
    private final UserService userService;
    private final LessonReviewService lessonReviewService;

    @GetMapping("/teacher/mypage")
    public String teacherMyPageForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        // 스프링 시큐리티에서 권한 막아놓아서 따로 권한 막지 않음

        List<HomeLessonListDto> teacherMyPageList = lessonService.teacherMyPageList(principalDetails);

        model.addAttribute("lessons", teacherMyPageList);

        return "teacher/teacherMyPage";
    }

    @GetMapping("/teacher/{teacherId}")
    public String teacherIntroduceForm(@PathVariable long teacherId, Model model) {
        TeacherIntroduceDto teacherIntroduceDto = userService.teacherIntroduceDto(teacherId);

        model.addAttribute("dto", teacherIntroduceDto);

        return "teacher/teacherIntroducePage";
    }

    @GetMapping("/teacher/modify")
    public String teacherModifyForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        TeacherModifyDto teacherModifyDto = userService.teacherModifyDto(principalDetails);
        model.addAttribute("dto", teacherModifyDto);
        return "teacher/modifyTeacher";
    }

    @PostMapping("/teacher/modify")
    public String teacherModify(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid TeacherModifyDto teacherModifyDto, BindingResult bindingResult) {

        userService.teacherModify(principalDetails, teacherModifyDto);
        return "redirect:/teacher/mypage";
    }

}
