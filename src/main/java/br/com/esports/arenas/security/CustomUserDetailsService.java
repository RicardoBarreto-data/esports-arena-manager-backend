package br.com.esports.arenas.security;

import br.com.esports.arenas.model.Usuario;
import br.com.esports.arenas.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

    if (usuario.getTipo() == null) {
        throw new RuntimeException("Usuário sem tipo definido");
    }

    return new User(
            usuario.getEmail(),
            usuario.getSenha(),
            Collections.singletonList(
         new SimpleGrantedAuthority("ROLE_" + usuario.getTipo().name().toUpperCase())
      )
    );
  }
}