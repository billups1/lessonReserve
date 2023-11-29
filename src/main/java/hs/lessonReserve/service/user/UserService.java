package hs.lessonReserve.service.user;

import hs.lessonReserve.domain.User.User;
import hs.lessonReserve.domain.user.UserRepository;
import hs.lessonReserve.web.dto.auth.UserJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(UserJoinDto UserJoinDto) {

        User user = User.builder()
                .email(UserJoinDto.getEmail())
                .password(bCryptPasswordEncoder.encode(UserJoinDto.getPassword()))
                .name(UserJoinDto.getName())
                .build();

        userRepository.save(user);
    }

}
