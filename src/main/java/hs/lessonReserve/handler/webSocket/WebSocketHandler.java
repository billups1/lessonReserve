package hs.lessonReserve.handler.webSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import hs.lessonReserve.domain.chat.Chat;
import hs.lessonReserve.domain.chat.ChatRoom;
import hs.lessonReserve.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println(payload);

        Chat chat = mapper.readValue(payload, Chat.class);
        ChatRoom chatRoom = chatService.findById(chat.getChatRoom().getId());
        List<WebSocketSession> sessions = chatRoom.getSessions();

        if (chat.getMessageType().equals("ENTER")) {
            sessions.add(session);
            chat.setMessage(chat.getUser().getName() + " 님이 입장하셨습니다.");
            sessions.forEach(s->{
                chatService.sendMessage(s, chat);
            });
        } else if (chat.getMessageType().equals("CHAT")) {
            chat.setMessage(chat.getMessage());
            sessions.forEach(s->{
                chatService.sendMessage(s, chat);
            });
        }

    }
}
