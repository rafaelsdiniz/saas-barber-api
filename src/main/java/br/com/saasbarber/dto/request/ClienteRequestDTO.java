package br.com.saasbarber.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    String nome,

    @Size(max = 100)
    String email,

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 20)
    String telefone,

    @NotNull(message = "Barbearia é obrigatória")
    Long barbeariaId

) {}
