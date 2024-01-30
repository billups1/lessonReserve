package hs.lessonReserve.domain.lesson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.domain.user.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "teacherId")
    @JsonIgnoreProperties({"lessons", "certificates"})
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Teacher teacher;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"lesson", "student"})
    private List<Apply> applies;

    private String name;
    private String content;
    private int maximumStudentsNumber;
    private String lessonTime;
    private int price;

    private String roadAddress;

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

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", maximumStudentsNumber=" + maximumStudentsNumber +
                ", lessonTime='" + lessonTime + '\'' +
                ", price=" + price +
                ", lessonStartDate=" + lessonStartDate +
                ", lessonEndDate=" + lessonEndDate +
                ", applyEndDate=" + applyEndDate +
                ", applyStatus='" + applyStatus + '\'' +
                ", studentNumber=" + studentNumber +
                ", userApplyStatus=" + userApplyStatus +
                ", createTime=" + createTime +
                '}';
    }
}
