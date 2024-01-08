package hs.lessonReserve.web.api;

import hs.lessonReserve.domain.chat.Chat;
import hs.lessonReserve.domain.gather.GatherRepository;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.domain.user.UserRepository;
import hs.lessonReserve.handler.ex.CustomApiException;
import hs.lessonReserve.web.dto.chat.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatApiController {

    private final SimpMessageSendingOperations template;
    private final UserRepository userRepository;

    @MessageMapping("/api/chat/enterUser")
    public void enterUser(@Payload ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {

        headerAccessor.getSessionAttributes().put("gatherId", chatDto.getGatherId());
        headerAccessor.getSessionAttributes().put("userId", chatDto.getUserId());

        User user = userRepository.findById(chatDto.getUserId()).orElseThrow(() -> {
            throw new CustomApiException("없는 유저입니다.");
        });

        chatDto.setMessage(user.getName() + " 님이 입장하셨습니다.");
        template.convertAndSend("/sub/chat/gather/" + chatDto.getGatherId(), chatDto);
    }

    @MessageMapping("/api/chat/sendMessage")
    public void sendMessage(@Payload ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
        template.convertAndSend("/sub/chat/gather/" + chatDto.getGatherId(), chatDto);
    }


}
