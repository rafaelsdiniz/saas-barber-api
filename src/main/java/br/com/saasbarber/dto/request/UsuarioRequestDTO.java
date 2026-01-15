package br.com.saasbarber.dto.request;

import br.com.saasbarber.domain.enums.PerfilUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 100)
    String email,

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    String senha,

    @NotNull(message = "Perfil é obrigatório")
    PerfilUsuario perfil,

    @NotNull(message = "Barbearia é obrigatória")
    Long barbeariaId

) {}
