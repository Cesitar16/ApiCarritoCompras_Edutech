package com.edutech.carritocompras.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CarritoDTO {

    private Integer idCarrito;
    private LocalDate fechaInscripcion;
    private Integer usuarioId;

}