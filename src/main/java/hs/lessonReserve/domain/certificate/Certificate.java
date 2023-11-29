package hs.lessonReserve.domain.certificate;

import hs.lessonReserve.domain.teacher.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "teacherId")
    @ManyToOne
    private Teacher teacher;

    private String certificatePaperImageUrl;

    private String certificateName; // 자격증명

    private String certificateField; // 종목

}
