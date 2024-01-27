package hs.lessonReserve.service;

import hs.lessonReserve.domain.alarm.Alarm_GatherApply;
import hs.lessonReserve.domain.alarm.AlarmRepository;
import hs.lessonReserve.domain.gather.gatherUser.GatherUser;
import hs.lessonReserve.domain.gather.gatherApply.GatherApply;
import hs.lessonReserve.domain.gather.gatherApply.GatherApplyRepository;
import hs.lessonReserve.domain.gather.gatherUser.GatherUserRepository;
import hs.lessonReserve.handler.ex.CustomApiException;
import hs.lessonReserve.web.dto.admin.AdminGatherApplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Alarm_GatherApply alarmGatherApply = Alarm_GatherApply.builder()
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
        Alarm_GatherApply alarmGatherApply = Alarm_GatherApply.builder()
                .toUser(gatherApply.getUser())
                .gatherApply(gatherApply)
                .domain("GatherApplyReject")
                .build();
        alarmRepository.save(alarmGatherApply);

    }

    public Page<AdminGatherApplyDto> adminGatherApplyDtosByGatherId(Long gatherId, Pageable pageable) {
        Page<GatherApply> gatherApplies = gatherApplyRepository.findAllByGatherIdOrderByIdDesc(gatherId, pageable);
        Page<AdminGatherApplyDto> adminGatherApplyDtos = gatherApplies.map(gatherApply -> {
            return new AdminGatherApplyDto(gatherApply);
        });

        return adminGatherApplyDtos;
    }
}
