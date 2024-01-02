package hs.lessonReserve.config;

import hs.lessonReserve.config.oauth.PrincipalOauth2UserService;
import hs.lessonReserve.handler.auth.LoginFailHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final LoginFailHandler loginFailHandler;

    @Bean
    public SecurityFilterChain filterChainForStudent(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/teacher/lesson/**", "/teacher/mypage/**", "/api/teacher/lesson/**").hasAnyRole("TEACHER")
                        .requestMatchers("/gather/create/**").hasAnyRole("TEACHER", "STUDENT")
                        .anyRequest().permitAll()
        );

        http.formLogin(login ->
                login
                        .loginPage("/login")
                        .usernameParameter("email")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .failureHandler(loginFailHandler));

        http.logout(logout ->
                logout.logoutSuccessUrl("/"));

        http.oauth2Login(login ->
                login.loginPage("/login").defaultSuccessUrl("/")
                        .userInfoEndpoint(point ->
                                point.userService(principalOauth2UserService)));

        return http.build();
    }

}
