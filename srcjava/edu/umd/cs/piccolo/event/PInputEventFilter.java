package edu.umd.cs.piccolo.event;

public class PInputEventFilter {
  public static int ALL_MODIFIERS_MASK = 63;
  
  private int andMask;
  
  private int orMask;
  
  private int notMask;
  
  private short clickCount = -1;
  
  private boolean marksAcceptedEventsAsHandled = false;
  
  private boolean acceptsAlreadyHandledEvents = false;
  
  private boolean acceptsKeyPressed = true;
  
  private boolean acceptsKeyReleased = true;
  
  private boolean acceptsKeyTyped = true;
  
  private boolean acceptsMouseClicked = true;
  
  private boolean acceptsMouseDragged = true;
  
  private boolean acceptsMouseEntered = true;
  
  private boolean acceptsMouseExited = true;
  
  private boolean acceptsMouseMoved = true;
  
  private boolean acceptsMousePressed = true;
  
  private boolean acceptsMouseReleased = true;
  
  private boolean acceptsMouseWheelRotated = true;
  
  private boolean acceptsFocusEvents = true;
  
  public PInputEventFilter() {
    acceptEverything();
  }
  
  public PInputEventFilter(int paramInt) {
    this();
    this.andMask = paramInt;
  }
  
  public PInputEventFilter(int paramInt1, int paramInt2) {
    this(paramInt1);
    this.notMask = paramInt2;
  }
  
  public boolean acceptsEvent(PInputEvent paramPInputEvent, int paramInt) {
    boolean bool = false;
    int i = 0;
    if (!paramPInputEvent.isFocusEvent())
      i = paramPInputEvent.getModifiers(); 
    if ((!paramPInputEvent.isHandled() || this.acceptsAlreadyHandledEvents) && (i == 0 || ((i & this.andMask) == this.andMask && (i & this.orMask) != 0 && (i & this.notMask) == 0)))
      if (paramPInputEvent.isMouseEvent() && this.clickCount != -1 && this.clickCount != paramPInputEvent.getClickCount()) {
        bool = false;
      } else {
        switch (paramInt) {
          case 401:
            bool = getAcceptsKeyPressed();
            break;
          case 402:
            bool = getAcceptsKeyReleased();
            break;
          case 400:
            bool = getAcceptsKeyTyped();
            break;
          case 500:
            bool = getAcceptsMouseClicked();
            break;
          case 506:
            bool = getAcceptsMouseDragged();
            break;
          case 504:
            bool = getAcceptsMouseEntered();
            break;
          case 505:
            bool = getAcceptsMouseExited();
            break;
          case 503:
            bool = getAcceptsMouseMoved();
            break;
          case 501:
            bool = getAcceptsMousePressed();
            break;
          case 502:
            bool = getAcceptsMouseReleased();
            break;
          case 0:
          case 1:
            bool = getAcceptsMouseWheelRotated();
            break;
          case 1004:
          case 1005:
            bool = getAcceptsFocusEvents();
            break;
          default:
            throw new RuntimeException("PInputEvent with bad ID");
        } 
      }  
    if (bool && getMarksAcceptedEventsAsHandled())
      paramPInputEvent.setHandled(true); 
    return bool;
  }
  
  public void acceptAllClickCounts() {
    this.clickCount = -1;
  }
  
  public void acceptAllEventTypes() {
    this.acceptsKeyPressed = true;
    this.acceptsKeyReleased = true;
    this.acceptsKeyTyped = true;
    this.acceptsMouseClicked = true;
    this.acceptsMouseDragged = true;
    this.acceptsMouseEntered = true;
    this.acceptsMouseExited = true;
    this.acceptsMouseMoved = true;
    this.acceptsMousePressed = true;
    this.acceptsMouseReleased = true;
    this.acceptsMouseWheelRotated = true;
    this.acceptsFocusEvents = true;
  }
  
  public void acceptEverything() {
    acceptAllEventTypes();
    setAndMask(0);
    setOrMask(ALL_MODIFIERS_MASK);
    setNotMask(0);
    acceptAllClickCounts();
  }
  
  public boolean getAcceptsKeyPressed() {
    return this.acceptsKeyPressed;
  }
  
  public boolean getAcceptsKeyReleased() {
    return this.acceptsKeyReleased;
  }
  
