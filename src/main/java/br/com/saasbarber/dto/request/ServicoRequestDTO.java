package br.com.saasbarber.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ServicoRequestDTO(

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    String nome,

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    Double preco,

    @NotNull(message = "Duração é obrigatória")
    @Positive(message = "Duração deve ser maior que zero")
    Integer duracaoMinutos,

    @NotNull(message = "Barbearia é obrigatória")
    Long barbeariaId

) {}
