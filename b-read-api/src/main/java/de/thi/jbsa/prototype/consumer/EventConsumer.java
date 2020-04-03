package de.thi.jbsa.prototype.consumer;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import de.thi.jbsa.prototype.model.event.Event;
import de.thi.jbsa.prototype.model.event.MessagePostedEvent;
import de.thi.jbsa.prototype.service.MessageService;
import lombok.extern.java.Log;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-02-18
 */
@Component
@EnableJms
@Log
public class EventConsumer {

  final MessageService messageService;

  public EventConsumer(MessageService messageService) {this.messageService = messageService;}

  @JmsListener(destination = "event-queue")
  public void listener(Event event) {
    EventConsumer.log.info("event received " + event);
    if (event instanceof MessagePostedEvent) {
      messageService.handleMessagePostedEvent((MessagePostedEvent) event);
    } else {
      throw new IllegalArgumentException("Not supported event: " + event);
    }
  }
}
