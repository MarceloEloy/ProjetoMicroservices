package com.Envios.publisher;

import com.Envios.publisher.representation.PedidoEnviadoRepresentation;
import com.Envios.service.EnvioService;
import com.Envios.subscriber.representation.PedidoFaturadoRepresentation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnvioPublisher {

    private final ObjectMapper objectMapper;

    private final EnvioService envioService;

    private final KafkaTemplate kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-enviados}")
    private String topic;

    public void post(PedidoFaturadoRepresentation pedidoFaturadoRepresentation){
        log.info("Inicializando publicação de envio");

        try {
            String codigoRastreio = envioService.gerarIdRastreio();

            var representation = new PedidoEnviadoRepresentation(pedidoFaturadoRepresentation.codigo(), codigoRastreio);
            var json = objectMapper.writeValueAsString(representation);

            kafkaTemplate.send(topic, "dados", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
