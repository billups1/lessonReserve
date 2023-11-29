package hs.lessonReserve.web.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

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

}
