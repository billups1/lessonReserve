package hs.lessonReserve.domain.chat;

import hs.lessonReserve.domain.gather.Gather;
import hs.lessonReserve.domain.user.User;
import jakarta.annotation.PostConstruct;
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
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String messageType; // ENTER, TALK

    @ManyToOne
    @JoinColumn(name = "gatherId")
    private Gather gather;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private String message;

    private LocalDateTime createTime;

    @PostConstruct
    public void createTime(String createTime) {
        this.createTime = LocalDateTime.now();;
    }
}
