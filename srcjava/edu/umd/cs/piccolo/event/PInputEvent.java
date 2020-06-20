package edu.umd.cs.piccolo.event;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PComponent;
import edu.umd.cs.piccolo.PInputManager;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PDimension;
import edu.umd.cs.piccolo.util.PPickPath;
import java.awt.Cursor;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import javax.swing.SwingUtilities;

public class PInputEvent {
  private InputEvent inputEvent;
  
  private PPickPath pickPath;
  
  private PInputManager inputManager;
  
  private boolean handled;
  
  public PInputEvent(PInputManager paramPInputManager, InputEvent paramInputEvent) {
    this.inputEvent = paramInputEvent;
    this.inputManager = paramPInputManager;
  }
  
  public void pushCursor(Cursor paramCursor) {
    PComponent pComponent = getTopCamera().getComponent();
    pComponent.pushCursor(paramCursor);
  }
  
  public void popCursor() {
    PComponent pComponent = getTopCamera().getComponent();
    pComponent.popCursor();
  }
  
  public PCamera getCamera() {
    return getPath().getBottomCamera();
  }
  
  public PCamera getTopCamera() {
    return getPath().getTopCamera();
  }
  
  public PComponent getComponent() {
    return getTopCamera().getComponent();
  }
  
  public PInputManager getInputManager() {
    return this.inputManager;
  }
  
  public PPickPath getPath() {
    return this.pickPath;
  }
  
  public void setPath(PPickPath paramPPickPath) {
    this.pickPath = paramPPickPath;
  }
  
  public PNode getPickedNode() {
    return this.pickPath.getPickedNode();
  }
  
  public int getKeyCode() {
    if (isKeyEvent()) {
      KeyEvent keyEvent = (KeyEvent)this.inputEvent;
      return keyEvent.getKeyCode();
    } 
    throw new IllegalStateException("Can't get keycode from mouse event");
  }
  
  public char getKeyChar() {
    if (isKeyEvent()) {
      KeyEvent keyEvent = (KeyEvent)this.inputEvent;
      return keyEvent.getKeyChar();
    } 
    throw new IllegalStateException("Can't get keychar from mouse event");
  }
  
  public int getKeyLocation() {
    if (isKeyEvent()) {
      KeyEvent keyEvent = (KeyEvent)this.inputEvent;
      return keyEvent.getKeyLocation();
    } 
    throw new IllegalStateException("Can't get keylocation from mouse event");
  }
  
  public boolean isActionKey() {
    if (isKeyEvent()) {
      KeyEvent keyEvent = (KeyEvent)this.inputEvent;
      return keyEvent.isActionKey();
    } 
    throw new IllegalStateException("Can't get isActionKey from mouse event");
  }
  
  public int getModifiers() {
    if (!isFocusEvent())
      return this.inputEvent.getModifiers(); 
    throw new IllegalStateException("Can't get modifiers from focus event");
  }
  
  public int getModifiersEx() {
    if (!isFocusEvent())
      return this.inputEvent.getModifiersEx(); 
    throw new IllegalStateException("Can't get modifiers ex from focus event");
  }
  
  public int getClickCount() {
    if (isMouseEvent())
      return ((MouseEvent)this.inputEvent).getClickCount(); 
    throw new IllegalStateException("Can't get clickcount from key event");
  }
  
  public long getWhen() {
    if (!isFocusEvent())
      return this.inputEvent.getWhen(); 
    throw new IllegalStateException("Can't get when from focus event");
  }
  
  public boolean isAltDown() {
    if (!isFocusEvent())
      return this.inputEvent.isAltDown(); 
    throw new IllegalStateException("Can't get altdown from focus event");
  }
  
  public boolean isControlDown() {
    if (!isFocusEvent())
      return this.inputEvent.isControlDown(); 
    throw new IllegalStateException("Can't get controldown from focus event");
  }
  
  public boolean isMetaDown() {
    if (!isFocusEvent())
      return this.inputEvent.isMetaDown(); 
    throw new IllegalStateException("Can't get modifiers from focus event");
  }
  
