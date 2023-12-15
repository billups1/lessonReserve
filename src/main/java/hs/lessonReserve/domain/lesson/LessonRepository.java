package hs.lessonReserve.domain.lesson;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long>, LessonRepositoryCustom {

    @Query(value = "select * from lesson where lessonStartDate >= DATE_ADD(NOW(), INTERVAL 3 day) order by lessonStartDate", nativeQuery = true)
    Page<Lesson> mHomeLessonList(Pageable pageable);

    @Query(value = "select l.* from lesson l inner join (select * from apply where studentId = :studentId) ls on l.id = ls.lessonId", nativeQuery = true)
    List<Lesson> mStudentMyPageList(long studentId);

    @Query(value = "select * from lesson where teacherId = :teacherId", nativeQuery = true)
    List<Lesson> mTeacherMyPageList(long teacherId);

    @Modifying
    @Query(value = "delete from lesson where id = :lessonId and teacherId = :teacherId", nativeQuery = true)
    void mDeleteLesson(long lessonId, long teacherId);

}
