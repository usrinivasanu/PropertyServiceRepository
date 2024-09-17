package centro.integrations.api.rentora.propertyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan(basePackages = "centro.integrations.api.rentora.propertyservice.entities")
public class PropertyManager {

	public static void main(String[] args) {
		SpringApplication.run(PropertyManager.class, args);
	}

}
