package hs.lessonReserve.service;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.alarm.Alarm;
import hs.lessonReserve.domain.alarm.AlarmGatherApply;
import hs.lessonReserve.domain.alarm.AlarmRepository;
import hs.lessonReserve.domain.gather.GatherRepository;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.domain.user.UserRepository;
import hs.lessonReserve.handler.ex.CustomApiException;
import hs.lessonReserve.web.dto.alarm.AlarmListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final GatherRepository gatherRepository;

    @Transactional(readOnly = true)
    public int alarmCount(PrincipalDetails principalDetails) {
        List<Alarm> alarms = alarmRepository.findByToUserId(principalDetails.getUser().getId());
        return alarms.size();
    }

    @Transactional(readOnly = true)
    public List<AlarmListDto> alarmList(PrincipalDetails principalDetails) {
        List<Alarm> alarms = alarmRepository.findByToUserId(principalDetails.getUser().getId());
        ArrayList<AlarmListDto> alarmListDtos = new ArrayList<>();
        for (Alarm alarm : alarms) {
            if (alarm.getDomain().equals("GatherApply")) {
                AlarmGatherApply alarmGatherApply = (AlarmGatherApply) alarm;
                alarmListDtos.add(AlarmListDto.builder()
                        .message(alarmGatherApply.getFromUser().getName() + " 님이 " + alarmGatherApply.getGatherApply().getGather().getName() + " 에 가입을 신청하셨습니다.")
                        .gatherApplyId(alarmGatherApply.getGatherApply().getId())
                        .gatherApplyAcceptStatus(alarmGatherApply.getGatherApply().getAcceptStatus())
                        .alarmId(alarmGatherApply.getId())
                        .build());
            } else if (alarm.getDomain().equals("GatherApplyAccept")) {
                AlarmGatherApply alarmGatherApply = (AlarmGatherApply) alarm;
                alarmListDtos.add(AlarmListDto.builder()
                        .message(alarmGatherApply.getGatherApply().getGather().getName() + " 에 가입이 승인되었습니다.")
                        .gatherApplyId(alarmGatherApply.getGatherApply().getId())
                        .gatherApplyAcceptStatus("GatherApplyAcceptResult")
                        .alarmId(alarmGatherApply.getId())
                        .build());
            }
            else if (alarm.getDomain().equals("GatherApplyReject")) {
                AlarmGatherApply alarmGatherApply = (AlarmGatherApply) alarm;
                alarmListDtos.add(AlarmListDto.builder()
                        .message(alarmGatherApply.getGatherApply().getGather().getName() + " 에 가입이 거절되었습니다.")
                        .gatherApplyId(alarmGatherApply.getGatherApply().getId())
                        .gatherApplyAcceptStatus("GatherApplyRejectResult")
                        .alarmId(alarmGatherApply.getId())
                        .build());
            }
        }
        return alarmListDtos;
    }


}
