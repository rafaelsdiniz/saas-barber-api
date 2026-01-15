package br.com.saasbarber.service;

import br.com.saasbarber.domain.model.Servico;
import br.com.saasbarber.dto.request.ServicoRequestDTO;
import br.com.saasbarber.dto.response.ServicoResponseDTO;
import br.com.saasbarber.repository.BarbeariaRepository;
import br.com.saasbarber.repository.ServicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ServicoService {

    @Inject
    ServicoRepository repository;

    @Inject
    BarbeariaRepository barbeariaRepository;

    @Transactional
    public ServicoResponseDTO criar(ServicoRequestDTO dto) {
        Servico s = new Servico();
        s.setNome(dto.nome());
        s.setPreco(dto.preco());
        s.setDuracaoMinutos(dto.duracaoMinutos());
        s.setBarbearia(barbeariaRepository.findById(dto.barbeariaId()));
        repository.persist(s);
        return toResponse(s);
    }

    public List<ServicoResponseDTO> listarPorBarbearia(Long barbeariaId) {
        return repository.findByBarbeariaId(barbeariaId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ServicoResponseDTO toResponse(Servico s) {
        return new ServicoResponseDTO(
                s.getId(),
                s.getNome(),
                s.getPreco(),
                s.getDuracaoMinutos()
        );
    }
}
