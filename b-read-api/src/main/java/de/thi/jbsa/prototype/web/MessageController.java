package de.thi.jbsa.prototype.web;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.thi.jbsa.prototype.model.model.Message;
import de.thi.jbsa.prototype.service.MessageService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {this.messageService = messageService;}

  @GetMapping("/messages")
  public ResponseEntity<List<Message>> getMessages() {
    return new ResponseEntity<>(messageService.getlast10Messages(), HttpStatus.OK);
  }
}
