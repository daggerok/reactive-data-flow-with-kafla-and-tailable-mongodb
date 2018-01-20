package daggerok.routes;

import daggerok.domain.Message;
import daggerok.domain.MessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static java.time.LocalDateTime.now;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RoutesConfig {

  @Bean RouterFunction<ServerResponse> routes(final MessageRepository messages,
                                              final KafkaOperations<Object, Object> kafka) {

    return

        // send via kafka broker
        route(POST("/api/v1/send-message"),
              request -> ok().contentType(APPLICATION_JSON)
                             .body(request.bodyToMono(Message.class)
                                          .doOnNext(message -> kafka.send("messages", message.getBody()))
                                          .map(e -> "sending message...")
                                          .flatMap(Mono::just), String.class))

            // save via mongo repository
            .andRoute(POST("/api/v1/save-message"),
                      request -> ok().contentType(APPLICATION_JSON)
                                     .body(request.bodyToMono(Message.class)
                                                  .map(message -> message.setAt(now()))
                                                  .flatMap(messages::save),
                                           Message.class))

            // fallback for everything else
            .andRoute(GET("/**"),
                      request -> ok().contentType(APPLICATION_STREAM_JSON)
                                     .body(messages.findBy()
                                                   .share(),
                                           Message.class))
        ;
  }
}
