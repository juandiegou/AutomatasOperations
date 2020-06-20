package edu.umd.cs.piccolox.util;

import java.awt.geom.Point2D;
import java.io.Serializable;

public abstract class PLocator implements Serializable {
  public Point2D locatePoint(Point2D paramPoint2D) {
    if (paramPoint2D == null)
      paramPoint2D = new Point2D.Double(); 
    paramPoint2D.setLocation(locateX(), locateY());
    return paramPoint2D;
  }
  
  public abstract double locateX();
  
  public abstract double locateY();
}
