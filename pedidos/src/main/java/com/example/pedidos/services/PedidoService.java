package com.example.pedidos.services;

import com.example.pedidos.client.ClienteClient;
import com.example.pedidos.client.ProdutoClient;
import com.example.pedidos.client.ServicoBancarioClient;
import com.example.pedidos.controller.mapper.PedidoMapper;
import com.example.pedidos.model.DTOs.PedidoDTO;
import com.example.pedidos.model.DadosPagamento;
import com.example.pedidos.model.ItemPedido;
import com.example.pedidos.model.Pedido;
import com.example.pedidos.model.enums.Status;
import com.example.pedidos.model.enums.TipoPagamento;
import com.example.pedidos.model.exception.ItemNaoEncontradoException;
import com.example.pedidos.publisher.PagamentoPublisher;
import com.example.pedidos.repository.ItemPedidoRepository;
import com.example.pedidos.repository.PedidoRepository;
import com.example.pedidos.validator.Validator;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class PedidoService {

    @Autowired
    ServicoBancarioClient servicoBancarioClient;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private ProdutoClient produtoClient;

    @Autowired
    private ServicoBancarioClient client;

    @Autowired
    private PagamentoPublisher pagamentoPublisher;

    public void atualizaStatusPagamento(Long codigoPedido, String chavePagamento, boolean status, String observacoes){

        var pedidoEnviado = pedidoRepository.findByCodigoAndChavePagamento(codigoPedido, chavePagamento);
        if (pedidoEnviado.isEmpty() && pedidoEnviado.get().getChavePagamento() != pedidoRepository.findById(codigoPedido).get().getChavePagamento()){
            var msg = String.format("Pedido não encontrado para o codigo %d e chave pagamento %s", codigoPedido, chavePagamento);
            log.error(msg);
            return;
        }

        Pedido pedido = pedidoEnviado.get();


        if (status){
            pedido.setStatus(Status.PAGO);
            carregarDadosCliente(pedido);
            carregarItensPedido(pedido);
            pagamentoPublisher.publicar(pedido);
        }
        else {
            pedido.setStatus(Status.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }
        pedidoRepository.save(pedido);

    };

    @Transactional
    public Pedido criar(Pedido pedido){
        validator.validar(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);
        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {

        var chavePagamento = client.solicitarPagamento(pedido);

        pedido.setChavePagamento(chavePagamento);

    };

    private void realizarPersistencia(Pedido pedido) {

        pedidoRepository.save(pedido);

        itemPedidoRepository.saveAll(pedido.getItens());

    };

    public ResponseEntity<Pedido> buscarPorCodigo(Long codigo) {

        Pedido pedidoV = pedidoRepository.findById(codigo).get();

        validator.validar(pedidoV);
        Optional<Pedido> pedido = pedidoRepository.findById(codigo);

        return pedido.map(ResponseEntity::ok).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });

    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dados, TipoPagamento pagamento) throws ItemNaoEncontradoException{

        var pedidoEncontrado = pedidoRepository.findById(codigoPedido);

        if (pedidoEncontrado.isEmpty()){
            throw new ItemNaoEncontradoException("Pedido não encontrado");
        }

        var pedido = pedidoEncontrado.get();

        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setPagamento(pagamento);
        dadosPagamento.setDados(dados);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(Status.REALIZADO);
        pedido.setObservacoes("novo pagamento realizado, aguardando novo processamento");

        String novaChavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);

        pedidoRepository.save(pedido);

    };


    public Optional<Pedido> carregarDadosCompletosPedido(Long codigo){

        Optional<Pedido> pedido = pedidoRepository.findById(codigo);
        pedido.ifPresent(this::carregarDadosCliente);
        pedido.ifPresent(this::carregarItensPedido);

        return pedido;
    };

    private void carregarDadosCliente(Pedido pedido){
        Long codigoCLiente = pedido.getCodigoCliente();
        var response = clienteClient.obterDados(codigoCLiente);
        pedido.setDadosCliente(response.getBody());
    };

    private void carregarItensPedido(Pedido pedido){
        List<ItemPedido> itensPedido = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itensPedido);
        pedido.getItens().forEach(this::carregarDadosProduto);
    };

    private void carregarDadosProduto(ItemPedido itemPedido){

        Long codigoProduto = itemPedido.getCodigoProduto();
        var response = produtoClient.obterPorCodigo(codigoProduto);
        itemPedido.setNome(response.getBody().nome());

    }

}
