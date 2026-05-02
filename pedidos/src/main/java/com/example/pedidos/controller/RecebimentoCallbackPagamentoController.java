package com.example.pedidos.controller;

import com.example.pedidos.model.DTOs.RecebimentoCallbackPagamentoDTO;
import com.example.pedidos.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pagamentos/callback-pagamentos")
public class RecebimentoCallbackPagamentoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Object> atualizarStatusPagamento
            (@RequestBody RecebimentoCallbackPagamentoDTO dto,
             @RequestHeader(required = true) String apikey){

            pedidoService.atualizaStatusPagamento(dto.codigo(), dto.chavePagamento(), dto.status(), dto.observacoes());

        return ResponseEntity.ok().build();
    }


}
