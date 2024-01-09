package hs.lessonReserve.web.dto.chat;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ChatListDto {

    private String name;
    private String profileImageUrl;
    private String message;
    private String position;
    private long userId;
    private String createTime;

    public ChatListDto(Object[] objects) {
        this.name = (String) objects[0];
        this.profileImageUrl = (String) objects[1];
        this.message = (String) objects[2];
        this.position = (String) objects[3];
        this.userId = (long) objects[4];
        this.createTime = (String) objects[5];
    }
}
