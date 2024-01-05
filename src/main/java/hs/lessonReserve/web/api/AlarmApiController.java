package hs.lessonReserve.web.api;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.alarm.Alarm;
import hs.lessonReserve.service.AlarmService;
import hs.lessonReserve.web.dto.alarm.AlarmListDto;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlarmApiController {

    private final AlarmService alarmService;

    @GetMapping("/api/alarm/count")
    public ResponseEntity alarmCount(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        int alarmCount = alarmService.alarmCount(principalDetails);
        return new ResponseEntity(new CMRespDto<>(1, "알람 갯수 조회 완료", alarmCount), HttpStatus.OK);
    }

    @GetMapping("/api/alarm/list")
    public ResponseEntity alarmList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<AlarmListDto> alarmListDtos = alarmService.alarmList(principalDetails);
        return new ResponseEntity(new CMRespDto<>(1, "알람 갯수 조회 완료", alarmListDtos), HttpStatus.OK);
    }

}
