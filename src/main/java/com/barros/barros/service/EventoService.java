package com.barros.barros.service;


import com.barros.barros.domain.Eventos;
import com.barros.barros.exception.EventoNotFoundException;
import com.barros.barros.repository.EventoRepository;
import com.sipios.springsearch.anotation.SearchSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventoService extends BaseService<Eventos, Integer, EventoRepository>{
    @Autowired
    private EventoRepository eventoRepository;


    public Page<Eventos> getEventos(Pageable pageable, @SearchSpec Specification<Eventos> specs){
        return eventoRepository.findAll(Specification.where(specs), pageable);
    }

    public Optional<Eventos> findById(Integer id){
        return eventoRepository.findById(id);
    }

    public Eventos addEvento(Eventos evento) {
        return eventoRepository.save(evento);
    }

    public void deleteEvento(Integer id) {
        eventoRepository.findById(id).orElseThrow(()-> new EventoNotFoundException(id));
        eventoRepository.deleteById(id);
    }

    public Eventos modifyEvento(Integer id, Eventos newEvento) {
        Eventos evento= eventoRepository.findById(id).orElseThrow(()-> new
                EventoNotFoundException(id));
        newEvento.setId(evento.getId());
        return eventoRepository.save(newEvento);
    }

}
