package hs.lessonReserve.config;

import hs.lessonReserve.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class StudentSecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain filterChainForStudent(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/teacher/lesson/**", "/teacher/mypage/**", "/api/teacher/lesson/**").hasAnyRole("TEACHER")
                        .anyRequest().permitAll()
        );

        http.formLogin(login ->
                login
                        .loginPage("/login")
                        .usernameParameter("email")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/"));

        http.logout(logout ->
                logout.logoutSuccessUrl("/"));

        http.oauth2Login(login ->
                login.loginPage("/login").defaultSuccessUrl("/")
                        .userInfoEndpoint(point ->
                                point.userService(principalOauth2UserService)));

        return http.build();
    }

}
