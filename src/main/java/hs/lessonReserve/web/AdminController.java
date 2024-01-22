package hs.lessonReserve.web;

import hs.lessonReserve.service.ApplyService;
import hs.lessonReserve.service.GatherService;
import hs.lessonReserve.service.LessonService;
import hs.lessonReserve.service.UserService;
import hs.lessonReserve.web.dto.admin.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.TypeCache;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final LessonService lessonService;
    private final UserService userService;
    private final ApplyService applyService;
    private final GatherService gatherService;

    @GetMapping({"/admin", "/admin/lesson/list"})
    public String adminLessonListForm() {
        return "admin/adminLessonList";
    }

    @GetMapping("/admin/lesson/{lessonId}")
    public String adminLessonForm(@PathVariable long lessonId, Model model) {
        AdminLessonDto adminLessonDto = lessonService.adminLessonDtosByLessonId(lessonId);
        AdminUserDto adminUserDto = userService.adminUserDtoByLessonId(lessonId);

        model.addAttribute("lessonDto", adminLessonDto);
        model.addAttribute("userDto", adminUserDto);

        return "admin/adminLesson";
    }

    @GetMapping("/admin/apply/list")
    public String adminApplyListForm() {
        return "admin/adminApplyList";
    }

    @GetMapping("/admin/apply/{applyId}")
    public String adminApplyForm(@PathVariable long applyId, Model model) {
        AdminApplyDto adminApplyDto = applyService.adminApplyDto(applyId);
        AdminUserDto adminUserDtoStudent = applyService.adminUserDtoStudentByApplyId(applyId);
        AdminLessonDto adminLessonDto = applyService.adminLessonDtoByApplyId(applyId);

        model.addAttribute("applyDto", adminApplyDto);
        model.addAttribute("userDto",adminUserDtoStudent);
        model.addAttribute("lessonDto",adminLessonDto);

        return "admin/adminApply";
    }


    @GetMapping("/admin/gather/list")
    public String adminGatherListForm() {
        return "admin/adminGatherList";
    }

    @GetMapping("admin/gather/{gatherId}")
    public String adminGatherForm(@PathVariable long gatherId, Model model) {
        AdminGatherDto adminGatherDto = gatherService.adminGatherDto(gatherId);

        model.addAttribute("gatherDto", adminGatherDto);
        return "admin/adminGather";
    }


    @GetMapping("/admin/user/list")
    public String adminUserListForm() {
        return "admin/adminUserList";
    }

    @GetMapping("admin/user/{userId}")
    public String adminUserForm(@PathVariable long userId, Model model) {
        AdminUserDto adminUserDto = userService.adminUserDto(userId);

        return "admin/adminUser";
    }


}
