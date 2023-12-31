package hs.lessonReserve.domain.apply;

import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "lessonId")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private User student;

    @Enumerated(EnumType.STRING)
    private ApplyStatus applyStatus;


    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }

}
