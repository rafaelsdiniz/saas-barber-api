package br.com.saasbarber.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

import br.com.saasbarber.domain.model.Usuario;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Optional<Usuario> findByEmailEndereco(String email) {
        return find("email.endereco", email).firstResultOptional();
    }
}
