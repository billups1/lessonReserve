package hs.lessonReserve.web.api;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.payment.Payment;
import hs.lessonReserve.service.LessonReviewService;
import hs.lessonReserve.service.LessonService;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import hs.lessonReserve.web.dto.lesson.HomeLessonListDto;
import hs.lessonReserve.web.dto.lesson.LessonSearchCondDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class LessonReviewApiController {

    private final LessonReviewService lessonReviewService;

    @DeleteMapping("/api/lessonReview/delete/{lessonReviewId}")
    public ResponseEntity user(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long lessonReviewId) {
        lessonReviewService.lessonReviewDelete(principalDetails, lessonReviewId);
        return new ResponseEntity(new CMRespDto<>(1, "유저 정보 반환 완료", null), HttpStatus.OK);
    }

}
