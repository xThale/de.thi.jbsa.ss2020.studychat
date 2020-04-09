package de.thi.jbsa.prototype.model.event;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessagePostedEvent
  extends AbstractEvent {

  private final UUID uuid = UUID.randomUUID();

  private UUID cmdUuid;

  private String content;

  private Long entityId;

  private String userId;
}
