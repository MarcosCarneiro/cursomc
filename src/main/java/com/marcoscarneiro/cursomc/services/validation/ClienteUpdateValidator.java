package com.marcoscarneiro.cursomc.services.validation;

import com.marcoscarneiro.cursomc.domain.Cliente;
import com.marcoscarneiro.cursomc.domain.enums.TipoCliente;
import com.marcoscarneiro.cursomc.dto.ClienteDTO;
import com.marcoscarneiro.cursomc.dto.ClienteNewDTO;
import com.marcoscarneiro.cursomc.repositories.ClienteRepository;
import com.marcoscarneiro.cursomc.resources.exception.FieldMessage;
import com.marcoscarneiro.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ServletRequest request;

    @Override
    public void initialize(ClienteUpdate clienteInsert) {

    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
        if(aux != null && !aux.getId().equals(uriId))
            list.add(new FieldMessage("email", "Email já existente"));

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
