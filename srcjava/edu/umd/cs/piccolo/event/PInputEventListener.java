package edu.umd.cs.piccolo.event;

import java.util.EventListener;

public interface PInputEventListener extends EventListener {
  void processEvent(PInputEvent paramPInputEvent, int paramInt);
}
