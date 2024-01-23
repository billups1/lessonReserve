package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.service.GatherService;
import hs.lessonReserve.service.LessonService;
import hs.lessonReserve.web.dto.gather.GatherListDto;
import hs.lessonReserve.web.dto.lesson.HomeLessonListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final LessonService lessonService;
    private final GatherService gatherService;

    @GetMapping({"/", "/home"})
    public String homeForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<HomeLessonListDto> homeLessonListDtos = lessonService.homeLessonList();
        List<GatherListDto> gatherListDtos = gatherService.gatherList(principalDetails);
        List<GatherListDto> gatherListDtoList = gatherListDtos.stream().sorted(Comparator.comparing(GatherListDto::getId).reversed()).limit(5).collect(Collectors.toList());

        model.addAttribute("lessonDtos", homeLessonListDtos);
        model.addAttribute("gatherDtos", gatherListDtoList);
        return "home/home";
    }

}
