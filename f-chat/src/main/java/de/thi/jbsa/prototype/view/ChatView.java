package de.thi.jbsa.prototype.view;

import java.util.LinkedList;
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
import lombok.extern.slf4j.Slf4j;

@UIScope
@SpringComponent
@Route("home")
@Slf4j
public class ChatView
  extends VerticalLayout {

  final RestTemplate restTemplate;

  @Value("${studychat.url.getMessage}")
  private String getMessageUrl;

  @Value("${studychat.url.getMessages}")
  private String getMessagesUrl;

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
    fetchMessageButton.addClickListener(e -> fetchMessageField.setValue(getLastMessage()));
    fetchMessageButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    VerticalLayout multipleMessagesView = new VerticalLayout();
    VerticalLayout messageListContainer = new VerticalLayout();

    Button fetchMessagesButton = new Button("Fetch all messages");
    fetchMessagesButton.addClickListener(e -> {
      fillUpMessageList(messageListContainer);
    });
    fetchMessagesButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    multipleMessagesView.add(messageListContainer);

    add(new Text("Welcome to Studychat"));
    sendLayout.add(sendMessageField);
    sendLayout.add(sendMessageButton);
    fetchLayout.add(fetchMessageField);
    fetchLayout.add(fetchMessageButton);
    fetchLayout.add(fetchMessagesButton);
    componentLayout.add(sendLayout);
    componentLayout.add(fetchLayout);
    componentLayout.add(multipleMessagesView);
    add(componentLayout);
    this.restTemplate = restTemplate;
  }

  private void fillUpMessageList(VerticalLayout messageListContainer) {
    messageListContainer.removeAll();
    getAllMessages().forEach(s -> {
      TextField newMessageField = new TextField();
      newMessageField.setReadOnly(true);
      newMessageField.setValue(s);
      messageListContainer.add(newMessageField);
    });
  }

  private LinkedList<String> getAllMessages() {
    return Optional.of(restTemplate.getForEntity(getMessagesUrl, LinkedList.class))
                   .orElse(new ResponseEntity<>(new LinkedList(), HttpStatus.I_AM_A_TEAPOT)).getBody();
  }

  private String getLastMessage() {
    return Optional.of(restTemplate.getForEntity(getMessageUrl, String.class)).orElse(new ResponseEntity<>("", HttpStatus.I_AM_A_TEAPOT)).getBody();
  }

  private void sendMessage(String message) {
    restTemplate.postForEntity(sendMessageUrl, message, String.class);
  }
}
