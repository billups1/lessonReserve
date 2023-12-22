package hs.lessonReserve.domain.lesson;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLesson is a Querydsl query type for Lesson
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLesson extends EntityPathBase<Lesson> {

    private static final long serialVersionUID = 415808049L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLesson lesson = new QLesson("lesson");

    public final ListPath<hs.lessonReserve.domain.apply.Apply, hs.lessonReserve.domain.apply.QApply> applies = this.<hs.lessonReserve.domain.apply.Apply, hs.lessonReserve.domain.apply.QApply>createList("applies", hs.lessonReserve.domain.apply.Apply.class, hs.lessonReserve.domain.apply.QApply.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lessonEndDate = createDateTime("lessonEndDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lessonStartDate = createDateTime("lessonStartDate", java.time.LocalDateTime.class);

    public final StringPath lessonTime = createString("lessonTime");

    public final NumberPath<Integer> maximumStudentsNumber = createNumber("maximumStudentsNumber", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath roadAddress = createString("roadAddress");

    public final hs.lessonReserve.domain.user.QTeacher teacher;

    public QLesson(String variable) {
        this(Lesson.class, forVariable(variable), INITS);
    }

    public QLesson(Path<? extends Lesson> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLesson(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLesson(PathMetadata metadata, PathInits inits) {
        this(Lesson.class, metadata, inits);
    }

    public QLesson(Class<? extends Lesson> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.teacher = inits.isInitialized("teacher") ? new hs.lessonReserve.domain.user.QTeacher(forProperty("teacher")) : null;
    }

}

