package edu.umd.cs.piccolox.handles;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventListener;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PDimension;
import edu.umd.cs.piccolo.util.PPickPath;
import edu.umd.cs.piccolox.util.PBoundsLocator;
import edu.umd.cs.piccolox.util.PLocator;
import java.awt.Cursor;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.ListIterator;

public class PBoundsHandle extends PHandle {
  private transient PBasicInputEventHandler handleCursorHandler;
  
  public static void addBoundsHandlesTo(PNode paramPNode) {
    paramPNode.addChild((PNode)new PBoundsHandle(PBoundsLocator.createEastLocator(paramPNode)));
    paramPNode.addChild((PNode)new PBoundsHandle(PBoundsLocator.createWestLocator(paramPNode)));
    paramPNode.addChild((PNode)new PBoundsHandle(PBoundsLocator.createNorthLocator(paramPNode)));
    paramPNode.addChild((PNode)new PBoundsHandle(PBoundsLocator.createSouthLocator(paramPNode)));
    paramPNode.addChild((PNode)new PBoundsHandle(PBoundsLocator.createNorthEastLocator(paramPNode)));
    paramPNode.addChild((PNode)new PBoundsHandle(PBoundsLocator.createNorthWestLocator(paramPNode)));
    paramPNode.addChild((PNode)new PBoundsHandle(PBoundsLocator.createSouthEastLocator(paramPNode)));
    paramPNode.addChild((PNode)new PBoundsHandle(PBoundsLocator.createSouthWestLocator(paramPNode)));
  }
  
  public static void addStickyBoundsHandlesTo(PNode paramPNode, PCamera paramPCamera) {
    paramPCamera.addChild((PNode)new PBoundsHandle(PBoundsLocator.createEastLocator(paramPNode)));
    paramPCamera.addChild((PNode)new PBoundsHandle(PBoundsLocator.createWestLocator(paramPNode)));
    paramPCamera.addChild((PNode)new PBoundsHandle(PBoundsLocator.createNorthLocator(paramPNode)));
    paramPCamera.addChild((PNode)new PBoundsHandle(PBoundsLocator.createSouthLocator(paramPNode)));
    paramPCamera.addChild((PNode)new PBoundsHandle(PBoundsLocator.createNorthEastLocator(paramPNode)));
    paramPCamera.addChild((PNode)new PBoundsHandle(PBoundsLocator.createNorthWestLocator(paramPNode)));
    paramPCamera.addChild((PNode)new PBoundsHandle(PBoundsLocator.createSouthEastLocator(paramPNode)));
    paramPCamera.addChild((PNode)new PBoundsHandle(PBoundsLocator.createSouthWestLocator(paramPNode)));
  }
  
  public static void removeBoundsHandlesFrom(PNode paramPNode) {
    ArrayList arrayList = new ArrayList();
    ListIterator listIterator = paramPNode.getChildrenIterator();
    while (listIterator.hasNext()) {
      PNode pNode = listIterator.next();
      if (pNode instanceof PBoundsHandle)
        arrayList.add(pNode); 
    } 
    paramPNode.removeChildren(arrayList);
  }
  
  public PBoundsHandle(PBoundsLocator paramPBoundsLocator) {
    super((PLocator)paramPBoundsLocator);
  }
  
  protected void installHandleEventHandlers() {
    super.installHandleEventHandlers();
    this.handleCursorHandler = new PBasicInputEventHandler(this) {
        boolean cursorPushed;
        
        private final PBoundsHandle this$0;
        
        public void mouseEntered(PInputEvent param1PInputEvent) {
          if (!this.cursorPushed) {
            param1PInputEvent.pushCursor(this.this$0.getCursorFor(((PBoundsLocator)this.this$0.getLocator()).getSide()));
            this.cursorPushed = true;
          } 
        }
        
        public void mouseExited(PInputEvent param1PInputEvent) {
          PPickPath pPickPath = param1PInputEvent.getInputManager().getMouseFocus();
          if (this.cursorPushed && (pPickPath == null || pPickPath.getPickedNode() != this.this$0)) {
            param1PInputEvent.popCursor();
            this.cursorPushed = false;
          } 
        }
        
        public void mouseReleased(PInputEvent param1PInputEvent) {
          if (this.cursorPushed) {
            param1PInputEvent.popCursor();
            this.cursorPushed = false;
          } 
        }
      };
    addInputEventListener((PInputEventListener)this.handleCursorHandler);
  }
  
  public PBasicInputEventHandler getHandleCursorEventHandler() {
    return this.handleCursorHandler;
  }
  
  public void startHandleDrag(Point2D paramPoint2D, PInputEvent paramPInputEvent) {
    PBoundsLocator pBoundsLocator = (PBoundsLocator)getLocator();
    pBoundsLocator.getNode().startResizeBounds();
  }
  
