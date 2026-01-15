package br.com.saasbarber.repository;

import br.com.saasbarber.domain.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public List<Cliente> findByBarbeariaId(Long barbeariaId) {
        return find("barbearia.id = ?1", barbeariaId).list();
    }
}
