package hs.lessonReserve.web.api.apply;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.apply.ApplyRepository;
import hs.lessonReserve.service.apply.ApplyService;
import hs.lessonReserve.service.lesson.LessonService;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.system.ApplicationTemp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ApplyApiController {

    private final ApplyService applyService;

    @PutMapping("/api/student/lesson/{lessonId}")
    public @ResponseBody ResponseEntity cancelApply(@PathVariable long lessonId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        applyService.cancelApply(lessonId, principalDetails);

        return new ResponseEntity<>(new CMRespDto<>(1, "수강취소 완료", null), HttpStatus.OK);
    }

}
