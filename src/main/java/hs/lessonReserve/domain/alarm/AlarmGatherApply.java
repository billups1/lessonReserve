package hs.lessonReserve.domain.alarm;

import hs.lessonReserve.domain.gather.gatherApply.GatherApply;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class AlarmGatherApply extends Alarm{

    @ManyToOne
    @JoinColumn(name = "gatherApplyId")
    private GatherApply gatherApply;

}
