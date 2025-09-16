package com.wa.condominio.services.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wa.condominio.dto.auth.LoginRequest;
import com.wa.condominio.dto.auth.LoginResponse;
import com.wa.condominio.model.auth.IUsuarioRepository;
import com.wa.condominio.model.auth.Usuario;
import com.wa.condominio.security.JwtTokenProvider;

@Service
public class AuthService {

    private final IUsuarioRepository usuarioRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(IUsuarioRepository usuarioRepository, JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse authenticate(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsernameIgnoreCase(request.username())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtTokenProvider.generateToken(usuario.getUsername(), usuario.getRole());
        return new LoginResponse(token);
    }
}