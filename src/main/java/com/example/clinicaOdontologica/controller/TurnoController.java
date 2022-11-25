package com.example.clinicaOdontologica.controller;

import com.example.clinicaOdontologica.entity.Turno;
import com.example.clinicaOdontologica.servicios.OdontologoStrategy;
import com.example.clinicaOdontologica.servicios.PacienteStrategy;
import com.example.clinicaOdontologica.servicios.TurnoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private final TurnoStrategy turnoStrategy;
    private final OdontologoStrategy odontologoStrategy;
    private final PacienteStrategy pacienteStrategy;

    @Autowired
    public TurnoController(TurnoStrategy turnoStrategy, OdontologoStrategy odontologoStrategy, PacienteStrategy pacienteStrategy) {
        this.turnoStrategy = turnoStrategy;
        this.odontologoStrategy = odontologoStrategy;
        this.pacienteStrategy = pacienteStrategy;
    }


    @GetMapping
    public ResponseEntity<List<Turno>> getTurnos() {
        return ResponseEntity.ok(turnoStrategy.buscarTodo());
    }

    @PostMapping
    public ResponseEntity<Turno> postTurno(@RequestBody Turno turno) {
        ResponseEntity<Turno> response;

        if (pacienteStrategy.buscar(turno.getPaciente().getId()).isPresent()
                && odontologoStrategy.buscar(turno.getOdontologo().getId()).isPresent()) {
            response = ResponseEntity.ok(turnoStrategy.guardar(turno));
        } else {
            response = ResponseEntity.badRequest().build();
        }

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> getTurno(@PathVariable("id") Long id) {
        Optional<Turno> turno = turnoStrategy.buscar(id);
        if (turno.isPresent()) {
            return ResponseEntity.ok(turno.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> updateTurnos(@RequestBody Turno turno) {
        if (turnoStrategy.buscar(turno.getId()).isPresent()) {
            if (pacienteStrategy.buscar(turno.getPaciente().getId()).isPresent()
                    && odontologoStrategy.buscar(turno.getOdontologo().getId()).isPresent()) {
                turnoStrategy.actualizar(turno);
                return ResponseEntity.ok().body("Se actualizo el turno con el id: " + turno.getId());
            } else {
                return ResponseEntity.badRequest().body("Error al actualizar, verificar si el odontologo o/y el paciente existen.");
            }
        } else {
            return ResponseEntity.badRequest().body("No se puede actualizar un turno que no existe en la base de datos");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTurno(@PathVariable("id") Long id) {
        if (turnoStrategy.buscar(id).isPresent()) {
            turnoStrategy.eliminar(id);
            return ResponseEntity.ok().body("Turno con id " + id + " se elimino exitosamente.");
        } else {
            return ResponseEntity.badRequest().body("No se puede eliminar un turno que no existe en la base de datos");
        }
    }
}
