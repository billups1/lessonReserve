package hs.lessonReserve.web.dto.admin;

import hs.lessonReserve.domain.gather.gatherApply.GatherApply;
import hs.lessonReserve.domain.gather.gatherUser.GatherUser;
import hs.lessonReserve.util.CustomFormatter;
import lombok.Data;

@Data
public class AdminGatherUserDto {

    private long id;
    private String userName;
    private String position;
    private String createTime;

    public AdminGatherUserDto(GatherUser gatherUser) {
        this.id = gatherUser.getId();
        this.userName = gatherUser.getUser().getName();
        this.position = gatherUser.getPosition();
        this.createTime = CustomFormatter.make_yyyyMMdd(gatherUser.getCreateTime());
    }
}
