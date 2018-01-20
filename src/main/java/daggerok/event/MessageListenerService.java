package daggerok.event;

import daggerok.domain.Message;
import daggerok.domain.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDateTime.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageListenerService {

  final MessageRepository messages;

  @Transactional
  @KafkaListener(topics = "messages")
  public void on(final String message) {
    log.info("received message: {}", message);
    messages.save(new Message().setAt(now())
                               .setBody(message))
            .subscribe(msg -> log.info("saved message: {}", msg));
  }
}
