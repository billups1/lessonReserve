package hs.lessonReserve.web.dto.lesson;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MakeLessonDto {

    private String lessonName;
    private String lessonContent;
    private int maximumStudentsNumber;
    private int price;
    private String lessonTime;
    private String lessonStartDate;
    private String lessonEndDate;

}
