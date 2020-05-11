/*
 * TestService
 * (c) Copyright BESK Kft, 2020
 * All Rights reserved.
 */

package de.thi.jbsa.prototype.service;

import org.springframework.stereotype.Service;
import de.thi.jbsa.prototype.aop.Censored;
import de.thi.jbsa.prototype.model.cmd.PostMessageCmd;

/**
 * FIXME @Timmy TO BE DELETED
 */
@Service
public class TestService {

  @Censored
  public String censoredMethod(PostMessageCmd cmd) {
    return cmd.getContent();
  }
}
