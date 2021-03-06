package com.marcoscarneiro.cursomc.services;

import com.marcoscarneiro.cursomc.domain.ItemPedido;
import com.marcoscarneiro.cursomc.domain.PagamentoComBoleto;
import com.marcoscarneiro.cursomc.domain.Pedido;
import com.marcoscarneiro.cursomc.domain.enums.EstadoPagamento;
import com.marcoscarneiro.cursomc.repositories.ItemPedidoRepository;
import com.marcoscarneiro.cursomc.repositories.PagamentoRepository;
import com.marcoscarneiro.cursomc.repositories.PedidoRepository;
import com.marcoscarneiro.cursomc.repositories.ProdutoRepository;
import com.marcoscarneiro.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private BoletoService boletoService;

    public Pedido find(Integer id){
        Pedido obj = repo.findOne(id);
        if(obj == null){
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName());
        }
        return obj;
    }

    public List<Pedido> findAll(){
        return repo.findAll();
    }

    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if(obj.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()){
            ip.setDesconto(0.0);
            ip.setPreco(produtoRepository.findOne(ip.getProduto().getId()).getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.save(obj.getItens());
        return obj;
    }
}
