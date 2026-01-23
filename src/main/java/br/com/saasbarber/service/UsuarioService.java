package br.com.saasbarber.service;

import br.com.saasbarber.domain.model.Email;
import br.com.saasbarber.domain.model.Usuario;
import br.com.saasbarber.dto.request.UsuarioRequestDTO;
import br.com.saasbarber.dto.response.UsuarioResponseDTO;
import br.com.saasbarber.repository.BarbeariaRepository;
import br.com.saasbarber.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Inject
    BarbeariaRepository barbeariaRepository;

    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {

        if (repository.findByEmailEndereco(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario u = new Usuario();
        u.setEmail(new Email(dto.email()));
        u.setSenha(BCrypt.hashpw(dto.senha(), BCrypt.gensalt()));
        u.setPerfil(dto.perfil());
        u.setBarbearia(barbeariaRepository.findById(dto.barbeariaId()));

        repository.persist(u);
        return toResponse(u);
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario u = repository.findById(id);
        if (u == null) throw new RuntimeException("Usuário não encontrado");
        return toResponse(u);
    }

    public List<UsuarioResponseDTO> listarPorBarbearia(Long barbeariaId) {
        return repository.find("barbearia.id", barbeariaId)
                .list()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void desativar(Long id) {
        Usuario u = repository.findById(id);
        if (u == null) throw new RuntimeException("Usuário não encontrado");
        u.setAtivo(false);
    }

    private UsuarioResponseDTO toResponse(Usuario u) {
        return new UsuarioResponseDTO(
                u.getId(),
                u.getEmail().getEndereco(),
                u.getPerfil().name()
        );
    }
}
