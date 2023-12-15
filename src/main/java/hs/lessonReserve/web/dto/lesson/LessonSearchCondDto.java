package hs.lessonReserve.web.dto.lesson;

import lombok.Data;

@Data
public class LessonSearchCondDto {

    private String cond1;
    private String cond2;
    private String searchText;
    private String searchDate;

}
