package com.edutech.carritocompras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.carritocompras.models.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer>
{

}
