package br.com.saasbarber.resource;

import br.com.saasbarber.service.R2StorageService;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;

@Path("/upload")
public class UploadResource {

    @Inject
    R2StorageService storageService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(
            @RestForm("file") FileUpload file
    ) {
        if (file == null) {
            return Response.status(400).entity("Arquivo não enviado").build();
        }

        String url = storageService.upload(
                file.uploadedFile().toFile(),
                file.contentType()
        );

        return Response.ok(url).build();
    }
}
