package com.Envios.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnvioService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate kafkaTemplate;

    public String gerarIdRastreio(){

        String id = UUID.randomUUID().toString();

        return id;
    }

}
