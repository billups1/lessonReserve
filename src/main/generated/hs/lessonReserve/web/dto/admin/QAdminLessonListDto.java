package hs.lessonReserve.web.dto.admin;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * hs.lessonReserve.web.dto.admin.QAdminLessonListDto is a Querydsl Projection type for AdminLessonListDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAdminLessonListDto extends ConstructorExpression<AdminLessonListDto> {

    private static final long serialVersionUID = 1350652719L;

    public QAdminLessonListDto(com.querydsl.core.types.Expression<? extends hs.lessonReserve.domain.lesson.Lesson> lesson) {
        super(AdminLessonListDto.class, new Class<?>[]{hs.lessonReserve.domain.lesson.Lesson.class}, lesson);
    }

}

