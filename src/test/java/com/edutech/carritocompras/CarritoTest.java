package com.edutech.carritocompras;

import com.edutech.carritocompras.models.Carrito;
import com.edutech.carritocompras.models.Curso;
import com.edutech.carritocompras.models.Usuario;
import com.edutech.carritocompras.dto.CarritoDTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarritoTest {

    @Test
    @DisplayName("Prueba para verificar la creación de un Carrito y sus relaciones")
    void testCrearCarritoConRelaciones() {
        // 1. Crear objetos de dependencia
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setUsername("cesar");

        Curso curso = new Curso();
        curso.setIdCurso(10);
        curso.setNombreCurso("Curso de Docker Avanzado");

        // 2. Crear el objeto principal
        Carrito carrito = new Carrito();
        carrito.setIdInscripcion(1);
        carrito.setFechaInscripcion(LocalDate.now());
        carrito.setUsuario(usuario);
        carrito.setCurso(curso);

        // 3. Validar
        assertNotNull(carrito);
        assertEquals(1, carrito.getIdInscripcion());
        assertEquals(1, carrito.getUsuario().getIdUsuario());
        assertEquals("Curso de Docker Avanzado", carrito.getCurso().getNombreCurso());
    }

    @Test
    @DisplayName("Prueba para verificar la creación de un CarritoDTO")
    void testCrearCarritoDTO() {
        // 1. Crear el DTO principal
        CarritoDTO carritoDTO = new CarritoDTO();
        carritoDTO.setIdInscripcion(5);
        carritoDTO.setUsuarioId(8);
        carritoDTO.setCursoId(15);
        carritoDTO.setFechaInscripcion(LocalDate.of(2025, 12, 1));

        // 2. Validar
        assertNotNull(carritoDTO);
        assertEquals(5, carritoDTO.getIdInscripcion());
        assertEquals(8, carritoDTO.getUsuarioId());
        assertEquals(15, carritoDTO.getCursoId());
    }
}
