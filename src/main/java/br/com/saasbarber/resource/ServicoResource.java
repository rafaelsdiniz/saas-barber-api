package br.com.saasbarber.resource;

import br.com.saasbarber.dto.request.ServicoRequestDTO;
import br.com.saasbarber.service.ServicoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/servicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServicoResource {

    @Inject
    ServicoService service;

    /**
     * 🔒 Somente ADMIN pode criar serviço
     */
    @POST
    @RolesAllowed("ADMIN")
    public Response criar(ServicoRequestDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.criar(dto))
                .build();
    }

    /**
     * 🔓 ADMIN, BARBEIRO e CLIENTE podem listar serviços da barbearia
     */
    @GET
    @Path("/barbearia/{barbeariaId}")
    @RolesAllowed({ "ADMIN", "BARBEIRO", "CLIENTE" })
    public Response listarPorBarbearia(@PathParam("barbeariaId") Long barbeariaId) {
        return Response.ok(service.listarPorBarbearia(barbeariaId)).build();
    }
}
