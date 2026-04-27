package com.generico.produto.Controllers;

import com.generico.produto.Services.ProdutoService;
import com.generico.produto.model.DTO.ProdutoDTO;
import com.generico.produto.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/produtos")
public class ProdutoController {
    @Autowired
    ProdutoService produtoService;

    @PostMapping(path = "/post")
    public ResponseEntity<Produto> addProduto(@RequestBody ProdutoDTO.BaseDTO dto){
        return produtoService.addProduto(dto);
    }

    @GetMapping(path = "/get/{codigo}")
    public ResponseEntity<Produto> procurarPorCodigo(@RequestParam Long codigo){
        return produtoService.
                obterPorCodigo(codigo).
                    map(ResponseEntity::ok).
                        orElseGet(() -> {
                            return ResponseEntity.notFound().build();
                        });
    }

}
