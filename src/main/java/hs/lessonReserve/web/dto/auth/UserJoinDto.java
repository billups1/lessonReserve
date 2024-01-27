package hs.lessonReserve.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
public class UserJoinDto {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 3, max = 20)
    private String password;
    private String passwordRecheck;
    @Size(min = 1, max = 15)
    @NotBlank
    private String name;
    private String verificationCode;
    private MultipartFile profileImageFile;
    private List<MultipartFile> certificateImageFiles;

}
