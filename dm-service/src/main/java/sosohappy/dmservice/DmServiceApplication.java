package sosohappy.dmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class DmServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmServiceApplication.class, args);
	}

}
