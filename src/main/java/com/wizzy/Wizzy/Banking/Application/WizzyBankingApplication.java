package com.wizzy.Wizzy.Banking.Application;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Wizzy-Banking-Application",
				description = "Backend REST APIs for Wizzy-Banking-Application",
				version = "v1.0",
				contact = @Contact(
						name = "Wisdom Chukwuka Onu Ugorji",
						email = "wizzypatrick777@gmail.com",
						url = "https://github.com/Wizzy44221/Spring-bank-dev.git"
				),
				license = @License(
						name = "Wizzy-Banking-Application",
						url = "https://github.com/Wizzy44221/Spring-bank-dev.git"
				)


		),
		externalDocs = @ExternalDocumentation(
				description = "Backend REST APIs for Wizzy-Banking-Application",
				url = "https://github.com/Wizzy44221/Spring-bank-dev.git"
		)
)
public class WizzyBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WizzyBankingApplication.class, args);
	}

}
