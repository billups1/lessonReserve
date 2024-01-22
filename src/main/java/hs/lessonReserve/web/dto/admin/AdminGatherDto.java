package hs.lessonReserve.web.dto.admin;

import hs.lessonReserve.domain.gather.Gather;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.util.CustomFormatter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminGatherDto {

    private Long id;
    private String name;
    private String content;
    private String representativeImageUrl;
    private String address;
    private int maximumParticipantNumber;
    private String createTime;

    public AdminGatherDto(Gather gather) {
        this.id = gather.getId();
        this.name = gather.getName();
        this.content = gather.getContent();
        this.representativeImageUrl = gather.getRepresentativeImageUrl();
        this.address = gather.getAddress();
        this.maximumParticipantNumber = gather.getMaximumParticipantNumber();
        this.createTime = CustomFormatter.make_yyyyMMddHHmmss(gather.getCreateTime());
    }
}
