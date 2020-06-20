package edu.umd.cs.piccolox.util;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PBounds;

public class PBoundsLocator extends PNodeLocator {
  private int side;
  
  public static PBoundsLocator createEastLocator(PNode paramPNode) {
    return new PBoundsLocator(paramPNode, 3);
  }
  
  public static PBoundsLocator createNorthEastLocator(PNode paramPNode) {
    return new PBoundsLocator(paramPNode, 2);
  }
  
  public static PBoundsLocator createNorthWestLocator(PNode paramPNode) {
    return new PBoundsLocator(paramPNode, 8);
  }
  
  public static PBoundsLocator createNorthLocator(PNode paramPNode) {
    return new PBoundsLocator(paramPNode, 1);
  }
  
  public static PBoundsLocator createSouthLocator(PNode paramPNode) {
    return new PBoundsLocator(paramPNode, 5);
  }
  
  public static PBoundsLocator createWestLocator(PNode paramPNode) {
    return new PBoundsLocator(paramPNode, 7);
  }
  
  public static PBoundsLocator createSouthWestLocator(PNode paramPNode) {
    return new PBoundsLocator(paramPNode, 6);
  }
  
  public static PBoundsLocator createSouthEastLocator(PNode paramPNode) {
    return new PBoundsLocator(paramPNode, 4);
  }
  
  public PBoundsLocator(PNode paramPNode, int paramInt) {
    super(paramPNode);
    this.side = paramInt;
  }
  
  public int getSide() {
    return this.side;
  }
  
  public void setSide(int paramInt) {
    this.side = paramInt;
  }
  
  public double locateX() {
    PBounds pBounds = this.node.getBoundsReference();
    switch (this.side) {
      case 6:
      case 7:
      case 8:
        return pBounds.getX();
      case 2:
      case 3:
      case 4:
        return pBounds.getX() + pBounds.getWidth();
      case 1:
      case 5:
        return pBounds.getX() + pBounds.getWidth() / 2.0D;
    } 
    return -1.0D;
  }
  
  public double locateY() {
    PBounds pBounds = this.node.getBoundsReference();
    switch (this.side) {
      case 3:
      case 7:
        return pBounds.getY() + pBounds.getHeight() / 2.0D;
      case 4:
      case 5:
      case 6:
        return pBounds.getY() + pBounds.getHeight();
      case 1:
      case 2:
      case 8:
        return pBounds.getY();
    } 
    return -1.0D;
  }
}