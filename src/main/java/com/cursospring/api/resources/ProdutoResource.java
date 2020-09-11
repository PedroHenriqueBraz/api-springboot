package com.cursospring.api.resources;

import com.cursospring.api.dto.ProdutoDTO;
import com.cursospring.api.resources.utils.URL;
import com.cursospring.api.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

    private final ProdutoService produtoService;

    public ProdutoResource( ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        var produto = produtoService.buscar(id);
        return ResponseEntity.ok().body(produto);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value="nome", defaultValue = "") String nome,
            @RequestParam(value="categorias", defaultValue = "") String categorias,
            @RequestParam(value="page", defaultValue = "0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue = "2") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value="direction", defaultValue = "ASC")String direction){

        List<Integer> ids = URL.decodeIntList(categorias);
        String nomeDecoded = URL.decodeParam(nome);

        var produtosPage = produtoService
                .search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);

        Page<ProdutoDTO> listDTO = produtosPage.map(obj -> new ProdutoDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }
}
