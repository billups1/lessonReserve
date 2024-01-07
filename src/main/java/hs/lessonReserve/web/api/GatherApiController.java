package hs.lessonReserve.web.api;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.service.GatherService;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import hs.lessonReserve.web.dto.gather.GatherListDto;
import hs.lessonReserve.web.dto.gather.GatherMypageListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GatherApiController {

    private final GatherService gatherService;

    @GetMapping("/api/gather/list")
    public ResponseEntity gatherList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<GatherListDto> gatherListDtos = gatherService.gatherList(principalDetails);
        return new ResponseEntity(new CMRespDto<>(1, "모임 리스트 불러오기 완료", gatherListDtos), HttpStatus.OK);
    }

    @GetMapping("/api/gather/list/mypage")
    public ResponseEntity gatherListMypage(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<GatherMypageListDto> gatherMypageListDtos = gatherService.gatherMypage(principalDetails);
        return new ResponseEntity(new CMRespDto<>(1, "모임 마이페이지 리스트 불러오기 완료", gatherMypageListDtos), HttpStatus.OK);
    }


}
