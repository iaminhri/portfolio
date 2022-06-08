package shadow.practice.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class PortfolioWebApp {
	public static void main(String[] args) {
		SpringApplication.run(PortfolioWebApp.class, args);
	}
}