package com.generico.produto.model;

import com.generico.produto.model.DTO.ProdutoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos")
public class Produto {
    public Produto(ProdutoDTO.BaseDTO dto){
        this.codigo = dto.codigo();
        this.valorUnitario = dto.valorUnitario();
        this.nome = dto.nome();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "valor_unitario", nullable = false, precision = 16, scale = 2)
    private BigDecimal valorUnitario;

}
