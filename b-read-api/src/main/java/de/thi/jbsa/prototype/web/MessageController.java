package de.thi.jbsa.prototype.web;

import java.util.LinkedList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.thi.jbsa.prototype.service.MessageService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class MessageController {

  final MessageService messageService;

  public MessageController(MessageService messageService) {this.messageService = messageService;}

  @GetMapping("/message")
  public ResponseEntity<String> getLastMessage() {
    return new ResponseEntity<>(messageService.getMessages().getLast(), HttpStatus.OK);
  }

  @GetMapping("/messages")
  public ResponseEntity<LinkedList<String>> getMessages() {
    final LinkedList<String> allMessages = messageService.getMessages();
    return new ResponseEntity<>(allMessages, HttpStatus.OK);
  }
}
