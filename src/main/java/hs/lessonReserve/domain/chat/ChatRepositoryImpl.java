package hs.lessonReserve.domain.chat;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hs.lessonReserve.web.dto.chat.ChatListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static hs.lessonReserve.domain.chat.QChat.*;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<ChatListDto> chatList(long gatherId) {
        List<ChatListDto> chatListDtos = queryFactory.select(
                        Projections.fields(
                                ChatListDto.class,
                                chat.user.name.as("name"),
                                chat.user.profileImageUrl.as("profileImageUrl"),
                                chat.message.as("message"),
                                chat.user.gatherUsers, // position
                                chat.user.id.as("userId"),
                                chat.createTime.as("createTime")
                        )
                ).from(chat)
                .fetch();

        return chatListDtos;
    }

}
