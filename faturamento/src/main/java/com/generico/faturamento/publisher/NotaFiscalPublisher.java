package com.generico.faturamento.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generico.faturamento.bucket.BucketService;
import com.generico.faturamento.model.Pedido;
import com.generico.faturamento.publisher.representation.AtualizacaoStatusPedidoRepresentation;
import com.generico.faturamento.publisher.representation.StatusPedido;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotaFiscalPublisher {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-faturados}")
    String topic;

    @SneakyThrows
    public void publicar(Pedido pedido, String nomeDoArquivo){
        log.info("Inicializando publicação de faturamento");

        try {
            var representation = new AtualizacaoStatusPedidoRepresentation(pedido.codigo(), StatusPedido.FATURADO, nomeDoArquivo);
            var json = objectMapper.writeValueAsString(representation);

            kafkaTemplate.send(topic, "dados", json);
            log.info("Finalizando publicação de faturamento: Sucesso");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
