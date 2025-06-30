package com.edutech.carritocompras.controllers;

import com.edutech.carritocompras.dto.CarritoDTO;
import com.edutech.carritocompras.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Importaciones estáticas para HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {

    @Autowired
    private CarritoService service;

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
        // Obtenemos el DTO. Si no existe, lanzará una excepción (o puedes manejarlo como prefieras)
        CarritoDTO dto = service.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con id: " + id)); // O una excepción más específica

        // 1. Links internos del microservicio (usando WebMvcLinkBuilder)
        // Link a sí mismo (self)
        dto.add(linkTo(methodOn(CarritoController.class).obtenerHATEOAS(id)).withSelfRel());
        // Link a la lista de todos los carritos
        dto.add(linkTo(methodOn(CarritoController.class).listarHATEOAS()).withRel("todos-los-carritos"));
        // Link para eliminar este carrito
        dto.add(linkTo(methodOn(CarritoController.class).eliminar(id)).withRel("eliminar"));

        // 2. Links externos a través del API Gateway (construidos manualmente)
        String gatewayUrl = "http://localhost:8888/api/proxy/carritos";
        // Link a sí mismo a través del Gateway
        dto.add(Link.of(gatewayUrl + "/" + dto.getIdInscripcion()).withSelfRel());
        // Link para modificar a través del Gateway
        dto.add(Link.of(gatewayUrl + "/" + dto.getIdInscripcion()).withRel("actualizar-via-gateway").withType("PUT"));
        // Link para eliminar a través del Gateway
        dto.add(Link.of(gatewayUrl + "/" + dto.getIdInscripcion()).withRel("eliminar-via-gateway").withType("DELETE"));

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
            // 1. Link interno a los detalles de este carrito
            dto.add(linkTo(methodOn(CarritoController.class).obtenerHATEOAS(dto.getIdInscripcion())).withSelfRel());

            // 2. Links externos a través del API Gateway
            // Link para crear un nuevo carrito (se puede añadir a cada item o a un wrapper de la colección)
             dto.add(Link.of(gatewayUrl).withRel("crear-nuevo-carrito").withType("POST"));
        }

        return carritos;
    }
}