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
@Order(2)
public class StudentSecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChainForStudent(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/teacher/**").hasAnyRole("TEACHER")
                        .anyRequest().permitAll()
        );

        http.formLogin(login ->
                login
                .loginPage("/login")
                .usernameParameter("email")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/"));

        http.logout(logout->
                logout.logoutSuccessUrl("/"));

        return http.build();
    }

}
