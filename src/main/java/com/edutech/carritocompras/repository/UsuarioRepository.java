package com.edutech.carritocompras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.carritocompras.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>
{

}
