package hs.lessonReserve.domain.LessonReview;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLessonReview is a Querydsl query type for LessonReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLessonReview extends EntityPathBase<LessonReview> {

    private static final long serialVersionUID = 1685731345L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLessonReview lessonReview = new QLessonReview("lessonReview");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final hs.lessonReserve.domain.lesson.QLesson lesson;

    public final NumberPath<Float> score = createNumber("score", Float.class);

    public final hs.lessonReserve.domain.user.QStudent student;

    public QLessonReview(String variable) {
        this(LessonReview.class, forVariable(variable), INITS);
    }

    public QLessonReview(Path<? extends LessonReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLessonReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLessonReview(PathMetadata metadata, PathInits inits) {
        this(LessonReview.class, metadata, inits);
    }

    public QLessonReview(Class<? extends LessonReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lesson = inits.isInitialized("lesson") ? new hs.lessonReserve.domain.lesson.QLesson(forProperty("lesson"), inits.get("lesson")) : null;
        this.student = inits.isInitialized("student") ? new hs.lessonReserve.domain.user.QStudent(forProperty("student")) : null;
    }

}

