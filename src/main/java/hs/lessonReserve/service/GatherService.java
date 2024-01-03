package hs.lessonReserve.service;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.gather.Gather;
import hs.lessonReserve.domain.gather.GatherRepository;
import hs.lessonReserve.domain.gather.GatherRepositoryImpl;
import hs.lessonReserve.domain.gather.GatherUser;
import hs.lessonReserve.domain.gather.gatherApply.GatherApply;
import hs.lessonReserve.domain.gather.gatherApply.GatherApplyRepository;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.web.dto.gather.GatherApplyDto;
import hs.lessonReserve.web.dto.gather.GatherCreateDto;
import hs.lessonReserve.web.dto.gather.GatherListDto;
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

    private final GatherRepositoryImpl gatherRepositoryImpl;
    private final GatherRepository gatherRepository;
    private final GatherApplyRepository gatherApplyRepository;

    @PersistenceContext
    EntityManager em;

    @Transactional(readOnly = true)
    public List<GatherListDto> gatherList(PrincipalDetails principalDetails) {

        StringBuffer sb = new StringBuffer();
        sb.append("select g.id, g.name, g.content, g.representativeImageUrl, g.address, ga.acceptStatus ");
        sb.append("from gather g ");
        sb.append("left join gatherApply ga ");

        Query query;
        if(principalDetails != null) {
            sb.append("on g.id = ga.gatherId and ga.userId = ?");
            query = em.createNativeQuery(sb.toString())
                    .setParameter(1, principalDetails.getUser().getId());
        } else {
            sb.append("on g.id = ga.gatherId");
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
        Gather gather = new Gather();
        gather.setId(gatherApplyDto.getGatherId());

        if (principalDetails == null) {
            throw new CustomException("로그인이 필요합니다.");
        }

        GatherApply gatherApply = GatherApply.builder()
                .user(principalDetails.getUser())
                .gather(gather)
                .content(gatherApplyDto.getContent())
                .acceptStatus("APPLY")
                .build();

        // 모임 리더에게 알림



        gatherApplyRepository.save(gatherApply);

    }
}
