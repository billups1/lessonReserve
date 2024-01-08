package hs.lessonReserve.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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


    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
