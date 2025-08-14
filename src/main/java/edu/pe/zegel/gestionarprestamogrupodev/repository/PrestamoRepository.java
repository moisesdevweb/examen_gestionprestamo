package edu.pe.zegel.gestionarprestamogrupodev.repository;

import edu.pe.zegel.gestionarprestamogrupodev.model.entity.Prestamo;
import edu.pe.zegel.gestionarprestamogrupodev.model.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    @Query(value = "SELECT COUNT(*) FROM prestamo WHERE alumno_id = :alumnoId AND fecha_fin > CURRENT_DATE AND fecha_devolucion IS NULL", nativeQuery = true)
    int countPrestamosActivosPorAlumno(Long alumnoId);

    @Query(value = "SELECT COUNT(*) FROM prestamo WHERE alumno_id = :alumnoId AND fecha_fin < CURRENT_DATE AND fecha_devolucion IS NULL", nativeQuery = true)
    int countPrestamosVencidosPorAlumno(Long alumnoId);

    List<Prestamo> findByAlumnoAndFechaFinAfterAndFechaDevolucionIsNull(Alumno alumno, LocalDate fecha);
}
