/*
 * DomainAspects
 * (c) Copyright BESK Kft, 2020
 * All Rights reserved.
 */

package de.thi.jbsa.prototype.aop;

import java.util.stream.Stream;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import de.thi.jbsa.prototype.model.cmd.PostMessageCmd;

/**
 * Created by Balazs Endredi <balazs.endredi@beskgroup.com> on 06.05.2020
 */
@Aspect
public class DomainAspects {

  private String calculateCensoredContent(String content, String term) {
    if (content == null) {
      return null;
    }
    return content.replace(term, "<not allowed term>");
  }

  @Before("@within(de.thi.jbsa.prototype.aop.Censored) && args(cmd,..)")
  public void censoredMethods(PostMessageCmd cmd) {
    Stream.of("JBSA", "jbsa", "JbSA")
          .forEach(term -> cmd.setContent(calculateCensoredContent(cmd.getContent(), term)));
  }
}
