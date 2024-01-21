package hs.lessonReserve.web.dto.admin;

import hs.lessonReserve.domain.gather.gatherApply.GatherApply;
import hs.lessonReserve.domain.gather.gatherUser.GatherUser;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.util.CustomFormatter;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AdminUserDto {

    private long id;
    private String email;
    private String name;
    private String role;
    private String profileImageUrl;
    private String phone;
    private String address;
    private String postcode;
    private String provider;
    private String providerId;
    private String createTime;

    public AdminUserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();
        this.profileImageUrl = user.getProfileImageUrl();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.postcode = user.getPostcode();
        this.provider = user.getPostcode();
        this.providerId = user.getProviderId();
        this.createTime = CustomFormatter.make_yyyyMMdd(user.getCreateTime());
    }
}
