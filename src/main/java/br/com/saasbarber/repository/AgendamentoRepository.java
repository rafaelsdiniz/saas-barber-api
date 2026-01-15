package br.com.saasbarber.repository;

import br.com.saasbarber.domain.model.Agendamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AgendamentoRepository implements PanacheRepository<Agendamento> {

    public List<Agendamento> findByBarbeiroAndPeriodo(
            Long barbeiroId,
            LocalDateTime inicio,
            LocalDateTime fim
    ) {
        return find(
            "barbeiro.id = ?1 and dataHora between ?2 and ?3",
            barbeiroId, inicio, fim
        ).list();
    }
}
