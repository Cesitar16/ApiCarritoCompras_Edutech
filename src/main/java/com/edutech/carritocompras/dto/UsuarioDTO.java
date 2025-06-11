package com.edutech.carritocompras.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Integer idUsuario;
    private String username;
    private String email;
    private String password;
    private LocalDate fechaRegistro;
    private int activo;

}