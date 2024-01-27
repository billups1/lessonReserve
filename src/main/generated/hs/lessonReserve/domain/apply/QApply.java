package hs.lessonReserve.domain.apply;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApply is a Querydsl query type for Apply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApply extends EntityPathBase<Apply> {

    private static final long serialVersionUID = 1737403633L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApply apply = new QApply("apply");

    public final EnumPath<hs.lessonReserve.constant.ApplyStatus> applyStatus = createEnum("applyStatus", hs.lessonReserve.constant.ApplyStatus.class);

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final hs.lessonReserve.domain.lesson.QLesson lesson;

    public final hs.lessonReserve.domain.payment.QPayment payment;

    public final hs.lessonReserve.domain.user.QUser student;

    public QApply(String variable) {
        this(Apply.class, forVariable(variable), INITS);
    }

    public QApply(Path<? extends Apply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApply(PathMetadata metadata, PathInits inits) {
        this(Apply.class, metadata, inits);
    }

    public QApply(Class<? extends Apply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lesson = inits.isInitialized("lesson") ? new hs.lessonReserve.domain.lesson.QLesson(forProperty("lesson"), inits.get("lesson")) : null;
        this.payment = inits.isInitialized("payment") ? new hs.lessonReserve.domain.payment.QPayment(forProperty("payment"), inits.get("payment")) : null;
        this.student = inits.isInitialized("student") ? new hs.lessonReserve.domain.user.QUser(forProperty("student")) : null;
    }

}

