package de.thi.jbsa.prototype.service;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Service;
import de.thi.jbsa.prototype.domain.Message;
import de.thi.jbsa.prototype.model.event.MessagePostedEvent;
import de.thi.jbsa.prototype.repository.MessageRepository;

@Service
public class MessageService {

  private final MessageRepository messageRepository;

  public MessageService(MessageRepository messageRepository) {this.messageRepository = messageRepository;}

  public void handleMessagePostedEvent(MessagePostedEvent event) {
    messageRepository.save(new Message(event));
  }

  public List<Message> getAllMessages(){
    return messageRepository.findAll();
  }
}
