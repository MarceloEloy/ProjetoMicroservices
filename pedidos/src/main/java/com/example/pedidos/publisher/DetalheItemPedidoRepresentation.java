package com.example.pedidos.publisher;

import java.math.BigDecimal;

public record DetalheItemPedidoRepresentation(

        Long codigoProduto,
        String nome,
        Integer quantidade,
        BigDecimal valorUnitario

) {

    public BigDecimal getTotal(){
        return BigDecimal.valueOf(quantidade).multiply(valorUnitario());
    }

}
