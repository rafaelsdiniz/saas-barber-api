package br.com.saasbarber.service;

import br.com.saasbarber.domain.enums.StatusAgendamento;
import br.com.saasbarber.domain.model.Agendamento;
import br.com.saasbarber.domain.model.UsuarioCliente;
import br.com.saasbarber.dto.request.AgendamentoRequestDTO;
import br.com.saasbarber.dto.response.AgendamentoResponseDTO;
import br.com.saasbarber.repository.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AgendamentoService {

    @Inject
    AgendamentoRepository repository;

    @Inject
    BarbeariaRepository barbeariaRepository;

    @Inject
    UsuarioClienteRepository usuarioClienteRepository;

    @Inject
    BarbeiroRepository barbeiroRepository;

    @Inject
    ServicoRepository servicoRepository;

    @Transactional
    public AgendamentoResponseDTO criar(@Valid AgendamentoRequestDTO dto) {

        UsuarioCliente usuarioCliente =
                usuarioClienteRepository.findById(dto.usuarioClienteId());

        if (usuarioCliente == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        var servico = servicoRepository.findById(dto.servicoId());
        if (servico == null) {
            throw new RuntimeException("Serviço não encontrado");
        }

        LocalDateTime inicio = dto.dataHora();
        LocalDateTime fim = inicio.plusMinutes(servico.getDuracaoMinutos());

        boolean ocupado = !repository
                .findByBarbeiroAndPeriodo(dto.barbeiroId(), inicio, fim)
                .isEmpty();

        if (ocupado) {
            throw new RuntimeException("Horário indisponível para este barbeiro");
        }

        Agendamento a = new Agendamento();
        a.setBarbearia(barbeariaRepository.findById(dto.barbeariaId()));
        a.setUsuarioCliente(usuarioCliente);
        a.setBarbeiro(barbeiroRepository.findById(dto.barbeiroId()));
        a.setServico(servico);
        a.setDataHora(dto.dataHora());
        a.setStatus(StatusAgendamento.PENDENTE);

        repository.persist(a);
        return toResponse(a);
    }

    public List<AgendamentoResponseDTO> listarPorBarbeiro(Long barbeiroId) {
        return repository.find("barbeiro.id", barbeiroId)
                .list()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void confirmar(Long id) {
        Agendamento a = repository.findById(id);
        if (a == null) {
            throw new RuntimeException("Agendamento não encontrado");
        }
        a.setStatus(StatusAgendamento.CONFIRMADO);
    }

    @Transactional
    public void cancelar(Long id) {
        Agendamento a = repository.findById(id);
        if (a == null) {
            throw new RuntimeException("Agendamento não encontrado");
        }
        a.setStatus(StatusAgendamento.CANCELADO);
    }

    private AgendamentoResponseDTO toResponse(Agendamento a) {
    var uc = a.getUsuarioCliente();

    return new AgendamentoResponseDTO(
            a.getId(),
            uc.getId(),
            uc.getCliente().getNome(),
            uc.getCliente().getTelefone().getNumero(),
            a.getBarbeiro().getId(),
            a.getBarbeiro().getNome(),
            a.getServico().getId(),
            a.getServico().getNome(),
            a.getDataHora(),
            a.getStatus().name()
    );
}

}
