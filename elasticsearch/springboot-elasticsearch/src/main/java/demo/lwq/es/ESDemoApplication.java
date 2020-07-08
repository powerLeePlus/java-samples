package demo.lwq.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
//@EnableElasticsearchRepositories
public class ESDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ESDemoApplication.class, args);
    }

}
