package com.generico.clientes.Controllers;

import com.generico.clientes.Services.ClienteService;
import com.generico.clientes.model.Cliente;
import com.generico.clientes.model.DTO.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping(path = "/post")
    public ResponseEntity<Cliente> addCliente(@RequestBody ClienteDTO.BaseDTO dto){
        return clienteService.addCliente(dto);
    }

    @GetMapping(path = "/get/{codigo}")
    public ResponseEntity<Cliente> getByCodigo(@PathVariable Long codigo){
        return clienteService
                .getByCodigo(codigo)
                    .map(ResponseEntity::ok)
                        .orElseGet(() -> {
                            return ResponseEntity.notFound().build();
                        });

    }

}
