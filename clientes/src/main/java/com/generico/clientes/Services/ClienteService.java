package com.generico.clientes.Services;

import com.generico.clientes.Repositorys.ClienteRepository;
import com.generico.clientes.model.Cliente;
import com.generico.clientes.model.DTO.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public ResponseEntity<Cliente> addCliente(ClienteDTO.BaseDTO dto){
        Cliente cliente = dto.cliente();

        return  ResponseEntity.ok(clienteRepository.save(cliente));
    }

    public Optional<Cliente> getByCodigo(Long codigo){
        return clienteRepository.findById(codigo);
    }

}
