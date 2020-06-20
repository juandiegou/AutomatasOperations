package edu.umd.cs.piccolo;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventListener;
import edu.umd.cs.piccolo.util.PPickPath;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class PInputManager extends PBasicInputEventHandler implements PRoot.InputSource {
  private Point2D lastCanvasPosition = new Point2D.Double();
  
  private Point2D currentCanvasPosition = new Point2D.Double();
  
  private InputEvent nextInput;
  
  private int nextType;
  
  private PCamera nextInputSource;
  
  private PPickPath mouseFocus;
  
  private PPickPath previousMouseFocus;
  
  private PPickPath mouseOver;
  
  private PPickPath previousMouseOver;
  
  private PInputEventListener keyboardFocus;
  
  private int pressedCount;
  
  public PInputEventListener getKeyboardFocus() {
    return this.keyboardFocus;
  }
  
  public void setKeyboardFocus(PInputEventListener paramPInputEventListener) {
    PInputEvent pInputEvent = new PInputEvent(this, null);
    if (this.keyboardFocus != null)
      dispatchEventToListener(pInputEvent, 1005, this.keyboardFocus); 
    this.keyboardFocus = paramPInputEventListener;
    if (this.keyboardFocus != null)
      dispatchEventToListener(pInputEvent, 1004, this.keyboardFocus); 
  }
  
  public PPickPath getMouseFocus() {
    return this.mouseFocus;
  }
  
  public void setMouseFocus(PPickPath paramPPickPath) {
    this.previousMouseFocus = this.mouseFocus;
    this.mouseFocus = paramPPickPath;
  }
  
  public PPickPath getMouseOver() {
    return this.mouseOver;
  }
  
  public void setMouseOver(PPickPath paramPPickPath) {
    this.mouseOver = paramPPickPath;
  }
  
  public Point2D getLastCanvasPosition() {
    return this.lastCanvasPosition;
  }
  
  public Point2D getCurrentCanvasPosition() {
    return this.currentCanvasPosition;
  }
  
  public void keyPressed(PInputEvent paramPInputEvent) {
    dispatchEventToListener(paramPInputEvent, 401, this.keyboardFocus);
  }
  
  public void keyReleased(PInputEvent paramPInputEvent) {
    dispatchEventToListener(paramPInputEvent, 402, this.keyboardFocus);
  }
  
  public void keyTyped(PInputEvent paramPInputEvent) {
    dispatchEventToListener(paramPInputEvent, 400, this.keyboardFocus);
  }
  
  public void mouseClicked(PInputEvent paramPInputEvent) {
    dispatchEventToListener(paramPInputEvent, 500, (PInputEventListener)this.previousMouseFocus);
  }
  
  public void mouseWheelRotated(PInputEvent paramPInputEvent) {
    setMouseFocus(getMouseOver());
    dispatchEventToListener(paramPInputEvent, 0, (PInputEventListener)this.mouseOver);
  }
  
  public void mouseWheelRotatedByBlock(PInputEvent paramPInputEvent) {
    setMouseFocus(getMouseOver());
    dispatchEventToListener(paramPInputEvent, 1, (PInputEventListener)this.mouseOver);
  }
  
  public void mouseDragged(PInputEvent paramPInputEvent) {
    checkForMouseEnteredAndExited(paramPInputEvent);
    dispatchEventToListener(paramPInputEvent, 506, (PInputEventListener)this.mouseFocus);
  }
  
  public void mouseEntered(PInputEvent paramPInputEvent) {
    dispatchEventToListener(paramPInputEvent, 504, (PInputEventListener)this.mouseOver);
  }
  
  public void mouseExited(PInputEvent paramPInputEvent) {
    dispatchEventToListener(paramPInputEvent, 505, (PInputEventListener)this.previousMouseOver);
  }
  
  public void mouseMoved(PInputEvent paramPInputEvent) {
    checkForMouseEnteredAndExited(paramPInputEvent);
    dispatchEventToListener(paramPInputEvent, 503, (PInputEventListener)this.mouseOver);
  }
  
  public void mousePressed(PInputEvent paramPInputEvent) {
    if (this.pressedCount == 0)
      setMouseFocus(getMouseOver()); 
    this.pressedCount++;
    dispatchEventToListener(paramPInputEvent, 501, (PInputEventListener)this.mouseFocus);
    if (this.pressedCount < 1 || this.pressedCount > 3)
      System.err.println("invalid pressedCount on mouse pressed: " + this.pressedCount); 
  }
  
  public void mouseReleased(PInputEvent paramPInputEvent) {
    this.pressedCount--;
    checkForMouseEnteredAndExited(paramPInputEvent);
    dispatchEventToListener(paramPInputEvent, 502, (PInputEventListener)this.mouseFocus);
    if (this.pressedCount == 0)
      setMouseFocus(null); 
    if (this.pressedCount < 0 || this.pressedCount > 2)
      System.err.println("invalid pressedCount on mouse released: " + this.pressedCount); 
  }
  
  protected void checkForMouseEnteredAndExited(PInputEvent paramPInputEvent) {
    PNode pNode1 = (this.mouseOver != null) ? this.mouseOver.getPickedNode() : null;
    PNode pNode2 = (this.previousMouseOver != null) ? this.previousMouseOver.getPickedNode() : null;
    if (pNode1 != pNode2) {
      dispatchEventToListener(paramPInputEvent, 505, (PInputEventListener)this.previousMouseOver);
      dispatchEventToListener(paramPInputEvent, 504, (PInputEventListener)this.mouseOver);
      this.previousMouseOver = this.mouseOver;
    } 
  }
  
  public void processInput() {
    if (this.nextInput == null)
      return; 
    PInputEvent pInputEvent = new PInputEvent(this, this.nextInput);
    Point2D point2D1 = null;
    Point2D point2D2 = null;
    if (pInputEvent.isMouseEvent())
      if (pInputEvent.isMouseEnteredOrMouseExited()) {
        PPickPath pPickPath = this.nextInputSource.pick(((MouseEvent)this.nextInput).getX(), ((MouseEvent)this.nextInput).getY(), 1.0D);
        setMouseOver(pPickPath);
        this.previousMouseOver = pPickPath;
        point2D1 = (Point2D)this.currentCanvasPosition.clone();
        point2D2 = (Point2D)this.lastCanvasPosition.clone();
      } else {
        this.lastCanvasPosition.setLocation(this.currentCanvasPosition);
        this.currentCanvasPosition.setLocation(((MouseEvent)this.nextInput).getX(), ((MouseEvent)this.nextInput).getY());
        PPickPath pPickPath = this.nextInputSource.pick(this.currentCanvasPosition.getX(), this.currentCanvasPosition.getY(), 1.0D);
        setMouseOver(pPickPath);
      }  
    this.nextInput = null;
    this.nextInputSource = null;
    processEvent(pInputEvent, this.nextType);
    if (point2D1 != null && point2D2 != null) {
      this.currentCanvasPosition.setLocation(point2D1);
      this.lastCanvasPosition.setLocation(point2D2);
    } 
  }
  
  public void processEventFromCamera(InputEvent paramInputEvent, int paramInt, PCamera paramPCamera) {
    this.nextInput = paramInputEvent;
    this.nextType = paramInt;
    this.nextInputSource = paramPCamera;
    paramPCamera.getRoot().processInputs();
  }
  
  private void dispatchEventToListener(PInputEvent paramPInputEvent, int paramInt, PInputEventListener paramPInputEventListener) {
    if (paramPInputEventListener != null) {
      paramPInputEvent.setHandled(false);
      paramPInputEventListener.processEvent(paramPInputEvent, paramInt);
    } 
  }
}
