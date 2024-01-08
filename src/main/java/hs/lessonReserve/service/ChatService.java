package hs.lessonReserve.service;

import hs.lessonReserve.domain.chat.Chat;
import hs.lessonReserve.domain.chat.ChatRepository;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.domain.user.UserRepository;
import hs.lessonReserve.handler.ex.CustomApiException;
import hs.lessonReserve.web.dto.chat.ChatListDto;
import hs.lessonReserve.web.dto.chat.ChatSendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final SimpMessageSendingOperations template;

    @Transactional
    public void sendMessage(ChatSendDto chatSendDto, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("gatherId", chatSendDto.getGatherId());
        headerAccessor.getSessionAttributes().put("userId", chatSendDto.getUserId());

        User user = userRepository.findById(chatSendDto.getUserId()).orElseThrow(() -> {
            throw new CustomApiException("없는 유저입니다.");
        });

        if (chatSendDto.getType().equals("ENTER")) {
            chatSendDto.setMessage(user.getName() + " 님이 입장하셨습니다.");
        }
        chatSendDto.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        template.convertAndSend("/sub/chat/gather/" + chatSendDto.getGatherId(), chatSendDto);

        Chat chat = new Chat(chatSendDto, user);
        chatRepository.save(chat);

    }

    @Transactional(readOnly = true)
    public List<ChatListDto> chatList(long gatherId) {

        StringBuffer sb = new StringBuffer();
        select * from chat c
        left join gatherUser gu
        on c.userId = gu.userId and c.gatherId = gu.userId
        where c.gatherId = 8;

    }
}
