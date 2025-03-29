package com.lopezmorales.app.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/atm/login", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/atm/home", "/atm/denominaciones", "/atm/retirar").authenticated() // 👈 permite retiro solo para usuarios logueados
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/atm/login")
                        .loginProcessingUrl("/atm/login")
                        .defaultSuccessUrl("/atm/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/atm/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // 👈 Solo si estás usando JS para enviar POST sin token CSRF

        return http.build();
    }

    // Usuario en memoria
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
