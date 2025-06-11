package com.gman97.cinemachain.config;

import com.gman97.cinemachain.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(urlPattern -> urlPattern
                        .requestMatchers("/admin/**", "/cinema-halls/**").hasAuthority(Role.ADMIN.getAuthority())
                        .requestMatchers("/login", "/register", "/movies/**", "/images/**", "/css/**").permitAll()
                        .requestMatchers("/seats/**", "/bay-ticket").authenticated()
                        .anyRequest().denyAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/movies"))
                .logout(logout -> logout.logoutSuccessUrl("/movies"))
                .build();
    }
}
