package com.example.pedidos.validator;

import com.example.pedidos.client.ClienteClient;
import com.example.pedidos.client.ProdutoClient;
import com.example.pedidos.client.representation.ProdutoRepresentation;
import com.example.pedidos.model.Pedido;
import com.example.pedidos.model.exception.ValidationException;
import feign.FeignException;
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

    private void validarItens(Pedido pedido) throws RuntimeException{
        log.info("Inicializando metodo de validação de Itens");

        pedido.getItens().forEach(item -> {
            try{
                produtoClient.obterPorCodigo(item.getCodigoProduto());
            }catch (FeignException.NotFound x){
                var message = String.format("Cliente de codigo %d não encontrado", item.getCodigoProduto());
                throw new ValidationException("codigoProduto", message);
            }
        });
        log.info("validação de produtos concluida");

    }

    private void validarCliente(Pedido pedido) throws RuntimeException{
        log.info("Inicializando metodo de validação de Cliente");

        try {
            clienteClient.obterDados(pedido.getCodigoCliente());
        }catch (FeignException.NotFound x){
            var message = String.format("Cliente de codigo %d não encontrado", pedido.getCodigoCliente());
            throw new ValidationException("codigoCliente", message);
        }

        log.info("validação de Cliente concluida");
    }
}
