package hs.lessonReserve.web.dto.admin;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * hs.lessonReserve.web.dto.admin.QAdminApplyDto is a Querydsl Projection type for AdminApplyDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAdminApplyDto extends ConstructorExpression<AdminApplyDto> {

    private static final long serialVersionUID = 1742619819L;

    public QAdminApplyDto(com.querydsl.core.types.Expression<? extends hs.lessonReserve.domain.apply.Apply> apply) {
        super(AdminApplyDto.class, new Class<?>[]{hs.lessonReserve.domain.apply.Apply.class}, apply);
    }

}

