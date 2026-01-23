package br.com.saasbarber.resource;

import br.com.saasbarber.dto.request.ClienteRequestDTO;
import br.com.saasbarber.service.ClienteService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService service;

    /**
     * 🔓 Cadastro de cliente (público)
     */
    @POST
    @PermitAll
    public Response criar(ClienteRequestDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.criar(dto))
                .build();
    }

    /**
     * 🔒 ADMIN e BARBEIRO podem listar clientes da barbearia
     */
    @GET
    @Path("/barbearia/{barbeariaId}")
    @RolesAllowed({ "ADMIN", "BARBEIRO" })
    public Response listarPorBarbearia(@PathParam("barbeariaId") Long barbeariaId) {
        return Response.ok(service.listarPorBarbearia(barbeariaId)).build();
    }

    /**
     * 🔒 Somente ADMIN pode desativar cliente
     */
    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response desativar(@PathParam("id") Long id) {
        service.desativar(id);
        return Response.noContent().build();
    }
}
