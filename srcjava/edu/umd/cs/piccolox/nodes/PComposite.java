package edu.umd.cs.piccolox.nodes;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PPickPath;

public class PComposite extends PNode {
  public boolean fullPick(PPickPath paramPPickPath) {
    if (super.fullPick(paramPPickPath)) {
      for (PNode pNode = paramPPickPath.getPickedNode(); pNode != this; pNode = paramPPickPath.getPickedNode()) {
        paramPPickPath.popTransform(pNode.getTransformReference(false));
        paramPPickPath.popNode(pNode);
      } 
      return true;
    } 
    return false;
  }
}
