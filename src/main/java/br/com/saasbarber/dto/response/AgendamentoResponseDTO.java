package br.com.saasbarber.dto.response;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(

    Long id,

    Long usuarioClienteId,
    String clienteNome,
    String clienteTelefone,

    Long barbeiroId,
    String barbeiroNome,

    Long servicoId,
    String servicoNome,

    LocalDateTime dataHora,
    String status

) {}
