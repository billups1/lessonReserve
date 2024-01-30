package hs.lessonReserve.domain.alarm;

import hs.lessonReserve.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@SuperBuilder
@Table(name = "alarm")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "toUserId")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User toUser;
    @ManyToOne
    @JoinColumn(name = "fromUserId")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User fromUser;
    private String domain; // GatherApply, GatherApplyReject, GatherApplyAccept, LessonApply, LessonPaymentCancel
    private String status; // UN_READ, READ
    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
        this.status = "UN_READ";
    }

}
