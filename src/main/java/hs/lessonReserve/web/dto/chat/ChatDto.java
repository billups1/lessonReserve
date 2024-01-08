package hs.lessonReserve.web.dto.chat;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChatDto {
    private String type; // 메시지 타입 ENTER, TALK, LEAVE
    private long gatherId; // 방 번호
    private long userId; // 채팅을 보낸 사람
    private String message; // 메시지
    private LocalDateTime createTime; // 생성 시간

    @PostConstruct
    private void createTime() {
        this.createTime = LocalDateTime.now();
    }

}
