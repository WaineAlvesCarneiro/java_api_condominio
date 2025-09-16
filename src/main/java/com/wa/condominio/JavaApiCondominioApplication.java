package com.wa.condominio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(title = "JAVA - API Condomínio", version = "1.0 ", description = "Documentação dos endpoints do Sistema do Condomínio"),
        servers = @Server(url = "/", description = "Servidor Local")
)
@SpringBootApplication
public class JavaApiCondominioApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaApiCondominioApplication.class, args);
    }
}