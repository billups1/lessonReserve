package hs.lessonReserve.web.dto.admin;

import hs.lessonReserve.domain.LessonReview.LessonReview;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.user.Student;
import hs.lessonReserve.util.CustomFormatter;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
public class AdminLessonReviewDto {
    private long id;
    private Long lessonId;
    private String lessonName;
    private Long studentId;
    private String studentName;
    private float score;
    private String content;
    private String createTime;

    public AdminLessonReviewDto(LessonReview lessonReview) {
        this.id = lessonReview.getId();
        this.lessonId = lessonReview.getLesson().getId();
        this.lessonName = lessonReview.getLesson().getName();
        this.studentId = lessonReview.getStudent().getId();
        this.score = lessonReview.getScore();
        this.content = lessonReview.getContent();
        this.createTime = CustomFormatter.make_yyyyMMddHHmmss(lessonReview.getCreateTime());
    }
}
