package de.thi.jbsa.prototype.model.event;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"uuid"}, callSuper = false)
public class MentionEvent
  extends AbstractEvent {

  private final UUID uuid = UUID.randomUUID();

  private UUID causationUuid;

  private String mentionedUser;

  private String userId;
}
