package de.thi.jbsa.prototype.service;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import de.thi.jbsa.prototype.domain.MessageDoc;
import de.thi.jbsa.prototype.model.event.MessagePostedEvent;
import de.thi.jbsa.prototype.model.model.Message;
import de.thi.jbsa.prototype.repository.MessageRepository;

@Service
public class MessageService {

  private final MessageRepository messageRepository;

  public MessageService(MessageRepository messageRepository) {this.messageRepository = messageRepository;}

  private Message createMsg(MessagePostedEvent event) {
    Message msg = new Message();
    msg.setCmdUuid(event.getCmdUuid());
    msg.setContent(event.getContent());
    msg.setCreated(new Date());
    msg.setEntityId(event.getEntityId());
    msg.setEventUuid(event.getUuid());
    msg.setSenderUserId(event.getUserId());
    return msg;
  }

  public List<MessageDoc> getAllMessages() {
    return messageRepository.findAll();
  }

  public void handleMessagePostedEvent(MessagePostedEvent event) {
    MessageDoc doc = new MessageDoc(createMsg(event));
    messageRepository.save(doc);
  }
}
