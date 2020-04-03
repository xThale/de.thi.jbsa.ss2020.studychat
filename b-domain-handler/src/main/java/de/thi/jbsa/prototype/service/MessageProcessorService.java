package de.thi.jbsa.prototype.service;

import javax.jms.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thi.jbsa.prototype.BusinessEvent;
import de.thi.jbsa.prototype.EventRepository;
import de.thi.jbsa.prototype.model.cmd.PostMessageCmd;
import de.thi.jbsa.prototype.model.event.MessagePostedEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-03
 */
@Service
@Slf4j
public class MessageProcessorService {

  private final Queue eventQueue;

  private final EventRepository eventRepository;

  private final JmsTemplate jmsTemplate;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public MessageProcessorService(@Qualifier("eventQueue") Queue eventQueue, JmsTemplate jmsTemplate, EventRepository eventRepository) {
    this.eventQueue = eventQueue;
    this.jmsTemplate = jmsTemplate;
    this.eventRepository = eventRepository;
  }

  public void postMessage(PostMessageCmd cmd) {

    log.info("creating event for ... " + cmd);
    MessagePostedEvent event = new MessagePostedEvent();
    event.setCmdUuid(cmd.getUuid());
    event.setContent(cmd.getContent());
    event.setUserId(cmd.getUserId());
    // This is the place for more business logic

    saveEvent(event);
    sendEvent(event);
  }

  private void saveEvent(MessagePostedEvent event) {
    BusinessEvent entity = new BusinessEvent();
    String json = toJson(event);
    entity.setValue(json);
    log.debug("Writing event... : " + json);
    //    eventRepository.save(entity);
    log.info("Sent event to queue " + event);
  }

  private void sendEvent(MessagePostedEvent event) {
    jmsTemplate.convertAndSend(eventQueue, event);
    log.info("Sent event to queue " + event);
  }

  private String toJson(de.thi.jbsa.prototype.model.event.Event event) {
    try {
      return objectMapper.writeValueAsString(event);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("BusinessEvent cannot be serialized: " + event, e);
    }
  }
}
