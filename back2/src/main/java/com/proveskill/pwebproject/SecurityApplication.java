package com.proveskill.pwebproject;

import com.proveskill.pwebproject.auth.RegisterRequest;
import com.proveskill.pwebproject.service.AuthenticationService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.proveskill.pwebproject.user.Role.ADMIN;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			// var admin = RegisterRequest.builder()
			// 		.name("Admin")
			// 		.school("IFAL")
			// 		.email("admin@mail.com")
			// 		.password("password")
			// 		.role(ADMIN)
			// 		.build();
			// System.out.println("Admin token: " + service.register(admin));

			// var manager = RegisterRequest.builder()
			// 		.firstname("Admin")
			// 		.lastname("Admin")
			// 		.email("manager@mail.com")
			// 		.password("password")
			// 		.role(MANAGER)
			// 		.build();
			// System.out.println("Manager token: " + service.register(manager).getAccessToken());

		};
	}
}
