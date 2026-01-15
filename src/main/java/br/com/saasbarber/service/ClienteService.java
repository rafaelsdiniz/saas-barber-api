package br.com.saasbarber.service;

import br.com.saasbarber.domain.model.Cliente;
import br.com.saasbarber.domain.model.Email;
import br.com.saasbarber.domain.model.Telefone;
import br.com.saasbarber.dto.request.ClienteRequestDTO;
import br.com.saasbarber.dto.response.ClienteResponseDTO;
import br.com.saasbarber.repository.BarbeariaRepository;
import br.com.saasbarber.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository repository;

    @Inject
    BarbeariaRepository barbeariaRepository;

    @Transactional
    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        Cliente c = new Cliente();
        c.setNome(dto.nome());
        c.setTelefone(new Telefone(dto.telefone()));
        if (dto.email() != null) {
            c.setEmail(new Email(dto.email()));
        }
        c.setBarbearia(barbeariaRepository.findById(dto.barbeariaId()));
        repository.persist(c);
        return toResponse(c);
    }

    public List<ClienteResponseDTO> listarPorBarbearia(Long barbeariaId) {
        return repository.findByBarbeariaId(barbeariaId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void desativar(Long id) {
        Cliente c = repository.findById(id);
        if (c == null) throw new RuntimeException("Cliente não encontrado");
        c.setAtivo(false);
    }

    private ClienteResponseDTO toResponse(Cliente c) {
        return new ClienteResponseDTO(
                c.getId(),
                c.getNome(),
                c.getEmail() != null ? c.getEmail().getEndereco() : null,
                c.getTelefone().getNumero()
        );
    }
}
