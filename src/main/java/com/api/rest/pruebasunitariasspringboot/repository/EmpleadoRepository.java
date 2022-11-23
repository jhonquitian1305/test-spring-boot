package com.api.rest.pruebasunitariasspringboot.repository;

import com.api.rest.pruebasunitariasspringboot.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByEmail(String email);
}
