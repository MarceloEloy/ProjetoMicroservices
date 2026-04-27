package com.generico.clientes.model.DTO;

import com.generico.clientes.model.Cliente;

public class ClienteDTO {

    public record BaseDTO(
        Long codigo,
        String email,
        String cpf,
        String telefone,
        String logradouro,
        String bairro,
        String numero,
        String nome
    ) {

        public Cliente cliente(){
            return new Cliente(this);
        }

    }

}
