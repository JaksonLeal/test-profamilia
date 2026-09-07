package com.profamilia.back.exception;

public class EmpleadoNotFoundException extends RuntimeException {

    public EmpleadoNotFoundException(Long id) {
        super("Empleado no encontrado con id: " + id);
    }
}
