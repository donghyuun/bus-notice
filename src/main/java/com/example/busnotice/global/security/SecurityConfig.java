package com.example.busnotice.global.security;

import com.example.busnotice.global.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
            .csrf(AbstractHttpConfigurer::disable)
            .csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(
                auth ->
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                            "/h2-console/**",
                            "/swagger-ui/**",
                            "/swagger-resource",
                            "/v3/api-docs",
                            "/api/v1/users/**",
                            "/api/v1/schedule/**"
                        ).permitAll()
                        .anyRequest().authenticated()
            )
            // jwt filter
            .addFilterBefore(jwtAuthorizationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