  public boolean isShiftDown() {
    if (!isFocusEvent())
      return this.inputEvent.isShiftDown(); 
    throw new IllegalStateException("Can't get shiftdown from focus event");
  }
  
  public boolean isLeftMouseButton() {
    if (isMouseEvent())
      return SwingUtilities.isLeftMouseButton((MouseEvent)getSourceSwingEvent()); 
    throw new IllegalStateException("Can't get isLeftMouseButton from focus event");
  }
  
  public boolean isMiddleMouseButton() {
    if (isMouseEvent())
      return SwingUtilities.isMiddleMouseButton((MouseEvent)getSourceSwingEvent()); 
    throw new IllegalStateException("Can't get isMiddleMouseButton from focus event");
  }
  
  public boolean isRightMouseButton() {
    if (isMouseEvent())
      return SwingUtilities.isRightMouseButton((MouseEvent)getSourceSwingEvent()); 
    throw new IllegalStateException("Can't get isRightMouseButton from focus event");
  }
  
  public boolean isHandled() {
    return this.handled;
  }
  
  public void setHandled(boolean paramBoolean) {
    this.handled = paramBoolean;
  }
  
  public int getButton() {
    if (isMouseEvent())
      return ((MouseEvent)this.inputEvent).getButton(); 
    throw new IllegalStateException("Can't get button from key event");
  }
  
  public int getWheelRotation() {
    if (isMouseWheelEvent())
      return ((MouseWheelEvent)this.inputEvent).getWheelRotation(); 
    throw new IllegalStateException("Can't get wheel rotation from non-wheel event");
  }
  
  public InputEvent getSourceSwingEvent() {
    return this.inputEvent;
  }
  
  public boolean isKeyEvent() {
    return this.inputEvent instanceof KeyEvent;
  }
  
  public boolean isMouseEvent() {
    return this.inputEvent instanceof MouseEvent;
  }
  
  public boolean isMouseWheelEvent() {
    return this.inputEvent instanceof MouseWheelEvent;
  }
  
  public boolean isFocusEvent() {
    return (this.inputEvent == null);
  }
  
  public boolean isMouseEnteredOrMouseExited() {
    return isMouseEvent() ? ((this.inputEvent.getID() == 504 || this.inputEvent.getID() == 505)) : false;
  }
  
  public boolean isPopupTrigger() {
    if (isMouseEvent())
      return ((MouseEvent)this.inputEvent).isPopupTrigger(); 
    throw new IllegalStateException("Can't get clickcount from key event");
  }
  
  public Point2D getCanvasPosition() {
    return (Point2D)this.inputManager.getCurrentCanvasPosition().clone();
  }
  
  public PDimension getCanvasDelta() {
    Point2D point2D1 = this.inputManager.getLastCanvasPosition();
    Point2D point2D2 = this.inputManager.getCurrentCanvasPosition();
    return new PDimension(point2D2.getX() - point2D1.getX(), point2D2.getY() - point2D1.getY());
  }
  
  public Point2D getPositionRelativeTo(PNode paramPNode) {
    Point2D point2D = getCanvasPosition();
    return this.pickPath.canvasToLocal(point2D, paramPNode);
  }
  
  public PDimension getDeltaRelativeTo(PNode paramPNode) {
    PDimension pDimension = getCanvasDelta();
    return (PDimension)this.pickPath.canvasToLocal((Dimension2D)pDimension, paramPNode);
  }
  
  public Point2D getPosition() {
    Point2D point2D = getCanvasPosition();
    this.pickPath.canvasToLocal(point2D, (PNode)getCamera());
    return getCamera().localToView(point2D);
  }
  
  public PDimension getDelta() {
    PDimension pDimension = getCanvasDelta();
    this.pickPath.canvasToLocal((Dimension2D)pDimension, (PNode)getCamera());
    return (PDimension)getCamera().localToView((Dimension2D)pDimension);
  }
  
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(super.toString().replaceAll(".*\\.", ""));
    stringBuffer.append('[');
    if (this.handled)
      stringBuffer.append("handled"); 
    stringBuffer.append(']');
    return stringBuffer.toString();
  }
}
