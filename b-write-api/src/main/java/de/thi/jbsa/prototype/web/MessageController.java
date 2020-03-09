package de.thi.jbsa.prototype.web;

import javax.jms.Queue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.java.Log;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-02-18
 */
@RestController
@RequestMapping("/api")
@Log
public class MessageController {

  private final Queue queue;

  private final JmsTemplate jmsTemplate;

  public MessageController(Queue queue, JmsTemplate jmsTemplate) {
    this.queue = queue;
    this.jmsTemplate = jmsTemplate;
  }

  @PostMapping(path="message", consumes = "text/plain")
  public ResponseEntity<String> publish(@RequestBody final String message) {
    jmsTemplate.convertAndSend(queue, message);
    log.info("Sent message to queue " + message);
    return new ResponseEntity(message, HttpStatus.OK);
  }
}
