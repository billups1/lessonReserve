package hs.lessonReserve.web.dto.gather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class GatherListDto {

    private long id;
    private String name;
    private String content;
    private String representativeImageUrl;
    private String address;
    private String userGatherState;

    public GatherListDto(Object[] objects) {
        this.id = (long) objects[0];
        this.name = (String) objects[1];
        this.content = (String) objects[2];
        this.representativeImageUrl = (String) objects[3];
        this.address = (String) objects[4];
        this.userGatherState = (String) objects[5];
    }
}
