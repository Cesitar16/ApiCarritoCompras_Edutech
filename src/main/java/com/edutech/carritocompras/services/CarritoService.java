package com.edutech.carritocompras.services;

import com.edutech.carritocompras.dto.CarritoDTO;
import com.edutech.carritocompras.models.Carrito;
import com.edutech.carritocompras.models.Curso;
import com.edutech.carritocompras.models.Usuario;
import com.edutech.carritocompras.repository.CarritoRepository;
import com.edutech.carritocompras.repository.CursoRepository;
import com.edutech.carritocompras.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CursoRepository cursoRepository;

    public CarritoDTO guardar(CarritoDTO dto) {
        Carrito carrito = toEntity(dto);
        Carrito saved = repository.save(carrito);
        return toDTO(saved);
    }

    public List<CarritoDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CarritoDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<CarritoDTO> actualizar(Integer id, CarritoDTO dto) {
        return repository.findById(id)
                .map(carrito -> {
                    carrito.setFechaInscripcion(dto.getFechaInscripcion());
                    
                    if (dto.getUsuarioId() != null) {
                        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + dto.getUsuarioId()));
                        carrito.setUsuario(usuario);
                    }

                    // CORRECCIÓN: Ahora usa cursoId
                    if (dto.getCursoId() != null) {
                        Curso curso = cursoRepository.findById(dto.getCursoId())
                                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con ID: " + dto.getCursoId()));
                        carrito.setCurso(curso);
                    }

                    Carrito actualizado = repository.save(carrito);
                    return toDTO(actualizado);
                });
    }

    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private CarritoDTO toDTO(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setIdInscripcion(carrito.getIdInscripcion());
        dto.setFechaInscripcion(carrito.getFechaInscripcion());

        if (carrito.getUsuario() != null) {
            dto.setUsuarioId(carrito.getUsuario().getIdUsuario());
        }

        // CORRECCIÓN: Ahora asigna solo el ID del curso
        if (carrito.getCurso() != null) {
            dto.setCursoId(carrito.getCurso().getIdCurso());
        }

        return dto;
    }

    private Carrito toEntity(CarritoDTO dto) {
        Carrito carrito = new Carrito();
        carrito.setIdInscripcion(dto.getIdInscripcion());
        carrito.setFechaInscripcion(dto.getFechaInscripcion() != null ? dto.getFechaInscripcion() : LocalDate.now());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + dto.getUsuarioId()));
            carrito.setUsuario(usuario);
        }
        
        // CORRECCIÓN: Ahora busca el curso usando el ID
        if (dto.getCursoId() != null) {
            Curso curso = cursoRepository.findById(dto.getCursoId())
                    .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con ID: " + dto.getCursoId()));
            carrito.setCurso(curso);
        }
        
        return carrito;
    }
}
