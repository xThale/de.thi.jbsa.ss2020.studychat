package de.thi.jbsa.prototype.consumer;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import de.thi.jbsa.prototype.service.MessageService;
import lombok.extern.java.Log;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-02-18
 */
@Component
@EnableJms
@Log
public class MessageConsumer {

  final MessageService messageService;

  public MessageConsumer(MessageService messageService) {this.messageService = messageService;}

  @JmsListener(destination = "event-queue")
  public void listener(String event) {
    log.info("event received " + event);
    messageService.addMessage(event);
  }
}
