package br.com.saasbarber.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO(

    @NotNull(message = "Barbearia é obrigatória")
    Long barbeariaId,

    @NotNull(message = "Barbeiro é obrigatório")
    Long barbeiroId,

    @NotNull(message = "Serviço é obrigatório")
    Long servicoId,

    @NotNull(message = "Data e hora são obrigatórias")
    @Future(message = "A data do agendamento deve ser no futuro")
    LocalDateTime dataHora

) {}
