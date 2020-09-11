package com.cursospring.api.resources;

import com.cursospring.api.domain.Pedido;
import com.cursospring.api.dto.CategoriaDTO;
import com.cursospring.api.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

    private final PedidoService pedidoService;

    public PedidoResource(PedidoService pedidoService) {

        this.pedidoService = pedidoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        var pedido = pedidoService.buscar(id);
        return ResponseEntity.ok().body(pedido);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido){
        pedido = pedidoService.insert(pedido);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
