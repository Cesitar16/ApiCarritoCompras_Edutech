package com.edutech.carritocompras.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CursoDTO {

    private Integer idCurso;
    private String nombreCurso;
    private String descripcion;
    private LocalDate fechaCreacion;
    private String estado;
    private int precio;
    private Integer usuarioId;

}