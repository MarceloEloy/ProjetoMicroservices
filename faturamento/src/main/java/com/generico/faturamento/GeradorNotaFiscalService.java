package com.generico.faturamento;

import com.generico.faturamento.bucket.BucketFile;
import com.generico.faturamento.bucket.BucketService;
import com.generico.faturamento.model.Pedido;
import com.generico.faturamento.service.NotaFiscalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeradorNotaFiscalService {

    private final NotaFiscalService notaFiscalService;
    private final BucketService bucketService;

    public void gerar(Pedido pedido){
        log.info("gerada nota fiscal para o pedido {}", pedido.codigo());

        try {

        byte[] byteArray = notaFiscalService.gerarNota(pedido);

        var nomeArquivo = String.format("notaFiscal_pedido_%d.pdf", pedido.codigo());

        var bucketFile = new BucketFile(nomeArquivo,
                new ByteArrayInputStream(byteArray),
                MediaType.APPLICATION_PDF,
                Integer.toUnsignedLong(byteArray.length));

        bucketService.upload(bucketFile);
        }catch (Exception e){

            log.error(e.getMessage(), e);

        }
    }

}
