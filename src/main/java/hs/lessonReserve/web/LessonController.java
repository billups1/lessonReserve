package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.service.ApplyService;
import hs.lessonReserve.service.LessonService;
import hs.lessonReserve.web.dto.lesson.ApplyLessonDto;
import hs.lessonReserve.web.dto.lesson.MakeLessonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final ApplyService applyService;

    @GetMapping("/teacher/lesson/create")
    public String makeLessonFrom() {

        return "lesson/makeLesson";
    }

    @PostMapping("/teacher/lesson/create")
    public String makeLesson(@Validated MakeLessonDto makeLessonDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(makeLessonDto.toString());
        lessonService.makeLesson(makeLessonDto, principalDetails);

        return "redirect:/";
    }

    @GetMapping("/lesson/{lessonId}")
    public String applyLessonForm(@PathVariable long lessonId, Model model) {

        ApplyLessonDto applyLessonDto = lessonService.applyLessonForm(lessonId);

        model.addAttribute("lesson", applyLessonDto);

        return "lesson/applyLesson";
    }

    @PostMapping("/lesson/{lessonId}")
    public String applyLesson(@PathVariable long lessonId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        applyService.makeApply(lessonId, principalDetails);

        return "redirect:/";
    }

}
