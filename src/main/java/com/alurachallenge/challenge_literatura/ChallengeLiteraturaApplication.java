package com.alurachallenge.challenge_literatura;

import com.alurachallenge.challenge_literatura.principal.Principal;
import com.alurachallenge.challenge_literatura.repository.AutorRepository;
import com.alurachallenge.challenge_literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraturaApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private LibroRepository libroRepository;
	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal( autorRepository, libroRepository);
		principal.muestraMenu();
	}
}
