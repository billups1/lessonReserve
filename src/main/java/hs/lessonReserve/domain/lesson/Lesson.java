package hs.lessonReserve.domain.lesson;

import hs.lessonReserve.domain.lessonStudent.LessonStudent;
import hs.lessonReserve.domain.user.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "teacherId")
    private Teacher teacher;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<LessonStudent> students;

    private String name;
    private String content;
    private int maximumStudentsNumber;
    private String lessonTime;
    private int price;

    private LocalDateTime lessonStartDate;
    private LocalDateTime lessonEndDate;

    @Transient
    private LocalDateTime applyEndDate;
    @Transient
    private String applyStatus;
    @Transient
    private int studentNumber;
    @Transient
    private boolean userApplyStatus;


    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }


}
