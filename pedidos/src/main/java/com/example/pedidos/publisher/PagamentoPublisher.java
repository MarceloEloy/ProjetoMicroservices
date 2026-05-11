package com.example.pedidos.publisher;

import com.example.pedidos.Config.KafkaConfig;
import com.example.pedidos.model.Pedido;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class PagamentoPublisher {

    @Autowired
    private DetalhesPedidoMapper detalhesPedidoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-pagos}")
    private String topico;

    public void publicar(Pedido pedido){

        log.info("publicando pedido pago {}", pedido.getCodigo());

        try {
            var representation = detalhesPedidoMapper.map(pedido);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topico, "dados", json);
        }catch (RuntimeException e){

            log.error("Erro ao processar o Json");

        }

    };


}
