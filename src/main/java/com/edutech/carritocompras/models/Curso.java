package com.edutech.carritocompras.models;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "curso")
@Data
public class Curso 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Integer idCurso;

    @Column(name = "nombre_curso", nullable = false, length = 30)
    private String nombreCurso;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @Column(nullable = false, length = 15)
    private String estado;

    @Column(nullable = false)
    private int precio;

    @ManyToOne
    @JoinColumn(name = "USUARIO_id_user", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "CATEGORIA_id_categoria", nullable = false)
    private Categoria categoria;
}
