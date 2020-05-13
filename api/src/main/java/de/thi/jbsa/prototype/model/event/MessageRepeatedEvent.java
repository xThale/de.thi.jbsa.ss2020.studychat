package de.thi.jbsa.prototype.model.event;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 12/5/20
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageRepeatedEvent
  extends AbstractEvent {

  private final UUID uuid = UUID.randomUUID();

  private int occurCount = 2;

  private UUID messageEventUUID;

  public static MessageRepeatedEvent of(MessagePostedEvent messagePostedEvent) {
    // if we get a repeated event it means that the message has appeared at least twice.
    return new MessageRepeatedEvent(2, messagePostedEvent.getUuid());
  }

  public static MessageRepeatedEvent of(MessageRepeatedEvent messageRepeatedEvent) {
    return new MessageRepeatedEvent(messageRepeatedEvent.getOccurCount() + 1, messageRepeatedEvent.getMessageEventUUID());
  }
}
