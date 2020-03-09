package de.thi.jbsa.prototype.view;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("chat")
@Component
public class ChatView
  extends VerticalLayout {

  final RestTemplate restTemplate;

  @Value("${studychat.url.sendMessage}")
  private String sendMessageUrl;

  public ChatView(RestTemplate restTemplate) {
    TextField messageField = new TextField();
    messageField.setLabel("Message");
    messageField.addKeyPressListener(Key.ENTER, e -> {
      sendMessage(messageField.getValue());
    });

    Button sendMessageButton = new Button();
    sendMessageButton.setText("Send message");
    sendMessageButton.addClickListener(e -> {
      sendMessage(messageField.getValue());
    });

    add(new Text("Welcome to Studychat."));
    add(messageField);
    add(sendMessageButton);
    this.restTemplate = restTemplate;
  }

  private void sendMessage(String message) {
    restTemplate.postForEntity(sendMessageUrl, message, String.class);
  }
}
