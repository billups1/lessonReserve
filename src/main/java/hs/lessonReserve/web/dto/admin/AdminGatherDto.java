package hs.lessonReserve.web.dto.admin;

import com.querydsl.core.annotations.QueryProjection;
import hs.lessonReserve.domain.gather.Gather;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.util.CustomFormatter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminGatherDto {

    private Long id;
    private String name;
    private String leaderName;
    private String content;
    private String representativeImageUrl;
    private String address;
    private int maximumParticipantNumber;
    private String createTime;
    private String currentUserCountState;

    @QueryProjection
    public AdminGatherDto(Gather gather) {
        this.id = gather.getId();
        this.name = gather.getName();
        this.leaderName = gather.getGatherUsers().stream().filter(gu ->
                gu.getPosition().equals("LEADER")
        ).findFirst().orElseThrow(() -> {
            throw new CustomException("해당 유저가 없습니다");
        }).getUser().getName();
        this.content = gather.getContent();
        this.representativeImageUrl = gather.getRepresentativeImageUrl();
        this.address = gather.getAddress();
        this.maximumParticipantNumber = gather.getMaximumParticipantNumber();
        this.createTime = CustomFormatter.make_yyyyMMddHHmmss(gather.getCreateTime());
        this.currentUserCountState = gather.getGatherUsers().size() + "/" + maximumParticipantNumber;
    }
}