  public void dragHandle(PDimension paramPDimension, PInputEvent paramPInputEvent) {
    PBoundsLocator pBoundsLocator = (PBoundsLocator)getLocator();
    PNode pNode1 = pBoundsLocator.getNode();
    PBounds pBounds = pNode1.getBounds();
    PNode pNode2 = getParent();
    if (pNode2 != pNode1 && pNode2 instanceof PCamera)
      ((PCamera)pNode2).localToView((Dimension2D)paramPDimension); 
    localToGlobal((Dimension2D)paramPDimension);
    pNode1.globalToLocal((Dimension2D)paramPDimension);
    double d1 = paramPDimension.getWidth();
    double d2 = paramPDimension.getHeight();
    switch (pBoundsLocator.getSide()) {
      case 1:
        pBounds.setRect(pBounds.x, pBounds.y + d2, pBounds.width, pBounds.height - d2);
        break;
      case 5:
        pBounds.setRect(pBounds.x, pBounds.y, pBounds.width, pBounds.height + d2);
        break;
      case 3:
        pBounds.setRect(pBounds.x, pBounds.y, pBounds.width + d1, pBounds.height);
        break;
      case 7:
        pBounds.setRect(pBounds.x + d1, pBounds.y, pBounds.width - d1, pBounds.height);
        break;
      case 8:
        pBounds.setRect(pBounds.x + d1, pBounds.y + d2, pBounds.width - d1, pBounds.height - d2);
        break;
      case 6:
        pBounds.setRect(pBounds.x + d1, pBounds.y, pBounds.width - d1, pBounds.height + d2);
        break;
      case 2:
        pBounds.setRect(pBounds.x, pBounds.y + d2, pBounds.width + d1, pBounds.height - d2);
        break;
      case 4:
        pBounds.setRect(pBounds.x, pBounds.y, pBounds.width + d1, pBounds.height + d2);
        break;
    } 
    boolean bool1 = false;
    boolean bool2 = false;
    if (pBounds.width < 0.0D) {
      bool1 = true;
      pBounds.width = -pBounds.width;
      pBounds.x -= pBounds.width;
    } 
    if (pBounds.height < 0.0D) {
      bool2 = true;
      pBounds.height = -pBounds.height;
      pBounds.y -= pBounds.height;
    } 
    if (bool1 || bool2)
      flipSiblingBoundsHandles(bool1, bool2); 
    pNode1.setBounds((Rectangle2D)pBounds);
  }
  
  public void endHandleDrag(Point2D paramPoint2D, PInputEvent paramPInputEvent) {
    PBoundsLocator pBoundsLocator = (PBoundsLocator)getLocator();
    pBoundsLocator.getNode().endResizeBounds();
  }
  
  public void flipSiblingBoundsHandles(boolean paramBoolean1, boolean paramBoolean2) {
    ListIterator listIterator = getParent().getChildrenIterator();
    while (listIterator.hasNext()) {
      PBoundsHandle pBoundsHandle = (PBoundsHandle)listIterator.next();
      if (pBoundsHandle instanceof PBoundsHandle)
        ((PBoundsHandle)pBoundsHandle).flipHandleIfNeeded(paramBoolean1, paramBoolean2); 
    } 
  }
  
  public void flipHandleIfNeeded(boolean paramBoolean1, boolean paramBoolean2) {
    PBoundsLocator pBoundsLocator = (PBoundsLocator)getLocator();
    if (paramBoolean1 || paramBoolean2)
      switch (pBoundsLocator.getSide()) {
        case 1:
          if (paramBoolean2)
            pBoundsLocator.setSide(5); 
          break;
        case 5:
          if (paramBoolean2)
            pBoundsLocator.setSide(1); 
          break;
        case 3:
          if (paramBoolean1)
            pBoundsLocator.setSide(7); 
          break;
        case 7:
          if (paramBoolean1)
            pBoundsLocator.setSide(3); 
          break;
        case 8:
          if (paramBoolean1 && paramBoolean2) {
            pBoundsLocator.setSide(4);
            break;
          } 
          if (paramBoolean1) {
            pBoundsLocator.setSide(2);
            break;
          } 
          if (paramBoolean2)
            pBoundsLocator.setSide(6); 
          break;
        case 6:
          if (paramBoolean1 && paramBoolean2) {
            pBoundsLocator.setSide(2);
            break;
          } 
          if (paramBoolean1) {
            pBoundsLocator.setSide(4);
            break;
          } 
          if (paramBoolean2)
            pBoundsLocator.setSide(8); 
          break;
        case 2:
          if (paramBoolean1 && paramBoolean2) {
            pBoundsLocator.setSide(6);
            break;
          } 
          if (paramBoolean1) {
            pBoundsLocator.setSide(8);
            break;
          } 
          if (paramBoolean2)
            pBoundsLocator.setSide(4); 
          break;
        case 4:
          if (paramBoolean1 && paramBoolean2) {
            pBoundsLocator.setSide(8);
            break;
          } 
          if (paramBoolean1) {
            pBoundsLocator.setSide(6);
            break;
          } 
          if (paramBoolean2)
            pBoundsLocator.setSide(2); 
          break;
      }  
    setLocator((PLocator)pBoundsLocator);
  }
  
  public Cursor getCursorFor(int paramInt) {
    switch (paramInt) {
      case 1:
        return new Cursor(8);
      case 5:
        return new Cursor(9);
      case 3:
        return new Cursor(11);
      case 7:
        return new Cursor(10);
      case 8:
        return new Cursor(6);
      case 6:
        return new Cursor(4);
      case 2:
        return new Cursor(7);
      case 4:
        return new Cursor(5);
    } 
    return null;
  }
}
