package de.thi.jbsa.prototype.aop;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import de.thi.jbsa.prototype.model.cmd.PostMessageCmd;
import de.thi.jbsa.prototype.service.TestService;

/**
 * Created by Balazs Endredi <balazs.endredi@beskgroup.com> on 06.05.2020
 */
@SpringBootTest
@TestPropertySource({ "classpath:application.properties", "classpath:application-test.properties" })
class DomainAspectsTest {

  @Autowired
  private TestService testService;

  @Test
  void censoredMethods() {
    PostMessageCmd cmd = new PostMessageCmd("user-id", "I'm a JBSA student!");
    Assert.assertEquals("I'm a <not allowed term> student!", testService.censoredMethod(cmd));
  }
}
