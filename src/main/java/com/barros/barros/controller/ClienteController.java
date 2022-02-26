package com.barros.barros.controller;

import com.barros.barros.domain.Cliente;
import com.barros.barros.exception.ClienteNotFoundException;
import com.barros.barros.service.ClienteService;
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
import org.springframework.web.bind.annotation.*;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    private final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @ApiOperation(value="Obtener clientes", notes="Se obtiene una pagina con los clientes que existen. Se puede filtrar y ordenar.")
    @GetMapping(path="/clientes")
    public Page<Cliente> listaClientes(@PageableDefault(size=10, page = 0)
                                             Pageable pageable, @SearchSpec Specification<Cliente> specs) {
        logger.info("inicio listaPaises paginados y con filtro");
        Page<Cliente> response;
        response=clienteService.getClientes(pageable, specs);
        if ( pageable.getPageNumber() > response.getTotalPages()) {
            throw new NotPageException();
        }
        logger.info("fin listaClientes  paginados y con filtro");
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class NotPageException extends RuntimeException {
        public NotPageException() {
            super("No se puede acceder a la página que estás solicitando");
        }
    }
    @ApiOperation(value="Obtener un determinado cliente", notes="Se obtiene un cliente determinado por su id")
    @GetMapping("/cliente/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Integer id) {
        Cliente cliente = clienteService.findById(id).orElseThrow(() -> new
                ClienteNotFoundException(id));
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
    @ApiOperation(value="Agregar cliente", notes="Agrega un cliente más con los datos introducidos.")
    @PostMapping(path="/cliente")
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente cliente) {
        Cliente addedCliente = clienteService.addCliente(cliente);
        return new ResponseEntity<>(addedCliente, HttpStatus.OK);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(ClienteNotFoundException pnfe) {
        Response response = Response.errorResponse(Response.NOT_FOUND,
                pnfe.getMessage());
        logger.error(pnfe.getMessage(), pnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ApiOperation(value="Eliminar cliente", notes="Elimina un cliente especificando su id")
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Response> deleteCliente(@PathVariable Integer id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }
    @ApiOperation(value="Modificar cliente", notes="Modifica los datos del cliente con el id introducido. Es necesario introducir los nuevos valores para el cliente en cuestión")
    @PutMapping("/cliente/{id}")
    public ResponseEntity<Cliente> modifyCliente(@PathVariable Integer id, @RequestBody Cliente
            newCliente) {
        Cliente addedCliente = clienteService.modifyCliente(id, newCliente);
        return new ResponseEntity<>(addedCliente, HttpStatus.OK);
    }
}
