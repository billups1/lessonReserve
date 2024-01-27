package hs.lessonReserve.domain.lesson;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.util.CustomFormatter;
import hs.lessonReserve.web.dto.admin.AdminLessonListDto;
import hs.lessonReserve.web.dto.admin.AdminLessonSearchCondDto;
import hs.lessonReserve.web.dto.admin.QAdminLessonListDto;
import hs.lessonReserve.web.dto.lesson.LessonSearchCondDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static hs.lessonReserve.domain.lesson.QLesson.lesson;

@Repository
@RequiredArgsConstructor
public class LessonRepositoryImpl implements LessonRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Lesson> mHomeLessonList(LessonSearchCondDto lessonSearchCondDto) {
        List<Lesson> lessons = queryFactory.selectFrom(lesson)
                .where(lessonStartDate3DaysAgo(), searchCond(lessonSearchCondDto))
                .orderBy(lesson.lessonStartDate.asc())
                .fetch();

        return lessons;

    }

    public Page<AdminLessonListDto> mAdminLessonListDto(AdminLessonSearchCondDto adminLessonSearchCondDto, Pageable pageable) {
        List<AdminLessonListDto> adminLessonListDtos = queryFactory.select(
                        new QAdminLessonListDto(lesson)
                ).from(lesson)
                .where(adminSearchCond(adminLessonSearchCondDto))
                .orderBy(lesson.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(lesson.count())
                .from(lesson)
                .where(adminSearchCond(adminLessonSearchCondDto))
                .fetchOne();

        return new PageImpl<>(adminLessonListDtos, pageable, totalCount);
    }

    private BooleanExpression lessonStartDate3DaysAgo() {
        return lesson.lessonStartDate.goe(LocalDateTime.now().minusDays(3));
    }

    private BooleanExpression searchCond(LessonSearchCondDto lessonSearchCondDto) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        if (lessonSearchCondDto == null || lessonSearchCondDto.getCond1() == null || lessonSearchCondDto.getCond1().equals("none")) {
            return null;
        } else if (lessonSearchCondDto.getCond1().equals("lessonName")) {
            return lesson.name.contains(lessonSearchCondDto.getSearchText());
        } else if (lessonSearchCondDto.getCond1().equals("teacher")) {
            return lesson.teacher.name.contains(lessonSearchCondDto.getSearchText());
        } else if (lessonSearchCondDto.getCond1().equals("price")) {
            if (lessonSearchCondDto.getCond2().equals("이상")) {
                return lesson.price.goe(Integer.parseInt(lessonSearchCondDto.getSearchText()));
            } else if (lessonSearchCondDto.getCond2().equals("이하")) {
                return lesson.price.loe(Integer.parseInt(lessonSearchCondDto.getSearchText()));
            }
        } else if (lessonSearchCondDto.getCond1().equals("lessonStartDate")) {
            if (lessonSearchCondDto.getCond2().equals("이후")) {
                return lesson.lessonStartDate.gt(LocalDateTime.parse(lessonSearchCondDto.getSearchDate() + " 00:00:00.000", dateTimeFormatter));
            } else if (lessonSearchCondDto.getCond2().equals("이전")) {
                return lesson.lessonStartDate.lt(LocalDateTime.parse(lessonSearchCondDto.getSearchDate() + " 00:00:00.000", dateTimeFormatter));
            }
        } else if (lessonSearchCondDto.getCond1().equals("lessonEndDate")) {
            if (lessonSearchCondDto.getCond2().equals("이후")) {
                return lesson.lessonEndDate.gt(LocalDateTime.parse(lessonSearchCondDto.getSearchDate() + " 23:59:59.000", dateTimeFormatter));
            } else if (lessonSearchCondDto.getCond2().equals("이전")) {
                return lesson.lessonEndDate.lt(LocalDateTime.parse(lessonSearchCondDto.getSearchDate() + " 23:59:59.000", dateTimeFormatter));
            }
        }

        return null;
    }

    private BooleanExpression adminSearchCond(AdminLessonSearchCondDto adminLessonSearchCondDto) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        if (adminLessonSearchCondDto == null || adminLessonSearchCondDto.getCond1() == null || adminLessonSearchCondDto.getCond1().equals("none")) {
            return null;
        } else if (adminLessonSearchCondDto.getCond1().equals("lessonName")) {
            return lesson.name.contains(adminLessonSearchCondDto.getSearchText());
        } else if (adminLessonSearchCondDto.getCond1().equals("teacher")) {
            return lesson.teacher.name.contains(adminLessonSearchCondDto.getSearchText());
        } else if (adminLessonSearchCondDto.getCond1().equals("price")) {
            if (adminLessonSearchCondDto.getCond2().equals("이상")) {
                return lesson.price.goe(Integer.parseInt(adminLessonSearchCondDto.getSearchText()));
            } else if (adminLessonSearchCondDto.getCond2().equals("이하")) {
                return lesson.price.loe(Integer.parseInt(adminLessonSearchCondDto.getSearchText()));
            }
        } else if (adminLessonSearchCondDto.getCond1().equals("lessonStartDate")) {
            if (adminLessonSearchCondDto.getCond2().equals("이후")) {
                return lesson.lessonStartDate.gt(LocalDateTime.parse(adminLessonSearchCondDto.getSearchDate() + " 00:00:00.000", dateTimeFormatter));
            } else if (adminLessonSearchCondDto.getCond2().equals("이전")) {
                return lesson.lessonStartDate.lt(LocalDateTime.parse(adminLessonSearchCondDto.getSearchDate() + " 00:00:00.000", dateTimeFormatter));
            }
        } else if (adminLessonSearchCondDto.getCond1().equals("lessonEndDate")) {
            if (adminLessonSearchCondDto.getCond2().equals("이후")) {
                return lesson.lessonEndDate.gt(LocalDateTime.parse(adminLessonSearchCondDto.getSearchDate() + " 23:59:59.000", dateTimeFormatter));
            } else if (adminLessonSearchCondDto.getCond2().equals("이전")) {
                return lesson.lessonEndDate.lt(LocalDateTime.parse(adminLessonSearchCondDto.getSearchDate() + " 23:59:59.000", dateTimeFormatter));
            }
        }

        return null;
    }
}
