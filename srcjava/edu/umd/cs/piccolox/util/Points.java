package edu.umd.cs.piccolox.util;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public interface Points {
  int getPointCount();
  
  double getX(int paramInt);
  
  double getY(int paramInt);
  
  Point2D getPoint(int paramInt, Point2D paramPoint2D);
  
  Rectangle2D getBounds(Rectangle2D paramRectangle2D);
}
