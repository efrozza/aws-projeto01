package br.com.efrozza.awsprojeto01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "br.com.efrozza.awsprojeto01.model")
@SpringBootApplication
public class AwsProjeto01Application {

	public static void main(String[] args) {
		SpringApplication.run(AwsProjeto01Application.class, args);
	}

}
