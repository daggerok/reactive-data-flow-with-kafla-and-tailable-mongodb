package daggerok.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
@Accessors(chain = true)
public class Message implements Serializable {

  private static final long serialVersionUID = 2612983025996993766L;

  @Id String id;

  String body;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  LocalDateTime at;
}
