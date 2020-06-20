package edu.umd.cs.piccolox.swt;

import java.awt.Component;
import java.awt.event.KeyEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;

public class PSWTKeyEvent extends KeyEvent {
  static Component fakeSrc = new Component() {
    
    };
  
  KeyEvent swtEvent;
  
  public PSWTKeyEvent(KeyEvent paramKeyEvent, int paramInt) {
    super(fakeSrc, paramInt, paramKeyEvent.time, 0, paramKeyEvent.keyCode, paramKeyEvent.character, 1);
    this.swtEvent = paramKeyEvent;
  }
  
  public Object getSource() {
    return this.swtEvent.getSource();
  }
  
  public boolean isShiftDown() {
    return ((this.swtEvent.stateMask & 0x20000) != 0);
  }
  
  public boolean isControlDown() {
    return ((this.swtEvent.stateMask & 0x40000) != 0);
  }
  
  public boolean isAltDown() {
    return ((this.swtEvent.stateMask & 0x10000) != 0);
  }
  
  public int getModifiers() {
    int i = 0;
    if (this.swtEvent != null) {
      if ((this.swtEvent.stateMask & 0x10000) != 0)
        i |= 0x8; 
      if ((this.swtEvent.stateMask & 0x40000) != 0)
        i |= 0x2; 
      if ((this.swtEvent.stateMask & 0x20000) != 0)
        i |= 0x1; 
    } 
    return i;
  }
  
  public int getModifiersEx() {
    int i = 0;
    if (this.swtEvent != null) {
      if ((this.swtEvent.stateMask & 0x10000) != 0)
        i |= 0x200; 
      if ((this.swtEvent.stateMask & 0x40000) != 0)
        i |= 0x80; 
      if ((this.swtEvent.stateMask & 0x20000) != 0)
        i |= 0x40; 
    } 
    return i;
  }
  
  public boolean isActionKey() {
    return false;
  }
  
  public Widget getWidget() {
    return this.swtEvent.widget;
  }
  
  public Display getDisplay() {
    return this.swtEvent.display;
  }
  
  public Object getData() {
    return this.swtEvent.data;
  }
}