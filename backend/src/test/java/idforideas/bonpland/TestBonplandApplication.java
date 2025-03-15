package idforideas.bonpland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration")
public class TestBonplandApplication {

    public static void main(String[] args) {

        SpringApplication.from(BonplandApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
