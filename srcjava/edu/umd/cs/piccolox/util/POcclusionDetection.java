package edu.umd.cs.piccolox.util;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPickPath;
import java.awt.geom.Rectangle2D;

public class POcclusionDetection {
  public void detectOccusions(PNode paramPNode, PBounds paramPBounds) {
    detectOcclusions(paramPNode, new PPickPath(null, paramPBounds));
  }
  
  public void detectOcclusions(PNode paramPNode, PPickPath paramPPickPath) {
    if (paramPNode.fullIntersects((Rectangle2D)paramPPickPath.getPickBounds())) {
      paramPPickPath.pushTransform(paramPNode.getTransformReference(false));
      int i = paramPNode.getChildrenCount();
      for (int j = i - 1; j >= 0; j--) {
        PNode pNode = paramPNode.getChild(j);
        if (paramPNode.getOccluded()) {
          pNode.setOccluded(true);
        } else {
          detectOcclusions(pNode, paramPPickPath);
        } 
      } 
      if (!paramPNode.getOccluded() && paramPNode.intersects((Rectangle2D)paramPPickPath.getPickBounds()) && paramPNode.isOpaque((Rectangle2D)paramPPickPath.getPickBounds())) {
        PNode pNode = paramPNode.getParent();
        while (pNode != null && !pNode.getOccluded())
          pNode.setOccluded(true); 
      } 
      paramPPickPath.popTransform(paramPNode.getTransformReference(false));
    } 
  }
}
