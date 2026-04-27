package com.example.pedidos.services;

import com.example.pedidos.controller.mapper.PedidoMapper;
import com.example.pedidos.model.DTOs.PedidoDTO;
import com.example.pedidos.model.ItemPedido;
import com.example.pedidos.model.Pedido;
import com.example.pedidos.repository.ItemPedidoRepository;
import com.example.pedidos.repository.PedidoRepository;
import com.example.pedidos.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private Validator validator;

    public Pedido criar(Pedido pedido){
        validator.validar(pedido);
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
        return pedido;
    }

    public ResponseEntity<Pedido> buscarPorCodigo(Long codigo) {

        Optional<Pedido> pedido = pedidoRepository.findById(codigo);

        return pedido.map(ResponseEntity::ok).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });

    }

}
