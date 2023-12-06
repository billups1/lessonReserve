package hs.lessonReserve.domain.user;

import hs.lessonReserve.domain.LessonReview.LessonReview;
import hs.lessonReserve.domain.certificate.Certificate;
import hs.lessonReserve.domain.lesson.Lesson;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter @Getter
public class Teacher extends User{

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Certificate> certificates;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Lesson> lessons;

}
