package com.barros.barros.service;


import com.barros.barros.domain.Cliente;
import com.barros.barros.exception.ClienteNotFoundException;
import com.barros.barros.repository.ClienteRepository;
import com.sipios.springsearch.anotation.SearchSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService extends BaseService<Cliente, Integer, ClienteRepository> {
    @Autowired
    private ClienteRepository clienteRepository;


    public Page<Cliente> getClientes(Pageable pageable, @SearchSpec Specification<Cliente> specs){
        return clienteRepository.findAll(Specification.where(specs), pageable);
    }

    public Optional<Cliente> findById(Integer id){
        return clienteRepository.findById(id);
    }

    public Cliente addCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deleteCliente(Integer id) {
        clienteRepository.findById(id).orElseThrow(()-> new ClienteNotFoundException(id));
        clienteRepository.deleteById(id);
    }

    public Cliente modifyCliente(Integer id, Cliente newCliente) {
        Cliente cliente= clienteRepository.findById(id).orElseThrow(()-> new
                ClienteNotFoundException(id));
        newCliente.setId(cliente.getId());
        return clienteRepository.save(newCliente);
    }
}
