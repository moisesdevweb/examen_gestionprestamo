package edu.pe.zegel.gestionarprestamogrupodev.controller;

import edu.pe.zegel.gestionarprestamogrupodev.model.entity.Prestamo;
import edu.pe.zegel.gestionarprestamogrupodev.service.PrestamoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestBody PrestamoRequest request) {
        try {
            Prestamo prestamo = prestamoService.crearPrestamo(
                    request.getAlumnoId(),
                    request.getLibro(),
                    request.getFechaInicio(),
                    request.getFechaFin()
            );
            return new ResponseEntity<>(prestamo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    public static class PrestamoRequest {
        private Long alumnoId;
        private String libro;
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
    }
}
