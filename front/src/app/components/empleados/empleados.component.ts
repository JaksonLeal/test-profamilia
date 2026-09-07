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

  nuevoEmpleado = {
    nombreCompleto: '',
    puesto: ''
  };

  ngOnInit(): void {
    this.cargarEmpleados();
  }

  cargarEmpleados(): void {
    this.empleadoService.listar().subscribe({
      next: (data) => {
        this.empleados = data;
      },
      error: (error) => {
        console.error('Error cargando empleados', error);
      }
    });
  }

  agregarEmpleado(): void {

    if (!this.nuevoEmpleado.nombreCompleto.trim() ||
        !this.nuevoEmpleado.puesto.trim()) {
      return;
    }

    this.empleadoService.crear(this.nuevoEmpleado).subscribe({
      next: () => {
        this.nuevoEmpleado = {
          nombreCompleto: '',
          puesto: ''
        };

        this.cargarEmpleados();
      },
      error: (error) => {
        console.error('Error creando empleado', error);
      }
    });
  }

  cambiarEstado(empleado: Empleado): void {

    this.empleadoService.cambiarAsistencia(empleado.id)
      .subscribe({
        next: () => {
          this.cargarEmpleados();
        },
        error: (error) => {
          console.error('Error cambiando estado', error);
        }
      });
  }
}
