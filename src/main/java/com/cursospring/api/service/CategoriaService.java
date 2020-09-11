package com.cursospring.api.service;

import com.cursospring.api.domain.Categoria;
import com.cursospring.api.dto.CategoriaDTO;
import com.cursospring.api.repository.CategoriaRepository;
import com.cursospring.api.service.exceptions.DataIntegrityException;
import com.cursospring.api.service.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria find(Integer id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
                + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria obj){
        obj.setId(null);
        return categoriaRepository.save(obj);
    }

    public Categoria update(Categoria obj){
        var newObj = find(obj.getId());
        updateData(newObj, obj);
        return categoriaRepository.save(newObj);
    }

    public void delete(Integer id){
        find(id);
        try
        {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Não é possivel excluir uma categoria que possui podutos");
        }
    }

    public List<CategoriaDTO> findAll(){
        List<CategoriaDTO> list = categoriaRepository.findAll().stream()
                .map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return list;
    }

    public Page<CategoriaDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest).map(obj -> new CategoriaDTO(obj));
    }

    public Categoria fromDTO(CategoriaDTO obj){
        return new Categoria(obj.getId(), obj.getNome());
    }

    private void updateData(Categoria newObj, Categoria obj){
        newObj.setNome(obj.getNome());
    }
}
