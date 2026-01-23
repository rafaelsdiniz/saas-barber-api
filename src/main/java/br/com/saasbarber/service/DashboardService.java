package br.com.saasbarber.service;

import java.time.LocalDate;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.com.saasbarber.domain.model.Agendamento;
import br.com.saasbarber.domain.model.Usuario;
import br.com.saasbarber.dto.response.BarbeariaResumoDTO;
import br.com.saasbarber.dto.response.DashboardResponseDTO;
import br.com.saasbarber.dto.response.DashboardStatsDTO;
import br.com.saasbarber.dto.response.ProximoAgendamentoDTO;
import br.com.saasbarber.repository.AgendamentoRepository;
import br.com.saasbarber.repository.BarbeariaRepository;
import br.com.saasbarber.repository.BarbeiroRepository;
import br.com.saasbarber.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DashboardService {

    @Inject
    AgendamentoRepository agendamentoRepository;

    @Inject
    BarbeiroRepository barbeiroRepository;

    @Inject
    BarbeariaRepository barbeariaRepository;

    public DashboardResponseDTO carregar(Long barbeariaId) {

        var hojeInicio = LocalDate.now().atStartOfDay();
        var hojeFim = LocalDate.now().atTime(23, 59, 59);

        long agendamentosHoje =
            agendamentoRepository.count(
                "barbearia.id = ?1 and dataHora between ?2 and ?3",
                barbeariaId, hojeInicio, hojeFim
            );

        long atendimentosAtivos =
            agendamentoRepository.count(
                "barbearia.id = ?1 and status = 'CONFIRMADO'",
                barbeariaId
            );

        double faturamentoHoje =
            agendamentoRepository.somarFaturamentoHoje(barbeariaId);

        long barbeirosAtivos =
            barbeiroRepository.count(
                "barbearia.id = ?1 and ativo = true",
                barbeariaId
            );

        var proximos =
            agendamentoRepository
                .listarProximos(barbeariaId)
                .stream()
                .map(a -> new ProximoAgendamentoDTO(
                    a.getDataHora().toLocalTime().toString(),
                    a.getCliente().getNome(),
                    a.getServico().getNome(),
                    a.getBarbeiro().getNome()
                ))
                .toList();

        var barbearia = barbeariaRepository.findById(barbeariaId);

        return new DashboardResponseDTO(
            new BarbeariaResumoDTO(barbearia.getNome(), barbearia.getAtivo()),
            new DashboardStatsDTO(
                agendamentosHoje,
                atendimentosAtivos,
                faturamentoHoje,
                barbeirosAtivos
            ),
            proximos
        );
    }
}
