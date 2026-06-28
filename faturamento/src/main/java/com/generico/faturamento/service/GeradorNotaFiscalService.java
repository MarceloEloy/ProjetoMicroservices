package com.generico.faturamento.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generico.faturamento.bucket.BucketFile;
import com.generico.faturamento.bucket.BucketService;
import com.generico.faturamento.model.Pedido;
import com.generico.faturamento.publisher.NotaFiscalPublisher;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeradorNotaFiscalService {

    private final KafkaTemplate kafkaTemplate;
    private final NotaFiscalService notaFiscalService;
    private final BucketService bucketService;
    private final NotaFiscalPublisher notaFiscalPublisher;

    public void gerar(Pedido pedido){
        log.info("Gerando nota fiscal para o pedido {}", pedido.codigo());

        try {

        byte[] byteArray = notaFiscalService.gerarNota(pedido);

        var nomeArquivo = String.format("notaFiscal_pedido_%d.pdf", pedido.codigo());

        var bucketFile = new BucketFile(nomeArquivo,
                new ByteArrayInputStream(byteArray),
                MediaType.APPLICATION_PDF,
                Integer.toUnsignedLong(byteArray.length));

        bucketService.upload(bucketFile);
        log.info("Gerada nota fiscal, {}", nomeArquivo);
        notaFiscalPublisher.publicar(pedido, bucketService.getUrl(nomeArquivo));
        }catch (Exception e){

            log.error(e.getMessage(), e);

        }
    }}

