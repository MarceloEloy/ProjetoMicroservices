package com.example.pedidos.model;

import com.example.pedidos.model.DTOs.PedidoDTO;
import com.example.pedidos.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity()
@Table(name = "pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "codigo_cliente", nullable = false)
    private Long codigoCliente;

    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    @Column(name = "total", nullable = false, precision = (16), scale = (2))
    private BigDecimal total;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "codigo_rastreio")
    private String codigoRastreio;

    @Column(name = "chave_pagamento")
    private String chavePagamento;

    @Column(name = "url_nf")
    private String urlNf;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Transient
    private DadosPagamento dadosPagamento;

    @OneToMany(mappedBy = "pedido")
    @JsonBackReference
    private List<ItemPedido> itens;

}
