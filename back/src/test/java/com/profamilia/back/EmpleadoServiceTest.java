package com.profamilia.back;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.profamilia.back.dto.EmpleadoRequest;
import com.profamilia.back.entity.Empleado;
import com.profamilia.back.exception.EmpleadoNotFoundException;
import com.profamilia.back.repository.EmpleadoRepository;
import com.profamilia.back.service.EmpleadoService;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

	@Mock
	private EmpleadoRepository empleadoRepository;

	@InjectMocks
	private EmpleadoService empleadoService;

	private Empleado empleado;

	@BeforeEach
	void setUp() {
		empleado = new Empleado("Juan Pérez", "Desarrollador");
	}

	@Test
	void debeCrearEmpleadoComoAusente() {

		EmpleadoRequest request = new EmpleadoRequest();
		request.setNombreCompleto("Juan Pérez");
		request.setPuesto("Desarrollador");

		when(empleadoRepository.save(any(Empleado.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Empleado resultado = empleadoService.crear(request);

		assertEquals("Juan Pérez", resultado.getNombreCompleto());
		assertEquals("Desarrollador", resultado.getPuesto());
		assertFalse(resultado.isPresente());

		verify(empleadoRepository).save(any(Empleado.class));
	}

	@Test
	void debeCambiarAsistencia() {

		when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

		when(empleadoRepository.save(any(Empleado.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Empleado resultado = empleadoService.cambiarAsistencia(1L);

		assertTrue(resultado.isPresente());

		verify(empleadoRepository).findById(1L);
		verify(empleadoRepository).save(empleado);
	}

	@Test
	void debeLanzarExcepcionSiEmpleadoNoExiste() {

		when(empleadoRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(EmpleadoNotFoundException.class, () -> empleadoService.cambiarAsistencia(999L));

		verify(empleadoRepository).findById(999L);
		verify(empleadoRepository, never()).save(any());
	}
}
