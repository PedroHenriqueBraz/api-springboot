package com.cursospring.api.resources;

import com.cursospring.api.dto.CategoriaDTO;
import com.cursospring.api.dto.ClienteDTO;
import com.cursospring.api.dto.ClienteNewDTO;
import com.cursospring.api.service.CategoriaService;
import com.cursospring.api.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

    private final ClienteService clienteService;

    public ClienteResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        var cliente = clienteService.find(id);
        return ResponseEntity.ok().body(cliente);
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        var cliente = clienteService.findAll();
        return ResponseEntity.ok().body(cliente);
    }

    @GetMapping("/page")
    public ResponseEntity<?> findPage(@RequestParam(value="page", defaultValue = "0") Integer page,
                                      @RequestParam(value="linesPerPage", defaultValue = "2") Integer linesPerPage,
                                      @RequestParam(value="orderBy", defaultValue = "nome") String orderBy,
                                      @RequestParam(value="direction", defaultValue = "ASC")String direction){
        var clientesPage = clienteService
                .findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(clientesPage);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO obj){
        var cli = clienteService.fromDTO(obj);
        cli = clienteService.insert(cli);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cli.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", produces = "application/json",  method=RequestMethod.PUT)
    public ResponseEntity<Void> updated(@Valid @RequestBody ClienteDTO obj, @PathVariable Integer id){
        var cli = clienteService.fromDTO(obj);
        cli.setId(id);
        clienteService.update(cli);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
