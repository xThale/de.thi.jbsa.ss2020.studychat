package de.thi.jbsa.prototype.config;

import javax.jms.Topic;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-21
 */
@Configuration
public class JmsConfig {

  @Bean
  public Topic topic() {
    return new ActiveMQTopic("ui-event-topic");
  }
}
