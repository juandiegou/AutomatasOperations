package edu.umd.cs.piccolox.util;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class XYArray implements MutablePoints {
  private double[] points = null;
  
  private int numPoints = 0;
  
  public int getPointCount() {
    return this.numPoints;
  }
  
  private int i(int paramInt) {
    if (paramInt < 0)
      paramInt = this.numPoints + paramInt; 
    if (paramInt >= this.numPoints)
      throw new IllegalArgumentException("The point index " + paramInt + " is not below " + this.numPoints); 
    return paramInt;
  }
  
  public double getX(int paramInt) {
    paramInt = i(paramInt);
    return this.points[paramInt * 2];
  }
  
  public double getY(int paramInt) {
    paramInt = i(paramInt);
    return this.points[paramInt * 2 + 1];
  }
  
  public Point2D getPoint(int paramInt, Point2D paramPoint2D) {
    paramInt = i(paramInt);
    paramPoint2D.setLocation(this.points[paramInt * 2], this.points[paramInt * 2 + 1]);
    return paramPoint2D;
  }
  
  public void setX(int paramInt, double paramDouble) {
    paramInt = i(paramInt);
    this.points[paramInt * 2] = paramDouble;
  }
  
  public void setY(int paramInt, double paramDouble) {
    paramInt = i(paramInt);
    this.points[paramInt * 2 + 1] = paramDouble;
  }
  
  public void setPoint(int paramInt, double paramDouble1, double paramDouble2) {
    paramInt = i(paramInt);
    this.points[paramInt * 2] = paramDouble1;
    this.points[paramInt * 2 + 1] = paramDouble2;
  }
  
  public void setPoint(int paramInt, Point2D paramPoint2D) {
    setPoint(paramInt, paramPoint2D.getX(), paramPoint2D.getY());
  }
  
  public void transformPoints(AffineTransform paramAffineTransform) {
    paramAffineTransform.transform(this.points, 0, this.points, 0, this.numPoints);
  }
  
  public Rectangle2D getBounds(Rectangle2D paramRectangle2D) {
    byte b = 0;
    if (paramRectangle2D.isEmpty() && getPointCount() > 0) {
      paramRectangle2D.setRect(getX(b), getY(b), 1.0D, 1.0D);
      b++;
    } 
    while (b < getPointCount()) {
      paramRectangle2D.add(getX(b), getY(b));
      b++;
    } 
    return paramRectangle2D;
  }
  
  public static double[] initPoints(double[] paramArrayOfdouble1, int paramInt, double[] paramArrayOfdouble2) {
    if (paramArrayOfdouble1 == null || paramInt * 2 > paramArrayOfdouble1.length)
      paramArrayOfdouble1 = new double[paramInt * 2]; 
    if (paramArrayOfdouble2 != null && paramArrayOfdouble1 != paramArrayOfdouble2)
      System.arraycopy(paramArrayOfdouble2, 0, paramArrayOfdouble1, 0, Math.min(paramArrayOfdouble2.length, paramInt * 2)); 
    return paramArrayOfdouble1;
  }
  
  private void initPoints(double[] paramArrayOfdouble, int paramInt) {
    this.points = initPoints(paramArrayOfdouble, paramInt, this.points);
    this.numPoints = (paramArrayOfdouble != null) ? (paramArrayOfdouble.length / 2) : 0;
  }
  
  public XYArray(double[] paramArrayOfdouble) {
    initPoints(paramArrayOfdouble, paramArrayOfdouble.length / 2);
  }
  
  public XYArray(int paramInt) {
    initPoints(null, paramInt);
  }
  
  public XYArray() {
    this(0);
  }
  
  public void addPoints(int paramInt1, Points paramPoints, int paramInt2, int paramInt3) {
    if (paramInt3 < 0)
      paramInt3 = paramPoints.getPointCount() + paramInt3 + 1; 
    int i = this.numPoints + paramInt3 - paramInt2;
    this.points = initPoints(this.points, i, this.points);
    int j = paramInt1 * 2;
    int k = (paramInt1 + paramInt3 - paramInt2) * 2;
    int m = (this.numPoints - paramInt1) * 2;
    System.arraycopy(this.points, j, this.points, k, m);
    this.numPoints = i;
    if (paramPoints == null)
      return; 
    byte b = 0;
    while (paramInt2 < paramInt3) {
      setPoint(paramInt1 + b, paramPoints.getX(paramInt2), paramPoints.getY(paramInt2));
      b++;
      paramInt2++;
    } 
  }
  
  public void addPoints(int paramInt, Points paramPoints) {
    addPoints(paramInt, paramPoints, 0, paramPoints.getPointCount());
  }
  
  public void appendPoints(Points paramPoints) {
    addPoints(this.numPoints, paramPoints);
  }
  
  public static XYArray copyPoints(Points paramPoints) {
    XYArray xYArray = new XYArray(paramPoints.getPointCount());
    xYArray.appendPoints(paramPoints);
    return xYArray;
  }
  
  public void addPoint(int paramInt, double paramDouble1, double paramDouble2) {
    addPoints(paramInt, null, 0, 1);
    setPoint(paramInt, paramDouble1, paramDouble2);
  }
  
  public void addPoint(int paramInt, Point2D paramPoint2D) {
    addPoint(paramInt, paramPoint2D.getX(), paramPoint2D.getY());
  }
  
  public void removePoints(int paramInt1, int paramInt2) {
    paramInt2 = Math.min(paramInt2, this.numPoints - paramInt1);
    if (paramInt2 <= 0)
      return; 
    System.arraycopy(this.points, (paramInt1 + paramInt2) * 2, this.points, paramInt1 * 2, (this.numPoints - paramInt1 + paramInt2) * 2);
    this.numPoints -= paramInt2;
  }
  
  public void removeAllPoints() {
    removePoints(0, this.numPoints);
  }
  
  public Object clone() {
    XYArray xYArray = null;
    try {
      xYArray = (XYArray)super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {}
    if (xYArray != null) {
      xYArray.points = initPoints(xYArray.points, this.numPoints, this.points);
      xYArray.numPoints = this.numPoints;
    } 
    return xYArray;
  }
}
