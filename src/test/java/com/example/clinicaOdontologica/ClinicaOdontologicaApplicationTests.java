package com.example.clinicaOdontologica;

import com.example.clinicaOdontologica.repository.BD;
import com.example.clinicaOdontologica.entity.Odontologo;
import com.example.clinicaOdontologica.servicios.OdontologoStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ClinicaOdontologicaApplicationTests {

	@Test
	public void listarOdontologosTest(){
		OdontologoStrategy odontologoStrategy = new OdontologoStrategy();
		BD.crearTabla();
		List<Odontologo> listaEncontrada = odontologoStrategy.buscarTodos();
		Assertions.assertEquals(1,listaEncontrada.size());
	}

	@Test
	void contextLoads() {
	}

}
