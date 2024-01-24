package hs.lessonReserve.web.dto.chat;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChatSendDto {
    private String type; // 메시지 타입 ENTER, TALK, LEAVE
    private long gatherId; // 방 번호
    private long userId; // 채팅을 보낸 사람
    private String userName;
    @NotBlank // 메시지 blank 유효성 검사
    private String message; // 메시지
    private String createTime; // 생성 시간

}
