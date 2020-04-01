package de.thi.jbsa.prototype.service;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Service;
import de.thi.jbsa.prototype.domain.Message;
import de.thi.jbsa.prototype.repository.MessageRepository;

@Service
public class MessageService {

  private final MessageRepository messageRepository;

  public MessageService(MessageRepository messageRepository) {this.messageRepository = messageRepository;}

  public void addMessage(String message) {
    messageRepository.save(new Message(message,new Timestamp(System.currentTimeMillis())));
  }

  public List<Message> getAllMessages(){
    return messageRepository.findAll();
  }
}
