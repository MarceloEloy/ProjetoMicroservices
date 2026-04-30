package com.example.pedidos.client.representation;

import jakarta.persistence.Column;

public record ClienteRepresentation(
        Long codigo,
        String nome,
        String cpf,
        String logradouro,
        String bairro,
        String numero,
        String email,
        String telefone

) {

}
