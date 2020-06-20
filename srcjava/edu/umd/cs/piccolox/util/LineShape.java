package edu.umd.cs.piccolox.util;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class LineShape implements Shape, MutablePoints {
  private MutablePoints points;
  
  private Rectangle2D bounds = new Rectangle2D.Double();
  
  public LineShape(MutablePoints paramMutablePoints) {
    setPoints(paramMutablePoints);
  }
  
  public void setPoints(MutablePoints paramMutablePoints) {
    if (paramMutablePoints == null)
      paramMutablePoints = new XYArray(); 
    this.points = paramMutablePoints;
  }
  
  public int getPointCount() {
    return this.points.getPointCount();
  }
  
  public double getX(int paramInt) {
    return this.points.getX(paramInt);
  }
  
  public double getY(int paramInt) {
    return this.points.getY(paramInt);
  }
  
  public Point2D getPoint(int paramInt, Point2D paramPoint2D) {
    return this.points.getPoint(paramInt, paramPoint2D);
  }
  
  public Rectangle2D getBounds(Rectangle2D paramRectangle2D) {
    this.points.getBounds(paramRectangle2D);
    return paramRectangle2D;
  }
  
  public void updateBounds() {
    this.bounds.setRect(0.0D, 0.0D, 0.0D, 0.0D);
    this.points.getBounds(this.bounds);
  }
  
  public void setPoint(int paramInt, double paramDouble1, double paramDouble2) {
    this.points.setPoint(paramInt, paramDouble1, paramDouble2);
    updateBounds();
  }
  
  public void addPoint(int paramInt, double paramDouble1, double paramDouble2) {
    this.points.addPoint(paramInt, paramDouble1, paramDouble2);
    updateBounds();
  }
  
  public void removePoints(int paramInt1, int paramInt2) {
    this.points.removePoints(paramInt1, paramInt2);
    updateBounds();
  }
  
  public void transformPoints(AffineTransform paramAffineTransform) {
    XYArray xYArray = new XYArray(this.points.getPointCount());
    xYArray.appendPoints(this.points);
    xYArray.transformPoints(paramAffineTransform);
    this.points = xYArray;
  }
  
  public Rectangle getBounds() {
    return new Rectangle((int)this.bounds.getX(), (int)this.bounds.getY(), (int)this.bounds.getWidth(), (int)this.bounds.getHeight());
  }
  
  public Rectangle2D getBounds2D() {
    return this.bounds;
  }
  
  public static boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, boolean paramBoolean1, boolean paramBoolean2, double paramDouble7) {
    double d5;
    double d1 = paramDouble5 - paramDouble3;
    double d2 = paramDouble6 - paramDouble4;
    double d3 = d1 * d1;
    double d4 = d2 * d2;
    if (d1 != 0.0D) {
      d5 = ((paramDouble1 - paramDouble3) / d1 + d2 * (paramDouble2 - paramDouble4) / d3) / (1.0D + d4 / d3);
    } else if (d2 != 0.0D) {
      d5 = ((paramDouble2 - paramDouble4) / d2 + d1 * (paramDouble1 - paramDouble3) / d4) / (1.0D + d3 / d4);
    } else {
      return false;
    } 
    if (paramBoolean2 && d5 > 1.0D)
      return false; 
    if (paramBoolean1 && d5 < 0.0D)
      return false; 
    d1 = d5 * d1 + paramDouble3 - paramDouble1;
    d2 = d5 * d2 + paramDouble4 - paramDouble2;
    double d6 = d1 * d1 + d2 * d2;
    return (d6 < paramDouble7);
  }
  
  public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3) {
    if (this.points.getPointCount() == 0)
      return false; 
    double d1 = this.points.getX(0);
    double d2 = this.points.getX(0);
    for (byte b = 0; b < this.points.getPointCount(); b++) {
      double d3 = d1;
      double d4 = d2;
      d1 = this.points.getX(b);
      d2 = this.points.getX(b);
      if (contains(paramDouble1, paramDouble2, d3, d4, d1, d2, false, false, paramDouble3))
        return true; 
    } 
    return false;
  }
  
  public boolean contains(double paramDouble1, double paramDouble2) {
    return contains(paramDouble1, paramDouble2, 2.0D);
  }
  
  public boolean contains(Point2D paramPoint2D) {
    return contains(paramPoint2D.getX(), paramPoint2D.getY());
  }
  
  public static boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
    double d5;
    double d6;
    double d1 = paramDouble3 - paramDouble1;
    double d2 = paramDouble4 - paramDouble2;
    double d3 = paramDouble7 - paramDouble5;
    double d4 = paramDouble8 - paramDouble6;
    if (d2 != 0.0D) {
      double d = d1 / d2;
      d5 = (paramDouble5 - paramDouble1 + d * (paramDouble2 - paramDouble6)) / (d * d4 - d3);
      d6 = (d4 * d5 + paramDouble6 - paramDouble2) / d2;
    } else if (d4 != 0.0D) {
      double d = d3 / d4;
      d6 = (paramDouble1 - paramDouble5 + d * (paramDouble6 - paramDouble2)) / (d * d2 - d1);
      d5 = (d2 * d6 + paramDouble2 - paramDouble6) / d4;
    } else if (d1 != 0.0D) {
      double d = d2 / d1;
      d5 = (paramDouble6 - paramDouble2 + d * (paramDouble1 - paramDouble5)) / (d * d3 - d4);
      d6 = (d3 * d5 + paramDouble5 - paramDouble1) / d1;
    } else if (d3 != 0.0D) {
      double d = d4 / d3;
      d6 = (paramDouble2 - paramDouble6 + d * (paramDouble5 - paramDouble1)) / (d * d1 - d2);
      d5 = (d1 * d6 + paramDouble1 - paramDouble5) / d3;
    } else {
      return false;
    } 
    return ((!paramBoolean1 || d6 >= 0.0D) && (!paramBoolean2 || d6 <= 1.0D) && (!paramBoolean3 || d5 >= 0.0D) && (!paramBoolean4 || d5 <= 1.0D));
  }
  
  public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    if (this.points.getPointCount() == 0)
      return false; 
    double d1 = this.points.getX(0);
    double d2 = this.points.getX(0);
    for (byte b = 0; b < this.points.getPointCount(); b++) {
      double d3 = d1;
      double d4 = d2;
      d1 = this.points.getX(b);
      d2 = this.points.getX(b);
      if (intersects(paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2, d3, d4, d1, d2, true, true, true, true) || intersects(paramDouble1 + paramDouble3, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4, d3, d4, d1, d2, true, true, true, true) || intersects(paramDouble1 + paramDouble3, paramDouble2 + paramDouble4, paramDouble1, paramDouble2 + paramDouble4, d3, d4, d1, d2, true, true, true, true) || intersects(paramDouble1, paramDouble2 + paramDouble4, paramDouble1, paramDouble2, d3, d4, d1, d2, true, true, true, true))
        return true; 
    } 
    return false;
  }
  
  public boolean intersects(Rectangle2D paramRectangle2D) {
    return intersects(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
  }
  
  public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    return (contains(paramDouble1, paramDouble2) && contains(paramDouble1 + paramDouble3, paramDouble2) && contains(paramDouble1, paramDouble2 + paramDouble4) && contains(paramDouble1 + paramDouble3, paramDouble2 + paramDouble4));
  }
  
  public boolean contains(Rectangle2D paramRectangle2D) {
    return contains(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
  }
  
  public PathIterator getPathIterator(AffineTransform paramAffineTransform) {
    return new LinePathIterator(this.points, paramAffineTransform);
  }
  
  public PathIterator getPathIterator(AffineTransform paramAffineTransform, double paramDouble) {
    return new LinePathIterator(this.points, paramAffineTransform);
  }
  
  private static class LinePathIterator implements PathIterator {
    private Points points;
    
    private AffineTransform trans;
    
    private int i = 0;
    
    private Point2D tempPoint = new Point2D.Double();
    
    public LinePathIterator(Points param1Points, AffineTransform param1AffineTransform) {
      this.points = param1Points;
      this.trans = param1AffineTransform;
    }
    
    public int getWindingRule() {
      return 0;
    }
    
    public boolean isDone() {
      return (this.i >= this.points.getPointCount());
    }
    
    public void next() {
      this.i++;
    }
    
    private void currentSegment() {
      this.tempPoint.setLocation(this.points.getX(this.i), this.points.getY(this.i));
      if (this.trans != null)
        this.trans.transform(this.tempPoint, this.tempPoint); 
    }
    
    public int currentSegment(float[] param1ArrayOffloat) {
      currentSegment();
      param1ArrayOffloat[0] = (float)this.tempPoint.getX();
      param1ArrayOffloat[1] = (float)this.tempPoint.getY();
      return (this.i == 0) ? 0 : 1;
    }
    
    public int currentSegment(double[] param1ArrayOfdouble) {
      currentSegment();
      param1ArrayOfdouble[0] = this.tempPoint.getX();
      param1ArrayOfdouble[1] = this.tempPoint.getY();
      return (this.i == 0) ? 0 : 1;
    }
  }
}
