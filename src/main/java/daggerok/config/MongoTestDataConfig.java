package daggerok.config;

import daggerok.domain.Message;
import daggerok.domain.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Configuration
public class MongoTestDataConfig {

  @Bean InitializingBean init(final MongoOperations ops,
                              final MessageRepository messages) {

    final AtomicLong atomicLong = new AtomicLong(1);
    final LocalDateTime base = LocalDateTime.now();

    if (ops.collectionExists(Message.class))
      ops.dropCollection(Message.class);

    ops.createCollection(Message.class, new CollectionOptions(1_000_000_000_000_000L, null, true));

    return () -> Flux.just(1, 2, 3)
                     .map(i -> new Message().setBody("message " + i)
                                            .setAt(base.plusMinutes(atomicLong.incrementAndGet())))
                     .flatMap(messages::save)
                     .subscribe(r -> ops.executeCommand("db.runCommand({ convertToCapped: 'reservation', size: 1111111111 })"));
  }
}
