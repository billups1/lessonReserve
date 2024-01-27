package hs.lessonReserve.web.dto.auth;

import hs.lessonReserve.domain.user.Student;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudentModifyDto {

    @Email
    @NotBlank
    private String email;
    @Size(min = 1, max = 15)
    @NotBlank
    private String name;
    @NotBlank
    @Size(min = 3, max = 20)
    private String password;
    private String passwordRecheck;
    private String profileImageUrl;
    private MultipartFile profileImageFile;
    private boolean profileImageDelete;

    public StudentModifyDto(Student student) {
        this.email = student.getEmail();
        this.name = student.getName();
        this.profileImageUrl = student.getProfileImageUrl();
    }
}
