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

    @Transactional(readOnly = true)
    public Integer alarmCount(PrincipalDetails principalDetails) {
        List<Alarm> alarms = alarmRepository.findByToUserIdOrderByIdDesc(principalDetails.getUser().getId());
        int alarmCount = 0;
        for (Alarm alarm : alarms) {
            if (alarm.getStatus().equals("UN_READ")) {
                alarmCount++;
            }
        }
        return alarmCount;
    }

    @Transactional
    public List<AlarmListDto> alarmList(PrincipalDetails principalDetails) {
        List<Alarm> alarms = alarmRepository.findByToUserIdOrderByIdDesc(principalDetails.getUser().getId());
        ArrayList<AlarmListDto> alarmListDtos = new ArrayList<>();
        for (Alarm alarm : alarms) {
            if (alarm.getDomain().equals("GatherApply")) {
                try {
                    AlarmGatherApply alarmGatherApply = (AlarmGatherApply) alarm;
                    System.out.println("aaa" + alarmGatherApply.getGatherApply().getId());
                    System.out.println("aaa" + alarmGatherApply.getGatherApply().getGather().getId());
                    alarmListDtos.add(AlarmListDto.builder()
                            .message(alarmGatherApply.getFromUser().getName() + " 님이 " + alarmGatherApply.getGatherApply().getGather().getName() + " 에 가입을 신청하셨습니다.")
                            .gatherApplyId(alarmGatherApply.getGatherApply().getId())
                            .gatherApplyAcceptStatus(alarmGatherApply.getGatherApply().getAcceptStatus())
                            .alarmId(alarmGatherApply.getId())
                            .build());
                } catch (NullPointerException e) {
                    continue;
                }
            } else if (alarm.getDomain().equals("GatherApplyAccept")) {
                try {
                    AlarmGatherApply alarmGatherApply = (AlarmGatherApply) alarm;
                    alarmListDtos.add(AlarmListDto.builder()
                            .message(alarmGatherApply.getGatherApply().getGather().getName() + " 에 가입이 승인되었습니다.")
                            .gatherApplyId(alarmGatherApply.getGatherApply().getId())
                            .gatherApplyAcceptStatus("GatherApplyAcceptResult")
                            .alarmId(alarmGatherApply.getId())
                            .build());
                } catch (NullPointerException e) {
                    continue;
                }
            } else if (alarm.getDomain().equals("GatherApplyReject")) {
                try {
                    AlarmGatherApply alarmGatherApply = (AlarmGatherApply) alarm;
                    alarmListDtos.add(AlarmListDto.builder()
                            .message(alarmGatherApply.getGatherApply().getGather().getName() + " 에 가입이 거절되었습니다.")
                            .gatherApplyId(alarmGatherApply.getGatherApply().getId())
                            .gatherApplyAcceptStatus("GatherApplyRejectResult")
                            .alarmId(alarmGatherApply.getId())
                            .build());
                } catch (NullPointerException e) {
                    continue;
                }
            }

            alarm.setStatus("READ"); // 확인한 알람 READ로 바꾸기
        }
        return alarmListDtos;
    }


}
