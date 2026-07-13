package com.banco.basico.simulador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SimuladorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimuladorApplication.class, args);
	}

}
