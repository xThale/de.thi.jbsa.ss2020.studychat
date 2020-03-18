package de.thi.jbsa.prototype.view;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
@Route("home")
public class ChatView
  extends VerticalLayout {

  final RestTemplate restTemplate;

  @Value("${studychat.url.getMessage}")
  private String getMessageUrl;

  @Value("${studychat.url.sendMessage}")
  private String sendMessageUrl;

  public ChatView(RestTemplate restTemplate) {
    HorizontalLayout componentLayout = new HorizontalLayout();

    VerticalLayout sendLayout = new VerticalLayout();
    VerticalLayout fetchLayout = new VerticalLayout();

    TextField sendMessageField = new TextField("Message To Send");
    sendMessageField.addKeyPressListener(Key.ENTER, e -> sendMessage(sendMessageField.getValue()));

    Button sendMessageButton = new Button("Send message");
    sendMessageButton.addClickListener(e -> sendMessage(sendMessageField.getValue()));
    sendMessageButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    TextField fetchMessageField = new TextField("Received Message");
    fetchMessageField.setValue("");
    fetchMessageField.setReadOnly(true);

    Button fetchMessageButton = new Button("Fetch message");
    fetchMessageButton.addClickListener(e -> fetchMessageField.setValue(getMessage()));
    fetchMessageButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    add(new Text("Welcome to Studychat."));
    sendLayout.add(sendMessageField);
    sendLayout.add(sendMessageButton);
    fetchLayout.add(fetchMessageField);
    fetchLayout.add(fetchMessageButton);
    componentLayout.add(sendLayout);
    componentLayout.add(fetchLayout);
    add(componentLayout);
    this.restTemplate = restTemplate;
  }

  private void sendMessage(String message) {
    restTemplate.postForEntity(sendMessageUrl, message, String.class);
  }

  private String getMessage() {
    return Optional.of(restTemplate.getForEntity(getMessageUrl, String.class)).orElse(new ResponseEntity<>("", HttpStatus.I_AM_A_TEAPOT)).getBody();
  }
}
