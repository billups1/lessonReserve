package hs.lessonReserve.web.dto.teacher;

import hs.lessonReserve.domain.user.Teacher;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeacherModifyDto {

    private String email;
    private String name;
    private String password;
    private String profileImageUrl;
    private MultipartFile profileImageFile;
    private List<String> certificateImageUrls;
    private List<MultipartFile> certificates;
    private boolean profileImageDelete;

    public TeacherModifyDto(Teacher teacher, ArrayList<String> certificatePaperImageUrls) {
        this.email = teacher.getEmail();
        this.name = teacher.getName();
        this.profileImageUrl = teacher.getProfileImageUrl();
        this.certificateImageUrls = certificatePaperImageUrls;
    }
}
