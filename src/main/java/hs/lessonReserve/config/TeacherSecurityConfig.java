package hs.lessonReserve.config;

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
@Order(1)
public class TeacherSecurityConfig {

    @Bean
    public SecurityFilterChain filterChainForTeacher(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.formLogin(login -> login
                .loginPage("/login/teacher")
                .usernameParameter("email")
                .loginProcessingUrl("/login/teacher")
                .defaultSuccessUrl("/"));

        return http.build();
    }

}
