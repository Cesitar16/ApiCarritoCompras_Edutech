package com.edutech.carritocompras.controllers;

import com.edutech.carritocompras.dto.CarritoDTO;
import com.edutech.carritocompras.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Ya no se necesitan las importaciones de WebMvcLinkBuilder

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    @Autowired
    private CarritoService service;

    // --- Métodos CRUD estándar (sin cambios) ---
    @PostMapping
    public ResponseEntity<CarritoDTO> crear(@RequestBody CarritoDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<CarritoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarritoDTO> actualizar(@PathVariable Integer id, @RequestBody CarritoDTO dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // ======================================================
    // MÉTODOS HATEOAS
    // ======================================================

    /**
     * Obtiene un carrito por su ID y le añade enlaces HATEOAS.
     */
    @GetMapping("/hateoas/{id}")
    public CarritoDTO obtenerHATEOAS(@PathVariable Integer id) {
        CarritoDTO dto = service.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con id: " + id));

        // URL base del Gateway para construir los enlaces
        String gatewayUrl = "http://localhost:8888/api/proxy/carritos";

        // --- Enlaces apuntando exclusivamente al API Gateway ---

        // Link a sí mismo
        dto.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());
        
        // Link a la lista de todos los carritos
        dto.add(Link.of(gatewayUrl + "/hateoas").withRel("todos-los-carritos"));
        
        // Link para eliminar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));
        
        // Link para actualizar (nombre simplificado)
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));

        return dto;
    }

    /**
     * Obtiene todos los carritos y añade enlaces HATEOAS a cada uno.
     */
    @GetMapping("/hateoas")
    public List<CarritoDTO> listarHATEOAS() {
        List<CarritoDTO> carritos = service.listar();
        String gatewayUrl = "http://localhost:8888/api/proxy/carritos";

        for (CarritoDTO dto : carritos) {
            // Link a los detalles de este carrito
            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getIdInscripcion()).withSelfRel());

            // Link para crear un nuevo carrito
            dto.add(Link.of(gatewayUrl).withRel("crear-nuevo-carrito").withType("POST"));
        }

        return carritos;
    } 
}