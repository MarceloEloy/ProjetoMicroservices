package com.generico.faturamento.publisher.representation;

public record AtualizacaoStatusPedidoRepresentation   (

        Long codigo,
        StatusPedido status,
        String urlNotaFiscal
){}
