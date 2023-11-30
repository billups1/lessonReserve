package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.lesson.LessonRepository;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.service.lesson.LessonService;
import hs.lessonReserve.web.dto.lesson.MakeLessonDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LessonController {

    private final LessonRepository lessonRepository;
    private final LessonService lessonService;

    @GetMapping("/lesson/create")
    public String makeLessonFrom() {

        return "lesson/makeLesson";
    }

    @PostMapping("/lesson/create")
    public String makeLesson(MakeLessonDto makeLessonDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(makeLessonDto.toString());
        lessonService.makeLesson(makeLessonDto, principalDetails);

        return "redirect:/";
    }

    @GetMapping("lesson/{lessonId}")
    public String applyLessonForm(@PathVariable long lessonId, Model model) {

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            throw new CustomException("없는 강의입니다.");
        });

        model.addAttribute("lesson", lesson);

        return "lesson/applyLesson";
    }

    @PostMapping("lesson/{lessonId}")
    public String applyLesson(@PathVariable long lessonId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        lessonService.applyLesson(lessonId, principalDetails);

        return "redirect:/";
    }


}
