package com.edutech.carritocompras.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "provincia")
@Data
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_provincia")
    private Integer idProvincia;

    @Column(nullable = false, length = 30)
    private String nombre;
}