package com.barros.barros.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "eventos")
public class Eventos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvento")
    private Integer id;
    @ApiModelProperty(value = "Tipo de evento", dataType = "String", position = 1, example = "boda")
    @Column(name = "tipoEvento")
    private String tipoEvento;
    @ApiModelProperty(value = "Fecha de inicio", dataType = "Date", position = 2, example = "1988-09-29")
    @Column(name = "fechaInicio")
    private Date fechaInicio;
    @ApiModelProperty(value = "Hora de Fin", dataType = "Date", position = 3)
    @Column(name = "horaFin")
    private Date horaFin;
    @ApiModelProperty(value = "Comida - Cena", dataType = "String", position = 4, example = "Solo comida")
    @Column(name = "comidaCena")
    private String comidaCena;
    @ApiModelProperty(value = "Salon completo", dataType = "String", position = 5, example = "Sala blanca y sala azul p1")
    @Column(name = "salonCompleto")
    private String salonCompleto;
    @ApiModelProperty(value = "Nº de comensales", dataType = "Integer", position = 6, example = "26")
    @Column(name = "comensales")
    private Integer comensales;
    @ApiModelProperty(value = "Numero de contrato", dataType = "String", position = 7, example = "2653")
    @Column(name = "nContrato")
    private String nContrato;
    @ApiModelProperty(value = "DJ", dataType = "String", position = 8, example = "El DJ Loko")
    @Column(name = "dj")
    private String dj;
    @ApiModelProperty(value = "Musica para el salon", dataType = "String", position = 9, example = "Rosalia")
    @Column(name = "musicaSalon")
    private String musicaSalon;
    @ApiModelProperty(value = "Barra libre de bebidas", dataType = "String", position = 10, example = "Hasta las 16:00")
    @Column(name = "barraLibre")
    private String barraLibre;
    @ApiModelProperty(value = "Flores para el evento", dataType = "String", position = 11, example = "Lirios y crisantemos")
    @Column(name = "flores")
    private String flores;
    @ApiModelProperty(value = "Ceremonia en barros", dataType = "Boolean", position = 12, example = "True")
    @Column(name = "barrosCeremonia")
    private Boolean barrosCeremonia;
    @ApiModelProperty(value = "Informacion de los autobuses", dataType = "String", position = 13, example = "Llegan a las 14:20")
    @Column(name = "autobus")
    private String autobus;
    @ApiModelProperty(value = "Bono de autobus", dataType = "Boolean", position = 14, example = "False")
    @Column(name = "bonoBus")
    private Boolean bonoBus;
    @ApiModelProperty(value = "Bono de hotel", dataType = "Boolean", position = 15, example = "True")
    @Column(name = "bonoHotel")
    private Boolean bonoHotel;
    @ApiModelProperty(value = "Vino para el evento", dataType = "String", position = 16, example = "Marqués de Riscal")
    @Column(name = "vino")
    private String vino;
    @ApiModelProperty(value = "Cava para el evento", dataType = "String", position = 17, example = "Ana Codorniu")
    @Column(name = "cava")
    private String cava;
    @ApiModelProperty(value = "Rincones durante el evento", dataType = "String", position = 18, example = "Rincón de creppes")
    @Column(name = "rincones")
    private String rincones;
    @ApiModelProperty(value = "Decoración para el evento", dataType = "String", position = 19, example = "Todo color salmón")
    @Column(name = "decoracion")
    private String decoracion;
    @ApiModelProperty(value = "Notas adicionales del evento", dataType = "String", position = 20, example = "Quieren una fuente de chocolate")
    @Column(name = "notas")
    private String notas;
    @ApiModelProperty(value = "Musica para el aperitivo", dataType = "String", position = 21, example = "Radiohead")
    @Column(name = "musicaAperitivo")
    private String musicaAperitivo;
    @ApiModelProperty(value = "Icono del evento", dataType = "String", position = 22, example = "anillo.png")
    @Column(name = "iconoEvento")
    private String iconoEvento;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany
    @JoinTable(name = "clientes_eventos",
            joinColumns = @JoinColumn(name = "idEvento"),
            inverseJoinColumns = @JoinColumn(name = "idCliente"))
    @ApiModelProperty(value = "Lista de los clientes del evento", dataType = "List<Cliente>", position = 23)
    private List<Cliente> clientes;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany
    @JoinTable(name = "archivos_eventos",
            joinColumns = @JoinColumn(name = "idEvento"),
            inverseJoinColumns = @JoinColumn(name = "idArchivo"))
    @ApiModelProperty(value = "Archivos asociados al evento", dataType = "List<Archivos>", position = 24)
    private List<Archivos> archivos;













    }
