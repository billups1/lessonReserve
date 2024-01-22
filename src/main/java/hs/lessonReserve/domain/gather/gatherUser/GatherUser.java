package hs.lessonReserve.domain.gather.gatherUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class GatherUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "gatherId")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Gather gather;
    @ManyToOne
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;
    private String position; // LEADER, VICE-LEADER, MEMBER

    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }

}
