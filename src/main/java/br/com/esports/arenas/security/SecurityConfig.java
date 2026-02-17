package br.com.esports.arenas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // Desabilita para facilitar os testes locais
        .authorizeHttpRequests(auth -> auth
            // Libera explicitamente as pastas e arquivos de estilo
            .requestMatchers("/login.html", "/css/**", "/js/**", "/style.css", "/vendor/**").permitAll()
            .requestMatchers("/api/auth/**").permitAll() 
            .anyRequest().authenticated() // Tudo o mais exige login
        )
        .formLogin(form -> form
            .loginPage("/login.html")
            .loginProcessingUrl("/login") // O 'action' do seu form de login deve ser /login
            .defaultSuccessUrl("/dashboard.html", true) // O 'true' força o redirecionamento
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login.html?logout")
            .permitAll()
        );

    return http.build();
}

// Ignora a segurança para recursos estáticos
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/style.css");
  }
}