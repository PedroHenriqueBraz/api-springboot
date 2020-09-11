package com.cursospring.api.service;

import com.cursospring.api.domain.Cidade;
import com.cursospring.api.domain.Cliente;
import com.cursospring.api.domain.Endereco;
import com.cursospring.api.domain.enuns.TipoCliente;
import com.cursospring.api.dto.ClienteDTO;
import com.cursospring.api.dto.ClienteNewDTO;
import com.cursospring.api.repository.ClienteRepository;
import com.cursospring.api.repository.EnderecoRepository;
import com.cursospring.api.service.exceptions.DataIntegrityException;
import com.cursospring.api.service.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private  final EnderecoRepository enderecoRepository;

    public ClienteService(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public Cliente find(Integer id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id
                + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente obj){
        obj.setId(null);
        obj = clienteRepository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos()); //preciso disso? funciona sem
        return obj;
    }

    public Cliente update(Cliente obj){
       var newObj =  find(obj.getId());
       updateData(newObj, obj);
       return clienteRepository.save(newObj);
    }

    public void delete(Integer id){
        find(id);
        try
        {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Não é possivel excluir um cliente pq ha entidades relacionadas");
        }
    }

    public List<ClienteDTO> findAll(){
        List<ClienteDTO> list = clienteRepository.findAll().stream()
                .map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return list;
    }

    public Page<ClienteDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest).map(obj -> new ClienteDTO(obj));
    }

    public Cliente fromDTO(ClienteDTO obj){
        return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null);
    }

    public Cliente fromDTO(ClienteNewDTO obj){
        Cidade cid = new Cidade(obj.getCidadeId(), null, null);
        Cliente cli =  new Cliente(null, obj.getNome(), obj.getEmail(), obj.getCnpjcpf(), TipoCliente.toEnum(obj.getTipo()));
        Endereco end = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(),
                obj.getBairro(), obj.getCep(), cli, cid);

        cli.getEnderecos().add(end);
        cli.getTelefones().add(obj.getTelefone1());
        if (obj.getTelefone2()!=null) {
            cli.getTelefones().add(obj.getTelefone2());
        }
        if (obj.getTelefone3()!=null) {
            cli.getTelefones().add(obj.getTelefone3());
        }
        return cli;
    }

    private void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}
