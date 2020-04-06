package de.thi.jbsa.prototype.repository;

import org.springframework.data.repository.CrudRepository;
import de.thi.jbsa.prototype.model.EventEntity;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 27.02.18
 */
public interface EventRepository
  extends CrudRepository<EventEntity, Long> {

}
