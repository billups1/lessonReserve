package hs.lessonReserve.domain.lesson;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.web.dto.lesson.HomeLessonListDto;
import hs.lessonReserve.web.dto.lesson.LessonSearchCondDto;
import hs.lessonReserve.web.dto.lesson.QHomeLessonListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static hs.lessonReserve.domain.lesson.QLesson.lesson;

@Repository
@RequiredArgsConstructor
public class LessonRepositoryImpl implements LessonRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Lesson> mHomeLessonList(LessonSearchCondDto lessonSearchCondDto, PrincipalDetails principalDetails) {
        List<Lesson> lessons = queryFactory.selectFrom(lesson)
                .where(lessonStartDate3DaysAgo(), searchCond(lessonSearchCondDto))
                .orderBy(lesson.lessonStartDate.asc())
                .fetch();

        return lessons;

    }

    private BooleanExpression lessonStartDate3DaysAgo() {
        return lesson.lessonStartDate.goe(LocalDateTime.now().minusDays(3));
    }

    private BooleanExpression searchCond(LessonSearchCondDto lessonSearchCondDto) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        if (lessonSearchCondDto.getCond1() == null || lessonSearchCondDto.getCond1().equals("none")) {
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
}
