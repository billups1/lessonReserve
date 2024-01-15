package hs.lessonReserve.config.auth;

import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new InternalAuthenticationServiceException("아이디 또는 비밀번호가 맞지 않습니다.");
        }

        PrincipalDetails principalDetails = new PrincipalDetails(user);
        return principalDetails;
    }
}
