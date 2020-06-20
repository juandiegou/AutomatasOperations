package edu.umd.cs.piccolox.handles;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPickPath;
import java.awt.geom.Rectangle2D;

public class PStickyHandleManager extends PNode {
  private PNode target;
  
  private PCamera camera;
  
  private boolean isResizing;
  
  public PStickyHandleManager(PCamera paramPCamera, PNode paramPNode) {
    setCameraTarget(paramPCamera, paramPNode);
    PBoundsHandle.addBoundsHandlesTo(this);
  }
  
  public void setCameraTarget(PCamera paramPCamera, PNode paramPNode) {
    this.camera = paramPCamera;
    this.camera.addChild(this);
    this.target = paramPNode;
  }
  
  public boolean setBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    PBounds pBounds = new PBounds(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    this.camera.localToGlobal((Rectangle2D)pBounds);
    this.camera.localToView((Rectangle2D)pBounds);
    this.target.globalToLocal((Rectangle2D)pBounds);
    this.target.setBounds((Rectangle2D)pBounds);
    return super.setBounds(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
  }
  
  protected boolean getBoundsVolatile() {
    return true;
  }
  
  public PBounds getBoundsReference() {
    PBounds pBounds1 = this.target.getFullBounds();
    this.camera.viewToLocal((Rectangle2D)pBounds1);
    this.camera.globalToLocal((Rectangle2D)pBounds1);
    PBounds pBounds2 = super.getBoundsReference();
    pBounds2.setRect(pBounds1);
    return super.getBoundsReference();
  }
  
  public void startResizeBounds() {
    super.startResizeBounds();
    this.target.startResizeBounds();
    this.isResizing = true;
  }
  
  public void endResizeBounds() {
    super.endResizeBounds();
    this.target.endResizeBounds();
    this.isResizing = false;
  }
  
  public boolean pickAfterChildren(PPickPath paramPPickPath) {
    return false;
  }
}
