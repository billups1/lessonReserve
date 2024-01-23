package hs.lessonReserve.web.dto.admin;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * hs.lessonReserve.web.dto.admin.QAdminGatherDto is a Querydsl Projection type for AdminGatherDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAdminGatherDto extends ConstructorExpression<AdminGatherDto> {

    private static final long serialVersionUID = -1022151670L;

    public QAdminGatherDto(com.querydsl.core.types.Expression<? extends hs.lessonReserve.domain.gather.Gather> gather) {
        super(AdminGatherDto.class, new Class<?>[]{hs.lessonReserve.domain.gather.Gather.class}, gather);
    }

    public QAdminGatherDto(com.querydsl.core.types.Expression<? extends hs.lessonReserve.domain.gather.Gather> gather, com.querydsl.core.types.Expression<Long> userId) {
        super(AdminGatherDto.class, new Class<?>[]{hs.lessonReserve.domain.gather.Gather.class, long.class}, gather, userId);
    }

}

