package br.com.saasbarber.repository;

import br.com.saasbarber.domain.enums.StatusAgendamento;
import br.com.saasbarber.domain.model.Agendamento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@ApplicationScoped
public class AgendamentoRepository implements PanacheRepository<Agendamento> {

    /**
     * ======================================================
     * 🔒 JÁ EXISTENTE – NÃO MEXER
     * ======================================================
     */
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

    /**
     * ======================================================
     * 📅 AGENDAMENTOS DE HOJE (BARBEARIA)
     * ======================================================
     */
    public long countHojePorBarbearia(Long barbeariaId) {
        LocalDate hoje = LocalDate.now();

        return count(
            "barbearia.id = ?1 and dataHora between ?2 and ?3",
            barbeariaId,
            hoje.atStartOfDay(),
            hoje.atTime(LocalTime.MAX)
        );
    }

    /**
     * ======================================================
     * ⏱️ ATENDIMENTOS ATIVOS (CONFIRMADOS)
     * ======================================================
     */
    public long countAtivos(Long barbeariaId) {
        return count(
            "barbearia.id = ?1 and status = ?2",
            barbeariaId,
            StatusAgendamento.CONFIRMADO
        );
    }

    /**
     * ======================================================
     * 💰 FATURAMENTO DE HOJE
     * ======================================================
     */
    public double somarFaturamentoHoje(Long barbeariaId) {

        LocalDate hoje = LocalDate.now();

        Double total = getEntityManager()
            .createQuery("""
                select sum(s.preco)
                from Agendamento a
                join a.servico s
                where a.barbearia.id = :barbeariaId
                  and a.status = :status
                  and a.dataHora between :inicio and :fim
            """, Double.class)
            .setParameter("barbeariaId", barbeariaId)
            .setParameter("status", StatusAgendamento.CONFIRMADO)
            .setParameter("inicio", hoje.atStartOfDay())
            .setParameter("fim", hoje.atTime(LocalTime.MAX))
            .getSingleResult();

        return total != null ? total : 0.0;
    }

    /**
     * ======================================================
     * 🔜 PRÓXIMOS AGENDAMENTOS DO DIA
     * ======================================================
     */
    public List<Agendamento> listarProximos(Long barbeariaId) {

        LocalDate hoje = LocalDate.now();

        return find(
            "barbearia.id = ?1 and dataHora between ?2 and ?3 order by dataHora",
            barbeariaId,
            LocalDateTime.now(),
            hoje.atTime(LocalTime.MAX)
        ).page(0, 5).list(); // 🔹 limita a 5
    }
}
