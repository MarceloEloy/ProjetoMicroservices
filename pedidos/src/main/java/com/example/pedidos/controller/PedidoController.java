package com.example.pedidos.controller;

import com.example.pedidos.controller.mapper.PedidoMapper;
import com.example.pedidos.model.DTOs.PedidoDTO;
import com.example.pedidos.model.ErroResposta;
import com.example.pedidos.model.Pedido;
import com.example.pedidos.model.exception.ValidationException;
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
    public ResponseEntity<Object> addPedido(@RequestBody PedidoDTO.NovoPedidoDto dto){
        try {

            var pedido = pedidoMapper.map(dto);
            var novoPedido = pedidoService.criar(pedido);
            return ResponseEntity.ok(novoPedido);
        }catch (ValidationException e){

            var erro = new ErroResposta("Erro validação", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);

        }



    }

    @GetMapping(path = "/get/{codigo}")
    public ResponseEntity<Pedido> buscarPorCodigo(@PathVariable Long codigo){
        return pedidoService.buscarPorCodigo(codigo);

    }

}
