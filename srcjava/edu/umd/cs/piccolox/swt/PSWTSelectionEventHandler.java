package edu.umd.cs.piccolox.swt;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolox.event.PSelectionEventHandler;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class PSWTSelectionEventHandler extends PSelectionEventHandler {
  PSWTPath marquee;
  
  PNode marqueeParent;
  
  Point2D pressPt;
  
  Point2D canvasPressPt;
  
  public PSWTSelectionEventHandler(PNode paramPNode1, PNode paramPNode2) {
    super(new PNode(), paramPNode2);
    this.marqueeParent = paramPNode1;
  }
  
  public PSWTSelectionEventHandler(PNode paramPNode, List paramList) {
    super(new PNode(), paramList);
    this.marqueeParent = paramPNode;
  }
  
  public void decorateSelectedNode(PNode paramPNode) {
    PSWTBoundsHandle.addBoundsHandlesTo(paramPNode);
  }
  
  public void undecorateSelectedNode(PNode paramPNode) {
    PSWTBoundsHandle.removeBoundsHandlesFrom(paramPNode);
  }
  
  protected void initializeSelection(PInputEvent paramPInputEvent) {
    super.initializeSelection(paramPInputEvent);
    this.pressPt = paramPInputEvent.getPosition();
    this.canvasPressPt = paramPInputEvent.getCanvasPosition();
  }
  
  protected void initializeMarquee(PInputEvent paramPInputEvent) {
    super.initializeMarquee(paramPInputEvent);
    this.marquee = new PSWTPath(this, new Rectangle2D.Float((float)this.pressPt.getX(), (float)this.pressPt.getY(), 0.0F, 0.0F)) {
        private final PSWTSelectionEventHandler this$0;
        
        protected void paint(PPaintContext param1PPaintContext) {
          SWTGraphics2D sWTGraphics2D = (SWTGraphics2D)param1PPaintContext.getGraphics();
          sWTGraphics2D.gc.setLineStyle(2);
          super.paint(param1PPaintContext);
          sWTGraphics2D.gc.setLineStyle(1);
        }
      };
    this.marquee.setStrokeColor(Color.black);
    this.marquee.setPaint(null);
    this.marqueeParent.addChild(this.marquee);
  }
  
  protected void updateMarquee(PInputEvent paramPInputEvent) {
    super.updateMarquee(paramPInputEvent);
    PBounds pBounds = new PBounds();
    if (this.marqueeParent instanceof edu.umd.cs.piccolo.PCamera) {
      pBounds.add(this.canvasPressPt);
      pBounds.add(paramPInputEvent.getCanvasPosition());
    } else {
      pBounds.add(this.pressPt);
      pBounds.add(paramPInputEvent.getPosition());
    } 
    this.marquee.setPathToRectangle((float)pBounds.x, (float)pBounds.y, (float)pBounds.width, (float)pBounds.height);
    pBounds.reset();
    pBounds.add(this.pressPt);
    pBounds.add(paramPInputEvent.getPosition());
  }
  
  protected PBounds getMarqueeBounds() {
    return (this.marquee != null) ? this.marquee.getBounds() : new PBounds();
  }
  
  protected void endMarqueeSelection(PInputEvent paramPInputEvent) {
    super.endMarqueeSelection(paramPInputEvent);
    this.marquee.removeFromParent();
    this.marquee = null;
  }
  
  protected void dragActivityStep(PInputEvent paramPInputEvent) {}
}
