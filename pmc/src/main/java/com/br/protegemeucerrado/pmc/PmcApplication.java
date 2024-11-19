package com.br.protegemeucerrado.pmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class PmcApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmcApplication.class, args);
	}

}
