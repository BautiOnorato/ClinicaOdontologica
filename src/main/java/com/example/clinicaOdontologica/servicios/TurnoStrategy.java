package com.example.clinicaOdontologica.servicios;

import com.example.clinicaOdontologica.entity.Turno;
import com.example.clinicaOdontologica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoStrategy {
    public TurnoRepository turnoStrategy;

    @Autowired
    public TurnoStrategy(TurnoRepository turnoStrategy) {
        this.turnoStrategy = turnoStrategy;
    }

    public Turno guardar(Turno turno) {
        return turnoStrategy.save(turno);
    }
    public void actualizar(Turno turno) {
        turnoStrategy.save(turno);
    }
    public void eliminar(Long id) {
        turnoStrategy.deleteById(id);
    }
    public Optional<Turno> buscar(Long id) {
        return turnoStrategy.findById(id);
    }
    public Turno buscarPorEmail(String email) {
        return null;
    }
    public List<Turno> buscarTodo() {
        return turnoStrategy.findAll();
    }
}
