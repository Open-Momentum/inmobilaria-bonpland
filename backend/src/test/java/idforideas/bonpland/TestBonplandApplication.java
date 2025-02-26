package idforideas.bonpland;

import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class TestBonplandApplication {

	public static void main(String[] args) {
		SpringApplication.from(BonplandApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
