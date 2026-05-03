//package com.resume.scanner.SecurityConfig;
//
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//
//
//    @Bean
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorizeRequests ->
//                authorizeRequests.requestMatchers("/h2-console/**").permitAll()
//                        .requestMatchers("/api/sign-in").permitAll()
//                        .anyRequest().authenticated());
//        http.sessionManagement(
//                session ->
//                        session.sessionCreationPolicy(
//                                SessionCreationPolicy.STATELESS)
//        );
//
//        http.csrf(AbstractHttpConfigurer::disable);
//
//        return http.build();
//    }
//
//}
