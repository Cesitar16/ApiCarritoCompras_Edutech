package com.edutech.carritocompras.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.carritocompras.models.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer>
{

}
