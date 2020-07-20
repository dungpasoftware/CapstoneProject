package fu.rms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import fu.rms.auditor.AuditorAwareImpl;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class Application {
	
	@Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}	
}
