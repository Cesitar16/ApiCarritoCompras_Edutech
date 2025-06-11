package com.edutech.carritocompras.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comuna")
@Data
public class Comuna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comuna")
    private Integer idComuna;

    @Column(nullable = false, length = 30)
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "PROVINCIA_id_provincia", nullable = false)
    private Provincia provincia;
}