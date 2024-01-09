package hs.lessonReserve.service;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.domain.apply.ApplyRepository;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.lesson.LessonRepository;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.web.dto.lesson.StudentMyPageLessonListDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final LessonRepository lessonRepository;
    @PersistenceContext
    EntityManager em;

    public List<StudentMyPageLessonListDto> studentMyPageList(PrincipalDetails principalDetails) {
        StringBuffer sb = new StringBuffer();
        sb.append("select a.id, l.name, u.name, u.profileImageUrl, l.lessonTime, l.price, ");
        sb.append("DATE_FORMAT(l.lessonStartDate, '%Y-%m-%d'), DATE_FORMAT(l.lessonEndDate, '%Y-%m-%d'), DATE_FORMAT(DATE_ADD(l.lessonEndDate, INTERVAL -3 DAY), '%Y-%m-%d'), DATE_FORMAT(a.createTime, '%Y-%m-%d'), ");
        sb.append("a.applyStatus, l.id ");
        sb.append("from apply a ");
        sb.append("inner join lesson l ");
        sb.append("on a.lessonId = l.id ");
        sb.append("inner join user u ");
        sb.append("on l.teacherId = u.id ");
        sb.append("where a.studentId = ?");

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalDetails.getUser().getId());

        List<Object[]> resultList = query.getResultList();

        List<StudentMyPageLessonListDto> studentMyPageLessonListDtos = resultList.stream().map(rl -> {
            return new StudentMyPageLessonListDto(rl);
        }).collect(Collectors.toList());


//        long studentId = principalDetails.getUser().getId();
//        List<Apply> studentMyPageList = applyRepository.findAllByStudentIdOrderByCreateTime(studentId);
//        ArrayList<StudentMyPageLessonListDto> studentMyPageLessonListDtos = new ArrayList<>();
//
//        for (Apply apply : studentMyPageList) {
//            StudentMyPageLessonListDto studentMyPageLessonListDto = StudentMyPageLessonListDto.builder()
//                    .applyId(apply.getId())
//                    .teacher(apply.getLesson().getTeacher())
//                    .name(apply.getLesson().getName())
//                    .maximumStudentsNumber(apply.getLesson().getMaximumStudentsNumber())
//                    .lessonTime(apply.getLesson().getLessonTime())
//                    .price(apply.getLesson().getPrice())
//                    .lessonStartDate(apply.getLesson().getLessonStartDate().toString().substring(0, 10))
//                    .lessonEndDate(apply.getLesson().getLessonEndDate().toString().substring(0, 10))
//                    .applyEndDate(apply.getLesson().getLessonStartDate().minusDays(3).toString().substring(0, 10))
//                    .applyCreateTime(apply.getCreateTime().toString().substring(0, 10))
//                    .applyStatus(apply.getLesson().getApplies().size() + " / " + apply.getLesson().getMaximumStudentsNumber())
//                    .userApplyStatus(apply.getApplyStatus())
//                    .lessonId(apply.getLesson().getId())
//                    .build();
//            studentMyPageLessonListDtos.add(studentMyPageLessonListDto);
//        }

        return studentMyPageLessonListDtos;
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

    @Transactional
    @Scheduled(cron = "1 0 0 * * *", zone = "Asia/Seoul") // 매일 0시 0분 1초에 체크
    public void ApplyCompletedCheck() {
        List<Apply> applies = applyRepository.mApplyCompletedCheckList();
        for (Apply apply : applies) {
            apply.setApplyStatus(ApplyStatus.COMPLETED);
        }
    }


    public Apply findApply(long applyId) {
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> {
            throw new CustomException("없는 수강신청입니다.");
        });
        return apply;
    }
}
