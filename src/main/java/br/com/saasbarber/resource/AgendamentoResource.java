package br.com.saasbarber.resource;

import br.com.saasbarber.dto.request.AgendamentoRequestDTO;
import br.com.saasbarber.service.AgendamentoService;
import jakarta.annotation.security.RolesAllowed;
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
    @RolesAllowed({ "CLIENTE", "ADMIN", "BARBEIRO" })
    public Response criar(AgendamentoRequestDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.criar(dto))
                .build();
    }

    @GET
    @Path("/barbeiro/{barbeiroId}")
    @RolesAllowed({ "ADMIN", "BARBEIRO" })
    public Response listarPorBarbeiro(@PathParam("barbeiroId") Long barbeiroId) {
        return Response.ok(service.listarPorBarbeiro(barbeiroId)).build();
    }

    @GET
    @Path("/barbearia/{barbeariaId}")
    @RolesAllowed({ "ADMIN", "BARBEIRO", "CLIENTE" })
    public Response listarPorBarbearia(@PathParam("barbeariaId") Long barbeariaId) {
        return Response.ok(service.listarPorBarbearia(barbeariaId)).build();
    }

    @PUT
    @Path("/{id}/confirmar")
    @RolesAllowed({ "ADMIN", "BARBEIRO" })
    public Response confirmar(@PathParam("id") Long id) {
        service.confirmar(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/cancelar")
    @RolesAllowed({ "ADMIN", "BARBEIRO" })
    public Response cancelar(@PathParam("id") Long id) {
        service.cancelar(id);
        return Response.noContent().build();
    }
}
