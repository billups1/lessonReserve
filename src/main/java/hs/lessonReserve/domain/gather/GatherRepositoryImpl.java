package hs.lessonReserve.domain.gather;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hs.lessonReserve.web.dto.gather.GatherListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GatherRepositoryImpl implements GatherRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<GatherListDto> gatherListDtoList() {
        List<GatherListDto> gatherListDtos = queryFactory.select(
                        Projections.bean(
                                GatherListDto.class,
                                QGather.gather.name.as("name"),
                                QGather.gather.content.as("content"),
                                QGather.gather.gatherType.as("gatherType"),
                                QGather.gather.representativeImageUrl.as("representativeImageUrl"),
                                QGather.gather.gatherTime.as("gatherTime"),
                                QGather.gather.maximumParticipantNumber.as("maximumParticipantNumber")
                        )
                ).from(QGather.gather)
                .fetch();

        return gatherListDtos;
    }
}
