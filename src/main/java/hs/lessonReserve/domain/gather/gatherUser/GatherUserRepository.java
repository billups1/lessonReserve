package hs.lessonReserve.domain.gather.gatherUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface GatherUserRepository extends JpaRepository<GatherUser, Long> {

    List<GatherUser> findByGatherId(long gatherId);

}
