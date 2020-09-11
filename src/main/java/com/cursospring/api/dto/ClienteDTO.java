package com.cursospring.api.dto;

import com.cursospring.api.domain.Cliente;
import com.cursospring.api.service.validations.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteUpdate
public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message = "Campo obrigatorio")
    @Length(min= 5, max = 120, message = "deve ter entre 5 e 120 caracteres")
    private String nome;

    @NotEmpty(message = "Campo obrigatorio")
    @Email(message = "email invalido")
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente obj){
        id = obj.getId();
        nome = obj.getNome();
        email = obj.getEmail();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
