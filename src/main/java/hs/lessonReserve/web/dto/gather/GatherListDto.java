package hs.lessonReserve.web.dto.gather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class GatherListDto {

    private String name;
    private String content;
    private String gatherType;
    private String representativeImageUrl;
    private String gatherTime;
    private int maximumParticipantNumber;
    private int currentParticipantNumber;


}
