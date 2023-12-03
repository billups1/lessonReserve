package hs.lessonReserve.web.api.lesson;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.domain.apply.ApplyRepository;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.service.lesson.LessonService;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import hs.lessonReserve.web.dto.lesson.HomeLessonListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LessonApiController {

    private final LessonService lessonService;
    private final ApplyRepository applyRepository;

    @DeleteMapping("/api/teacher/lesson/{lessonId}")
    public ResponseEntity deleteLesson(@PathVariable long lessonId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        List<Apply> applies = applyRepository.findAllByLessonId(lessonId);
        for (Apply apply : applies) {
            apply.setLesson(null);
        }

        lessonService.deleteLesson(lessonId, principalDetails);

        return new ResponseEntity<>(new CMRespDto<>(1, "강의삭제 완료", null), HttpStatus.OK);
    }

    @GetMapping("/api/lesson/home")
    public ResponseEntity homeLessonList(Pageable pageable, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Page<HomeLessonListDto> lessons = lessonService.homeLessonList(principalDetails, pageable);
        return new ResponseEntity<>(new CMRespDto<>(1,"홈 레슨리스트 불러오기 완료", lessons), HttpStatus.OK);
    }

}
