package hs.lessonReserve.web.api;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.service.LessonService;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import hs.lessonReserve.web.dto.lesson.HomeLessonListDto;
import hs.lessonReserve.web.dto.lesson.LessonSearchCondDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LessonApiController {

    private final LessonService lessonService;

    @DeleteMapping("/api/teacher/lesson/{lessonId}")
    public ResponseEntity deleteLesson(@PathVariable long lessonId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        lessonService.deleteLesson(lessonId, principalDetails);

        return new ResponseEntity<>(new CMRespDto<>(1, "강의삭제 완료", null), HttpStatus.OK);
    }

    @GetMapping("/api/lesson/home")
    public ResponseEntity homeLessonList(Pageable pageable, @AuthenticationPrincipal PrincipalDetails principalDetails, LessonSearchCondDto lessonSearchCondDto) {
        System.out.println("lessonSearchCondDto = " + lessonSearchCondDto);
        Page<HomeLessonListDto> lessons = lessonService.homeLessonList(principalDetails, lessonSearchCondDto, pageable);
        return new ResponseEntity<>(new CMRespDto<>(1,"홈 레슨리스트 불러오기 완료", lessons), HttpStatus.OK);
    }

}
