package hs.lessonReserve.domain.gather;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGatherUser is a Querydsl query type for GatherUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGatherUser extends EntityPathBase<GatherUser> {

    private static final long serialVersionUID = 146634812L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGatherUser gatherUser = new QGatherUser("gatherUser");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final QGather gather;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath position = createString("position");

    public final hs.lessonReserve.domain.user.QUser user;

    public final DateTimePath<java.time.LocalDateTime> withdrawalDate = createDateTime("withdrawalDate", java.time.LocalDateTime.class);

    public QGatherUser(String variable) {
        this(GatherUser.class, forVariable(variable), INITS);
    }

    public QGatherUser(Path<? extends GatherUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGatherUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGatherUser(PathMetadata metadata, PathInits inits) {
        this(GatherUser.class, metadata, inits);
    }

    public QGatherUser(Class<? extends GatherUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gather = inits.isInitialized("gather") ? new QGather(forProperty("gather")) : null;
        this.user = inits.isInitialized("user") ? new hs.lessonReserve.domain.user.QUser(forProperty("user")) : null;
    }

}

