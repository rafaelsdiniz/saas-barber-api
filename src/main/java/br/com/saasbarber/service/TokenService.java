package br.com.saasbarber.service;

import br.com.saasbarber.domain.model.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenService {

    public String gerarToken(Usuario usuario) {

        return Jwt.issuer("saas-barber") // ⚠️ TEM que bater com application.properties
                .subject(usuario.getEmail().getEndereco()) // ✅ Email correto

                // ✅ ROLES (ESSENCIAL)
                .groups(usuario.getPerfil().name())

                // Claims úteis
                .claim("userId", usuario.getId())
                .claim("email", usuario.getEmail().getEndereco())
                .claim("perfil", usuario.getPerfil().name())

                .expiresIn(60 * 60 * 8) // 8h
                .sign(); // 🔐 usa privateKey.pem
    }
}
