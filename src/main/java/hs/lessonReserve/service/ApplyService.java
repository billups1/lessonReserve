package hs.lessonReserve.service;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.domain.apply.ApplyRepository;
import hs.lessonReserve.domain.apply.ApplyRepositoryImpl;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.lesson.LessonRepository;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.web.dto.admin.AdminApplyDto;
import hs.lessonReserve.web.dto.admin.AdminSearchCondDto;
import hs.lessonReserve.web.dto.admin.AdminLessonDto;
import hs.lessonReserve.web.dto.admin.AdminUserDto;
import hs.lessonReserve.web.dto.lesson.StudentMyPageLessonListDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final ApplyRepositoryImpl applyRepositoryImpl;
    private final LessonRepository lessonRepository;
    @PersistenceContext
    EntityManager em;

    public List<StudentMyPageLessonListDto> studentMyPageList(PrincipalDetails principalDetails) {
        StringBuffer sb = new StringBuffer();
        sb.append("select a.id, l.name, u.name, u.profileImageUrl, l.lessonTime, l.price, ");
        sb.append("DATE_FORMAT(l.lessonStartDate, '%Y-%m-%d'), DATE_FORMAT(l.lessonEndDate, '%Y-%m-%d'), DATE_FORMAT(DATE_ADD(l.lessonEndDate, INTERVAL -3 DAY), '%Y-%m-%d'), DATE_FORMAT(a.createTime, '%Y-%m-%d'), ");
        sb.append("a.applyStatus, l.id, a.paymentId ");
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

        return studentMyPageLessonListDtos;
    }


    @Transactional
    public void applyAvailabilityValidate(long lessonId, PrincipalDetails principalDetails) {

        if (principalDetails == null) {
            throw new CustomException("로그인 후 수강신청해 주세요.");
        }

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            throw new CustomException("없는 강의입니다.");
        });

        if (lesson.getApplies().size() >= lesson.getMaximumStudentsNumber()) {
            throw new CustomException("수강인원이 모두 찼습니다.");
        }

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

    public Page<AdminApplyDto> adminApplyDtosByLessonId(long lessonId, Pageable pageable) {

        Page<Apply> applies = applyRepository.findAllByLessonId(lessonId, pageable);
        Page<AdminApplyDto> adminApplyDtos = applies.map(apply -> {
            return new AdminApplyDto(apply);
        });

        return adminApplyDtos;
    }

    public Page<AdminApplyDto> adminApplyDtos(Pageable pageable, AdminSearchCondDto adminSearchCondDto) {
        Page<AdminApplyDto> adminApplyDtos = applyRepositoryImpl.adminApplyDtos(pageable, adminSearchCondDto);
        return adminApplyDtos;
    }

    public AdminApplyDto adminApplyDto(long applyId) {
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> {
            throw new CustomException("없는 레슨신청입니다.");
        });
        AdminApplyDto adminApplyDto = new AdminApplyDto(apply);
        return adminApplyDto;
    }

    public AdminUserDto adminUserDtoStudentByApplyId(long applyId) {
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> {
            throw new CustomException("없는 레슨신청입니다.");
        });
        if (apply.getStudent() != null) {
            AdminUserDto adminUserDto = new AdminUserDto(apply.getStudent());
            return adminUserDto;
        }
        return null;
    }

    public AdminLessonDto adminLessonDtoByApplyId(long applyId) {
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> {
            throw new CustomException("없는 레슨신청입니다.");
        });
        AdminLessonDto adminLessonDto = new AdminLessonDto(apply.getLesson());
        return adminLessonDto;
    }
}
