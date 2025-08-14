package edu.pe.zegel.gestionarprestamogrupodev.repository;

import edu.pe.zegel.gestionarprestamogrupodev.model.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Optional<Alumno> findByDni(String dni);
}
