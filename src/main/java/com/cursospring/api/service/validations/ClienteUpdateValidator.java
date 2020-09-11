package com.cursospring.api.service.validations;

import com.cursospring.api.domain.Cliente;
import com.cursospring.api.dto.ClienteDTO;
import com.cursospring.api.repository.ClienteRepository;
import com.cursospring.api.resources.exceptions.FieldMessage;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    private final ClienteRepository clienteRepository;
    private final HttpServletRequest httpServletRequest;

    public ClienteUpdateValidator(ClienteRepository clienteRepository, HttpServletRequest httpServletRequest) {
        this.clienteRepository = clienteRepository;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteDTO value, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        Map<String, String> map = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        var uriId = Integer.parseInt(map.get("id"));

        Cliente cliente = clienteRepository.findByEmail(value.getEmail());

        if(cliente != null && !cliente.getId().equals(uriId)){
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
