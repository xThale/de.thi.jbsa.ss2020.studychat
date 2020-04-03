package de.thi.jbsa.prototype.domain;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import de.thi.jbsa.prototype.model.event.MessagePostedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "messages")
public class Message {

  private UUID cmdUuid;

  private String content;

  private Date created = Date.from(Instant.now());

  private UUID eventUuid;

  @Id
  private String id;

  private String senderUserId;

  public Message(MessagePostedEvent event) {
    this.content = event.getContent();
    eventUuid = event.getUuid();
    cmdUuid = event.getCmdUuid();
    senderUserId = event.getUserId();
  }
}
