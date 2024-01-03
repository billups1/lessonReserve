package hs.lessonReserve.domain.gather.gatherApply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GatherApplyRepository extends JpaRepository<GatherApply, Long> {

}
