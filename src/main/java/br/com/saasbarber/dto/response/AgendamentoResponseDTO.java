package br.com.saasbarber.dto.response;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(

    Long id,

    // 👤 CLIENTE
    Long clienteId,
    String clienteNome,
    String clienteTelefone,

    // ✂️ BARBEIRO
    Long barbeiroId,
    String barbeiroNome,

    // 💼 SERVIÇO
    Long servicoId,
    String servicoNome,

    // ⏰ DATA / STATUS
    LocalDateTime dataHora,
    String status

) {}
