package hs.lessonReserve.domain.LessonReview;

import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.user.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "lessonReview")
public class LessonReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "lessonId")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "studentId")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Student student;

    private float score;

    private String content;

    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }

}
