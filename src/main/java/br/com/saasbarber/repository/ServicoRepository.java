package br.com.saasbarber.repository;

import br.com.saasbarber.domain.model.Servico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ServicoRepository implements PanacheRepository<Servico> {

    public List<Servico> findByBarbeariaId(Long barbeariaId) {
        return find("barbearia.id = ?1", barbeariaId).list();
    }
}
