package com.generico.clientes.model;

import com.generico.clientes.model.DTO.ClienteDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "cliente")
@Table(name = "clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    public Cliente(ClienteDTO.BaseDTO dto){

        this.codigo = dto.codigo();
        this.cpf = dto.cpf();
        this.nome = dto.nome();
        this.email =  dto.email();
        this.telefone = dto.telefone();
        this.logradouro = dto.logradouro();
        this.bairro = dto.bairro();
        this.numero = dto.numero();

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "numero")
    private String numero;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;

}
