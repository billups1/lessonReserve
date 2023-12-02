package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.lesson.LessonRepository;
import hs.lessonReserve.service.lesson.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final LessonService lessonService;
    private final LessonRepository lessonRepository;

    @GetMapping({"/", "/home"})
    public String homeForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<Lesson> homeLessonList = lessonService.homeLessonList(principalDetails);
        model.addAttribute("lessons", homeLessonList);

        return "home/home";
    }

}
