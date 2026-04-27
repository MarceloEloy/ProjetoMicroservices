package com.generico.produto.model.DTO;

import com.generico.produto.model.Produto;

import java.math.BigDecimal;

public class ProdutoDTO {

    public record BaseDTO(
            Long codigo,
            String nome,
            BigDecimal valorUnitario
    ){
        public Produto produto(){
            return new Produto(this);
        }
    };

}
