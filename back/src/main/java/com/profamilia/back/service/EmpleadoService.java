package com.profamilia.back.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.profamilia.back.dto.EmpleadoRequest;
import com.profamilia.back.entity.Empleado;
import com.profamilia.back.exception.EmpleadoNotFoundException;
import com.profamilia.back.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }

    public Empleado crear(EmpleadoRequest request) {

        Empleado empleado = new Empleado(
                request.getNombreCompleto(),
                request.getPuesto()
        );

        return empleadoRepository.save(empleado);
    }

    public Empleado cambiarAsistencia(Long id) {

        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNotFoundException(id));

        empleado.setPresente(!empleado.isPresente());

        return empleadoRepository.save(empleado);
    }
}
