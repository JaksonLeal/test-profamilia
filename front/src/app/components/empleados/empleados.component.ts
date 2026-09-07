import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmpleadoService } from '../../services/empleado.service';
import { Empleado } from '../../models/empleado';

@Component({
  selector: 'app-empleados',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './empleados.component.html',
  styleUrl: './empleados.component.css'
})
export class EmpleadosComponent implements OnInit {

  private empleadoService = inject(EmpleadoService);

  empleados: Empleado[] = [];
  loading = false;
  errorMessage = '';
  successMessage = '';
  nuevoEmpleado = {
    nombreCompleto: '',
    puesto: ''
  };

  ngOnInit(): void {
    this.cargarEmpleados();
  }

  cargarEmpleados(): void {
    this.loading = true;
    this.errorMessage = '';

    this.empleadoService.listar().subscribe({
      next: (data) => {
        this.empleados = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'No fue posible cargar los empleados.';
        this.loading = false;
      }
    });
  }

  agregarEmpleado(): void {

    if (!this.nuevoEmpleado.nombreCompleto.trim() ||
      !this.nuevoEmpleado.puesto.trim()) {
      this.errorMessage = 'Debe completar todos los campos.';
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.empleadoService.crear(this.nuevoEmpleado).subscribe({
      next: () => {

        this.nuevoEmpleado = {
          nombreCompleto: '',
          puesto: ''
        };

        this.successMessage = 'Empleado registrado correctamente.';
        this.cargarEmpleados();
      },

      error: () => {
        this.errorMessage = 'No fue posible registrar el empleado.';
        this.loading = false;
      }
    });
  }

  cambiarEstado(empleado: Empleado): void {

    const estadoActual = empleado.presente
      ? 'Presente'
      : 'Ausente';

    const nuevoEstado = empleado.presente
      ? 'Ausente'
      : 'Presente';

    const confirmar = window.confirm(
      `¿Desea cambiar a ${empleado.nombreCompleto} de ${estadoActual} a ${nuevoEstado}?`
    );

    if (!confirmar) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.empleadoService.cambiarAsistencia(empleado.id)
      .subscribe({
        next: () => {
          this.successMessage = 'Estado actualizado correctamente.';
          this.cargarEmpleados();
        },
        error: () => {
          this.errorMessage = 'No fue posible cambiar el estado.';
          this.loading = false;
        }
      });
  }
}
