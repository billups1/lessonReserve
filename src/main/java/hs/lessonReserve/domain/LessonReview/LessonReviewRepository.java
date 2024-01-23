package hs.lessonReserve.domain.LessonReview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonReviewRepository extends JpaRepository<LessonReview, Long> {

    @Query(value = "select lr.* from LessonReview lr inner join Lesson l on lr.lessonId = l.id where l.teacherId = :teacherId", nativeQuery = true)
    List<LessonReview> mFindByTeacher(long teacherId);

    Page<LessonReview> findAllByLessonIdOrderByIdDesc(long lessonId, Pageable pageable);

    Page<LessonReview> findAllByStudentIdOrderByIdDesc(Long studentId, Pageable pageable);

    @Query(value = "select * from LessonReview lr inner join lesson l on lr.lessonId = l.id and l.teacherId = :teacherId", nativeQuery = true)
    Page<LessonReview> findAllByTeacherIdOrderByIdDesc(Long teacherId, Pageable pageable);
}
