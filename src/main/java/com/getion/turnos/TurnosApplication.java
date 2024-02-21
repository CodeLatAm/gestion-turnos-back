package com.getion.turnos;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class TurnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurnosApplication.class, args);
		//System.out.println(generarFechasPorSemana(LocalDate.now(),LocalDate.of(2024,11,30)));


	}
	/*
	public static List<String> generarFechasPorSemana(LocalDate fechaInicio, LocalDate fechaFin) {
		List<String> fechasFormateadas = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d/MM/yyyy");

		LocalDate fechaActual = fechaInicio;

		while (!fechaActual.isAfter(fechaFin)) {
			String fechaFormateada = fechaActual.format(formatter);
			fechasFormateadas.add(fechaFormateada);

			// Avanza al siguiente día
			fechaActual = fechaActual.plusDays(1);
		}

		return fechasFormateadas;
	}
*/

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
