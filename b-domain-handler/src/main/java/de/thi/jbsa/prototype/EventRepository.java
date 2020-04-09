package de.thi.jbsa.prototype;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 27.02.18
 */
public interface EventRepository
  extends CrudRepository<EventEntity, Long> {

}
