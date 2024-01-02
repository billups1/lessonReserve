package hs.lessonReserve.service;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.gather.Gather;
import hs.lessonReserve.domain.gather.GatherRepository;
import hs.lessonReserve.domain.gather.GatherRepositoryImpl;
import hs.lessonReserve.domain.gather.GatherUser;
import hs.lessonReserve.web.dto.gather.GatherCreateDto;
import hs.lessonReserve.web.dto.gather.GatherListDto;
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

@Service
@RequiredArgsConstructor
public class GatherService {

    @Value("${file.path}")
    private String uploadFolder;

    private final GatherRepositoryImpl gatherRepositoryImpl;
    private final GatherRepository gatherRepository;

    @Transactional(readOnly = true)
    public List<GatherListDto> gatherList() {
        List<GatherListDto> gatherListDtos = gatherRepositoryImpl.gatherListDtoList();
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
                .address(gatherCreateDto.getSidoSelect() + " " + gatherCreateDto.getSigunGuSelect() + " " + gatherCreateDto.getEupMeonDongSelect())
                .build();

        switch (gatherCreateDto.getFlexRadioDefault()) {
            case "basketball":
                gather.setRepresentativeImageUrl(uploadFolder + "lrppp_basketball.png");
                break;
            case "cycling":
                gather.setRepresentativeImageUrl(uploadFolder + "lrppp_cycling.png");
                break;
            case "running":
                gather.setRepresentativeImageUrl(uploadFolder + "lrppp_running.jpg");
                break;
            case "swim":
                gather.setRepresentativeImageUrl(uploadFolder + "lrppp_swim.png");
                break;
            case "tennis":
                gather.setRepresentativeImageUrl(uploadFolder + "lrppp_tennis.png");
                break;
            case "volleyball":
                gather.setRepresentativeImageUrl(uploadFolder + "lrppp_volleyball.png");
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

        gather.getGatherUsers().add(gatherUser);
        System.out.println(gather);

        gatherRepository.save(gather);

    }
}
