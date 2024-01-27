package hs.lessonReserve.domain.alarm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlarm_LessonApply is a Querydsl query type for Alarm_LessonApply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarm_LessonApply extends EntityPathBase<Alarm_LessonApply> {

    private static final long serialVersionUID = -1108076242L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlarm_LessonApply alarm_LessonApply = new QAlarm_LessonApply("alarm_LessonApply");

    public final QAlarm _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime;

    //inherited
    public final StringPath domain;

    // inherited
    public final hs.lessonReserve.domain.user.QUser fromUser;

    //inherited
    public final NumberPath<Long> id;

    public final hs.lessonReserve.domain.lesson.QLesson lesson;

    //inherited
    public final StringPath status;

    // inherited
    public final hs.lessonReserve.domain.user.QUser toUser;

    public QAlarm_LessonApply(String variable) {
        this(Alarm_LessonApply.class, forVariable(variable), INITS);
    }

    public QAlarm_LessonApply(Path<? extends Alarm_LessonApply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlarm_LessonApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlarm_LessonApply(PathMetadata metadata, PathInits inits) {
        this(Alarm_LessonApply.class, metadata, inits);
    }

    public QAlarm_LessonApply(Class<? extends Alarm_LessonApply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QAlarm(type, metadata, inits);
        this.createTime = _super.createTime;
        this.domain = _super.domain;
        this.fromUser = _super.fromUser;
        this.id = _super.id;
        this.lesson = inits.isInitialized("lesson") ? new hs.lessonReserve.domain.lesson.QLesson(forProperty("lesson"), inits.get("lesson")) : null;
        this.status = _super.status;
        this.toUser = _super.toUser;
    }

}

