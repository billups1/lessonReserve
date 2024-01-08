package hs.lessonReserve.service;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.alarm.AlarmGatherApply;
import hs.lessonReserve.domain.alarm.AlarmRepository;
import hs.lessonReserve.domain.gather.Gather;
import hs.lessonReserve.domain.gather.GatherRepository;
import hs.lessonReserve.domain.gather.gatherUser.GatherUser;
import hs.lessonReserve.domain.gather.gatherApply.GatherApply;
import hs.lessonReserve.domain.gather.gatherApply.GatherApplyRepository;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.web.dto.gather.GatherApplyDto;
import hs.lessonReserve.web.dto.gather.GatherCreateDto;
import hs.lessonReserve.web.dto.gather.GatherListDto;
import hs.lessonReserve.web.dto.gather.GatherMypageListDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GatherService {

    @Value("${file.path}")
    private String uploadFolder;

    private final GatherRepository gatherRepository;
    private final GatherApplyRepository gatherApplyRepository;
    private final AlarmRepository alarmRepository;

    @PersistenceContext
    EntityManager em;

    @Transactional(readOnly = true)
    public List<GatherListDto> gatherList(PrincipalDetails principalDetails) {

        StringBuffer sb = new StringBuffer();

        Query query;
        if (principalDetails != null) {
            sb.append("select g.id, g.name, g.content, g.representativeImageUrl, g.address, ga.acceptStatus ");
            sb.append("from gather g ");
            sb.append("left join gatherApply ga ");
            sb.append("on g.id = ga.gatherId and ga.userId = ? ");
            sb.append("left join gatheruser gu ");
            sb.append("on g.id = gu.gatherId and gu.userId = ? ");
            sb.append("where (ga.acceptStatus is null or (ga.acceptStatus != 'ACCEPT' and ga.acceptStatus != 'REJECT')) and (gu.position is null or gu.position != 'LEADER')");
            query = em.createNativeQuery(sb.toString())
                    .setParameter(1, principalDetails.getUser().getId())
                    .setParameter(2, principalDetails.getUser().getId());
        } else {
            sb.append("select g.id, g.name, g.content, g.representativeImageUrl, g.address, null ");
            sb.append("from gather g ");
            query = em.createNativeQuery(sb.toString());
        }

        List<Object[]> resultList = query.getResultList();

        List<GatherListDto> gatherListDtos = resultList.stream().map(r -> {
            return new GatherListDto(r);
        }).collect(Collectors.toList());

        return gatherListDtos;
    }

    @Transactional
    public void gatherCreate(GatherCreateDto gatherCreateDto, PrincipalDetails principalDetails) {

        GatherUser gatherUser = GatherUser.builder()
                .user(principalDetails.getUser())
                .position("LEADER")
                .build();

        Gather gather = Gather.builder()
                .name(gatherCreateDto.getName())
                .content(gatherCreateDto.getContent())
                .maximumParticipantNumber(gatherCreateDto.getMaximumParticipantNumber())
                .address(gatherCreateDto.getSidoSelect() + " "
                        + (gatherCreateDto.getSigunGuSelect().equals("선택안함") ? "" : gatherCreateDto.getSigunGuSelect()) + " "
                        + (gatherCreateDto.getEupMeonDongSelect().equals("선택안함") ? "" : gatherCreateDto.getEupMeonDongSelect()))
                .build();

        switch (gatherCreateDto.getFlexRadioDefault()) {
            case "basketball":
                gather.setRepresentativeImageUrl("lrppp_basketball.png");
                break;
            case "cycling":
                gather.setRepresentativeImageUrl("lrppp_cycling.png");
                break;
            case "running":
                gather.setRepresentativeImageUrl("lrppp_running.jpg");
                break;
            case "swim":
                gather.setRepresentativeImageUrl("lrppp_swim.png");
                break;
            case "tennis":
                gather.setRepresentativeImageUrl("lrppp_tennis.png");
                break;
            case "volleyball":
                gather.setRepresentativeImageUrl("lrppp_volleyball.png");
                break;
            case "customUpload":
                if (gatherCreateDto.getRepresentativeImageFile() != null) {
                    UUID uuid = UUID.randomUUID();
                    String profileImageFilename = uuid + gatherCreateDto.getRepresentativeImageFile().getOriginalFilename();
                    Path path = Paths.get(uploadFolder + profileImageFilename);
                    try {
                        Files.write(path, gatherCreateDto.getRepresentativeImageFile().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

        gather.setGatherUsers(gatherUser);
        gatherUser.setGather(gather);

        gatherRepository.save(gather);

    }

    @Transactional
    public void gatherApply(GatherApplyDto gatherApplyDto, PrincipalDetails principalDetails) {
        Gather gather = gatherRepository.findById(gatherApplyDto.getGatherId())
                .orElseThrow(() -> {
                    throw new CustomException("해당 모임이 없습니다.");
                });

        if (principalDetails == null) {
            throw new CustomException("로그인이 필요합니다.");
        }

        GatherApply gatherApply = GatherApply.builder()
                .user(principalDetails.getUser())
                .gather(gather)
                .content(gatherApplyDto.getContent())
                .acceptStatus("APPLY")
                .build();

        gatherApplyRepository.save(gatherApply);

        // 모임 리더에게 모임 가입 신청 알림
        GatherUser gatherUser = gather.getGatherUsers().stream().filter(gu ->
                gu.getPosition().equals("LEADER")
        ).findFirst().orElseThrow(() -> {
            throw new CustomException("해당 유저가 없습니다");
        });

        AlarmGatherApply alarmGatherApply = AlarmGatherApply.builder()
                .toUser(gatherUser.getUser())
                .fromUser(principalDetails.getUser())
                .gatherApply(gatherApply)
                .domain("GatherApply")
                .build();

        alarmRepository.save(alarmGatherApply);

    }

    @Transactional(readOnly = true)
    public List<GatherMypageListDto> gatherMypage(PrincipalDetails principalDetails) {
        StringBuffer sb = new StringBuffer();
        sb.append("select g.id, g.name, g.content, g.representativeImageUrl, g.address, gu.position = 'LEADER' ");
        sb.append("from gather g ");
        sb.append("inner join gatheruser gu ");
        sb.append("on g.id = gu.gatherId and gu.userId = ? ");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalDetails.getUser().getId());

        List<Object[]> resultList = query.getResultList();

        List<GatherMypageListDto> gatherMypageListDtos = resultList.stream().map(r -> {
            return new GatherMypageListDto(r);
        }).collect(Collectors.toList());

        return gatherMypageListDtos;
    }

    @Transactional(readOnly = true)
    public void gatherChatting(PrincipalDetails principalDetails, long gatherId) {
        Gather gather = gatherRepository.findById(gatherId).orElseThrow(() -> {
            throw new CustomException("없는 모입입니다.");
        });

        boolean gatherJoinState = false;

        List<GatherUser> gatherUsers = gather.getGatherUsers();
        for (GatherUser gatherUser : gatherUsers) {
            if (gatherUser.getUser().getId() == principalDetails.getUser().getId()) {
                gatherJoinState = true;
                break;
            }
        }

        if (!gatherJoinState) {
            throw new CustomException("접근 권한이 없습니다.");
        }

    }
}
