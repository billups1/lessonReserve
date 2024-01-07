package hs.lessonReserve.service;

import hs.lessonReserve.domain.alarm.AlarmGatherApply;
import hs.lessonReserve.domain.alarm.AlarmRepository;
import hs.lessonReserve.domain.gather.gatherUser.GatherUser;
import hs.lessonReserve.domain.gather.gatherApply.GatherApply;
import hs.lessonReserve.domain.gather.gatherApply.GatherApplyRepository;
import hs.lessonReserve.domain.gather.gatherUser.GatherUserRepository;
import hs.lessonReserve.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GatherApplyService {

    private final GatherApplyRepository gatherApplyRepository;
    private final AlarmRepository alarmRepository;
    private final GatherUserRepository gatherUserRepository;

    @Transactional
    public void gatherApplyAccept(long gatherApplyId) {
        gatherApplyRepository.mGatherApplyAccept(gatherApplyId);

        GatherApply gatherApply = gatherApplyRepository.findById(gatherApplyId).orElseThrow(() -> {
            throw new CustomApiException("없는 유저입니다.");
        });
        AlarmGatherApply alarmGatherApply = AlarmGatherApply.builder()
                .toUser(gatherApply.getUser())
                .gatherApply(gatherApply)
                .domain("GatherApplyAccept")
                .build();

        alarmRepository.save(alarmGatherApply);

        GatherUser gatherUser = GatherUser.builder()
                .user(gatherApply.getUser())
                .gather(gatherApply.getGather())
                .position("MEMBER")
                .build();

        gatherUserRepository.save(gatherUser);

    }

    @Transactional
    public void gatherApplyReject(long gatherApplyId) {
        gatherApplyRepository.mGatherApplyReject(gatherApplyId);

        GatherApply gatherApply = gatherApplyRepository.findById(gatherApplyId).orElseThrow(() -> {
            throw new CustomApiException("없는 유저입니다.");
        });
        AlarmGatherApply alarmGatherApply = AlarmGatherApply.builder()
                .toUser(gatherApply.getUser())
                .gatherApply(gatherApply)
                .domain("GatherApplyReject")
                .build();
        alarmRepository.save(alarmGatherApply);

    }

}
