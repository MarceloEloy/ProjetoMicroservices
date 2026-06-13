package com.generico.faturamento.mapper;

import com.generico.faturamento.model.Cliente;
import com.generico.faturamento.model.ItemPedido;
import com.generico.faturamento.model.Pedido;
import com.generico.faturamento.subscriber.representation.DetalheItemPedidoRepresentation;
import com.generico.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido map(DetalhePedidoRepresentation representation){
        Cliente cliente = new Cliente(representation.nome(), representation.cpf(),
                representation.logradouro(), representation.numero(), representation.bairro(),
                representation.email());

        List<ItemPedido> itens = representation.itens().stream().map(this::mapItem).toList();


        return new Pedido(representation.codigo(), cliente, representation.dataPedido(), representation.total(), itens);
    }

    private ItemPedido mapItem(DetalheItemPedidoRepresentation representation){
        return new ItemPedido(representation.codigoProduto(), representation.nome(), representation.valorUnitario(), representation.quantidade());

    }

}
