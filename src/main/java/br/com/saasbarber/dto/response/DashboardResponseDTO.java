package br.com.saasbarber.dto.response;

import java.util.List;

public record DashboardResponseDTO(
    BarbeariaResumoDTO barbearia,
    DashboardStatsDTO stats,
    List<ProximoAgendamentoDTO> proximosAgendamentos
) {}

