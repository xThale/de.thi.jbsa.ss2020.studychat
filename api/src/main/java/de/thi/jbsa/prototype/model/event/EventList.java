package de.thi.jbsa.prototype.model.event;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventList {

  private List<AbstractEvent> events;
}
