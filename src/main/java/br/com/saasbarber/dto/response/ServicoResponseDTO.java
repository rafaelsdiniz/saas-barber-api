package br.com.saasbarber.dto.response;

public record ServicoResponseDTO(

    Long id,
    String nome,
    Double preco,
    Integer duracaoMinutos

) {}
