package com.generico.produto.Services;

import com.generico.produto.Repositorys.ProdutoRepository;
import com.generico.produto.model.DTO.ProdutoDTO;
import com.generico.produto.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository produtoRepository;

    public ResponseEntity<Produto> addProduto(ProdutoDTO.BaseDTO dto){
        Produto produto = dto.produto();
        return ResponseEntity.ok(produtoRepository.save(produto));
    }

    public Optional<Produto> obterPorCodigo(Long codigoProduto){
        return produtoRepository.findById(codigoProduto);
    }
}
