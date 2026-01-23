package br.com.saasbarber.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BarbeiroRequestDTO(

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    String nome,

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 20)
    String telefone,

    @NotNull(message = "Barbearia é obrigatória")
    Long barbeariaId,

    /** URL da imagem já enviada para o R2 */
    String fotoUrl

) {}
