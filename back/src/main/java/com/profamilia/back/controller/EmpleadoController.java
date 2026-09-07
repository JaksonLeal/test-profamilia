package com.profamilia.back.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.profamilia.back.dto.EmpleadoRequest;
import com.profamilia.back.entity.Empleado;
import com.profamilia.back.service.EmpleadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empleado crear(@Valid @RequestBody EmpleadoRequest request) {
        return empleadoService.crear(request);
    }

    @PutMapping("/{id}/asistencia")
    public Empleado cambiarAsistencia(@PathVariable Long id) {
        return empleadoService.cambiarAsistencia(id);
    }
}
