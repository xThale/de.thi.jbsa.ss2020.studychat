package de.thi.jbsa.prototype.consumer;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import de.thi.jbsa.prototype.config.JmsConfig;
import de.thi.jbsa.prototype.model.cmd.Cmd;
import de.thi.jbsa.prototype.model.cmd.PostMessageCmd;
import de.thi.jbsa.prototype.service.MessageProcessorService;
import lombok.extern.java.Log;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-03
 */
@Component
@EnableJms
@Log
public class CommandConsumer {

  private final MessageProcessorService messageProcessorService;

  public CommandConsumer(MessageProcessorService messageProcessorService) {
    this.messageProcessorService = messageProcessorService;
  }

  @JmsListener(destination = JmsConfig.COMMAND_QUEUE_NAME)
  public void listener(Cmd cmd) {
    log.info("Command received " + cmd);
    if (cmd instanceof PostMessageCmd) {
      messageProcessorService.postMessage((PostMessageCmd) cmd);
    } else {
      throw new IllegalArgumentException("Command isn't supported: " + cmd);
    }
  }
}
