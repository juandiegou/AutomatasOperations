package edu.umd.cs.piccolox.nodes;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolo.util.PPickPath;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

public class PClip extends PPath {
  public PBounds computeFullBounds(PBounds paramPBounds) {
    if (paramPBounds == null)
      paramPBounds = new PBounds(); 
    paramPBounds.reset();
    paramPBounds.add(getBoundsReference());
    localToParent((Rectangle2D)paramPBounds);
    return paramPBounds;
  }
  
  public void repaintFrom(PBounds paramPBounds, PNode paramPNode) {
    if (paramPNode != this) {
      Rectangle2D.intersect((Rectangle2D)getBoundsReference(), (Rectangle2D)paramPBounds, (Rectangle2D)paramPBounds);
      super.repaintFrom(paramPBounds, paramPNode);
    } else {
      super.repaintFrom(paramPBounds, paramPNode);
    } 
  }
  
  protected void paint(PPaintContext paramPPaintContext) {
    Paint paint = getPaint();
    if (paint != null) {
      Graphics2D graphics2D = paramPPaintContext.getGraphics();
      graphics2D.setPaint(paint);
      graphics2D.fill(getPathReference());
    } 
    paramPPaintContext.pushClip(getPathReference());
  }
  
  protected void paintAfterChildren(PPaintContext paramPPaintContext) {
    paramPPaintContext.popClip(getPathReference());
    if (getStroke() != null && getStrokePaint() != null) {
      Graphics2D graphics2D = paramPPaintContext.getGraphics();
      graphics2D.setPaint(getStrokePaint());
      graphics2D.setStroke(getStroke());
      graphics2D.draw(getPathReference());
    } 
  }
  
  public boolean fullPick(PPickPath paramPPickPath) {
    if (getPickable() && fullIntersects((Rectangle2D)paramPPickPath.getPickBounds())) {
      paramPPickPath.pushNode((PNode)this);
      paramPPickPath.pushTransform(getTransformReference(false));
      if (pick(paramPPickPath))
        return true; 
      if (getChildrenPickable() && getPathReference().intersects((Rectangle2D)paramPPickPath.getPickBounds())) {
        int i = getChildrenCount();
        for (int j = i - 1; j >= 0; j--) {
          PNode pNode = getChild(j);
          if (pNode.fullPick(paramPPickPath))
            return true; 
        } 
      } 
      if (pickAfterChildren(paramPPickPath))
        return true; 
      paramPPickPath.popTransform(getTransformReference(false));
      paramPPickPath.popNode((PNode)this);
    } 
    return false;
  }
}
