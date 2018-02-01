package com.marcoscarneiro.cursomc.services;

import com.marcoscarneiro.cursomc.domain.Cliente;
import com.marcoscarneiro.cursomc.repositories.ClienteRepository;
import com.marcoscarneiro.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Cliente find(Integer id){
        Cliente obj = repo.findOne(id);
        if(obj == null){
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
        }
        return obj;
    }
}
