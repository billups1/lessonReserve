package hs.lessonReserve.domain.alarm;

import hs.lessonReserve.domain.gather.gatherApply.GatherApply;
import hs.lessonReserve.domain.lesson.Lesson;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Alarm_LessonApply extends Alarm{

    @ManyToOne
    @JoinColumn(name = "lessonId")
    private Lesson lesson;

}
