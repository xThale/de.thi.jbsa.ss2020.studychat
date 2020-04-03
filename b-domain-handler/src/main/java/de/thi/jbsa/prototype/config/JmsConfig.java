package de.thi.jbsa.prototype.config;

import javax.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-02-18
 */

@Configuration
public class JmsConfig {

  public static final String COMMAND_QUEUE_NAME = "command-queue";

  public static final String EVENT_QUEUE_NAME = "event-queue";

  @Bean(name = "commandQueue")
  public Queue commandQueue() {
    return new ActiveMQQueue(COMMAND_QUEUE_NAME);
  }

  @Bean(name = "eventQueue")
  public Queue eventQueue() {
    return new ActiveMQQueue(EVENT_QUEUE_NAME);
  }
}
