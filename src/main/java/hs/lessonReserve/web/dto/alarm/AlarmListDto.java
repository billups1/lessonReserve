package hs.lessonReserve.web.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmListDto {

    private String message;
    private long gatherApplyId;
    private String gatherApplyAcceptStatus;
    private long alarmId;

}
