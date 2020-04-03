package de.thi.jbsa.prototype.service;

import javax.jms.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-03
 */
@Service
@Slf4j
public class CommandProcessor {

  private final Queue eventQueue;

  private final JmsTemplate jmsTemplate;

  public CommandProcessor(@Qualifier("eventQueue") Queue eventQueue, JmsTemplate jmsTemplate) {
    this.eventQueue = eventQueue;
    this.jmsTemplate = jmsTemplate;
  }

  public void processCommand(String event) {
    jmsTemplate.convertAndSend(eventQueue, event);
    log.info("Sent event to queue " + event);
  }
}
