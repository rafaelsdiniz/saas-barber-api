package br.com.saasbarber.dto.response;

public record ProximoAgendamentoDTO(
    String hora,
    String cliente,
    String servico,
    String barbeiro
) {}

