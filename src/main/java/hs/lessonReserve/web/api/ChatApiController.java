package hs.lessonReserve.web.api;

import hs.lessonReserve.service.ChatService;
import hs.lessonReserve.web.dto.chat.ChatListDto;
import hs.lessonReserve.web.dto.chat.ChatSendDto;
import hs.lessonReserve.web.dto.ex.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatService chatService;

    @MessageMapping("/api/chat/enterUser")
    public void enterUser(@Payload ChatSendDto chatSendDto, SimpMessageHeaderAccessor headerAccessor) {
        chatService.sendMessage(chatSendDto, headerAccessor);
    }

    @MessageMapping("/api/chat/sendMessage")
    public void sendMessage(@Payload ChatSendDto chatSendDto, SimpMessageHeaderAccessor headerAccessor) {
        chatService.sendMessage(chatSendDto, headerAccessor);
    }

    @GetMapping("/api/chat/list/{gatherId}")
    public ResponseEntity chatList(@PathVariable long gatherId) {
        List<ChatListDto> chatListDtos = chatService.chatList(gatherId);
        return new ResponseEntity<>(new CMRespDto<>(1, "채팅 리스트 불러오기 완료", chatListDtos), HttpStatus.OK);
    }

}
