package hs.lessonReserve.web.api;

import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.service.*;
import hs.lessonReserve.web.dto.admin.*;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final LessonService lessonService;
    private final ApplyService applyService;
    private final LessonReviewService lessonReviewService;
    private final GatherService gatherService;
    private final GatherApplyService gatherApplyService;
    private final GatherUserService gatherUserService;
    private final UserService userService;

    @GetMapping("/api/admin/lesson/list")
    public ResponseEntity adminLessonListDtos(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable, AdminLessonSearchCondDto adminLessonSearchCondDto) {
        Page<AdminLessonListDto> lessonListDtos = lessonService.adminLessonListDto(pageable, adminLessonSearchCondDto);
        return new ResponseEntity(new CMRespDto<>(1, "Admin 레슨 리스트 조회 완료", lessonListDtos), HttpStatus.OK);
    }

    @GetMapping("/api/admin/apply/list/lessonId/{lessonId}")
    public ResponseEntity adminApplyListDtosByLessonId(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable long lessonId) {
        Page<AdminApplyDto> adminApplyDtos = applyService.adminApplyDtosByLessonId(lessonId, pageable);
        return new ResponseEntity(new CMRespDto<>(1, "Admin Apply 리스트 조회 완료", adminApplyDtos), HttpStatus.OK);
    }

    @GetMapping("/api/admin/lessonReview/list/lessonId/{lessonId}")
    public ResponseEntity adminLessonReviewListDtosByLessonId(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable long lessonId) {
        Page<AdminLessonReviewDto> lessonReviewDtos = lessonReviewService.adminLessonReviewDtosByLessonId(lessonId, pageable);
        return new ResponseEntity(new CMRespDto<>(1, "Admin 레슨리뷰 리스트 조회 완료", lessonReviewDtos), HttpStatus.OK);
    }

    @GetMapping("/api/admin/apply/list")
    public ResponseEntity adminApplyDtos(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable, AdminSearchCondDto adminSearchCondDto) {
        Page<AdminApplyDto> adminApplyDtos = applyService.adminApplyDtos(pageable, adminSearchCondDto);
        return new ResponseEntity(new CMRespDto<>(1, "Admin 레슨신청·결제 리스트 조회 완료", adminApplyDtos), HttpStatus.OK);
    }

    @GetMapping("/api/admin/gatherApply/list/gatherId/{gatherId}")
    public ResponseEntity adminGatherApplyDtos(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long gatherId) {
        Page<AdminGatherApplyDto> adminGatherApplyDtos = gatherApplyService.adminGatherApplyDtosByGatherId(gatherId, pageable);
        return new ResponseEntity(new CMRespDto<>(1, "Admin 모임신청 리스트 조회 완료", adminGatherApplyDtos), HttpStatus.OK);
    }

    @GetMapping("/api/admin/gather/list")
    public ResponseEntity adminGatherDtos(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable, AdminSearchCondDto adminSearchCondDto) {
        Page<AdminGatherDto> adminGatherDtos = gatherService.adminGatherDtos(pageable, adminSearchCondDto);
        return new ResponseEntity(new CMRespDto<>(1, "Admin 모임 리스트 조회 완료", adminGatherDtos), HttpStatus.OK);
    }

    @GetMapping("/api/admin/gatherUser/list/gatherId/{gatherId}")
    public ResponseEntity adminGatherUserDtos(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long gatherId) {
        Page<AdminGatherUserDto> adminGatherUserDtos = gatherUserService.adminGatherUserDtosByGatherId(gatherId, pageable);
        return new ResponseEntity(new CMRespDto<>(1, "Admin 모임유저 리스트 조회 완료", adminGatherUserDtos), HttpStatus.OK);
    }

    @GetMapping("/api/admin/lesson/list/teacherId/{teacherId}")
    public ResponseEntity adminLEssonDtosByTeacherId(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long teacherId) {
        Page<AdminLessonListDto> adminLessonListDtos = lessonService.adminLessonDtosByTeacherId(teacherId, pageable);
        return new ResponseEntity(new CMRespDto<>(1, "Admin 레슨 리스트(By 강사ID) 조회 완료", adminLessonListDtos), HttpStatus.OK);
    }

    @GetMapping("/api/admin/lesson/list/studentId/{studentId}")
    public ResponseEntity adminLessonDtosByStudentId(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long studentId) {
        Page<AdminLessonListDto> adminLessonListDtos = lessonService.adminLessonDtosByStudentId(studentId, pageable);
        return new ResponseEntity(new CMRespDto<>(1, "Admin 레슨 리스트(By 수강생ID) 조회 완료", adminLessonListDtos), HttpStatus.OK);
    }

    @GetMapping("/api/admin/lessonReview/list/studentId/{studentId}")
    public ResponseEntity adminGatherDtosByStudentId(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long studentId) {
        Page<AdminLessonReviewDto> adminLessonReviewDtos = lessonReviewService.adminLessonReviewDtosBystudentId(studentId, pageable);
        return new ResponseEntity(new CMRespDto<>(1, "Admin 레슨리뷰 조회 완료", adminLessonReviewDtos), HttpStatus.OK);
    }

    @GetMapping("/api/admin/user/list")
    public ResponseEntity adminUserDtos(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable, AdminSearchCondDto adminSearchCondDto) {
        Page<AdminUserDto> adminUserDtos = userService.adminUserDtos(pageable, adminSearchCondDto);
        return new ResponseEntity(new CMRespDto<>(1, "Admin 유저 리스트 조회 완료", adminUserDtos), HttpStatus.OK);
    }

}
