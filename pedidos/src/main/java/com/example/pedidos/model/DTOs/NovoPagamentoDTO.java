package com.example.pedidos.model.DTOs;

import com.example.pedidos.model.enums.TipoPagamento;

public record NovoPagamentoDTO(
        Long codigoPedido,
        String dadosCartao,
        TipoPagamento tipo
) {
}
