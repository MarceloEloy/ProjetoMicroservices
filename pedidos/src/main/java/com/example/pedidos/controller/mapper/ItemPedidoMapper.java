package com.example.pedidos.controller.mapper;

import com.example.pedidos.model.DTOs.PedidoDTO;
import com.example.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido map(PedidoDTO.BaseItemPedidoDTO dto);

}
