package com.edutech.carritocompras.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;

@Data
public class CarritoDTO extends RepresentationModel <CarritoDTO>{

    private Integer idInscripcion;
    private LocalDate fechaInscripcion;
    private Integer usuarioId;
    private Integer cursoId;
}