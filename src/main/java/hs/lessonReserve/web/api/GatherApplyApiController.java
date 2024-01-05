package hs.lessonReserve.web.api;

import hs.lessonReserve.service.GatherApplyService;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GatherApplyApiController {

    private final GatherApplyService gatherApplyService;

    @PutMapping("/api/gather/{gatherApplyId}/accept")
    public ResponseEntity gatherApplyAccept(@PathVariable long gatherApplyId) {
        gatherApplyService.gatherApplyAccept(gatherApplyId);
        return new ResponseEntity<>(new CMRespDto<>(1, "모임신청 승인 완료", null), HttpStatus.OK);
    }

    @PutMapping("/api/gather/{gatherApplyId}/reject")
    public ResponseEntity gatherApplyReject(@PathVariable long gatherApplyId) {
        gatherApplyService.gatherApplyReject(gatherApplyId);
        return new ResponseEntity<>(new CMRespDto<>(1, "모임신청 거절 완료", null), HttpStatus.OK);
    }

}
