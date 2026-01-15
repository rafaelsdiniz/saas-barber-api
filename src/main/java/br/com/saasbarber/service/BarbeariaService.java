package br.com.saasbarber.service;

import br.com.saasbarber.domain.model.Barbearia;
import br.com.saasbarber.domain.model.Telefone;
import br.com.saasbarber.dto.request.BarbeariaRequestDTO;
import br.com.saasbarber.dto.response.BarbeariaResponseDTO;
import br.com.saasbarber.repository.BarbeariaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class BarbeariaService {

    @Inject
    BarbeariaRepository repository;

    @Transactional
    public BarbeariaResponseDTO criar(BarbeariaRequestDTO dto) {
        Barbearia b = new Barbearia();
        b.setNome(dto.nome());
        b.setTelefone(new Telefone(dto.telefone())); 
        repository.persist(b);
        return toResponse(b);
    }

    public BarbeariaResponseDTO buscarPorId(Long id) {
        Barbearia b = repository.findById(id);
        if (b == null) {
            throw new RuntimeException("Barbearia não encontrada");
        }
        return toResponse(b);
    }

    public List<BarbeariaResponseDTO> listar() {
        return repository.listAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public BarbeariaResponseDTO atualizar(Long id, BarbeariaRequestDTO dto) {
        Barbearia b = repository.findById(id);
        if (b == null) {
            throw new RuntimeException("Barbearia não encontrada");
        }
        b.setNome(dto.nome());
        b.setTelefone(new Telefone(dto.telefone())); 
        return toResponse(b);
    }

    @Transactional
    public void desativar(Long id) {
        Barbearia b = repository.findById(id);
        if (b == null) {
            throw new RuntimeException("Barbearia não encontrada");
        }
        b.setAtivo(false);
    }

    private BarbeariaResponseDTO toResponse(Barbearia b) {
        return new BarbeariaResponseDTO(
                b.getId(),
                b.getNome(),
                b.getTelefone().getNumero() 
        );
    }
}
