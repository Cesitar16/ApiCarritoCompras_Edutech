package com.edutech.carritocompras.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;

@Data
public class UsuarioDTO extends RepresentationModel<UsuarioDTO>{

    private Integer idUsuario;
    private String username;
    private String email;
    private String password;
    private LocalDate fechaRegistro;
    private int activo;

}