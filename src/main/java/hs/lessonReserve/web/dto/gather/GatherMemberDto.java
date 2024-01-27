package hs.lessonReserve.web.dto.gather;

import lombok.Data;

@Data
public class GatherMemberDto {

    private long id;
    private String name;

    public GatherMemberDto(Object[] objects) {
        this.id = (long) objects[0];
        this.name = (String) objects[1];
    }
}
