package hs.lessonReserve.domain.payment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = 1786728609L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

    public final hs.lessonReserve.domain.apply.QApply apply;

    public final DateTimePath<java.time.LocalDateTime> cancelTime = createDateTime("cancelTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath impUid = createString("impUid");

    public final hs.lessonReserve.domain.lesson.QLesson lesson;

    public final BooleanPath lessonPolicyAgree = createBoolean("lessonPolicyAgree");

    public final StringPath merchantUid = createString("merchantUid");

    public final StringPath paymentGateway = createString("paymentGateway");

    public final StringPath paymentMethod = createString("paymentMethod");

    public final BooleanPath pgPolicyAgree = createBoolean("pgPolicyAgree");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath status = createString("status");

    public final hs.lessonReserve.domain.user.QUser user;

    public QPayment(String variable) {
        this(Payment.class, forVariable(variable), INITS);
    }

    public QPayment(Path<? extends Payment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayment(PathMetadata metadata, PathInits inits) {
        this(Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.apply = inits.isInitialized("apply") ? new hs.lessonReserve.domain.apply.QApply(forProperty("apply"), inits.get("apply")) : null;
        this.lesson = inits.isInitialized("lesson") ? new hs.lessonReserve.domain.lesson.QLesson(forProperty("lesson"), inits.get("lesson")) : null;
        this.user = inits.isInitialized("user") ? new hs.lessonReserve.domain.user.QUser(forProperty("user")) : null;
    }

}

