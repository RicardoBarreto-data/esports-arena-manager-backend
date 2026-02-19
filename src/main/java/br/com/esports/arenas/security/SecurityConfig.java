package br.com.esports.arenas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            //usando fetch/AJAX
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // Recursos Públicos
                .requestMatchers(
                        "/login",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/vendor/**"
                ).permitAll()

                // APIs REST
                .requestMatchers("/api/**")
                    .permitAll()

                // Páginas Protegidas
                .requestMatchers("/dashboard-admin")
                    .hasRole("ADMINISTRADOR")

                .requestMatchers("/dashboard-organizador")
                    .hasRole("ORGANIZADOR")

                .requestMatchers("/torneios", "/times", "/partidas", "/rankings")
                    .hasAnyRole("ADMINISTRADOR", "ORGANIZADOR")
                    
                // Qualquer outra requisição
                .anyRequest().authenticated()
            )

            // Login
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {

                    boolean isAdmin = authentication.getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"));

                    if (isAdmin) {
                        response.sendRedirect("/dashboard-admin");
                    } else {
                        response.sendRedirect("/dashboard-organizador");
                    }
                })
                .permitAll()
            )

            // Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}