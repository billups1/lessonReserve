package hs.lessonReserve.domain.gather.gatherApply;

import hs.lessonReserve.domain.gather.Gather;
import hs.lessonReserve.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GatherApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "gatherId")
    private Gather gather;

    private String content;
    private String acceptStatus;


    private LocalDateTime createTime;
    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }

}
