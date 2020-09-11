package com.cursospring.api.resources;

import com.cursospring.api.domain.Categoria;
import com.cursospring.api.dto.CategoriaDTO;
import com.cursospring.api.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

    private final CategoriaService categoriaService;

    public CategoriaResource(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        var categoria = categoriaService.find(id);
        return ResponseEntity.ok().body(categoria);
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        var categoria = categoriaService.findAll();
        return ResponseEntity.ok().body(categoria);
    }

    @GetMapping("/page")
    public ResponseEntity<?> findPage(@RequestParam(value="page", defaultValue = "0") Integer page,
                                      @RequestParam(value="linesPerPage", defaultValue = "2") Integer linesPerPage,
                                      @RequestParam(value="orderBy", defaultValue = "nome") String orderBy,
                                      @RequestParam(value="direction", defaultValue = "ASC")String direction){
        var categoriasPage = categoriaService
                .findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(categoriasPage);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO obj){
        var cat = categoriaService.fromDTO(obj);
        cat = categoriaService.insert(cat);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cat.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", produces = "application/json",  method=RequestMethod.PUT)
    public ResponseEntity<Void> updated(@Valid @RequestBody CategoriaDTO obj, @PathVariable Integer id){
        var cat = categoriaService.fromDTO(obj);
        categoriaService.update(cat);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
