package hs.lessonReserve.domain.gather.gatherApply;

import hs.lessonReserve.web.dto.admin.AdminGatherApplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<GatherApply> findAllByGatherIdOrderByIdDesc(Long gatherId, Pageable pageable);
}
