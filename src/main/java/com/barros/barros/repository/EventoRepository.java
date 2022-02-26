package com.barros.barros.repository;

import com.barros.barros.domain.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EventoRepository extends JpaRepository<Eventos,Integer>, JpaSpecificationExecutor {

}
