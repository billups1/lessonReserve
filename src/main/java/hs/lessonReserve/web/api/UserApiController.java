package hs.lessonReserve.web.api;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class UserApiController {

    @GetMapping("/api/user")
    public ResponseEntity user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        HashMap<String, Object> map = new HashMap<>();
        if (principalDetails != null) {
            map.put("userId", principalDetails.getUser().getId());
            map.put("userName", principalDetails.getUser().getName());
        }
        return new ResponseEntity(new CMRespDto<>(1, "유저 정보 반환 완료", map), HttpStatus.OK);
    }
}
