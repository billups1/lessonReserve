package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.gather.Gather;
import hs.lessonReserve.service.GatherService;
import hs.lessonReserve.web.dto.gather.GatherApplyDto;
import hs.lessonReserve.web.dto.gather.GatherCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class GatherController {

    private final GatherService gatherService;

    @GetMapping("/gather")
    public String gatherHome() {

        return "gather/gather";
    }

    @PostMapping("/gather")
    public String gatherApply(GatherApplyDto gatherApplyDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(gatherApplyDto);
        gatherService.gatherApply(gatherApplyDto, principalDetails);
        return "redirect:/gather";
    }

    @GetMapping("/gather/create")
    public String gatherCreateForm() {
        return "gather/gatherCreate";
    }

    @PostMapping("/gather/create")
    public String gatherCreate(GatherCreateDto gatherCreateDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        gatherService.gatherCreate(gatherCreateDto, principalDetails);
        return "redirect:/gather";
    }

}
