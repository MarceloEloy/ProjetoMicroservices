package com.example.pedidos.client;

import com.example.pedidos.client.representation.ProdutoRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produto",url = "${icompras.pedidos.clients.produtos.url}")
public interface ProdutoClient {

    @GetMapping("/get/{codigo}")
    ResponseEntity<ProdutoRepresentation> obterPorCodigo(@PathVariable Long codigo);

}
