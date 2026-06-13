package com.generico.faturamento.model;

public record Cliente (
    String nome,
    String cpf,
    String logradouro,
    String numero,
    String bairro,
    String email){}
