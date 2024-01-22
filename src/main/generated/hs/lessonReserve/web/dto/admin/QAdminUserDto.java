package hs.lessonReserve.web.dto.admin;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * hs.lessonReserve.web.dto.admin.QAdminUserDto is a Querydsl Projection type for AdminUserDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAdminUserDto extends ConstructorExpression<AdminUserDto> {

    private static final long serialVersionUID = 1810654650L;

    public QAdminUserDto(com.querydsl.core.types.Expression<? extends hs.lessonReserve.domain.user.User> user) {
        super(AdminUserDto.class, new Class<?>[]{hs.lessonReserve.domain.user.User.class}, user);
    }

}

