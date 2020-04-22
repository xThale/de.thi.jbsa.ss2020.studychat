package de.thi.jbsa.prototype.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.jms.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.thi.jbsa.prototype.model.EventEntity;
import de.thi.jbsa.prototype.model.EventName;
import de.thi.jbsa.prototype.model.cmd.PostMessageCmd;
import de.thi.jbsa.prototype.model.event.AbstractEvent;
import de.thi.jbsa.prototype.model.event.MentionEvent;
import de.thi.jbsa.prototype.model.event.MessagePostedEvent;
import de.thi.jbsa.prototype.repository.EventRepository;
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

  private Optional<MentionEvent> checkForUserMentions(MessagePostedEvent event) {
    List<String> mentionedUsers = new ArrayList<>();
    Matcher matcher = Pattern.compile("\\s@([\\w_-]+)").matcher(event.getContent());
    while (matcher.find()) {
      mentionedUsers.add(matcher.group());
    }
    if (mentionedUsers.size() > 0) {
      MentionEvent mentionEvent = new MentionEvent();
      mentionEvent.setUserId(event.getUserId());
      mentionEvent.setMentionedUsers(mentionedUsers);
      mentionEvent.setCausationUuid(event.getUuid());
      return Optional.of(mentionEvent);
    }
    return Optional.empty();
  }

  public void postMessage(PostMessageCmd cmd) {

    log.info("creating event for ... " + cmd);
    MessagePostedEvent event = new MessagePostedEvent();
    event.setCmdUuid(cmd.getUuid());
    event.setContent(cmd.getContent());
    event.setUserId(cmd.getUserId());
    // This is the place for more business logic
    Optional<MentionEvent> mentionEventOptional = checkForUserMentions(event);
    if (mentionEventOptional.isPresent()) {
      log.info("Found mentions of one or more users");
      final MentionEvent mentionEvent = mentionEventOptional.get();
      saveAndSendEvent(mentionEvent);
    }

    saveAndSendEvent(event);
  }

  private void saveAndSendEvent(AbstractEvent event) {
    log.info("Saving event " + event);
    EventEntity entity = saveEvent(event);
    event.setEntityId(entity.getId());
    sendEvent(event);
  }

  private EventEntity saveEvent(AbstractEvent event) {
    EventEntity entity = new EventEntity();
    String json = toJson(event);
    entity.setValue(json);
    if (event instanceof MessagePostedEvent) {
      entity.setEventName(EventName.MESSAGE_POSTED);
    } else if (event instanceof MentionEvent) {
      entity.setEventName(EventName.MENTION);
    }
    log.debug("Writing event... : " + json);
    eventRepository.save(entity);
    log.info("Written event to db " + event);
    return entity;
  }

  private void sendEvent(AbstractEvent event) {
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
