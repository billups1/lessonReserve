package hs.lessonReserve.domain.gather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hs.lessonReserve.domain.gather.gatherApply.GatherApply;
import hs.lessonReserve.domain.gather.gatherUser.GatherUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "gather")
public class Gather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String content;
    private String representativeImageUrl;
    private String address;
    private int maximumParticipantNumber;

    @OneToMany(mappedBy = "gather", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"gather", "user"})
    private List<GatherUser> gatherUsers;

    @OneToMany(mappedBy = "gather", fetch = FetchType.LAZY)
    private List<GatherApply> gatherApplies;

    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }

    public void setGatherUsers(GatherUser gatherUsers) {
        this.gatherUsers = new ArrayList<>();
        this.gatherUsers.add(gatherUsers);
    }

    public Gather(long id) {
        this.id = id;
    }
}
