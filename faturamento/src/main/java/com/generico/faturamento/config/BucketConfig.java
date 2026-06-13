package com.generico.faturamento.config;


import com.generico.faturamento.props.MinioProps;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfig {

    @Autowired
    private MinioProps props;

    @Bean
    public MinioClient bucketClient(){
        return MinioClient.builder()
                .endpoint(props.getUrl())
                .credentials(props.getAccessKey(), props.getSecretKey())
                .build();
    };

}
