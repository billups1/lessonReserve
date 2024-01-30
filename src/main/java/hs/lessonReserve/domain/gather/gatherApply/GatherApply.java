package hs.lessonReserve.domain.gather.gatherApply;

import hs.lessonReserve.domain.gather.Gather;
import hs.lessonReserve.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "gatherApply")
public class GatherApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;

    @ManyToOne
    @JoinColumn(name = "gatherId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Gather gather;

    private String content;
    private String acceptStatus; // APPLY, ACCEPT, REJECT, RESULT

    private LocalDateTime createTime;
    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }

}
