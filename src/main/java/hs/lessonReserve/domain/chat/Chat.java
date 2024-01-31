package hs.lessonReserve.domain.chat;

import hs.lessonReserve.domain.gather.Gather;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.web.dto.chat.ChatSendDto;
import jakarta.annotation.PostConstruct;
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
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String messageType; // ENTER, TALK

    @ManyToOne
    @JoinColumn(name = "gatherId")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Gather gather;
    @ManyToOne
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;
    private String message;

    private LocalDateTime createTime;

    @PostConstruct
    public void createTime(String createTime) {
        this.createTime = LocalDateTime.now();
    }

    public Chat(ChatSendDto chatSendDto, User user) {
        this.messageType = chatSendDto.getType();
        this.gather = new Gather(chatSendDto.getGatherId());
        this.user = user;
        this.message = chatSendDto.getMessage();
        this.createTime = LocalDateTime.now();
    }
}
