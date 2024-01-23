package hs.lessonReserve.domain.gather;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hs.lessonReserve.domain.gather.gatherApply.QGatherApply;
import hs.lessonReserve.domain.gather.gatherUser.QGatherUser;
import hs.lessonReserve.web.dto.admin.AdminGatherDto;
import hs.lessonReserve.web.dto.admin.AdminSearchCondDto;
import hs.lessonReserve.web.dto.admin.QAdminGatherDto;
import hs.lessonReserve.web.dto.gather.GatherListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static hs.lessonReserve.domain.apply.QApply.apply;
import static hs.lessonReserve.domain.gather.QGather.*;
import static hs.lessonReserve.domain.lesson.QLesson.lesson;

@Repository
@RequiredArgsConstructor
public class GatherRepositoryImpl implements GatherRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AdminGatherDto> adminGatherDtos(Pageable pageable, AdminSearchCondDto adminSearchCondDto) {
        List<AdminGatherDto> adminGatherDtos = queryFactory.select(
                        new QAdminGatherDto(gather)
                ).from(gather)
                .where(searchCond(adminSearchCondDto))
                .orderBy(gather.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory.select(gather.count())
                .from(gather)
                .where(searchCond(adminSearchCondDto))
                .fetchOne();

        return new PageImpl<>(adminGatherDtos, pageable, totalCount);
    }

    public Page<AdminGatherDto> adminGatherDtosByUserId(Pageable pageable, long userId) {
        List<AdminGatherDto> adminGatherDtos = queryFactory.select(
                        new QAdminGatherDto(gather)
                ).from(gather)
                .join(gather.gatherUsers, QGatherUser.gatherUser)
                .on(QGatherUser.gatherUser.user.id.eq(userId))
//                .where(searchCond(adminSearchCondDto))
                .orderBy(gather.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory.select(gather.count())
                .from(gather)
//                .where(searchCond(adminSearchCondDto))
                .fetchOne();

        return new PageImpl<>(adminGatherDtos, pageable, totalCount);
    }

    private BooleanExpression searchCond(AdminSearchCondDto adminSearchCondDto) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        if (adminSearchCondDto == null || adminSearchCondDto.getCond1() == null || adminSearchCondDto.getCond1().equals("none")) {
            return null;
        } else if (adminSearchCondDto.getCond1().equals("id")) {
            return gather.id.eq(Long.parseLong(adminSearchCondDto.getSearchText()));
        } else if (adminSearchCondDto.getCond1().equals("name")) {
            return gather.name.contains(adminSearchCondDto.getSearchText());
        } else if (adminSearchCondDto.getCond1().equals("createTime")) {
            if (adminSearchCondDto.getCond2().equals("이후")) {
                return gather.createTime.gt(LocalDateTime.parse(adminSearchCondDto.getSearchDate() + " 00:00:00.000", dateTimeFormatter));
            } else if (adminSearchCondDto.getCond2().equals("이전")) {
                return gather.createTime.lt(LocalDateTime.parse(adminSearchCondDto.getSearchDate() + " 00:00:00.000", dateTimeFormatter));
            }
        } else if (adminSearchCondDto.getCond1().equals("address")) {
            return gather.address.contains(adminSearchCondDto.getSearchText());
        }

        return null;
    }

}
