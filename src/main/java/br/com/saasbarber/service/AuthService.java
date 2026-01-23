package br.com.saasbarber.service;

import br.com.saasbarber.domain.model.Usuario;
import br.com.saasbarber.dto.request.LoginRequestDTO;
import br.com.saasbarber.dto.response.LoginResponseDTO;
import br.com.saasbarber.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class AuthService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    TokenService tokenService;

    public LoginResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository
                .findByEmailEndereco(dto.email()) // ✅ seu método real
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));

        if (!BCrypt.checkpw(dto.senha(), usuario.getSenha())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        String token = tokenService.gerarToken(usuario);

        return new LoginResponseDTO(
                token,
                usuario.getEmail().getEndereco(),
                usuario.getPerfil().name()
        );
    }
}
