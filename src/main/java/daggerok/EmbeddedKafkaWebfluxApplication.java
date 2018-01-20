package daggerok;

import daggerok.config.EmbeddedKafkaConfig;
import daggerok.routes.RoutesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Import({
    RoutesConfig.class,
    EmbeddedKafkaConfig.class,
})
@SpringBootApplication
@EnableReactiveMongoRepositories(considerNestedRepositories = true)
public class EmbeddedKafkaWebfluxApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmbeddedKafkaWebfluxApplication.class, args);
  }
}
