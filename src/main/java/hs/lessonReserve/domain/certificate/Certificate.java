package hs.lessonReserve.domain.certificate;

import hs.lessonReserve.domain.user.Teacher;
import hs.lessonReserve.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "certificate")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "userId")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Teacher teacher;

    private String certificatePaperImageUrl;

    private String certificateName; // 자격증명

    private String certificateField; // 종목

}
