package br.com.saasbarber.service;

import br.com.saasbarber.domain.model.Barbeiro;
import br.com.saasbarber.domain.model.Telefone;
import br.com.saasbarber.dto.request.BarbeiroRequestDTO;
import br.com.saasbarber.dto.response.BarbeiroResponseDTO;
import br.com.saasbarber.repository.BarbeiroRepository;
import br.com.saasbarber.repository.BarbeariaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class BarbeiroService {

    @Inject
    BarbeiroRepository repository;

    @Inject
    BarbeariaRepository barbeariaRepository;

    @Transactional
    public BarbeiroResponseDTO criar(BarbeiroRequestDTO dto) {
        Barbeiro b = new Barbeiro();
        b.setNome(dto.nome());
        b.setTelefone(new Telefone(dto.telefone()));
        b.setBarbearia(barbeariaRepository.findById(dto.barbeariaId()));
        repository.persist(b);
        return toResponse(b);
    }

    public List<BarbeiroResponseDTO> listarPorBarbearia(Long barbeariaId) {
        return repository.findByBarbeariaId(barbeariaId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void desativar(Long id) {
        Barbeiro b = repository.findById(id);
        if (b == null) throw new RuntimeException("Barbeiro não encontrado");
        b.setAtivo(false);
    }

    private BarbeiroResponseDTO toResponse(Barbeiro b) {
        return new BarbeiroResponseDTO(
                b.getId(),
                b.getNome(),
                b.getTelefone().getNumero()
        );
    }
}
