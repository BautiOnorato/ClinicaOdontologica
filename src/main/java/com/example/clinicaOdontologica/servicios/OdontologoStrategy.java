package com.example.clinicaOdontologica.servicios;
import com.example.clinicaOdontologica.entity.Odontologo;
import com.example.clinicaOdontologica.repository.OdontologoRepository;
import com.example.clinicaOdontologica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoStrategy {
    public OdontologoRepository odontologoRepository;

    @Autowired
    public OdontologoStrategy(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardar(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }
    public void actualizar(Odontologo odontologo) {
        odontologoRepository.save(odontologo);
    }
    public void eliminar(Long id) {
        odontologoRepository.deleteById(id);
    }
    public Optional<Odontologo> buscar(Long id) {
        return odontologoRepository.findById(id);
    }
    public Odontologo buscarPorEmail(String email) {
        return null;
    }
    public List<Odontologo> buscarTodo() {
        return odontologoRepository.findAll();
    }

}
