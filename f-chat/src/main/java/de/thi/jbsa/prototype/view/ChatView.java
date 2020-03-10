package de.thi.jbsa.prototype.view;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @Value("${studychat.url.getMessage}")
  private String getMessageUrl;

  @Value("${studychat.url.sendMessage}")
  private String sendMessageUrl;

  public ChatView(RestTemplate restTemplate) {
    TextField receivedMessageField = new TextField();
    receivedMessageField.setLabel("Received Message");
    receivedMessageField.setValue("");
    receivedMessageField.setReadOnly(true);

    TextField sendMessageField = new TextField();
    sendMessageField.setLabel("Send Message");
    sendMessageField.addKeyPressListener(Key.ENTER, e -> {
      sendMessage(sendMessageField.getValue());
      receivedMessageField.setValue(getMessage());
    });

    Button sendMessageButton = new Button();
    sendMessageButton.setText("Send message");
    sendMessageButton.addClickListener(e -> {
      sendMessage(sendMessageField.getValue());
      receivedMessageField.setValue(getMessage());
    });

    add(new Text("Welcome to Studychat."));
    add(sendMessageField);
    add(sendMessageButton);
    add(receivedMessageField);
    this.restTemplate = restTemplate;
  }

  private void sendMessage(String message) {
    restTemplate.postForEntity(sendMessageUrl, message, String.class);
  }

  private String getMessage() {
    return Optional.of(restTemplate.getForEntity(getMessageUrl, String.class)).orElse(new ResponseEntity<>("", HttpStatus.I_AM_A_TEAPOT)).getBody();
  }
}
