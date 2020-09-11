package com.cursospring.api;

import com.cursospring.api.domain.*;
import com.cursospring.api.domain.enuns.EstadoPagamento;
import com.cursospring.api.domain.enuns.TipoCliente;
import com.cursospring.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
