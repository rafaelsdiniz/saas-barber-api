package br.com.saasbarber.service;

import br.com.saasbarber.domain.enums.PerfilUsuario;
import br.com.saasbarber.domain.enums.StatusAgendamento;
import br.com.saasbarber.domain.model.Agendamento;
import br.com.saasbarber.domain.model.Cliente;
import br.com.saasbarber.domain.model.Servico;
import br.com.saasbarber.domain.model.Usuario;
import br.com.saasbarber.dto.request.AgendamentoRequestDTO;
import br.com.saasbarber.dto.response.AgendamentoResponseDTO;
import br.com.saasbarber.repository.AgendamentoRepository;
import br.com.saasbarber.repository.BarbeariaRepository;
import br.com.saasbarber.repository.BarbeiroRepository;
import br.com.saasbarber.repository.ClienteRepository;
import br.com.saasbarber.repository.ServicoRepository;
import br.com.saasbarber.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AgendamentoService {

    @Inject
    AgendamentoRepository agendamentoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    BarbeariaRepository barbeariaRepository;

    @Inject
    BarbeiroRepository barbeiroRepository;

    @Inject
    ServicoRepository servicoRepository;

    @Inject
    JsonWebToken jwt;

    // ======================================================
    // CRIAR AGENDAMENTO
    // ======================================================
   @Transactional
public AgendamentoResponseDTO criar(@Valid AgendamentoRequestDTO dto) {

    // 🔑 Usuário autenticado
    String email = jwt.getSubject();
    Usuario usuarioLogado = usuarioRepository
            .findByEmailEndereco(email)
            .orElseThrow(() -> new RuntimeException("Usuário não autenticado"));

    // 👤 Cliente SEMPRE vem do usuário logado
    Cliente cliente = clienteRepository.findByUsuarioId(usuarioLogado.getId());

    if (cliente == null) {
        throw new RuntimeException("Cliente não vinculado ao usuário logado");
    }

    // 🏪 Barbearia
    var barbearia = barbeariaRepository.findById(dto.barbeariaId());
    if (barbearia == null) {
        throw new RuntimeException("Barbearia não encontrada");
    }

    // ✂️ Serviço
    Servico servico = servicoRepository.findById(dto.servicoId());
    if (servico == null) {
        throw new RuntimeException("Serviço não encontrado");
    }

    // ⏱️ Validação de horário
    LocalDateTime inicio = dto.dataHora();
    LocalDateTime fim = inicio.plusMinutes(servico.getDuracaoMinutos());

    boolean barbeiroOcupado = !agendamentoRepository
            .findByBarbeiroAndPeriodo(dto.barbeiroId(), inicio, fim)
            .isEmpty();

    if (barbeiroOcupado) {
        throw new RuntimeException("Horário indisponível para este barbeiro");
    }

    // 🧾 Criar agendamento
    Agendamento agendamento = new Agendamento();
    agendamento.setBarbearia(barbearia);
    agendamento.setCliente(cliente);
    agendamento.setBarbeiro(barbeiroRepository.findById(dto.barbeiroId()));
    agendamento.setServico(servico);
    agendamento.setDataHora(dto.dataHora());
    agendamento.setStatus(StatusAgendamento.PENDENTE);

    agendamentoRepository.persist(agendamento);

    return toResponse(agendamento);
}


    // ======================================================
    // LISTAR POR BARBEIRO
    // ======================================================
    public List<AgendamentoResponseDTO> listarPorBarbeiro(Long barbeiroId) {
        return agendamentoRepository.find("barbeiro.id", barbeiroId)
                .list()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<AgendamentoResponseDTO> listarPorBarbearia(Long barbeariaId) {
    return agendamentoRepository.find("barbearia.id", barbeariaId)
            .list()
            .stream()
            .map(this::toResponse)
            .toList();
}


    // ======================================================
    // CONFIRMAR AGENDAMENTO
    // ======================================================
    @Transactional
    public void confirmar(Long id) {
        Agendamento a = agendamentoRepository.findById(id);
        if (a == null) {
            throw new RuntimeException("Agendamento não encontrado");
        }
        a.setStatus(StatusAgendamento.CONFIRMADO);
    }

    // ======================================================
    // CANCELAR AGENDAMENTO
    // ======================================================
    @Transactional
    public void cancelar(Long id) {
        Agendamento a = agendamentoRepository.findById(id);
        if (a == null) {
            throw new RuntimeException("Agendamento não encontrado");
        }
        a.setStatus(StatusAgendamento.CANCELADO);
    }

    // ======================================================
    // MAPEAMENTO RESPONSE DTO
    // ======================================================
    private AgendamentoResponseDTO toResponse(Agendamento a) {

        Cliente cliente = a.getCliente();

        return new AgendamentoResponseDTO(
                a.getId(),

                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone() != null
                        ? cliente.getTelefone().getNumero()
                        : null,

                a.getBarbeiro().getId(),
                a.getBarbeiro().getNome(),

                a.getServico().getId(),
                a.getServico().getNome(),

                a.getDataHora(),
                a.getStatus().name()
        );
    }
}
