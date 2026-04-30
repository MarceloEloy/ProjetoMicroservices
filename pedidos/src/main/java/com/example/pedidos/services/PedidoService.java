package com.example.pedidos.services;

import com.example.pedidos.client.ServicoBancarioClient;
import com.example.pedidos.controller.mapper.PedidoMapper;
import com.example.pedidos.model.DTOs.PedidoDTO;
import com.example.pedidos.model.ItemPedido;
import com.example.pedidos.model.Pedido;
import com.example.pedidos.repository.ItemPedidoRepository;
import com.example.pedidos.repository.PedidoRepository;
import com.example.pedidos.validator.Validator;
import jakarta.transaction.Transactional;
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

    @Autowired
    private ServicoBancarioClient client;

    @Transactional
    public Pedido criar(Pedido pedido){
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);
        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        var chavePagamento = client.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

    public ResponseEntity<Pedido> buscarPorCodigo(Long codigo) {

        Pedido pedidoV = pedidoRepository.findById(codigo).get();

        validator.validar(pedidoV);
        Optional<Pedido> pedido = pedidoRepository.findById(codigo);

        return pedido.map(ResponseEntity::ok).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });

    }

}
