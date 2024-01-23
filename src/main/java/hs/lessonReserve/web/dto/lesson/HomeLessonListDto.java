package hs.lessonReserve.web.dto.lesson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryProjection;
import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.user.Teacher;
import hs.lessonReserve.util.CustomFormatter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
public class HomeLessonListDto {

    private long id;

    private long teacherId;
    private String teacherName;
    private String teacherProfileImageUrl;

    private String name;
    private String lessonTime;
    private String price;
    private String address;

    private String lessonStartDate;
    private String lessonEndDate;
    private String applyEndDate;
    private String applyStatus;
    private boolean userApplyStatus;

    @QueryProjection
    public HomeLessonListDto(Lesson lesson, PrincipalDetails principalDetails) {
        id = lesson.getId();

        teacherId = lesson.getTeacher().getId();
        teacherName = lesson.getTeacher().getName();
        teacherProfileImageUrl = lesson.getTeacher().getProfileImageUrl();
        if (lesson.getRoadAddress() != null) {
            String[] roadAddressSplit = lesson.getRoadAddress().split(" ");
            address = roadAddressSplit[0] + " " + roadAddressSplit[1];
        }
        name = lesson.getName();
        lessonTime = lesson.getLessonTime();
        price = CustomFormatter.makePrice(lesson.getPrice());
        lessonStartDate = lesson.getLessonStartDate().toString().substring(0, 10);
        lessonEndDate = lesson.getLessonEndDate().toString().substring(0, 10);
        applyEndDate = lesson.getLessonStartDate().minusDays(3).toString().substring(0,10);
        applyStatus = lesson.getApplies().stream()
                .filter(a -> ApplyStatus.APPLY.equals(a.getApplyStatus()))
                .filter(a -> a.getStudent() != null)
                .collect(Collectors.toList()).size() + " / " + lesson.getMaximumStudentsNumber();

        List<Apply> applies = lesson.getApplies();
        userApplyStatus = false;
        if (principalDetails != null) {
            for (Apply apply : applies) {
                if (apply.getStudent() != null && apply.getStudent().getId() == principalDetails.getUser().getId() && apply.getApplyStatus() == ApplyStatus.APPLY) {
                    userApplyStatus = true;
                    break;
                }
            }
        }
    }

    public HomeLessonListDto(Lesson lesson) {
        id = lesson.getId();

        teacherId = lesson.getTeacher().getId();
        teacherName = lesson.getTeacher().getName();
        teacherProfileImageUrl = lesson.getTeacher().getProfileImageUrl();
        if (lesson.getRoadAddress() != null) {
            String[] roadAddressSplit = lesson.getRoadAddress().split(" ");
            address = roadAddressSplit[0] + " " + roadAddressSplit[1];
        }
        name = lesson.getName();
        lessonTime = lesson.getLessonTime();
        price = CustomFormatter.makePrice(lesson.getPrice());
        lessonStartDate = lesson.getLessonStartDate().toString().substring(0, 10);
        lessonEndDate = lesson.getLessonEndDate().toString().substring(0, 10);
        applyEndDate = lesson.getLessonStartDate().minusDays(3).toString().substring(0,10);
        applyStatus = lesson.getApplies().stream()
                .filter(a -> ApplyStatus.APPLY.equals(a.getApplyStatus()))
                .filter(a -> a.getStudent() != null)
                .collect(Collectors.toList()).size() + " / " + lesson.getMaximumStudentsNumber();
    }

}
