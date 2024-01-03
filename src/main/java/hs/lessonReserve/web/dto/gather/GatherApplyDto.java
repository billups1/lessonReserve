package hs.lessonReserve.web.dto.gather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class GatherApplyDto {

    private long gatherId;
    private String content;

}
