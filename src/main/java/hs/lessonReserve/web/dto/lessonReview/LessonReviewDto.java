package hs.lessonReserve.web.dto.lessonReview;

import lombok.Data;

@Data
public class LessonReviewDto {

    // 0.5~5 별도 유효성검사
    private float score;

    private String content;

}
