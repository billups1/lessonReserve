package hs.lessonReserve.domain.lesson;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hs.lessonReserve.web.dto.lesson.HomeLessonListDto;
import hs.lessonReserve.web.dto.lesson.LessonSearchCondDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static hs.lessonReserve.domain.lesson.QLesson.lesson;

@Repository
@RequiredArgsConstructor
public class LessonRepositoryImpl implements LessonRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Lesson> mHomeLessonList(LessonSearchCondDto lessonSearchCondDto) {
        return queryFactory.selectFrom(lesson)
                .where(lessonStartDate3DaysAgo(),
                        searchCond(lessonSearchCondDto))
                .fetch();

    }

    private BooleanExpression lessonStartDate3DaysAgo() {
        return lesson.lessonStartDate.goe(LocalDateTime.now().minusDays(3));
    }

    private BooleanExpression searchCond(LessonSearchCondDto lessonSearchCondDto) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        if (lessonSearchCondDto.getCond1() == null || lessonSearchCondDto.getCond1().equals("none") || lessonSearchCondDto.getSearchText().isEmpty()) {
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
