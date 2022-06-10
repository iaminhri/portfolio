package shadow.practice.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("shadow.practice.portfolio.Repository")
@EntityScan("shadow.practice.portfolio.Model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class PortfolioWebApp {
	public static void main(String[] args) {
		SpringApplication.run(PortfolioWebApp.class, args);
	}
}