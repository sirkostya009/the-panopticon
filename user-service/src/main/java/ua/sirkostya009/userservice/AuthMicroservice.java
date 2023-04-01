package ua.sirkostya009.userservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.sirkostya009.userservice.model.Role;
import ua.sirkostya009.userservice.model.User;
import ua.sirkostya009.userservice.repository.UserRepository;

import java.util.Collections;

@SpringBootApplication
@EnableConfigurationProperties
public class AuthMicroservice {

	public static void main(String[] args) {
		SpringApplication.run(AuthMicroservice.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository repository, PasswordEncoder encoder) {
		return args -> {
			repository.deleteByUsername("admin");

			repository.save(User.builder()
					.email("admin@email.com")
					.username("admin")
					.password(encoder.encode("password"))
					.firstName("admin")
					.isEnabled(true)
					.roles(Collections.singleton(Role.SUPER_USER))
					.build());
		};
	}

}
