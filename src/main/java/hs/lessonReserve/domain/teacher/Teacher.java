package hs.lessonReserve.domain.teacher;

import hs.lessonReserve.domain.certificate.Certificate;
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
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String password;
    private String role;
    private String name;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Certificate> certificates;
    private LocalDateTime createTime;

    @PrePersist
    public void createTime() {
        this.createTime = LocalDateTime.now();
    }
    @PrePersist
    public void setRole() {
        this.role = "ROLE_TEACHER";
    }

}
