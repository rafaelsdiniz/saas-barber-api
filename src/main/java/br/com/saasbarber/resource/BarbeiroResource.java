package br.com.saasbarber.resource;

import br.com.saasbarber.dto.request.BarbeiroRequestDTO;
import br.com.saasbarber.service.BarbeiroService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/barbeiros")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BarbeiroResource {

    @Inject
    BarbeiroService service;

    /**
     * 🔒 Somente ADMIN pode criar barbeiro
     */
    @POST
    @RolesAllowed("ADMIN")
    public Response criar(BarbeiroRequestDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.criar(dto))
                .build();
    }

    /**
     * 🔓 ADMIN, BARBEIRO e CLIENTE podem listar barbeiros da barbearia
     */
    @GET
    @Path("/barbearia/{barbeariaId}")
    @RolesAllowed({ "ADMIN", "BARBEIRO", "CLIENTE" })
    public Response listarPorBarbearia(@PathParam("barbeariaId") Long barbeariaId) {
        return Response.ok(service.listarPorBarbearia(barbeariaId)).build();
    }

    /**
     * 🔒 Somente ADMIN pode desativar barbeiro
     */
    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response desativar(@PathParam("id") Long id) {
        service.desativar(id);
        return Response.noContent().build();
    }
}
