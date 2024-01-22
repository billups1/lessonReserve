package hs.lessonReserve.web.api;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.service.GatherUserService;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GatherUserApiController {

    private final GatherUserService gatherUserService;

    @PutMapping("/api/gatherUser/withdraw/{gatherUserId}")
    public ResponseEntity gatherUserWithdraw(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long gatherUserId) {
        gatherUserService.gatherUserWithdraw(gatherUserId, principalDetails);

        return new ResponseEntity<>(new CMRespDto<>(1, "모임 탈퇴 완료", null), HttpStatus.OK);
    }

}
