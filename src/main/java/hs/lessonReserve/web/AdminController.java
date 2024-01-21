package hs.lessonReserve.web;

import hs.lessonReserve.service.ApplyService;
import hs.lessonReserve.service.LessonService;
import hs.lessonReserve.service.UserService;
import hs.lessonReserve.web.dto.admin.AdminApplyDto;
import hs.lessonReserve.web.dto.admin.AdminLessonDto;
import hs.lessonReserve.web.dto.admin.AdminUserDto;
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

    @GetMapping({"/admin", "/admin/lesson/list"})
    public String adminLessonListForm() {
        return "admin/adminLessonList";
    }

    @GetMapping("/admin/lesson/{lessonId}")
    public String adminLessonForm(@PathVariable long lessonId, Model model) {
        AdminLessonDto adminLessonDto = lessonService.adminLessonDto(lessonId);
        AdminUserDto adminUserDto = userService.adminUserDtoByLessonId(lessonId);

        model.addAttribute("lessonDto", adminLessonDto);
        model.addAttribute("userDto", adminUserDto);

        return "admin/adminLesson";
    }

    @GetMapping("/admin/apply/list")
    public String adminApplyListForm() {
        return "admin/adminApplyList";
    }


}
