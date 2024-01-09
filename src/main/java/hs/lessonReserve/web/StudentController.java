package hs.lessonReserve.web;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.handler.ex.CustomApiException;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.service.ApplyService;
import hs.lessonReserve.service.LessonReviewService;
import hs.lessonReserve.service.UserService;
import hs.lessonReserve.web.dto.auth.StudentModifyDto;
import hs.lessonReserve.web.dto.lessonReview.LessonReviewDto;
import hs.lessonReserve.web.dto.lesson.StudentMyPageLessonListDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final ApplyService applyService;
    private final LessonReviewService lessonReviewService;
    private final UserService userService;

    @GetMapping("/student/mypage")
    public String studentMyPageForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails == null) {
            throw new CustomException("로그인 후 접속 가능합니다.");
        }

        List<StudentMyPageLessonListDto> studentMyPageList = applyService.studentMyPageList(principalDetails);
        model.addAttribute("dtos", studentMyPageList);

        return "student/studentMyPage";
    }

    @GetMapping("/student/lesson/review/{applyId}")
    public String studentLessonReviewForm(@PathVariable long applyId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Apply apply = applyService.findApply(applyId);

        model.addAttribute("apply", apply);
        return "student/LessonReviewForm";
    }

    @PostMapping("/student/LessonReview/{applyId}")
    public String review(@PathVariable long applyId, @Validated LessonReviewDto lessonReviewDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) throws URISyntaxException {
        if (lessonReviewDto.getScore() >5 || lessonReviewDto.getScore() <0.5) {
            throw new CustomApiException("평점은 0.5부터 5 사이로 입력해 주세요");
        }

        lessonReviewService.makeReview(applyId, lessonReviewDto, principalDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/student/mypage"));

        return "redirect:/student/mypage";
    }

    @GetMapping("/student/modify")
    public String studentModifyForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        StudentModifyDto studentModifyDto = userService.studentModifyDto(principalDetails);
        model.addAttribute("dto", studentModifyDto);
        return "student/modifyStudent";
    }

    @PostMapping("/student/modify")
    public String studentModify(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid StudentModifyDto studentModifyDto, BindingResult bindingResult) {

        userService.studentModify(principalDetails, studentModifyDto);
        return "redirect:/student/mypage";
    }

}
