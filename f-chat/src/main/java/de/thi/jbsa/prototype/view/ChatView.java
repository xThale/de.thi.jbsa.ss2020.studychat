package de.thi.jbsa.prototype.view;

import java.text.MessageFormat;
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
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import de.thi.jbsa.prototype.model.cmd.MessageList;
import de.thi.jbsa.prototype.model.cmd.PostMessageCmd;
import de.thi.jbsa.prototype.model.model.Message;
import lombok.extern.slf4j.Slf4j;

@UIScope
@SpringComponent
@Route("home")
@Slf4j
public class ChatView
  extends VerticalLayout {

  @Value("${studychat.url.getMessage}")
  private String getMessageUrl;

  @Value("${studychat.url.getMessages}")
  private String getMessagesUrl;

  final RestTemplate restTemplate;

  @Value("${studychat.url.sendMessage}")
  private String sendMessageUrl;

  private static <T> T[] toArray(T... param) {
    return param;
  }

  public ChatView(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    HorizontalLayout componentLayout = new HorizontalLayout();

    VerticalLayout sendLayout = new VerticalLayout();
    VerticalLayout fetchLayout = new VerticalLayout();

    TextField sendUserIdField = new TextField("User-ID");
    sendUserIdField.setValue("User-ID");

    TextField sendMessageField = new TextField("Message To Send");
    sendMessageField.setValue("My Message");

    sendUserIdField.addKeyPressListener(Key.ENTER, e -> sendMessage(sendMessageField.getValue(), sendUserIdField.getValue()));
    sendMessageField.addKeyPressListener(Key.ENTER, e -> sendMessage(sendMessageField.getValue(), sendUserIdField.getValue()));

    Button sendMessageButton = new Button("Send message");
    sendMessageButton.addClickListener(e -> sendMessage(sendMessageField.getValue(), sendUserIdField.getValue()));
    sendMessageButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    ListBox<Message> msgListBox = new ListBox<>();
    MessageFormat msgListBoxTipFormat = new MessageFormat(
      "" +
        "Sent: \t\t{0,time,short}\n" +
        "From: \t\t{1}\n" +
        "Cmd-UUID: \t{2}\n" +
        "Event-UUID: \t{3}\n" +
        "Entity-ID: \t\t{4}\n");

    msgListBox.setRenderer(new ComponentRenderer<>(msg -> {
      Label label = new Label(msg.getContent());
      label.setEnabled(false);
      String tip = msgListBoxTipFormat.format(ChatView.toArray(msg.getCreated(), msg.getSenderUserId(), msg.getCmdUuid(), msg.getEventUuid(), msg.getEntityId()));
      label.setTitle(tip);
      return label;
    }));
    //
    Button fetchMessagesButton = new Button("Fetch all messages");
    fetchMessagesButton.addClickListener(e -> {
      List<Message> msgList = getAllMessages();
      msgListBox.setItems(msgList);
      Notification.show(msgList.size() + " items found");
    });
    fetchMessagesButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    add(new Text("Welcome to Studychat"));
    sendLayout.add(sendUserIdField);
    sendLayout.add(sendMessageField);
    sendLayout.add(sendMessageButton);

    fetchLayout.add(msgListBox);
    fetchLayout.add(fetchMessagesButton);

    componentLayout.add(sendLayout);
    componentLayout.add(fetchLayout);
    add(componentLayout);
  }

  private List<Message> getAllMessages() {
    ResponseEntity<MessageList> responseEntity = restTemplate.getForEntity(getMessagesUrl, MessageList.class);
    if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
      return responseEntity.getBody().getMessages();
    }
    return new ArrayList<>();
  }

  private Message getLastMessage() {
    Message msg = Objects.requireNonNull(
      Optional.of(restTemplate.getForEntity(getMessageUrl, Message.class))
              .orElse(new ResponseEntity<>(new Message(), HttpStatus.I_AM_A_TEAPOT)).getBody());
    return msg;
  }

  private void sendMessage(String message, String userId) {
    PostMessageCmd cmd = new PostMessageCmd(userId, message);
    restTemplate.postForEntity(sendMessageUrl, cmd, PostMessageCmd.class);
  }
}
