package com.proveskill.pwebproject;

import com.proveskill.pwebproject.auth.RegisterRequest;
import com.proveskill.pwebproject.repository.UserRepository;
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
			AuthenticationService service,
			UserRepository repository) {
		return args -> {
			var user = repository.findByEmail("admin@admin.com");
			if (user.isEmpty()) {
				var admin = RegisterRequest.builder()
						.name("Admin")
						.school("IFAL")
						.email("admin@admin.com")
						.password("admin")
						.role(ADMIN)
						.build();
				service.register(admin);
			}
		};
	}
}
