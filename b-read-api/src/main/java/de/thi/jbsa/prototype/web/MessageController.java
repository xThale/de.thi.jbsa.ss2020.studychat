package de.thi.jbsa.prototype.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.thi.jbsa.prototype.service.MessageService;

@RestController
@RequestMapping("/api")
public class MessageController {

  final MessageService messageService;

  public MessageController(MessageService messageService) {this.messageService = messageService;}

  @GetMapping("/message")
  public ResponseEntity<String> getMessage() {
    return new ResponseEntity<>(messageService.getMessage(), HttpStatus.OK);
  }
}
