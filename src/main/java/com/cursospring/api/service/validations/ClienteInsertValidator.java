package com.cursospring.api.service.validations;

import com.cursospring.api.domain.Cliente;
import com.cursospring.api.domain.enuns.TipoCliente;
import com.cursospring.api.dto.ClienteNewDTO;
import com.cursospring.api.repository.ClienteRepository;
import com.cursospring.api.resources.exceptions.FieldMessage;
import com.cursospring.api.service.validations.utils.BR;

import javax.validation.ConstraintValidator;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    private final ClienteRepository clienteRepository;

    public ClienteInsertValidator(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void initialize(ClienteInsert constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteNewDTO value, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        // inclua os testes aqui, inserindo erros na lista

        if (value.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(value.getCnpjcpf())) {
            list.add(new FieldMessage("cnpjcpf", "CPF inválido"));
        }

        if (value.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(value.getCnpjcpf())) {
            list.add(new FieldMessage("cnpjcpf", "CNPJ inválido"));
        }

        Cliente cliente = clienteRepository.findByEmail(value.getEmail());
        if(cliente != null){
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
