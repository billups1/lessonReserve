package hs.lessonReserve.domain.alarm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarm_GatherApply is a Querydsl query type for Alarm_GatherApply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarm_GatherApply extends EntityPathBase<Alarm_GatherApply> {

    private static final long serialVersionUID = 398682699L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarm_GatherApply alarm_GatherApply = new QAlarm_GatherApply("alarm_GatherApply");

    public final QAlarm _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime;

    //inherited
    public final StringPath domain;

    // inherited
    public final hs.lessonReserve.domain.user.QUser fromUser;

    public final hs.lessonReserve.domain.gather.gatherApply.QGatherApply gatherApply;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath status;

    // inherited
    public final hs.lessonReserve.domain.user.QUser toUser;

    public QAlarm_GatherApply(String variable) {
        this(Alarm_GatherApply.class, forVariable(variable), INITS);
    }

    public QAlarm_GatherApply(Path<? extends Alarm_GatherApply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarm_GatherApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarm_GatherApply(PathMetadata metadata, PathInits inits) {
        this(Alarm_GatherApply.class, metadata, inits);
    }

    public QAlarm_GatherApply(Class<? extends Alarm_GatherApply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QAlarm(type, metadata, inits);
        this.createTime = _super.createTime;
        this.domain = _super.domain;
        this.fromUser = _super.fromUser;
        this.gatherApply = inits.isInitialized("gatherApply") ? new hs.lessonReserve.domain.gather.gatherApply.QGatherApply(forProperty("gatherApply"), inits.get("gatherApply")) : null;
        this.id = _super.id;
        this.status = _super.status;
        this.toUser = _super.toUser;
    }

}

