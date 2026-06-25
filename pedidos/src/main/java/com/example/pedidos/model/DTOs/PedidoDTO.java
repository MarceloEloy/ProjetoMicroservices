package com.example.pedidos.model.DTOs;

import com.example.pedidos.model.ItemPedido;
import com.example.pedidos.model.Pedido;
import com.example.pedidos.model.enums.Status;
import com.example.pedidos.model.enums.TipoPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {

    public record BaseItemPedidoDTO(


            Long codigoProduto,

            Integer quantidade,

            BigDecimal valorUnitario

    )
    {

    };

    public record BaseDadosPagamentoDTO(
            String dados,

            TipoPagamento pagamento
    ){

    }

    public record NovoPedidoDto(

            Long codigoCliente,

            List<BaseItemPedidoDTO> itens,

            BaseDadosPagamentoDTO dadosPagamento,

            String observacoes

    ){

    }

}
