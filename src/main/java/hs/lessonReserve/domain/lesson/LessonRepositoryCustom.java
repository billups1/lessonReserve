package hs.lessonReserve.domain.lesson;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.web.dto.lesson.HomeLessonListDto;
import hs.lessonReserve.web.dto.lesson.LessonSearchCondDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LessonRepositoryCustom {
    List<Lesson> mHomeLessonList(LessonSearchCondDto lessonSearchCondDto, PrincipalDetails principalDetails);
}
