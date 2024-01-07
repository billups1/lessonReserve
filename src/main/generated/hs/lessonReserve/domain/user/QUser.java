package hs.lessonReserve.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1174238481L;

    public static final QUser user = new QUser("user");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final ListPath<hs.lessonReserve.domain.gather.gatherApply.GatherApply, hs.lessonReserve.domain.gather.gatherApply.QGatherApply> gatherApplies = this.<hs.lessonReserve.domain.gather.gatherApply.GatherApply, hs.lessonReserve.domain.gather.gatherApply.QGatherApply>createList("gatherApplies", hs.lessonReserve.domain.gather.gatherApply.GatherApply.class, hs.lessonReserve.domain.gather.gatherApply.QGatherApply.class, PathInits.DIRECT2);

    public final ListPath<hs.lessonReserve.domain.gather.gatherUser.GatherUser, hs.lessonReserve.domain.gather.gatherUser.QGatherUser> gatherUsers = this.<hs.lessonReserve.domain.gather.gatherUser.GatherUser, hs.lessonReserve.domain.gather.gatherUser.QGatherUser>createList("gatherUsers", hs.lessonReserve.domain.gather.gatherUser.GatherUser.class, hs.lessonReserve.domain.gather.gatherUser.QGatherUser.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final StringPath provider = createString("provider");

    public final StringPath providerId = createString("providerId");

    public final StringPath role = createString("role");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

