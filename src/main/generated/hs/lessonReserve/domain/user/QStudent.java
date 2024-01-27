package hs.lessonReserve.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudent is a Querydsl query type for Student
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudent extends EntityPathBase<Student> {

    private static final long serialVersionUID = 1793010485L;

    public static final QStudent student = new QStudent("student");

    public final QUser _super = new QUser(this);

    //inherited
    public final StringPath address = _super.address;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    //inherited
    public final StringPath email = _super.email;

    //inherited
    public final ListPath<hs.lessonReserve.domain.gather.gatherApply.GatherApply, hs.lessonReserve.domain.gather.gatherApply.QGatherApply> gatherApplies = _super.gatherApplies;

    //inherited
    public final ListPath<hs.lessonReserve.domain.gather.gatherUser.GatherUser, hs.lessonReserve.domain.gather.gatherUser.QGatherUser> gatherUsers = _super.gatherUsers;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ListPath<hs.lessonReserve.domain.apply.Apply, hs.lessonReserve.domain.apply.QApply> lessons = this.<hs.lessonReserve.domain.apply.Apply, hs.lessonReserve.domain.apply.QApply>createList("lessons", hs.lessonReserve.domain.apply.Apply.class, hs.lessonReserve.domain.apply.QApply.class, PathInits.DIRECT2);

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath password = _super.password;

    //inherited
    public final StringPath phone = _super.phone;

    //inherited
    public final StringPath postcode = _super.postcode;

    //inherited
    public final StringPath profileImageUrl = _super.profileImageUrl;

    //inherited
    public final StringPath provider = _super.provider;

    //inherited
    public final StringPath providerId = _super.providerId;

    //inherited
    public final StringPath role = _super.role;

    public QStudent(String variable) {
        super(Student.class, forVariable(variable));
    }

    public QStudent(Path<? extends Student> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudent(PathMetadata metadata) {
        super(Student.class, metadata);
    }

}

