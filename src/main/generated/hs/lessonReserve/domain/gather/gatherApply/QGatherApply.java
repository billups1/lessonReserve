package hs.lessonReserve.domain.gather.gatherApply;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGatherApply is a Querydsl query type for GatherApply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGatherApply extends EntityPathBase<GatherApply> {

    private static final long serialVersionUID = -1626209566L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGatherApply gatherApply = new QGatherApply("gatherApply");

    public final StringPath acceptStatus = createString("acceptStatus");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final hs.lessonReserve.domain.gather.QGather gather;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final hs.lessonReserve.domain.user.QUser user;

    public QGatherApply(String variable) {
        this(GatherApply.class, forVariable(variable), INITS);
    }

    public QGatherApply(Path<? extends GatherApply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGatherApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGatherApply(PathMetadata metadata, PathInits inits) {
        this(GatherApply.class, metadata, inits);
    }

    public QGatherApply(Class<? extends GatherApply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gather = inits.isInitialized("gather") ? new hs.lessonReserve.domain.gather.QGather(forProperty("gather")) : null;
        this.user = inits.isInitialized("user") ? new hs.lessonReserve.domain.user.QUser(forProperty("user")) : null;
    }

}

