package de.thi.jbsa.prototype.consumer;

import java.util.LinkedList;
import java.util.function.Consumer;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.vaadin.flow.shared.Registration;
import de.thi.jbsa.prototype.model.event.AbstractEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-21
 */
@Component
@EnableJms
@Slf4j
public class UiEventConsumer {

  private static LinkedList<Consumer<AbstractEvent>> listeners = new LinkedList<>();

  @JmsListener(destination = "ui-event-topic")
  public void listener(AbstractEvent event) {
    log.info("received event " + event);
    listeners.forEach(abstractEventConsumer -> abstractEventConsumer.accept(event));
  }

  public static Registration registrer(
    Consumer<AbstractEvent> listener
  ) {
    listeners.add(listener);
    return () -> listeners.remove();
  }
}
