package br.com.saasbarber.service;

import br.com.saasbarber.domain.model.Email;
import br.com.saasbarber.domain.model.UsuarioCliente;
import br.com.saasbarber.dto.request.UsuarioClienteRequestDTO;
import br.com.saasbarber.dto.response.UsuarioClienteResponseDTO;
import br.com.saasbarber.repository.ClienteRepository;
import br.com.saasbarber.repository.UsuarioClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class UsuarioClienteService {

    @Inject
    UsuarioClienteRepository repository;

    @Inject
    ClienteRepository clienteRepository;

    @Transactional
    public UsuarioClienteResponseDTO criar(UsuarioClienteRequestDTO dto) {

        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (repository.findByClienteId(dto.clienteId()).isPresent()) {
            throw new RuntimeException("Cliente já possui login");
        }

        UsuarioCliente uc = new UsuarioCliente();
        uc.setEmail(new Email(dto.email()));
        uc.setSenha(BCrypt.hashpw(dto.senha(), BCrypt.gensalt()));
        uc.setCliente(clienteRepository.findById(dto.clienteId()));

        repository.persist(uc);
        return toResponse(uc);
    }

    public UsuarioClienteResponseDTO buscarPorCliente(Long clienteId) {
        UsuarioCliente uc = repository.findByClienteId(clienteId)
                .orElseThrow(() -> new RuntimeException("Login não encontrado"));
        return toResponse(uc);
    }

    @Transactional
    public void desativar(Long id) {
        UsuarioCliente uc = repository.findById(id);
        if (uc == null) throw new RuntimeException("Login não encontrado");
        uc.setAtivo(false);
    }

    private UsuarioClienteResponseDTO toResponse(UsuarioCliente uc) {
        return new UsuarioClienteResponseDTO(
                uc.getId(),
                uc.getEmail().getEndereco(),
                uc.getCliente().getId()
        );
    }
}
