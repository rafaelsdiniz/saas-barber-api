package br.com.saasbarber.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BarbeariaRequestDTO(

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    String nome,

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 20)
    String telefone

) {}
