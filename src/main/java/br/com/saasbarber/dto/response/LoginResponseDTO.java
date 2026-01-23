package br.com.saasbarber.dto.response;

public record LoginResponseDTO(
        String token,
        String email,
        String perfil
) {}
