package com.cursospring.api.service;

import com.cursospring.api.domain.ItemPedido;
import com.cursospring.api.domain.PagamentoComBoleto;
import com.cursospring.api.domain.Pedido;
import com.cursospring.api.domain.enuns.EstadoPagamento;
import com.cursospring.api.repository.ItemPedidoRepository;
import com.cursospring.api.repository.PagamentoRepository;
import com.cursospring.api.repository.PedidoRepository;
import com.cursospring.api.service.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final BoletoService boletoService;

    private final PagamentoRepository pagamentoRepository;

    private final ProdutoService produtoService;

    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository, BoletoService boletoService, PagamentoRepository pagamentoRepository, ProdutoService produtoService, ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.boletoService = boletoService;
        this.pagamentoRepository = pagamentoRepository;
        this.produtoService = produtoService;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public Pedido buscar(Integer id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
                + ", Tipo: " + Pedido.class.getName()));
    }

    public Pedido insert(Pedido pedido){
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if (pedido.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
        }
        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        for (ItemPedido ip : pedido.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.buscar(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(pedido);
        }

        itemPedidoRepository.saveAll(pedido.getItens());
        //emailService.sendOrderConfirmationEmail(obj);
        return pedido;
    }
}
