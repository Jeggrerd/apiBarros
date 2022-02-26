package com.barros.barros.repository;


import com.barros.barros.domain.Archivos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArchivoRepository  extends JpaRepository<Archivos,Integer>, JpaSpecificationExecutor {
}
