package com.Envios.subscriber;

import com.Envios.publisher.EnvioPublisher;
import com.Envios.service.EnvioService;
import com.Envios.subscriber.representation.PedidoFaturadoRepresentation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnvioPedidoSubscriber {

    private final ObjectMapper objectMapper;

    private final EnvioPublisher envioPublisher;

    @KafkaListener(groupId = "icompras-faturamento", topics = "${icompras.config.kafka.topics.pedidos-faturados}")
    public void listen(String json) throws JsonProcessingException {
        PedidoFaturadoRepresentation pedido = objectMapper.readValue(json, PedidoFaturadoRepresentation.class);

        envioPublisher.post(pedido);
    }
}
