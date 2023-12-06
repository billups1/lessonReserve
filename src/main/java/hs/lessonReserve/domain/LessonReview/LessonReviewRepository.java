package hs.lessonReserve.domain.LessonReview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonReviewRepository extends JpaRepository<LessonReview, Long> {

    @Query(value = "select lr.* from lessonreview lr inner join lesson l on lr.lessonId = l.id where l.teacherId = :teacherId", nativeQuery = true)
    List<LessonReview> mFindByTeacher(long teacherId);
}
