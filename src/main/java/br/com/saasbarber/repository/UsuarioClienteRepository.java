package br.com.saasbarber.repository;

import br.com.saasbarber.domain.model.UsuarioCliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UsuarioClienteRepository implements PanacheRepository<UsuarioCliente> {

    public Optional<UsuarioCliente> findByEmail(String email) {
        return find("email.endereco", email).firstResultOptional();
    }

    public Optional<UsuarioCliente> findByClienteId(Long clienteId) {
        return find("cliente.id", clienteId).firstResultOptional();
    }
}
