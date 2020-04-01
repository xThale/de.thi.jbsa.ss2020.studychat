package de.thi.jbsa.prototype.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import de.thi.jbsa.prototype.domain.Message;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-01
 */
public interface MessageRepository extends MongoRepository<Message, String> {

}
