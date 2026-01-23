package br.com.saasbarber.dto.response;

public record DashboardStatsDTO(
    long agendamentosHoje,
    long atendimentosAtivos,
    double faturamentoHoje,
    long barbeirosAtivos
) {}

