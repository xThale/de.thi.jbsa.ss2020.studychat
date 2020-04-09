package de.thi.jbsa.prototype.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import de.thi.jbsa.prototype.domain.MessageDoc;
import de.thi.jbsa.prototype.model.event.AbstractEvent;
import de.thi.jbsa.prototype.model.event.MessagePostedEvent;
import de.thi.jbsa.prototype.model.model.Message;
import de.thi.jbsa.prototype.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService {

  // TODO Workaround. This does not scale. Create another instance of this service and clients will have different Events
  private final List<AbstractEvent> messagePostedEvents = new ArrayList<>();

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

  public List<AbstractEvent> getEvents() {
    return messagePostedEvents;
  }

  public List<AbstractEvent> getEvents(UUID lastEvent) {
    return messagePostedEvents
      .stream()
      .filter(messagePostedEvent -> messagePostedEvent.getUuid().equals(lastEvent))
      .findFirst().map(
        messagePostedEvent -> messagePostedEvents.subList(messagePostedEvents.indexOf(messagePostedEvent) + 1, messagePostedEvents.size()))
      .orElse(messagePostedEvents);
  }

  public void handleMessagePostedEvent(MessagePostedEvent event) {
    MessageDoc doc = new MessageDoc(createMsg(event));
    messageRepository.save(doc);
    messagePostedEvents.add(event);
  }
}
