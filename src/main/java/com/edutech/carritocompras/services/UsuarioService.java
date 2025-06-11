package com.edutech.carritocompras.services;

import com.edutech.carritocompras.dto.UsuarioDTO;
import com.edutech.carritocompras.models.Usuario;
import com.edutech.carritocompras.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public UsuarioDTO guardar(UsuarioDTO dto) {
        Usuario usuario = toEntity(dto);
        Usuario saved = repository.save(usuario);
        return toDTO(saved);
    }

    public List<UsuarioDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<UsuarioDTO> actualizar(Integer id, UsuarioDTO dto) {
        return repository.findById(id)
                .map(usuario -> {
                    usuario.setUsername(dto.getUsername());
                    usuario.setEmail(dto.getEmail());
                    usuario.setPassword(dto.getPassword());
                    usuario.setActivo(dto.getActivo());
                    Usuario actualizado = repository.save(usuario);
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

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setPassword(null); // Nunca devolver el password por seguridad
        dto.setFechaRegistro(usuario.getFechaRegistro());
        dto.setActivo(usuario.getActivo());
        return dto;
    }

    private Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.getIdUsuario());
        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setFechaRegistro(dto.getFechaRegistro() != null ? dto.getFechaRegistro() : LocalDate.now());
        usuario.setActivo(dto.getActivo());
        return usuario;
    }
}
