package h1r0ku.security;

import h1r0ku.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors().disable()
                    .csrf().disable()
                    .authorizeHttpRequests()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/", "index", "**/css/**", "**/js/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/v3/api-docs").permitAll()
                        .anyRequest().authenticated()
                    .and()
                        .headers()
                            .frameOptions()
                                .sameOrigin()
                    .and()
                        .sessionManagement()
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                        .authenticationProvider(authenticationProvider)
                        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }
}
