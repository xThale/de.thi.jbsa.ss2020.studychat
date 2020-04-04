package de.thi.jbsa.prototype.web;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.thi.jbsa.prototype.model.model.Message;
import de.thi.jbsa.prototype.model.model.MessageList;
import de.thi.jbsa.prototype.service.MessageService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class MessageController {

  final MessageService messageService;

  public MessageController(MessageService messageService) {this.messageService = messageService;}

  @GetMapping("/message")
  public ResponseEntity<Message> getLastMessage() {
    final Optional<Message> lastMessageOpt = messageService.getAllMessages().stream()
                                                           .map(messageDoc -> messageDoc.getMessage())
                                                           .reduce(BinaryOperator.maxBy(Comparator.comparingLong(value -> value.getCreated().getTime())));
    return ResponseEntity.of(lastMessageOpt); // throws HTTP 400 if null
  }

  @GetMapping("/messages")
  public ResponseEntity<MessageList> getMessages() {
    MessageList messageList = new MessageList(
      messageService.getAllMessages().stream()
                    .map(messageDoc -> messageDoc.getMessage())
                    .collect(Collectors.toList())
    );
    return new ResponseEntity<>(messageList, HttpStatus.OK);
  }
}
