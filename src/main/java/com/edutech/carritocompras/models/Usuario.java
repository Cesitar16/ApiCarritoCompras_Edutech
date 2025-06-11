package com.edutech.carritocompras.models;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class Usuario 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUsuario;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(nullable = false)
    private int activo;

    @ManyToOne
    @JoinColumn(name = "COMUNA_id_comuna", nullable = false)
    private Comuna comuna;

    @ManyToOne
    @JoinColumn(name = "ROL_id_rol", nullable = false)
    private Rol rol;
}