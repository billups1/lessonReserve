package hs.lessonReserve.domain.gather;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatherRepository extends JpaRepository<Gather, Long> {

}
