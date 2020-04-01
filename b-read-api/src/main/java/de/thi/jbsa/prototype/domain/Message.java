package de.thi.jbsa.prototype.domain;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "messages")
public class Message {

  private String content;

  private Date date = Date.from(Instant.now());

  @Id
  private String id;

  public Message(String content, Date timestamp) {
    this.content = content;
  }
}
