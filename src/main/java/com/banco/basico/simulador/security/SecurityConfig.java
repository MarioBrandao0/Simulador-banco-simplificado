package com.banco.basico.simulador.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/api/usuario/criar").permitAll()

                        .requestMatchers("/api/transacao/**").hasAnyAuthority("LOJISTA", "CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/api/transacao/transferir").hasAuthority("CLIENTE")

                        .anyRequest().permitAll()
                )

                //Aqui colocamos as exceptions que rodam antes de chegar nos controllers
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, exceptionAcesso) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json");

                            response.getWriter().write("""
                                {
                                  "status": 403,
                                  "mensagem": "Acesso negado"
                                }
                            """);
                        })

                        .authenticationEntryPoint(
                                (request, response, erroAutenticacao) -> {
                                    response.setStatus(
                                            HttpStatus.UNAUTHORIZED.value()
                                    );

                                    response.setContentType(
                                            MediaType.APPLICATION_JSON_VALUE
                                    );

                                    response.setCharacterEncoding("UTF-8");

                                    response.getWriter().write("""
                                        {
                                          "status": 401,
                                          "mensagem": "Você precisa estar autenticado"
                                        }
                                    """);
                                }
                        )
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }
}
