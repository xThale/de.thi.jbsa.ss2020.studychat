package de.thi.jbsa.prototype.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import de.thi.jbsa.prototype.model.cmd.MessageList;
import de.thi.jbsa.prototype.model.cmd.PostMessageCmd;
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

    TextField sendMessageField = new TextField("MessageCmd To Send");
    sendMessageField.addKeyPressListener(Key.ENTER, e -> sendMessage(sendMessageField.getValue(), "userId"));

    Button sendMessageButton = new Button("Send message");
    sendMessageButton.addClickListener(e -> sendMessage(sendMessageField.getValue(), "userId"));
    sendMessageButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    TextField fetchMessageField = new TextField("Received MessageCmd");
    fetchMessageField.setValue("");
    fetchMessageField.setReadOnly(true);

    Button fetchMessageButton = new Button("Fetch message");
    fetchMessageButton.addClickListener(e -> fetchMessageField.setValue(getLastMessage()));
    fetchMessageButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    VerticalLayout multipleMessagesView = new VerticalLayout();
    VerticalLayout messageListContainer = new VerticalLayout();

    Button fetchMessagesButton = new Button("Fetch all messages");
    fetchMessagesButton.addClickListener(e -> fillUpMessageList(messageListContainer));
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
      newMessageField.setValue(s.getContent());
      messageListContainer.add(newMessageField);
    });
  }

  private List<PostMessageCmd> getAllMessages() {
    ResponseEntity<MessageList> responseEntity = restTemplate.getForEntity(getMessagesUrl, MessageList.class);
    if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
      return responseEntity.getBody().getMessages();
    }
    return new ArrayList<>();
  }

  private String getLastMessage() {
    return Objects.requireNonNull(Optional.of(restTemplate.getForEntity(getMessageUrl, PostMessageCmd.class))
                                          .orElse(new ResponseEntity<>(new PostMessageCmd("", ""), HttpStatus.I_AM_A_TEAPOT)).getBody()).getContent();
  }

  private void sendMessage(String message, String userId) {
    restTemplate.postForEntity(sendMessageUrl, message, String.class);
  }
}
