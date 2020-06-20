package edu.umd.cs.piccolox.swt;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PDragSequenceEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventFilter;
import edu.umd.cs.piccolo.event.PInputEventListener;
import edu.umd.cs.piccolo.util.PAffineTransform;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PDimension;
import edu.umd.cs.piccolox.util.PLocator;
import edu.umd.cs.piccolox.util.PNodeLocator;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PSWTHandle extends PSWTPath {
  public static float DEFAULT_HANDLE_SIZE = 8.0F;
  
  public static Shape DEFAULT_HANDLE_SHAPE = new Ellipse2D.Float(0.0F, 0.0F, DEFAULT_HANDLE_SIZE, DEFAULT_HANDLE_SIZE);
  
  public static Color DEFAULT_COLOR = Color.white;
  
  private static PAffineTransform TEMP_TRANSFORM = new PAffineTransform();
  
  private PLocator locator;
  
  private PDragSequenceEventHandler handleDragger;
  
  public PSWTHandle(PLocator paramPLocator) {
    super(DEFAULT_HANDLE_SHAPE);
    this.locator = paramPLocator;
    setPaint(DEFAULT_COLOR);
    installHandleEventHandlers();
  }
  
  protected void installHandleEventHandlers() {
    this.handleDragger = new PDragSequenceEventHandler(this) {
        private final PSWTHandle this$0;
        
        protected void startDrag(PInputEvent param1PInputEvent) {
          super.startDrag(param1PInputEvent);
          this.this$0.startHandleDrag(param1PInputEvent.getPositionRelativeTo(this.this$0), param1PInputEvent);
        }
        
        protected void drag(PInputEvent param1PInputEvent) {
          super.drag(param1PInputEvent);
          PDimension pDimension = param1PInputEvent.getDeltaRelativeTo(this.this$0);
          if (pDimension.getWidth() != 0.0D || pDimension.getHeight() != 0.0D)
            this.this$0.dragHandle(pDimension, param1PInputEvent); 
        }
        
        protected void endDrag(PInputEvent param1PInputEvent) {
          super.endDrag(param1PInputEvent);
          this.this$0.endHandleDrag(param1PInputEvent.getPositionRelativeTo(this.this$0), param1PInputEvent);
        }
      };
    addPropertyChangeListener("transform", new PropertyChangeListener(this) {
          private final PSWTHandle this$0;
          
          public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
            this.this$0.relocateHandle();
          }
        });
    this.handleDragger.setEventFilter(new PInputEventFilter(16));
    this.handleDragger.getEventFilter().setMarksAcceptedEventsAsHandled(true);
    this.handleDragger.getEventFilter().setAcceptsMouseEntered(false);
    this.handleDragger.getEventFilter().setAcceptsMouseExited(false);
    this.handleDragger.getEventFilter().setAcceptsMouseMoved(false);
    addInputEventListener((PInputEventListener)this.handleDragger);
  }
  
  public PDragSequenceEventHandler getHandleDraggerHandler() {
    return this.handleDragger;
  }
  
  public PLocator getLocator() {
    return this.locator;
  }
  
  public void setLocator(PLocator paramPLocator) {
    this.locator = paramPLocator;
    invalidatePaint();
    relocateHandle();
  }
  
  public void startHandleDrag(Point2D paramPoint2D, PInputEvent paramPInputEvent) {}
  
  public void dragHandle(PDimension paramPDimension, PInputEvent paramPInputEvent) {}
  
  public void endHandleDrag(Point2D paramPoint2D, PInputEvent paramPInputEvent) {}
  
  public void setParent(PNode paramPNode) {
    super.setParent(paramPNode);
    relocateHandle();
  }
  
  public void parentBoundsChanged() {
    relocateHandle();
  }
  
  public void relocateHandle() {
    if (this.locator != null) {
      PBounds pBounds = getBoundsReference();
      Point2D point2D = this.locator.locatePoint(null);
      if (this.locator instanceof PNodeLocator) {
        PNode pNode1 = ((PNodeLocator)this.locator).getNode();
        PNode pNode2 = getParent();
        pNode1.localToGlobal(point2D);
        globalToLocal(point2D);
        if (pNode2 != pNode1 && pNode2 instanceof PCamera)
          ((PCamera)pNode2).viewToLocal(point2D); 
      } 
      double d1 = point2D.getX();
      double d2 = point2D.getY();
      if (d1 != pBounds.getCenterX() || d2 != pBounds.getCenterY())
        centerBoundsOnPoint(d1, d2); 
    } 
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    paramObjectInputStream.defaultReadObject();
    installHandleEventHandlers();
  }
}
