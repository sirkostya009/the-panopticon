package ua.sirkostya009.userservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.sirkostya009.userservice.model.Authority;
import ua.sirkostya009.userservice.model.User;
import ua.sirkostya009.userservice.repository.UserRepository;

@SpringBootApplication
@EnableConfigurationProperties
public class UserMicroservice {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroservice.class, args);
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
					.authorities(Authority.Presets.SUPER_USER)
					.build());
		};
	}

}
