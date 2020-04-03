package de.thi.jbsa.prototype.consumer;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import de.thi.jbsa.prototype.config.JmsConfig;
import de.thi.jbsa.prototype.service.CommandProcessor;
import lombok.extern.java.Log;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-03
 */
@Component
@EnableJms
@Log
public class CommandConsumer {

  private final CommandProcessor commandProcessor;

  public CommandConsumer(CommandProcessor commandProcessor) {
    this.commandProcessor = commandProcessor;
  }

  @JmsListener(destination = JmsConfig.COMMAND_QUEUE_NAME)
  public void listener(String message) {
    log.info("Command received " + message);
    commandProcessor.processCommand(message);
  }
}
