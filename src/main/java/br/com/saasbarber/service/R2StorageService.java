package br.com.saasbarber.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@ApplicationScoped
public class R2StorageService {

    private final AmazonS3 s3;
    private final String bucket;
    private final String publicUrl;

    
    public R2StorageService(
            @ConfigProperty(name = "r2.bucket") String bucket,
            @ConfigProperty(name = "r2.endpoint") String endpoint,
            @ConfigProperty(name = "r2.access-key") String accessKey,
            @ConfigProperty(name = "r2.secret-key") String secretKey,
            @ConfigProperty(name = "r2.public-url") String publicUrl
    ) {
        this.bucket = bucket;
        this.publicUrl = publicUrl;

        this.s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(endpoint, "us-east-1")
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(accessKey, secretKey)
                        )
                )
                .withPathStyleAccessEnabled(true)
                .build();
    }

    public String upload(File file, String contentType) {
    try (InputStream is = new FileInputStream(file)) {

        String filename = UUID.randomUUID().toString();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(file.length());

        s3.putObject(bucket, filename, is, metadata);

        return publicUrl + "/" + filename;

    } catch (Exception e) {
        throw new RuntimeException("Erro ao enviar arquivo para o R2", e);
    }
}

}
