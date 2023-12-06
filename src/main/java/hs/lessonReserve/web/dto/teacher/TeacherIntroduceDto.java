package hs.lessonReserve.web.dto.teacher;

import hs.lessonReserve.domain.LessonReview.LessonReview;
import hs.lessonReserve.domain.user.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TeacherIntroduceDto {
    private Teacher teacher;
    private List<LessonReview> lessonReviews;
    private int averageScore;
}
