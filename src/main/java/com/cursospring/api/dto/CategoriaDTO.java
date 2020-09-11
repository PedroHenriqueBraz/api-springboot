package com.cursospring.api.dto;

import com.cursospring.api.domain.Categoria;
import com.cursospring.api.service.validations.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CategoriaDTO implements Serializable {
    private static final long serialVersionUID = 1L; //perguntar??

    private Integer id;

    @NotEmpty(message="preenchimento obrigatorio")
    @Length(min = 5, max = 80, message = "deve ser entre 5 e 80 caracters")
    private String nome;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Categoria categoria)
    {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
