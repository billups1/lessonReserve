package hs.lessonReserve.domain.alarm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findByToUserIdOrderByIdDesc(long toUserId);

}
