package de.thi.jbsa.prototype.service;

import javax.jms.Queue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.thi.jbsa.prototype.model.cmd.Cmd;
import de.thi.jbsa.prototype.model.cmd.PostMessageCmd;
import de.thi.jbsa.prototype.model.event.MessagePostedEvent;
import lombok.extern.java.Log;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-02-18
 */
@Service
@Log
public class CommandHandlerService {

  private final Queue queue;

  private final JmsTemplate jmsTemplate;

  public CommandHandlerService(Queue queue, JmsTemplate jmsTemplate) {
    this.queue = queue;
    this.jmsTemplate = jmsTemplate;
  }

  public void handleCommand(Cmd cmd) {
    jmsTemplate.convertAndSend(queue, cmd);
    log.info("Sent cmd to queue " + cmd);
  }
}
