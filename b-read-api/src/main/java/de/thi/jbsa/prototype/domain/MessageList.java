package de.thi.jbsa.prototype.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-01
 */
@Data
@AllArgsConstructor
public class MessageList {

  private List<Message> messages;
}
