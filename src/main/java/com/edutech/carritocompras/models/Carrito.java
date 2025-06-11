package com.edutech.carritocompras.models;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carrito")
@Data
public class Carrito 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    private Integer idInscripcion;

    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_id_user", nullable = false)
    @JsonBackReference("usuario-carrito")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURSO_id_curso", nullable = false)
    @JsonBackReference("curso-carrito")
    private Curso curso;
}
