package br.com.saasbarber.resource;

import br.com.saasbarber.service.DashboardService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dashboard")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ "ADMIN", "BARBEIRO" })
public class DashboardResource {

    @Inject
    DashboardService service;

    @GET
    public Response carregar(@QueryParam("barbeariaId") Long barbeariaId) {
        return Response.ok(service.carregar(barbeariaId)).build();
    }
}
