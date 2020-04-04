package de.thi.jbsa.prototype.model.event;

import java.io.Serializable;

/**
 * Created by Balazs Endredi <balazs.endredi@beskgroup.com> on 03.04.2020
 */
public interface Event
  extends Serializable {

  java.util.UUID getUuid();
}
