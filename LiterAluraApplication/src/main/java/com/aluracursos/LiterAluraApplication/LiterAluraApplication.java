package com.aluracursos.LiterAluraApplication;

import com.aluracursos.LiterAluraApplication.Model.Libro;
import com.aluracursos.LiterAluraApplication.Principal.Principal;
import com.aluracursos.LiterAluraApplication.repository.AutorRepository;
import com.aluracursos.LiterAluraApplication.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepository);
		principal.muestraElMenu();
	}
}
