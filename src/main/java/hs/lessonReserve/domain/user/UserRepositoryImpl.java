package hs.lessonReserve.domain.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hs.lessonReserve.web.dto.admin.AdminSearchCondDto;
import hs.lessonReserve.web.dto.admin.AdminUserDto;
import hs.lessonReserve.web.dto.admin.QAdminUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static hs.lessonReserve.domain.gather.QGather.gather;
import static hs.lessonReserve.domain.user.QUser.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AdminUserDto> adminUserDto(Pageable pageable, AdminSearchCondDto adminSearchCondDto) {
        List<AdminUserDto> adminUserDtos = queryFactory.select(
                        new QAdminUserDto(user)
                ).from(user)
                .where(searchCond(adminSearchCondDto))
                .orderBy(user.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalPage = queryFactory.select(user.count())
                .from(user)
                .where(searchCond(adminSearchCondDto))
                .fetchOne();

        return new PageImpl<>(adminUserDtos, pageable, totalPage);
    }

    private BooleanExpression searchCond(AdminSearchCondDto adminSearchCondDto) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        if (adminSearchCondDto == null || adminSearchCondDto.getCond1() == null || adminSearchCondDto.getCond1().equals("none")) {
            return null;
        } else if (adminSearchCondDto.getCond1().equals("id")) {
            return user.id.eq(Long.parseLong(adminSearchCondDto.getSearchText()));
        } else if (adminSearchCondDto.getCond1().equals("name")) {
            return user.name.contains(adminSearchCondDto.getSearchText());
        } else if (adminSearchCondDto.getCond1().equals("role")) {
            return user.role.contains(adminSearchCondDto.getSearchText());
        } else if (adminSearchCondDto.getCond1().equals("email")) {
            return user.email.contains(adminSearchCondDto.getSearchText());
        } else if (adminSearchCondDto.getCond1().equals("phone")) {
            return user.phone.contains(adminSearchCondDto.getSearchText());
        } else if (adminSearchCondDto.getCond1().equals("createTime")) {
            if (adminSearchCondDto.getCond2().equals("이후")) {
                return user.createTime.gt(LocalDateTime.parse(adminSearchCondDto.getSearchDate() + " 00:00:00.000", dateTimeFormatter));
            } else if (adminSearchCondDto.getCond2().equals("이전")) {
                return user.createTime.lt(LocalDateTime.parse(adminSearchCondDto.getSearchDate() + " 00:00:00.000", dateTimeFormatter));
            }
        }

        return null;
    }
}
