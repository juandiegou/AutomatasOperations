package edu.umd.cs.piccolo.event;

public class PBasicInputEventHandler implements PInputEventListener {
  private PInputEventFilter eventFilter = new PInputEventFilter();
  
  public void processEvent(PInputEvent paramPInputEvent, int paramInt) {
    if (!acceptsEvent(paramPInputEvent, paramInt))
      return; 
    switch (paramInt) {
      case 401:
        keyPressed(paramPInputEvent);
        return;
      case 402:
        keyReleased(paramPInputEvent);
        return;
      case 400:
        keyTyped(paramPInputEvent);
        return;
      case 500:
        mouseClicked(paramPInputEvent);
        return;
      case 506:
        mouseDragged(paramPInputEvent);
        return;
      case 504:
        mouseEntered(paramPInputEvent);
        return;
      case 505:
        mouseExited(paramPInputEvent);
        return;
      case 503:
        mouseMoved(paramPInputEvent);
        return;
      case 501:
        mousePressed(paramPInputEvent);
        return;
      case 502:
        mouseReleased(paramPInputEvent);
        return;
      case 0:
        mouseWheelRotated(paramPInputEvent);
        return;
      case 1:
        mouseWheelRotatedByBlock(paramPInputEvent);
        return;
      case 1004:
        keyboardFocusGained(paramPInputEvent);
        return;
      case 1005:
        keyboardFocusLost(paramPInputEvent);
        return;
    } 
    throw new RuntimeException("Bad Event Type");
  }
  
  public boolean acceptsEvent(PInputEvent paramPInputEvent, int paramInt) {
    return this.eventFilter.acceptsEvent(paramPInputEvent, paramInt);
  }
  
  public PInputEventFilter getEventFilter() {
    return this.eventFilter;
  }
  
  public void setEventFilter(PInputEventFilter paramPInputEventFilter) {
    this.eventFilter = paramPInputEventFilter;
  }
  
  public void keyPressed(PInputEvent paramPInputEvent) {}
  
  public void keyReleased(PInputEvent paramPInputEvent) {}
  
  public void keyTyped(PInputEvent paramPInputEvent) {}
  
  public void mouseClicked(PInputEvent paramPInputEvent) {}
  
  public void mousePressed(PInputEvent paramPInputEvent) {}
  
  public void mouseDragged(PInputEvent paramPInputEvent) {}
  
  public void mouseEntered(PInputEvent paramPInputEvent) {}
  
  public void mouseExited(PInputEvent paramPInputEvent) {}
  
  public void mouseMoved(PInputEvent paramPInputEvent) {}
  
  public void mouseReleased(PInputEvent paramPInputEvent) {}
  
  public void mouseWheelRotated(PInputEvent paramPInputEvent) {}
  
  public void mouseWheelRotatedByBlock(PInputEvent paramPInputEvent) {}
  
  public void keyboardFocusGained(PInputEvent paramPInputEvent) {}
  
  public void keyboardFocusLost(PInputEvent paramPInputEvent) {}
  
  public String toString() {
    String str = super.toString().replaceAll(".*\\.", "");
    return str + "[" + paramString() + "]";
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(("eventFilter=" + this.eventFilter == null) ? "null" : this.eventFilter.toString());
    return stringBuffer.toString();
  }
}
