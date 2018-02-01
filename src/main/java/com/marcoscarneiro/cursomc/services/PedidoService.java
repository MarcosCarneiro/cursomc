package com.marcoscarneiro.cursomc.services;

import com.marcoscarneiro.cursomc.domain.Pedido;
import com.marcoscarneiro.cursomc.repositories.PedidoRepository;
import com.marcoscarneiro.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public Pedido find(Integer id){
        Pedido obj = repo.findOne(id);
        if(obj == null){
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName());
        }
        return obj;
    }
}
