package com.example.pedidos.subscriber;

import com.example.pedidos.services.PedidoService;
import com.example.pedidos.subscriber.representation.PedidoEnviadoRepresentation;
import com.example.pedidos.subscriber.representation.PedidoFaturadoRepresentation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoFaturadoSubscriber {

    private final ObjectMapper objectMapper;

    private final PedidoService pedidoService;

    @KafkaListener(groupId = "icompras-faturamento",topics = "${icompras.config.kafka.topics.pedidos-faturados}")
    public void listenFaturamento(String json) throws JsonProcessingException {
        try {
            log.info("Inicializando atualização de status do pedido");
            var pedidoFaturado = objectMapper.readValue(json, PedidoFaturadoRepresentation.class);

            pedidoService.atualizarPedidoFaturado(pedidoFaturado.codigo(), pedidoFaturado.urlNotaFiscal());
            log.info("Atualização de status do pedido concluída");
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

    };

    @KafkaListener( groupId = "icompras-envio",topics = "${icompras.config.kafka.topics.pedidos-enviados}")
    public void listenEnvio(String json) throws JsonProcessingException {
        try {
            log.info("Inicializando atualização de status do pedido");
            var pedidoEnviado = objectMapper.readValue(json, PedidoEnviadoRepresentation.class);

            pedidoService.atualizarPedidoEnviado(pedidoEnviado.codigo(), pedidoEnviado.codigoRastreio());
            log.info("Atualização de status do pedido concluída");
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

    }

}
