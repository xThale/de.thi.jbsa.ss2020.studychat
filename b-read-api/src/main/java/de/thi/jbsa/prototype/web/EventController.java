package de.thi.jbsa.prototype.web;

import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import de.thi.jbsa.prototype.model.event.EventList;
import de.thi.jbsa.prototype.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Christopher Timm <christopher.timm@beskgroup.com> on 2020-04-08
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class EventController {

  private final MessageService messageService;

  @GetMapping("/events")
  public ResponseEntity<EventList> getEvents(@RequestParam Optional<UUID> lastUUID, @RequestParam String userid) {
    log.info("lastEvent = " + lastUUID);
    log.info("userid = " + userid);
    EventList eventList;
    if (lastUUID.isPresent()) {
      eventList = new EventList(messageService.getEvents(lastUUID.get()));
    } else {
      eventList = new EventList(messageService.getEvents());
    }
    return new ResponseEntity<>(eventList, HttpStatus.OK);
  }
}
