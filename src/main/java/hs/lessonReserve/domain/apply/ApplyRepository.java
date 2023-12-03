package hs.lessonReserve.domain.apply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    @Modifying
    @Query(value = "update apply set applyStatus = 'CANCEL' where lessonId = :lessonId and studentID = :studentId and applyStatus = 'APPLY'", nativeQuery = true)
    void mCancelApply(long lessonId, long studentId);

    List<Apply> findAllByLessonId(long lessonId);

    List<Apply> findAllByStudentId(long studentId);
}
