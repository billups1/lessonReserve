package hs.lessonReserve.domain.gather;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hs.lessonReserve.domain.gather.gatherApply.QGatherApply;
import hs.lessonReserve.web.dto.gather.GatherListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static hs.lessonReserve.domain.gather.QGather.*;

@Repository
@RequiredArgsConstructor
public class GatherRepositoryImpl implements GatherRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<GatherListDto> gatherListDtoList() {
        List<GatherListDto> gatherListDtos = queryFactory.select(
                        Projections.fields(
                                GatherListDto.class,
                                gather.id.as("id"),
                                gather.name.as("name"),
                                gather.content.as("content"),
                                gather.representativeImageUrl.as("representativeImageUrl"),
                                gather.maximumParticipantNumber.as("maximumParticipantNumber")
                        )
                ).from(gather)

                .fetch();

        return gatherListDtos;
    }
}
