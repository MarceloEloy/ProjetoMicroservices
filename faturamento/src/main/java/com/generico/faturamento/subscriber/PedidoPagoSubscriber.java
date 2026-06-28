package com.generico.faturamento.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generico.faturamento.service.GeradorNotaFiscalService;
import com.generico.faturamento.mapper.PedidoMapper;
import com.generico.faturamento.model.Pedido;
import com.generico.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class PedidoPagoSubscriber {

    private final ObjectMapper objectMapper;

    private final PedidoMapper pedidoMapper;

    private final GeradorNotaFiscalService notaFiscalService;

    @KafkaListener(groupId = "icompras-faturamento", topics = "${icompras.config.kafka.topics.pedidos-pagos}")
    public void Listen(String json){
        System.out.println(json);

        try {
            log.info("Recebendo pedido para faturamento: {}", json);
            var representation = objectMapper.readValue(json, DetalhePedidoRepresentation.class);
            Pedido pedido = pedidoMapper.map(representation);
            notaFiscalService.gerar(pedido);
        }catch (Exception e){
            log.error("Erro na consumação do topico de pedidos pagos");
        }

    }
    


}
