package com.example.clinicaOdontologica.controller;

import com.example.clinicaOdontologica.entity.Odontologo;
import com.example.clinicaOdontologica.entity.Paciente;
import com.example.clinicaOdontologica.servicios.OdontologoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    private OdontologoStrategy odontologoStrategy;

    @Autowired
    public OdontologoController(OdontologoStrategy odontologoStrategy) {
        this.odontologoStrategy = odontologoStrategy;
    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoStrategy.guardar(odontologo));
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> getOdontologos() {
        return ResponseEntity.ok(odontologoStrategy.buscarTodo());
    }

    @PutMapping
    public ResponseEntity<String> putOdontologo(@RequestBody Odontologo odontologo) {
        Optional<Odontologo> odontologoBuscado = odontologoStrategy.buscar(odontologo.getId());
        if (odontologoBuscado.isPresent()) {
            odontologoStrategy.actualizar(odontologo);
            return ResponseEntity.ok("Se actulizó el odontologo con " +
                    "id "+ odontologo.getId());
        } else {
            return ResponseEntity.badRequest().body("El odontologo con id= "+
                    odontologo.getId()+" no existe en la BD." +
                    "No puede actualizar algo que no existe :(");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> getOdontologo(@PathVariable Long id) {
        Optional<Odontologo> odontologo = odontologoStrategy.buscar(id);
        if (odontologo.isPresent()) {
            return ResponseEntity.ok(odontologo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOdontologo(@PathVariable("id") Long id) {
        if (odontologoStrategy.buscar(id).isPresent()) {
            odontologoStrategy.eliminar(id);
            return ResponseEntity.ok().body("Odontologo con id " + id + " se elimino exitosamente.");
        } else {
            return ResponseEntity.badRequest().body("No se puede eliminar un odontologo que no existe en la base de datos");
        }
    }
}
