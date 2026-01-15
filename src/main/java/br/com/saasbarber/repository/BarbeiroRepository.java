package br.com.saasbarber.repository;

import br.com.saasbarber.domain.model.Barbeiro;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class BarbeiroRepository implements PanacheRepository<Barbeiro> {

    public List<Barbeiro> findByBarbeariaId(Long barbeariaId) {
        return find("barbearia.id = ?1", barbeariaId).list();
    }
}
