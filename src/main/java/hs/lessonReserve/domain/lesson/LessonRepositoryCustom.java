package hs.lessonReserve.domain.lesson;

import hs.lessonReserve.web.dto.lesson.HomeLessonListDto;
import hs.lessonReserve.web.dto.lesson.LessonSearchCondDto;

import java.util.List;

public interface LessonRepositoryCustom {
    List<Lesson> mHomeLessonList(LessonSearchCondDto lessonSearchCondDto);
}
