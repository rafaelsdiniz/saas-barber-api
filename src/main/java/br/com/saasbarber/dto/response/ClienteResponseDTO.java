package br.com.saasbarber.dto.response;

public record ClienteResponseDTO(

    Long id,
    String nome,
    String email,
    String telefone

) {}
