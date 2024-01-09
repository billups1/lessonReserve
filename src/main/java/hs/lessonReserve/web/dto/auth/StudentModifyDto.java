package hs.lessonReserve.web.dto.auth;

import hs.lessonReserve.domain.user.Student;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudentModifyDto {

    private String email;
    private String name;
    private String password;
    private String profileImageUrl;
    private MultipartFile profileImageFile;
    private boolean profileImageDelete;

    public StudentModifyDto(Student student) {
        this.email = student.getEmail();
        this.name = student.getName();
        this.profileImageUrl = student.getProfileImageUrl();
    }
}
