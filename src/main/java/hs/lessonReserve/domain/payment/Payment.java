package hs.lessonReserve.domain.payment;

import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.domain.lesson.Lesson;
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
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lessonId")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Lesson lesson;
    private int price;
    private String impUid;
    private String merchantUid;

    private String paymentMethod; // card, bankTransfer,...
    private String paymentGateway; // kakao, danal,...
    private boolean lessonPolicyAgree;
    private boolean pgPolicyAgree;

    @OneToOne(mappedBy = "payment")
    private Apply apply;

    private String status; // payComplete, beforeTransfer
    private LocalDateTime cancelTime;

    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }

}
