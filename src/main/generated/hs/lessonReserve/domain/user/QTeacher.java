package hs.lessonReserve.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeacher is a Querydsl query type for Teacher
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeacher extends EntityPathBase<Teacher> {

    private static final long serialVersionUID = -2062388004L;

    public static final QTeacher teacher = new QTeacher("teacher");

    public final QUser _super = new QUser(this);

    public final ListPath<hs.lessonReserve.domain.certificate.Certificate, hs.lessonReserve.domain.certificate.QCertificate> certificates = this.<hs.lessonReserve.domain.certificate.Certificate, hs.lessonReserve.domain.certificate.QCertificate>createList("certificates", hs.lessonReserve.domain.certificate.Certificate.class, hs.lessonReserve.domain.certificate.QCertificate.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    //inherited
    public final StringPath email = _super.email;

    //inherited
    public final ListPath<hs.lessonReserve.domain.gather.GatherUser, hs.lessonReserve.domain.gather.QGatherUser> gatherUsers = _super.gatherUsers;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ListPath<hs.lessonReserve.domain.lesson.Lesson, hs.lessonReserve.domain.lesson.QLesson> lessons = this.<hs.lessonReserve.domain.lesson.Lesson, hs.lessonReserve.domain.lesson.QLesson>createList("lessons", hs.lessonReserve.domain.lesson.Lesson.class, hs.lessonReserve.domain.lesson.QLesson.class, PathInits.DIRECT2);

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath password = _super.password;

    //inherited
    public final StringPath profileImageUrl = _super.profileImageUrl;

    //inherited
    public final StringPath provider = _super.provider;

    //inherited
    public final StringPath providerId = _super.providerId;

    //inherited
    public final StringPath role = _super.role;

    public QTeacher(String variable) {
        super(Teacher.class, forVariable(variable));
    }

    public QTeacher(Path<? extends Teacher> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeacher(PathMetadata metadata) {
        super(Teacher.class, metadata);
    }

}

