package com.profamilia.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profamilia.back.entity.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
