package com.generico.faturamento.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ItemPedido{

    private Long codigo;
    private String nome;
    private BigDecimal valorUnitario;
    private Integer quantidade;
    private BigDecimal total;

    public BigDecimal getTotal(){

        return BigDecimal.valueOf(quantidade).multiply(valorUnitario);
    }
}
