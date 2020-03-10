package de.thi.jbsa.prototype.service;

import org.springframework.stereotype.Service;
import lombok.Getter;
import lombok.Setter;

@Service
public class MessageService {

  @Getter
  @Setter
  private String message;
}
