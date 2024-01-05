package hs.lessonReserve.domain.gather.gatherApply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GatherApplyRepository extends JpaRepository<GatherApply, Long> {

    @Modifying
    @Query(value = "update gatherApply set acceptStatus = 'ACCEPT' where id = :gatherApplyId", nativeQuery = true)
    void mGatherApplyAccept(long gatherApplyId);

    @Modifying
    @Query(value = "update gatherApply set acceptStatus = 'REJECT' where id = :gatherApplyId", nativeQuery = true)
    void mGatherApplyReject(long gatherApplyId);
}
