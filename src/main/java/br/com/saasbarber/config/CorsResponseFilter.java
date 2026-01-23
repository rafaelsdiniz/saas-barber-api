package br.com.saasbarber.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.Priorities;
import jakarta.annotation.Priority;

@Provider
@Priority(Priorities.AUTHENTICATION - 1) // 🔥 roda ANTES da security
public class CorsResponseFilter
        implements ContainerRequestFilter, ContainerResponseFilter {

    private static final String ORIGIN = "http://localhost:3000";

    // 🔥 INTERCEPTA O PREFLIGHT (OPTIONS)
    @Override
    public void filter(ContainerRequestContext requestContext) {

        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
            requestContext.abortWith(
                Response.ok()
                    .header("Access-Control-Allow-Origin", ORIGIN)
                    .header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS")
                    .header("Access-Control-Allow-Headers", "Authorization,Content-Type")
                    .header("Access-Control-Allow-Credentials", "false")
                    .build()
            );
        }
    }

    // 🔥 ADICIONA HEADERS EM TODAS AS RESPOSTAS
    @Override
    public void filter(
            ContainerRequestContext requestContext,
            ContainerResponseContext responseContext
    ) {
        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Origin",
                ORIGIN
        );
        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Methods",
                "GET,POST,PUT,DELETE,OPTIONS"
        );
        responseContext.getHeaders().putSingle(
                "Access-Control-Allow-Headers",
                "Authorization,Content-Type"
        );
        responseContext.getHeaders().putSingle(
                "Access-Control-Expose-Headers",
                "Authorization"
        );
    }
}
