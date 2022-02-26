package com.barros.barros.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "archivos")
public class Archivos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArchivo")
    private Integer id;
    @Column(name = "tipo")
    @ApiModelProperty(value = "Tipo del archivo", dataType = "String", position = 1, example = "vinos")
    private String tipo;
    @Column(name = "nombre")
    @ApiModelProperty(value = "Nombre del archivo", dataType = "String", position = 2, example = "murano.png")
    private String nombre;
    @Column(name = "observable")
    @ApiModelProperty(value = "Observaciones del archivo", dataType = "String", position = 3)
    private String observable;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "archivos")
    @ApiModelProperty(value = "Lista de eventos para un archivo", dataType = "List<Eventos>", position = 4)
    private List<Eventos> eventos;

}
