package com.edutech.carritocompras.services;

import com.edutech.carritocompras.dto.CursoDTO;
import com.edutech.carritocompras.models.Curso;
import com.edutech.carritocompras.models.Usuario;
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
public class CursoService {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public CursoDTO guardar(CursoDTO dto) {
        Curso curso = toEntity(dto);
        Curso saved = repository.save(curso);
        return toDTO(saved);
    }

    public List<CursoDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CursoDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<CursoDTO> actualizar(Integer id, CursoDTO dto) {
        return repository.findById(id)
                .map(curso -> {
                    curso.setNombreCurso(dto.getNombreCurso());
                    curso.setDescripcion(dto.getDescripcion());
                    curso.setEstado(dto.getEstado());
                    curso.setPrecio(dto.getPrecio());

                    if (dto.getUsuarioId() != null) {
                        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + dto.getUsuarioId()));
                        curso.setUsuario(usuario);
                    }
                    
                    Curso actualizado = repository.save(curso);
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

    private CursoDTO toDTO(Curso curso) {
        CursoDTO dto = new CursoDTO();
        dto.setIdCurso(curso.getIdCurso());
        dto.setNombreCurso(curso.getNombreCurso());
        dto.setDescripcion(curso.getDescripcion());
        dto.setFechaCreacion(curso.getFechaCreacion());
        dto.setEstado(curso.getEstado());
        dto.setPrecio(curso.getPrecio());
        
        if (curso.getUsuario() != null) {
            dto.setUsuarioId(curso.getUsuario().getIdUsuario());
        }
        
        return dto;
    }

    private Curso toEntity(CursoDTO dto) {
        Curso curso = new Curso();
        curso.setIdCurso(dto.getIdCurso());
        curso.setNombreCurso(dto.getNombreCurso());
        curso.setDescripcion(dto.getDescripcion());
        curso.setFechaCreacion(dto.getFechaCreacion() != null ? dto.getFechaCreacion() : LocalDate.now());
        curso.setEstado(dto.getEstado());
        curso.setPrecio(dto.getPrecio());

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + dto.getUsuarioId()));
            curso.setUsuario(usuario);
        }

        return curso;
    }
}
