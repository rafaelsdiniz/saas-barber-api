package br.com.saasbarber.resource;

import br.com.saasbarber.dto.request.LoginRequestDTO;
import br.com.saasbarber.dto.response.LoginResponseDTO;
import br.com.saasbarber.service.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @PermitAll
    public LoginResponseDTO login(LoginRequestDTO dto) {
        return authService.login(dto);
    }
}
