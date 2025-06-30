package com.edutech.carritocompras.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;

@Data
public class CursoDTO extends RepresentationModel <CursoDTO>{

    private Integer idCurso;
    private String nombreCurso;
    private String descripcion;
    private LocalDate fechaCreacion;
    private String estado;
    private int precio;
    private Integer usuarioId;

}