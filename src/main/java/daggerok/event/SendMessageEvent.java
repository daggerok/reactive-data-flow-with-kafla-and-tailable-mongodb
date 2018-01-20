package daggerok.event;

import daggerok.domain.Message;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class SendMessageEvent {

  final Message message;
}
