package com.example.pedidos.model.DTOs;

public record RecebimentoCallbackPagamentoDTO(
        Long codigo,
        String chavePagamento,
        boolean status,
        String observacoes
) {
}
