package hs.lessonReserve.domain.apply;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hs.lessonReserve.web.dto.admin.AdminApplyDto;
import hs.lessonReserve.web.dto.admin.AdminApplySearchCondDto;
import hs.lessonReserve.web.dto.admin.QAdminApplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static hs.lessonReserve.domain.apply.QApply.*;
import static hs.lessonReserve.domain.lesson.QLesson.lesson;

@Repository
@RequiredArgsConstructor
public class ApplyRepositoryImpl implements ApplyRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AdminApplyDto> adminApplyDtos(Pageable pageable, AdminApplySearchCondDto adminApplySearchCondDto) {
        List<AdminApplyDto> adminApplyDtos = queryFactory.select(
                        new QAdminApplyDto(apply)
                ).from(apply)
                .where(searchCond(adminApplySearchCondDto))
                .orderBy(apply.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        System.out.println(adminApplyDtos);

        Long totalCount = queryFactory.select(lesson.count())
                .from(lesson)
                .where(searchCond(adminApplySearchCondDto))
                .fetchOne();

        return new PageImpl<>(adminApplyDtos, pageable, totalCount);
    }

    private BooleanExpression searchCond(AdminApplySearchCondDto adminApplySearchCondDto) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        if (adminApplySearchCondDto == null || adminApplySearchCondDto.getCond1() == null || adminApplySearchCondDto.getCond1().equals("none")) {
            return null;
        } else if (adminApplySearchCondDto.getCond1().equals("lessonName")) {
            return apply.lesson.name.contains(adminApplySearchCondDto.getSearchText());
        } else if (adminApplySearchCondDto.getCond1().equals("student")) {
            return apply.student.name.contains(adminApplySearchCondDto.getSearchText());
        } else if (adminApplySearchCondDto.getCond1().equals("teacher")) {
            return apply.lesson.teacher.name.contains(adminApplySearchCondDto.getSearchText());
        } else if (adminApplySearchCondDto.getCond1().equals("price")) {
            if (adminApplySearchCondDto.getCond2().equals("이상")) {
                return apply.lesson.price.goe(Integer.parseInt(adminApplySearchCondDto.getSearchText()));
            } else if (adminApplySearchCondDto.getCond2().equals("이하")) {
                return apply.lesson.price.loe(Integer.parseInt(adminApplySearchCondDto.getSearchText()));
            }
        } else if (adminApplySearchCondDto.getCond1().equals("paymentCreateTime")) {
            if (adminApplySearchCondDto.getCond2().equals("이후")) {
                return lesson.lessonStartDate.gt(LocalDateTime.parse(adminApplySearchCondDto.getSearchDate() + " 00:00:00.000", dateTimeFormatter));
            } else if (adminApplySearchCondDto.getCond2().equals("이전")) {
                return lesson.lessonStartDate.lt(LocalDateTime.parse(adminApplySearchCondDto.getSearchDate() + " 00:00:00.000", dateTimeFormatter));
            }
        } else if (adminApplySearchCondDto.getCond1().equals("applyStatus")) {
            return apply.lesson.name.contains(adminApplySearchCondDto.getSearchText());
        } else if (adminApplySearchCondDto.getCond1().equals("paymentStatus")) {
            return apply.lesson.name.contains(adminApplySearchCondDto.getSearchText());
        }

        return null;
    }

}
