package com.example.pedidos.subscriber.representation;

import com.example.pedidos.model.enums.Status;

public record PedidoFaturadoRepresentation(
        Long codigo,
        Status status,
        String urlNotaFiscal

) {
}
