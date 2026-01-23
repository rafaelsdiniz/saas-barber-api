package br.com.saasbarber.resource;

import br.com.saasbarber.dto.request.UsuarioRequestDTO;
import br.com.saasbarber.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN") // 🔒 tudo aqui é administrativo
public class UsuarioResource {

    @Inject
    UsuarioService service;

    /**
     * 🔒 Criar usuário (ADMIN, BARBEIRO, CLIENTE)
     * Quem decide o perfil é o ADMIN
     */
    @POST
    public Response criar(UsuarioRequestDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.criar(dto))
                .build();
    }

    /**
     * 🔒 Listar usuários da barbearia
     */
    @GET
    @Path("/barbearia/{barbeariaId}")
    public Response listar(@PathParam("barbeariaId") Long barbeariaId) {
        return Response.ok(service.listarPorBarbearia(barbeariaId)).build();
    }

    /**
     * 🔒 Desativar usuário
     */
    @DELETE
    @Path("/{id}")
    public Response desativar(@PathParam("id") Long id) {
        service.desativar(id);
        return Response.noContent().build();
    }
}
