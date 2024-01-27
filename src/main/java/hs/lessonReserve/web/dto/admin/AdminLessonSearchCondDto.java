package hs.lessonReserve.web.dto.admin;

import lombok.Data;

@Data
public class AdminLessonSearchCondDto {
    private String cond1;
    private String cond2;
    private String searchText;
    private String searchDate;

}
