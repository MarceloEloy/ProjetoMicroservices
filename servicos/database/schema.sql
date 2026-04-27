CREATE database microProdutos;

create table if not exists produtos (
	codigo serial not null primary key,
	nome varchar(100) not null,
	valor_unitario decimal (16, 2) not null
);


CREATE database micropedidos;

create table if not exists pedido (
	codigo serial not null primary key,
	codigo_cliente bigint not null,
	data_pedido timestamp not null default now(),
	chave_pagamento text,
	observacoes text,
	status varchar(20) check (status in('REALIZADO', 'PAGO', 'FATURADO', 'ENVIADO', 'ERRO_PAGAMENTO', 'PREPARANDO_ENVIO')),
	total decimal (16, 2),
	codigo_rastreio varchar(255),
	url_nf text
);

create table if not exists item_pedido(
	codigo serial not null primary key,
	codigo_pedido bigint not null references pedido (codigo),
	codigo_produto bigint not null,
	quantidade int not null,
	valor_unitario decimal (16, 2) not null
);

CREATE database microclientes;

create table if not exists clientes(
	codigo serial not null primary key,
	nome varchar(255) not null,
	cpf char(11) not null,
	logradouro varchar(100),
	numero varchar(10),
	bairro varchar(100),
	email varchar(150),
	telefone varchar(20)
);
