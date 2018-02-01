package com.marcoscarneiro.cursomc.services;

import com.marcoscarneiro.cursomc.domain.Categoria;
import com.marcoscarneiro.cursomc.repositories.CategoriaRepository;
import com.marcoscarneiro.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id){
        Categoria obj = repo.findOne(id);
        if(obj == null){
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
        }
        return obj;
    }

    public Categoria insert(Categoria obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj){
        find(obj.getId());
        return repo.save(obj);
    }
}
