package br.com.saasbarber.resource;

import br.com.saasbarber.dto.request.ServicoRequestDTO;
import br.com.saasbarber.service.ServicoService;
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

    @POST
    public Response criar(ServicoRequestDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.criar(dto))
                .build();
    }

    @GET
    @Path("/barbearia/{barbeariaId}")
    public Response listarPorBarbearia(@PathParam("barbeariaId") Long barbeariaId) {
        return Response.ok(service.listarPorBarbearia(barbeariaId)).build();
    }
}
