package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.gather.Gather;
import hs.lessonReserve.service.GatherService;
import hs.lessonReserve.web.dto.gather.GatherApplyDto;
import hs.lessonReserve.web.dto.gather.GatherCreateDto;
import hs.lessonReserve.web.dto.gather.GatherMypageListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/gather/mypage")
    public String gatherMypage(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return "gather/gatherMypage";
    }

    @GetMapping("/gather/chatting/{gatherId}")
    public String gatherChatting(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long gatherId, Model model) {
        gatherService.gatherChatting(principalDetails, gatherId);
        String gatherName = gatherService.gatherName(gatherId);
        model.addAttribute("gatherId", gatherId);
        model.addAttribute("gatherName", gatherName);
        return "gather/gatherChatting";
    }

}
