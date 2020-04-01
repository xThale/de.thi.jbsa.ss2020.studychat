package de.thi.jbsa.prototype.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.Getter;
import lombok.Setter;

@Service
public class MessageService {

  @Getter
  private LinkedList<String> messages = new LinkedList<>();

  public void addMessage(String message){
    messages.add(message);
  }

}
