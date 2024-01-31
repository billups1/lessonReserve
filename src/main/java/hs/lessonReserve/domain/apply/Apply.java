package hs.lessonReserve.domain.apply;

import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.payment.Payment;
import hs.lessonReserve.domain.user.User;
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
public class Apply {

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
    private User student;

    @Enumerated(EnumType.STRING)
    private ApplyStatus applyStatus;

    @OneToOne
    @JoinColumn(name = "paymentId")
    private Payment payment;

    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }

}
