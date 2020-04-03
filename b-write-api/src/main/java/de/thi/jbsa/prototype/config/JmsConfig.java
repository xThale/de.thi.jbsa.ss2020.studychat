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

  @Bean
  public Queue queue() {
    return new ActiveMQQueue("cmd-queue");
  }
}
