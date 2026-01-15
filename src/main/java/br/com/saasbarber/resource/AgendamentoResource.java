package br.com.saasbarber.resource;

import br.com.saasbarber.dto.request.AgendamentoRequestDTO;
import br.com.saasbarber.service.AgendamentoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/agendamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AgendamentoResource {

    @Inject
    AgendamentoService service;

    @POST
    public Response criar(AgendamentoRequestDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.criar(dto))
                .build();
    }

    @GET
    @Path("/barbeiro/{barbeiroId}")
    public Response listarPorBarbeiro(@PathParam("barbeiroId") Long barbeiroId) {
        return Response.ok(service.listarPorBarbeiro(barbeiroId)).build();
    }

    @PUT
    @Path("/{id}/confirmar")
    public Response confirmar(@PathParam("id") Long id) {
        service.confirmar(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/cancelar")
    public Response cancelar(@PathParam("id") Long id) {
        service.cancelar(id);
        return Response.noContent().build();
    }
}
