package br.com.saasbarber.resource;

import br.com.saasbarber.dto.request.BarbeariaRequestDTO;
import br.com.saasbarber.service.BarbeariaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/barbearias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BarbeariaResource {

    @Inject
    BarbeariaService service;

    /**
     * 🔒 Somente ADMIN cria barbearia
     */
    @POST
    @RolesAllowed("ADMIN")
    public Response criar(BarbeariaRequestDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.criar(dto))
                .build();
    }

    /**
     * 🔓 Todos autenticados podem listar
     */
    @GET
    @RolesAllowed({ "ADMIN", "BARBEIRO", "CLIENTE" })
    public Response listar() {
        return Response.ok(service.listar()).build();
    }

    /**
     * 🔓 Todos autenticados podem visualizar
     */
    @GET
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "BARBEIRO", "CLIENTE" })
    public Response buscar(@PathParam("id") Long id) {
        return Response.ok(service.buscarPorId(id)).build();
    }

    /**
     * 🔒 Somente ADMIN atualiza
     */
    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response atualizar(@PathParam("id") Long id, BarbeariaRequestDTO dto) {
        return Response.ok(service.atualizar(id, dto)).build();
    }

    /**
     * 🔒 Somente ADMIN desativa
     */
    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response desativar(@PathParam("id") Long id) {
        service.desativar(id);
        return Response.noContent().build();
    }
}
