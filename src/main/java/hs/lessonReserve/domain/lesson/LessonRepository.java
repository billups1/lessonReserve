package hs.lessonReserve.domain.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(value = "select * from lesson where lessonStartDate >= DATE_ADD(NOW(), INTERVAL 3 day) order by lessonStartDate", nativeQuery = true)
    List<Lesson> homeLessonList();

}
