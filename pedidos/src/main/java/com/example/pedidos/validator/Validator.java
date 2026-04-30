package com.example.pedidos.validator;

import com.example.pedidos.client.ClienteClient;
import com.example.pedidos.client.ProdutoClient;
import com.example.pedidos.client.representation.ProdutoRepresentation;
import com.example.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Validator {

    @Autowired
    private ProdutoClient produtoClient;

    @Autowired
    private ClienteClient clienteClient;

    public void validar(Pedido pedido) throws RuntimeException{
        validarItens(pedido);
        validarCliente(pedido);
    };

    public void validarItens(Pedido pedido) throws RuntimeException{
        log.info("Inicializando metodo de validação de Itens");

        pedido.getItens().forEach(item -> {
            try{
                produtoClient.obterPorCodigo(item.getCodigoProduto());
            }catch (RuntimeException x){
                throw new RuntimeException("PRODUTO REFERENTE AO 'CODIGO DO PRODUTO' NÃO EXISTE NO BANCO DE DADOS");
            }
        });
        log.info("validação de produtos concluida");

    }

    public void validarCliente(Pedido pedido) throws RuntimeException{
        log.info("Inicializando metodo de validação de Cliente");

        try {
            clienteClient.obterDados(pedido.getCodigoCliente());
        }catch (RuntimeException x){
            throw new RuntimeException("CLIENTE REFERENTE AO 'CODIGO DO CLIENTE' NÃO EXISTE NO BANCO DE DADOS");
        }

        log.info("validação de Cliente concluida");
    }
}
