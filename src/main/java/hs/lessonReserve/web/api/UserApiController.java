package hs.lessonReserve.web.api;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.domain.user.UserRepository;
import hs.lessonReserve.service.UserService;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/api/user")
    public ResponseEntity user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        HashMap<String, Object> map = new HashMap<>();
        if (principalDetails != null) {
            map.put("userId", principalDetails.getUser().getId());
            map.put("userName", principalDetails.getUser().getName());
        }
        return new ResponseEntity(new CMRespDto<>(1, "유저 정보 반환 완료", map), HttpStatus.OK);
    }

    @DeleteMapping("/api/user/withdrawal")
    public ResponseEntity userWithdrawal(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.userWithdrawal(principalDetails);
        return new ResponseEntity(new CMRespDto<>(1, "회원 탈퇴 완료", null), HttpStatus.OK);
    }

}
