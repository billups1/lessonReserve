package hs.lessonReserve.domain.gather;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Gather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String content;
    private String gatherType; // SOCCER, BASKETBALL, RUNNING, YOGA, ...
    private String representativeImageUrl;
    private String gatherTime;
    private String roadAddress;
    private int maximumParticipantNumber;
    @OneToMany(mappedBy = "gather", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<GatherUser> gatherUsers;

    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }

}
