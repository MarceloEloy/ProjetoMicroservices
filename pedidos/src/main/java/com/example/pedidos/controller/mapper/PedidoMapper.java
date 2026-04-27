package com.example.pedidos.controller.mapper;

import com.example.pedidos.model.DTOs.PedidoDTO;
import com.example.pedidos.model.DadosPagamento;
import com.example.pedidos.model.ItemPedido;
import com.example.pedidos.model.Pedido;
import com.example.pedidos.model.enums.Status;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    ItemPedidoMapper ITEM_PEDIDO_MAPPER = Mappers.getMapper(ItemPedidoMapper.class);

    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    Pedido map(PedidoDTO.NovoPedidoDto dadosPagamentoDTO);


    @Named("mapItens")
    default List<ItemPedido> mapItems(List<PedidoDTO.BaseItemPedidoDTO> dtos){

        return dtos.stream().map(ITEM_PEDIDO_MAPPER::map).toList();

    }

    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido){

        pedido.setDataPedido(LocalDateTime.now());

        pedido.setStatus(Status.REALIZADO);

        var total = pedido.getItens().stream().map(item -> {

           return item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setTotal(total);

        pedido.getItens().forEach(item -> item.setPedido(pedido));

    }



}
