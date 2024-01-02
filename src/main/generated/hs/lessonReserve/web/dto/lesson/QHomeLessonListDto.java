package hs.lessonReserve.web.dto.lesson;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * hs.lessonReserve.web.dto.lesson.QHomeLessonListDto is a Querydsl Projection type for HomeLessonListDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QHomeLessonListDto extends ConstructorExpression<HomeLessonListDto> {

    private static final long serialVersionUID = 913804074L;

    public QHomeLessonListDto(com.querydsl.core.types.Expression<? extends hs.lessonReserve.domain.lesson.Lesson> lesson, com.querydsl.core.types.Expression<? extends hs.lessonReserve.config.auth.PrincipalDetails> principalDetails) {
        super(HomeLessonListDto.class, new Class<?>[]{hs.lessonReserve.domain.lesson.Lesson.class, hs.lessonReserve.config.auth.PrincipalDetails.class}, lesson, principalDetails);
    }

}

