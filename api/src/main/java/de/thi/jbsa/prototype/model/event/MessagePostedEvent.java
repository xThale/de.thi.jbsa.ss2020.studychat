package de.thi.jbsa.prototype.model.event;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagePostedEvent
  implements Event {
  private final UUID uuid = UUID.randomUUID();
  private UUID cmdUuid;
  private String userId;
  private String content;
}
