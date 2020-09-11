package com.cursospring.api.service;

import com.cursospring.api.domain.*;
import com.cursospring.api.domain.enuns.EstadoPagamento;
import com.cursospring.api.domain.enuns.TipoCliente;
import com.cursospring.api.repository.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DbService {
    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final EstadoRepository estadoRepository;
    private final CidadeRepository cidadeRepository;
    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;
    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

    public DbService(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository, EstadoRepository estadoRepository, CidadeRepository cidadeRepository, EnderecoRepository enderecoRepository, ClienteRepository clienteRepository, PagamentoRepository pagamentoRepository, PedidoRepository pedidoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.estadoRepository = estadoRepository;
        this.cidadeRepository = cidadeRepository;
        this.enderecoRepository = enderecoRepository;
        this.clienteRepository = clienteRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public void instatiateTestDb() throws ParseException {
        Categoria cat1 = new Categoria(null, "Informatica");
        Categoria cat2 = new Categoria(null, "Escritorio");
        Categoria cat3 = new Categoria(null, "categoria 3");
        Categoria cat4 = new Categoria(null, "categoria 4");
        Categoria cat5 = new Categoria(null, "categoria 5");

        Produto p1 = new Produto(null, "Computador", 2000.0);
        Produto p2 = new Produto(null, "Impressora", 1000.0);
        Produto p3 = new Produto(null, "Caneta", 800.0);
        Produto p4 = new Produto(null, "Cadeira", 100.0);
        Produto p5 = new Produto(null, "Monitor", 400.0);
        Produto p6 = new Produto(null, "Mouse", 500.0);

        cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
        cat2.getProdutos().addAll(Arrays.asList(p2,p4));
        cat3.getProdutos().addAll(Arrays.asList(p1,p5));
        cat4.getProdutos().addAll(Arrays.asList(p6));
        cat5.getProdutos().addAll(Arrays.asList(p1,p2,p6));

        p1.getCategorias().addAll(Arrays.asList(cat1,cat3,cat5));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat5));
        p3.getCategorias().addAll(Arrays.asList(cat1));
        p4.getCategorias().addAll(Arrays.asList(cat2));
        p5.getCategorias().addAll(Arrays.asList(cat3));
        p6.getCategorias().addAll(Arrays.asList(cat5, cat4));


        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null, "Uberlandia", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2,c3));

        Cliente cli1  = new Cliente(null, "pedro braz", "email@email.com","77777", TipoCliente.PESSOAFISICA);

        cli1.getTelefones().addAll(Arrays.asList("23232-323","23424234-13213"));

        Endereco end1 = new Endereco(null, "rua xyz", "22","apto 1", "bairro", "2223-000",cli1, c1);
        Endereco end2 = new Endereco(null, "rua xyz222", "22","apto 1", "bairro", "2223-884",cli1, c2);

        cli1.getEnderecos().addAll(Arrays.asList(end2,end2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null, sdf.parse("30/08/2020 18:30"), cli1, end1);
        Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pag1);

        cli1.getPedidos().addAll(Arrays.asList(ped1));

        categoriaRepository.saveAll(Arrays.asList(cat1,cat2, cat3, cat4, cat5));
        produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
        estadoRepository.saveAll(Arrays.asList(est1,est2));
        cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));

        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(end1, end2));

        pedidoRepository.save(ped1);
        pagamentoRepository.save(pag1);

    }
}
