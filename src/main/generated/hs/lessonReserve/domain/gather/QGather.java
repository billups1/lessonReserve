package hs.lessonReserve.domain.gather;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGather is a Querydsl query type for Gather
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGather extends EntityPathBase<Gather> {

    private static final long serialVersionUID = 860350673L;

    public static final QGather gather = new QGather("gather");

    public final StringPath address = createString("address");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final ListPath<hs.lessonReserve.domain.gather.gatherApply.GatherApply, hs.lessonReserve.domain.gather.gatherApply.QGatherApply> gatherApplies = this.<hs.lessonReserve.domain.gather.gatherApply.GatherApply, hs.lessonReserve.domain.gather.gatherApply.QGatherApply>createList("gatherApplies", hs.lessonReserve.domain.gather.gatherApply.GatherApply.class, hs.lessonReserve.domain.gather.gatherApply.QGatherApply.class, PathInits.DIRECT2);

    public final ListPath<GatherUser, QGatherUser> gatherUsers = this.<GatherUser, QGatherUser>createList("gatherUsers", GatherUser.class, QGatherUser.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maximumParticipantNumber = createNumber("maximumParticipantNumber", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath representativeImageUrl = createString("representativeImageUrl");

    public QGather(String variable) {
        super(Gather.class, forVariable(variable));
    }

    public QGather(Path<? extends Gather> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGather(PathMetadata metadata) {
        super(Gather.class, metadata);
    }

}

