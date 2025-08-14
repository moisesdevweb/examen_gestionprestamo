package edu.pe.zegel.gestionarprestamogrupodev.service;

import edu.pe.zegel.gestionarprestamogrupodev.model.entity.Alumno;
import edu.pe.zegel.gestionarprestamogrupodev.model.entity.Prestamo;
import edu.pe.zegel.gestionarprestamogrupodev.repository.AlumnoRepository;
import edu.pe.zegel.gestionarprestamogrupodev.repository.PrestamoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PrestamoService {
    @Autowired
    private PrestamoRepository prestamoRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;

    @Transactional
    public Prestamo crearPrestamo(Long alumnoId, String libro, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        // Validar existencia del alumno
        Optional<Alumno> alumnoOpt = alumnoRepository.findById(alumnoId);
        if (alumnoOpt.isEmpty()) {
            throw new Exception("El alumno no existe");
        }
        Alumno alumno = alumnoOpt.get();

        // Validar límite de préstamos activos
        int prestamosActivos = prestamoRepository.countPrestamosActivosPorAlumno(alumnoId);
        if (prestamosActivos >= 3) {
            throw new Exception("El alumno ya tiene 3 préstamos activos");
        }

        // Validar préstamos vencidos
        int prestamosVencidos = prestamoRepository.countPrestamosVencidosPorAlumno(alumnoId);
        if (prestamosVencidos > 0) {
            throw new Exception("El alumno tiene préstamos vencidos");
        }

        // Validar fechas
        if (fechaFin.isBefore(fechaInicio) || fechaFin.isEqual(fechaInicio)) {
            throw new Exception("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        // Crear préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setAlumno(alumno);
        prestamo.setLibro(libro);
        prestamo.setFechaInicio(fechaInicio);
        prestamo.setFechaFin(fechaFin);
        prestamo.setFechaDevolucion(null);
        return prestamoRepository.save(prestamo);
    }
}
