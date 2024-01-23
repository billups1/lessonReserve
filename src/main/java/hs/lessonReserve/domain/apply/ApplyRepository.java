package hs.lessonReserve.domain.apply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long>, ApplyRepositoryCustom {

    @Modifying
    @Query(value = "update Apply set applyStatus = 'CANCEL' where lessonId = :lessonId and studentID = :studentId and applyStatus = 'APPLY'", nativeQuery = true)
    void mCancelApply(long lessonId, long studentId);

    List<Apply> findAllByLessonId(long lessonId);
    Page<Apply> findAllByLessonId(long lessonId, Pageable pageable);

    @Query(value = "select * from Apply as a inner join Lesson as l on a.lessonId = l.id where l.lessonEndDate <= now() and a.applyStatus = 'APPLY'", nativeQuery = true)
    List<Apply> mApplyCompletedCheckList(); // 현재 기준 레슨 종료일이 경과하고, applyStatus가 APPLY인 APPLY들을, 수강완료(COMPLETED) 처리하기 위함

    Page<Apply> findAllByStudentIdOrderByIdDesc(long studentId, Pageable pageable);
}
