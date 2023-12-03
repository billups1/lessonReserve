package hs.lessonReserve.service.apply;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.domain.apply.ApplyRepository;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.lesson.LessonRepository;
import hs.lessonReserve.handler.ex.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final LessonRepository lessonRepository;

    public List<Apply> studentMyPageList(PrincipalDetails principalDetails) {

        long studentId = principalDetails.getUser().getId();
        List<Apply> studentMyPageList = applyRepository.findAllByStudentId(studentId);

        for (Apply apply : studentMyPageList) {
            Lesson lesson = apply.getLesson();
            lesson.setApplyEndDate(lesson.getLessonStartDate().minusDays(3));
            lesson.setApplyStatus(lesson.getApplies().size() + " / " + lesson.getMaximumStudentsNumber());
        }

        return studentMyPageList;
    }


    @Transactional
    public void makeApply(long lessonId, PrincipalDetails principalDetails) {

        if (principalDetails == null) {
            throw new CustomException("로그인 후 수강신청해 주세요.");
        }

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            throw new CustomException("없는 강의입니다.");
        });

        if (lesson.getApplies().size() >= lesson.getMaximumStudentsNumber()) {
            throw new CustomException("수강인원이 모두 찼습니다.");
        }

        Apply apply = Apply.builder()
                .lesson(lesson)
                .student(principalDetails.getUser())
                .applyStatus(ApplyStatus.APPLY)
                .build();

        applyRepository.save(apply);

    }

    @Transactional
    public void cancelApply(long lessonId, PrincipalDetails principalDetails) {
        long studentId = principalDetails.getUser().getId();
        applyRepository.mCancelApply(lessonId, studentId);
    }

}
