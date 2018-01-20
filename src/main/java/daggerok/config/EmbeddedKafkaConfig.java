package daggerok.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.rule.KafkaEmbedded;

@Configuration
public class EmbeddedKafkaConfig {

  @Bean KafkaEmbedded kafka() {
    final KafkaEmbedded kafkaEmbedded = new KafkaEmbedded(1, false, "messages");
    kafkaEmbedded.setKafkaPorts(9092);
    return kafkaEmbedded;
  }
}
