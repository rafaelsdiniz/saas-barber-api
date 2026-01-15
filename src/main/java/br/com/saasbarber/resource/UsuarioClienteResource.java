package br.com.saasbarber.resource;

import br.com.saasbarber.dto.request.UsuarioClienteRequestDTO;
import br.com.saasbarber.service.UsuarioClienteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuario-clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioClienteResource {

    @Inject
    UsuarioClienteService service;

    @POST
    public Response criar(UsuarioClienteRequestDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.criar(dto))
                .build();
    }

    @GET
    @Path("/cliente/{clienteId}")
    public Response buscarPorCliente(@PathParam("clienteId") Long clienteId) {
        return Response.ok(service.buscarPorCliente(clienteId)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response desativar(@PathParam("id") Long id) {
        service.desativar(id);
        return Response.noContent().build();
    }
}
