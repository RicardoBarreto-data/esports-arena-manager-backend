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
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                // Libera arquivos estáticos e login
                .requestMatchers(
                        "/login.html",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/vendor/**"
                ).permitAll()

                // Libera endpoints públicos (se houver)
                .requestMatchers("/api/auth/**").permitAll()

                // Proteção por role
                .requestMatchers("/dashboard-admin.html")
                    .hasRole("ADMINISTRADOR")

                .requestMatchers("/dashboard-organizador.html")
                    .hasRole("ORGANIZADOR")
                    
                .requestMatchers("/api/dashboard/organizador")
                    .hasRole("ORGANIZADOR")

                // Qualquer outra requisição precisa estar autenticada
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {

                    boolean isAdmin = authentication.getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"));

                    if (isAdmin) {
                        response.sendRedirect("/dashboard-admin.html");
                    } else {
                        response.sendRedirect("/dashboard-organizador.html");
                    }
                })
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html?logout")
                .permitAll()
            );

        return http.build();
    }
}