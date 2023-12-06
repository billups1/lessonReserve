package hs.lessonReserve.config.oauth;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.user.Student;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println(userRequest.getClientRegistration().toString());
        System.out.println(userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttribute: " + oAuth2User.getAttributes());
        Map<String, String> properties = (Map<String, String>) oAuth2User.getAttributes().get("properties");

        String provider = userRequest.getClientRegistration().getRegistrationId(); //kakao
        String providerId = userRequest.getClientRegistration().getClientId();
        String username = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("bbcc");
        String role = "ROLE_STUDENT";
        String profileImageUrl = properties.get("profile_image");
        String nickname = properties.get("nickname");

        Student student = (Student) userRepository.findByEmail(username);

        if (student == null) {
            student = Student.builder()
                    .provider(provider)
                    .providerId(providerId)
                    .email(username) // 카카오: 이메일 받으려면 사업자등록 필요
                    .password(password)
                    .role(role)
                    .profileImageUrl(profileImageUrl)
                    .name(nickname)
                    .build();
            userRepository.save(student);
        }

        return new PrincipalDetails(student);
    }
}
