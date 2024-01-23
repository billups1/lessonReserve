package hs.lessonReserve.web.dto.admin;

import hs.lessonReserve.domain.gather.Gather;
import hs.lessonReserve.domain.gather.gatherApply.GatherApply;
import hs.lessonReserve.util.CustomFormatter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminGatherApplyDto {

    private Long id;
    private String userName;
    private Long userId;
    private String content;
    private String acceptStatus;
    private String createTime;

    public AdminGatherApplyDto(GatherApply gatherApply) {
        this.id = gatherApply.getId();
        if (gatherApply.getUser()!=null) {
            this.userName = gatherApply.getUser().getName();
            this.userId = gatherApply.getUser().getId();
        }
        this.content = gatherApply.getContent();
        this.acceptStatus = gatherApply.getAcceptStatus();
        this.createTime = CustomFormatter.make_yyyyMMdd(gatherApply.getCreateTime());
    }
}
