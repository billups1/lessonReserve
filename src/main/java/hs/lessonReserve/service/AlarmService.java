package hs.lessonReserve.service;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.alarm.Alarm;
import hs.lessonReserve.domain.alarm.Alarm_GatherApply;
import hs.lessonReserve.domain.alarm.AlarmRepository;
import hs.lessonReserve.domain.alarm.Alarm_LessonApply;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.web.dto.alarm.AlarmListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
            String fromUserName = alarm.getFromUser() == null ? "(탈퇴한 회원)" : alarm.getFromUser().getName();
            String toUserName = alarm.getToUser() == null ? "(탈퇴한 회원)" : alarm.getToUser().getName();
            String gatherName = null;
            if (alarm.getDomain().startsWith("Gather")) {
                Alarm_GatherApply alarmGatherApply = (Alarm_GatherApply) alarm;
                if (alarmGatherApply.getGatherApply().getGather() != null) {
                    gatherName = alarmGatherApply.getGatherApply().getGather().getName();
                } else {
                    gatherName = "(삭제된 모임)";
                }
            }
            String lessonName = null;
            if (alarm.getDomain().startsWith("Lesson")) {
                Alarm_LessonApply alarmLessonApply = (Alarm_LessonApply) alarm;
                if (alarmLessonApply.getLesson() != null) {
                    lessonName = alarmLessonApply.getLesson().getName();
                } else {
                    lessonName = "(삭제된 레슨)";
                }
            }

            if (alarm.getDomain().equals("GatherApply")) {
                Alarm_GatherApply alarmGatherApply = (Alarm_GatherApply) alarm;
                alarmListDtos.add(AlarmListDto.builder()
                        .message(fromUserName + " 님이 " + gatherName + " 에 가입을 신청하셨습니다.")
                        .gatherApplyId(alarmGatherApply.getGatherApply().getId())
                        .gatherApplyAcceptStatus(alarmGatherApply.getGatherApply().getAcceptStatus())
                        .alarmId(alarmGatherApply.getId())
                        .build());
            } else if (alarm.getDomain().equals("GatherApplyAccept")) {
                Alarm_GatherApply alarmGatherApply = (Alarm_GatherApply) alarm;
                alarmListDtos.add(AlarmListDto.builder()
                        .message(gatherName + " 에 가입이 승인되었습니다.")
                        .gatherApplyId(alarmGatherApply.getGatherApply().getId())
                        .gatherApplyAcceptStatus("GatherApplyAcceptResult")
                        .alarmId(alarmGatherApply.getId())
                        .build());
            } else if (alarm.getDomain().equals("GatherApplyReject")) {

                Alarm_GatherApply alarmGatherApply = (Alarm_GatherApply) alarm;
                alarmListDtos.add(AlarmListDto.builder()
                        .message(gatherName + " 에 가입이 거절되었습니다.")
                        .gatherApplyId(alarmGatherApply.getGatherApply().getId())
                        .gatherApplyAcceptStatus("GatherApplyRejectResult")
                        .alarmId(alarmGatherApply.getId())
                        .build());
            } else if (alarm.getDomain().equals("LessonApply")) {
                Alarm_LessonApply alarm_lessonApply = (Alarm_LessonApply) alarm;
                alarmListDtos.add(AlarmListDto.builder()
                        .message(fromUserName + "님이 " + lessonName + " 에 가입하셨습니다.")
                        .alarmId(alarm_lessonApply.getId())
                        .build());
            }

            alarm.setStatus("READ"); // 확인한 알람 READ로 바꾸기
        }
        return alarmListDtos;
    }


}
