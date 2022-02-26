package com.barros.barros.controller;


import com.barros.barros.domain.Eventos;
import com.barros.barros.exception.EventoNotFoundException;
import com.barros.barros.service.EventoService;
import com.barros.barros.service.FileStorageService;
import com.sipios.springsearch.anotation.SearchSpec;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
public class EventoController {

    @Autowired
    private EventoService eventoService;
    @Autowired
    private FileStorageService fileStorageService;
    private final Logger logger = LoggerFactory.getLogger(EventoController.class);

    @ApiOperation(value="Obtener todos los eventos", notes="Se obtiene un Page con todos los eventos")
    @GetMapping(path="/eventos")
    public Page<Eventos> listaEventos(@PageableDefault(size=10, page = 0)
                                               Pageable pageable, @SearchSpec Specification<Eventos> specs) {
        logger.info("inicio listaEventos paginados y con filtro");
        Page<Eventos> response;
        response=eventoService.getEventos(pageable, specs);
        if ( pageable.getPageNumber() > response.getTotalPages()) {
            throw new EventoController.NotPageException();
        }
        logger.info("fin listaEventos  paginados y con filtro");
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class NotPageException extends RuntimeException {
        public NotPageException() {
            super("No se puede acceder a la página que estás solicitando");
        }
    }
    @ApiOperation(value="Obtener un evento concreto por id de evento", notes="Se obtienen los datos del evento cuyo id se ha introducido.")
    @GetMapping("/evento/{id}")
    public ResponseEntity<Eventos> findById(@PathVariable Integer id) {
        Eventos evento = eventoService.findById(id).orElseThrow(() -> new
                EventoNotFoundException(id));
        return new ResponseEntity<>(evento, HttpStatus.OK);
    }
    @ApiOperation(value="Agregar evento nuevo", notes="Agrega un evento con los datos introducidos.")
    @PostMapping(path="/evento")
    public ResponseEntity<Eventos> addEvento(@RequestPart("archivo") @Nullable MultipartFile multipartFile, @RequestPart("evento") Eventos evento) {
        UUID uuid = UUID.randomUUID();
        String uploadDir = "archivosEventos";
        evento.setArchivos(List.of(fileStorageService.storeFile(uploadDir,multipartFile)));
        Eventos addedEvento = eventoService.addEvento(evento);
        return new ResponseEntity<>(addedEvento, HttpStatus.OK);
    }

    @ExceptionHandler(EventoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(EventoNotFoundException pnfe) {
        Response response = Response.errorResponse(Response.NOT_FOUND,
                pnfe.getMessage());
        logger.error(pnfe.getMessage(), pnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ApiOperation(value="Borrar un evento", notes="Elimina el evento con el id introducido.")
    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<Response> deleteEvento(@PathVariable Integer id) {
        eventoService.deleteEvento(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }
    @ApiOperation(value="Modificar evento", notes="Se selecciona un evento por su id, y se modifica con los nuevos datos introducidos")
    @PutMapping("/evento/{id}")
    public ResponseEntity<Eventos> modifyEvento(@PathVariable Integer id, @RequestBody Eventos
            newEvento) {
        Eventos addedEvento = eventoService.modifyEvento(id, newEvento);
        return new ResponseEntity<>(addedEvento, HttpStatus.OK);
    }
}
