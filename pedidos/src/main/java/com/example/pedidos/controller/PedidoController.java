package com.example.pedidos.controller;

import com.example.pedidos.controller.mapper.PedidoMapper;
import com.example.pedidos.model.DTOs.PedidoDTO;
import com.example.pedidos.model.Pedido;
import com.example.pedidos.services.PedidoService;
import com.example.pedidos.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoMapper pedidoMapper;

    @PostMapping(path = "/post")
    public ResponseEntity<Pedido> addPedido(@RequestBody PedidoDTO.NovoPedidoDto dto){
        System.out.println(dto.dadosPagamento().dados() + " " + dto.dadosPagamento().pagamento());
        var pedido = pedidoMapper.map(dto);

        return ResponseEntity.ok(pedidoService.criar(pedido));
    }

    @GetMapping(path = "/get/{codigo}")
    public ResponseEntity<Pedido> buscarPorCodigo(@PathVariable Long codigo){
        return pedidoService.buscarPorCodigo(codigo);

    }

}
