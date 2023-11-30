package hs.lessonReserve.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
public class UserJoinDto {

    @Email
    @NotNull
    private String email;
    @NotNull
    @Size(min = 4, max = 20)
    private String password;
    @Size(min = 1, max = 15)
    @NotNull
    private String name;
    private MultipartFile profileImageFile;
    private List<MultipartFile> certificateImageFiles;

}
