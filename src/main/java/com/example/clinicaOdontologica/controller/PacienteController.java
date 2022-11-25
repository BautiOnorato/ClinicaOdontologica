package com.example.clinicaOdontologica.controller;

import com.example.clinicaOdontologica.entity.Paciente;
import com.example.clinicaOdontologica.servicios.PacienteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private PacienteStrategy pacienteStrategy;

    @Autowired
    public PacienteController(PacienteStrategy pacienteStrategy) {
        this.pacienteStrategy = pacienteStrategy;
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteStrategy.guardar(paciente));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> getPacientes() {
        return ResponseEntity.ok(pacienteStrategy.buscarTodo());
    }

    @PutMapping
    public ResponseEntity<String> putPaciente(@RequestBody Paciente paciente) {
        Optional<Paciente> pacienteBuscado = pacienteStrategy.buscar(paciente.getId());
        if (pacienteBuscado.isPresent()) {
            pacienteStrategy.actualizar(paciente);
            return ResponseEntity.ok("Se actulizó el paciente con " +
                    "id "+ paciente.getId());
        } else {
            return ResponseEntity.badRequest().body("El paciente con id= "+
                    paciente.getId()+" no existe en la BD." +
                    "No puede actualizar algo que no existe :(");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getPaciente(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteStrategy.buscar(id);
        if (paciente.isPresent()) {
            return ResponseEntity.ok(paciente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaciente(@PathVariable("id") Long id) {
        Optional<Paciente> paciente = pacienteStrategy.buscar(id);
        if (paciente.isPresent()) {
            pacienteStrategy.eliminar(id);
            return ResponseEntity.ok().body("Paciente con id " + id + " se elimino exitosamente.");
        } else {
            return ResponseEntity.badRequest().body("No se puede eliminar un paciente que no existe en la base de datos");
        }
    }

}
