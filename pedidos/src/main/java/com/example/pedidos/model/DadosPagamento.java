package com.example.pedidos.model;

import com.example.pedidos.model.DTOs.PedidoDTO;
import com.example.pedidos.model.enums.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DadosPagamento {
    public DadosPagamento(PedidoDTO.BaseDadosPagamentoDTO dadosPagamentoDTO){
        this.pagamento = dadosPagamentoDTO.pagamento();
        this.dados = dadosPagamentoDTO.dados();
    }
    private String dados;

    private TipoPagamento pagamento;
}
