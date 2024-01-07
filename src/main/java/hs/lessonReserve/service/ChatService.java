package hs.lessonReserve.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hs.lessonReserve.domain.chat.ChatRoom;
import hs.lessonReserve.domain.chat.ChatRoomRepository;
import hs.lessonReserve.handler.ex.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ObjectMapper mapper;
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .build();
        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ChatRoom findById(long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> {
            throw new CustomException("없는 채팅방입니다.");
        });
        return chatRoom;
    }
}
