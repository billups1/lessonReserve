package hs.lessonReserve.domain.alarm;

import hs.lessonReserve.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@SuperBuilder
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "toUserId")
    private User toUser;
    @ManyToOne
    @JoinColumn(name = "fromUserId")
    private User fromUser;
    private String domain; //
    private String status; // UN_READ, READ
    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
        this.status = "UN_READ";
    }

}
