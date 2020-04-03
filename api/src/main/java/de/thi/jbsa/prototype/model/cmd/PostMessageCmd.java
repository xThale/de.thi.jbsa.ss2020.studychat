package de.thi.jbsa.prototype.model.cmd;

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
public class PostMessageCmd
  implements Cmd {
  private final UUID uuid = UUID.randomUUID();
  private String userId;
  private String content;
}
