package com.example.pedidos.client;

import com.example.pedidos.client.representation.ClienteRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente", url = "${icompras.pedidos.clients.clientes.url}")
public interface ClienteClient {

    @GetMapping(path = "/get/{codigo}")
    ResponseEntity<ClienteRepresentation> obterDados(@PathVariable Long codigo);

}
