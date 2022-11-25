package com.example.clinicaOdontologica.servicios;

import com.example.clinicaOdontologica.entity.Paciente;
import com.example.clinicaOdontologica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteStrategy {

    public PacienteRepository pacienteRepository;

    @Autowired
    public PacienteStrategy(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }
    public void actualizar(Paciente paciente) {
        pacienteRepository.save(paciente);
    }
    public void eliminar(Long id) {
        pacienteRepository.deleteById(id);
    }
    public Optional<Paciente> buscar(Long id) {
        return pacienteRepository.findById(id);
    }
    public Paciente buscarPorEmail(String email) {
        return null;
    }
    public List<Paciente> buscarTodo() {
        return pacienteRepository.findAll();
    }

}
