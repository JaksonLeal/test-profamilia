import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Empleado } from '../models/empleado';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {

  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/empleados';

  listar(): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(this.apiUrl);
  }

  crear(empleado: {
    nombreCompleto: string;
    puesto: string;
  }): Observable<Empleado> {
    return this.http.post<Empleado>(this.apiUrl, empleado);
  }

  cambiarAsistencia(id: number): Observable<Empleado> {
    return this.http.put<Empleado>(
      `${this.apiUrl}/${id}/asistencia`,
      {}
    );
  }
}