  public boolean getAcceptsKeyTyped() {
    return this.acceptsKeyTyped;
  }
  
  public boolean getAcceptsMouseClicked() {
    return this.acceptsMouseClicked;
  }
  
  public boolean getAcceptsMouseDragged() {
    return this.acceptsMouseDragged;
  }
  
  public boolean getAcceptsMouseEntered() {
    return this.acceptsMouseEntered;
  }
  
  public boolean getAcceptsMouseExited() {
    return this.acceptsMouseExited;
  }
  
  public boolean getAcceptsMouseMoved() {
    return this.acceptsMouseMoved;
  }
  
  public boolean getAcceptsMousePressed() {
    return this.acceptsMousePressed;
  }
  
  public boolean getAcceptsMouseReleased() {
    return this.acceptsMouseReleased;
  }
  
  public boolean getAcceptsMouseWheelRotated() {
    return this.acceptsMouseWheelRotated;
  }
  
  public boolean getAcceptsFocusEvents() {
    return this.acceptsFocusEvents;
  }
  
  public boolean getAcceptsAlreadyHandledEvents() {
    return this.acceptsAlreadyHandledEvents;
  }
  
  public boolean getMarksAcceptedEventsAsHandled() {
    return this.marksAcceptedEventsAsHandled;
  }
  
  public void rejectAllClickCounts() {
    this.clickCount = Short.MAX_VALUE;
  }
  
  public void rejectAllEventTypes() {
    this.acceptsKeyPressed = false;
    this.acceptsKeyReleased = false;
    this.acceptsKeyTyped = false;
    this.acceptsMouseClicked = false;
    this.acceptsMouseDragged = false;
    this.acceptsMouseEntered = false;
    this.acceptsMouseExited = false;
    this.acceptsMouseMoved = false;
    this.acceptsMousePressed = false;
    this.acceptsMouseReleased = false;
    this.acceptsMouseWheelRotated = false;
    this.acceptsFocusEvents = false;
  }
  
  public void setAcceptClickCount(short paramShort) {
    this.clickCount = paramShort;
  }
  
  public void setAcceptsKeyPressed(boolean paramBoolean) {
    this.acceptsKeyPressed = paramBoolean;
  }
  
  public void setAcceptsKeyReleased(boolean paramBoolean) {
    this.acceptsKeyReleased = paramBoolean;
  }
  
  public void setAcceptsKeyTyped(boolean paramBoolean) {
    this.acceptsKeyTyped = paramBoolean;
  }
  
  public void setAcceptsMouseClicked(boolean paramBoolean) {
    this.acceptsMouseClicked = paramBoolean;
  }
  
  public void setAcceptsMouseDragged(boolean paramBoolean) {
    this.acceptsMouseDragged = paramBoolean;
  }
  
  public void setAcceptsMouseEntered(boolean paramBoolean) {
    this.acceptsMouseEntered = paramBoolean;
  }
  
  public void setAcceptsMouseExited(boolean paramBoolean) {
    this.acceptsMouseExited = paramBoolean;
  }
  
  public void setAcceptsMouseMoved(boolean paramBoolean) {
    this.acceptsMouseMoved = paramBoolean;
  }
  
  public void setAcceptsMousePressed(boolean paramBoolean) {
    this.acceptsMousePressed = paramBoolean;
  }
  
  public void setAcceptsMouseReleased(boolean paramBoolean) {
    this.acceptsMouseReleased = paramBoolean;
  }
  
  public void setAcceptsMouseWheelRotated(boolean paramBoolean) {
    this.acceptsMouseWheelRotated = paramBoolean;
  }
  
  public void setAcceptsFocusEvents(boolean paramBoolean) {
    this.acceptsFocusEvents = paramBoolean;
  }
  
  public void setAndMask(int paramInt) {
    this.andMask = paramInt;
  }
  
  public void setAcceptsAlreadyHandledEvents(boolean paramBoolean) {
    this.acceptsAlreadyHandledEvents = paramBoolean;
  }
  
  public void setMarksAcceptedEventsAsHandled(boolean paramBoolean) {
    this.marksAcceptedEventsAsHandled = paramBoolean;
  }
  
  public void setNotMask(int paramInt) {
    this.notMask = paramInt;
  }
  
  public void setOrMask(int paramInt) {
    this.orMask = paramInt;
  }
}