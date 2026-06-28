package com.Envios.subscriber.representation;

import com.Envios.model.enums.Status;

public record PedidoFaturadoRepresentation(
        Long codigo,
        Status status,
        String urlNotaFiscal

) {
}