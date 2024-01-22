package hs.lessonReserve.domain.apply;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hs.lessonReserve.web.dto.admin.AdminApplyDto;
import hs.lessonReserve.web.dto.admin.AdminSearchCondDto;
import hs.lessonReserve.web.dto.admin.QAdminApplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<AdminApplyDto> adminApplyDtos(Pageable pageable, AdminSearchCondDto adminSearchCondDto) {
        List<AdminApplyDto> adminApplyDtos = queryFactory.select(
                        new QAdminApplyDto(apply)
                ).from(apply)
                .where(searchCond(adminSearchCondDto))
                .orderBy(apply.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        System.out.println(adminApplyDtos);

        Long totalCount = queryFactory.select(lesson.count())
                .from(lesson)
                .where(searchCond(adminSearchCondDto))
                .fetchOne();

        return new PageImpl<>(adminApplyDtos, pageable, totalCount);
    }

    private BooleanExpression searchCond(AdminSearchCondDto adminSearchCondDto) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        if (adminSearchCondDto == null || adminSearchCondDto.getCond1() == null || adminSearchCondDto.getCond1().equals("none")) {
            return null;
        } else if (adminSearchCondDto.getCond1().equals("lessonName")) {
            return apply.lesson.name.contains(adminSearchCondDto.getSearchText());
        } else if (adminSearchCondDto.getCond1().equals("student")) {
            return apply.student.name.contains(adminSearchCondDto.getSearchText());
        } else if (adminSearchCondDto.getCond1().equals("teacher")) {
            return apply.lesson.teacher.name.contains(adminSearchCondDto.getSearchText());
        } else if (adminSearchCondDto.getCond1().equals("price")) {
            if (adminSearchCondDto.getCond2().equals("이상")) {
                return apply.lesson.price.goe(Integer.parseInt(adminSearchCondDto.getSearchText()));
            } else if (adminSearchCondDto.getCond2().equals("이하")) {
                return apply.lesson.price.loe(Integer.parseInt(adminSearchCondDto.getSearchText()));
            }
        } else if (adminSearchCondDto.getCond1().equals("paymentCreateTime")) {
            if (adminSearchCondDto.getCond2().equals("이후")) {
                return lesson.lessonStartDate.gt(LocalDateTime.parse(adminSearchCondDto.getSearchDate() + " 00:00:00.000", dateTimeFormatter));
            } else if (adminSearchCondDto.getCond2().equals("이전")) {
                return lesson.lessonStartDate.lt(LocalDateTime.parse(adminSearchCondDto.getSearchDate() + " 00:00:00.000", dateTimeFormatter));
            }
        } else if (adminSearchCondDto.getCond1().equals("applyStatus")) {
            return apply.lesson.name.contains(adminSearchCondDto.getSearchText());
        } else if (adminSearchCondDto.getCond1().equals("paymentStatus")) {
            return apply.lesson.name.contains(adminSearchCondDto.getSearchText());
        }

        return null;
    }

}
