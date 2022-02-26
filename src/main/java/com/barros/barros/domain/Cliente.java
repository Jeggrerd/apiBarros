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
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "nombre_completo")
    @ApiModelProperty(value = "Nombre completo del cliente", dataType = "String", position = 1, example = "Javier Cámara")
    private String nombre_completo;
    @ApiModelProperty(value = "Email del cliente", dataType = "String", position = 2, example = "javierCamara@gmail.com")
    @Column(name = "email")
    private String email;
    @ApiModelProperty(value = "Telefono del cliente", dataType = "String", position = 3, example = "987654321")
    @Column(name = "telefono")
    private String telefono;
    @ApiModelProperty(value = "Otros datos del cliente", dataType = "String", position = 4, example = "Odia las fresias")
    @Column(name = "otros")
    private String otros;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "Lista de eventos del cliente", dataType = "List<Eventos>", position = 5)
    @ManyToMany(mappedBy = "clientes")
    private List<Eventos> eventos;
}

