package edu.umd.cs.piccolo.util;

import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PAffineTransform extends AffineTransform {
  private static double[] PTS1 = new double[8];
  
  private static double[] PTS2 = new double[8];
  
  public PAffineTransform() {}
  
  public PAffineTransform(double[] paramArrayOfdouble) {
    super(paramArrayOfdouble);
  }
  
  public PAffineTransform(float[] paramArrayOffloat) {
    super(paramArrayOffloat);
  }
  
  public PAffineTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
    super(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
  }
  
  public PAffineTransform(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public PAffineTransform(AffineTransform paramAffineTransform) {
    super(paramAffineTransform);
  }
  
  public void scaleAboutPoint(double paramDouble1, double paramDouble2, double paramDouble3) {
    translate(paramDouble2, paramDouble3);
    scale(paramDouble1, paramDouble1);
    translate(-paramDouble2, -paramDouble3);
  }
  
  public double getScale() {
    PTS1[0] = 0.0D;
    PTS1[1] = 0.0D;
    PTS1[2] = 1.0D;
    PTS1[3] = 0.0D;
    transform(PTS1, 0, PTS2, 0, 2);
    return Point2D.distance(PTS2[0], PTS2[1], PTS2[2], PTS2[3]);
  }
  
  public void setScale(double paramDouble) {
    if (paramDouble == 0.0D)
      throw new RuntimeException("Can't set scale to 0"); 
    scaleAboutPoint(paramDouble / getScale(), 0.0D, 0.0D);
  }
  
  public void setOffset(double paramDouble1, double paramDouble2) {
    setTransform(getScaleX(), getShearY(), getShearX(), getScaleY(), paramDouble1, paramDouble2);
  }
  
  public double getRotation() {
    PTS1[0] = 0.0D;
    PTS1[1] = 0.0D;
    PTS1[2] = 1.0D;
    PTS1[3] = 0.0D;
    transform(PTS1, 0, PTS2, 0, 2);
    double d1 = Math.abs(PTS2[3] - PTS2[1]);
    double d2 = Point2D.distance(PTS2[0], PTS2[1], PTS2[2], PTS2[3]);
    double d3 = Math.asin(d1 / d2);
    if (PTS2[3] - PTS2[1] > 0.0D) {
      if (PTS2[2] - PTS2[0] < 0.0D)
        d3 = Math.PI - d3; 
    } else if (PTS2[2] - PTS2[0] > 0.0D) {
      d3 = 6.283185307179586D - d3;
    } else {
      d3 += Math.PI;
    } 
    return d3;
  }
  
  public void setRotation(double paramDouble) {
    rotate(paramDouble - getRotation());
  }
  
  public Dimension2D transform(Dimension2D paramDimension2D1, Dimension2D paramDimension2D2) {
    if (paramDimension2D2 == null)
      paramDimension2D2 = (Dimension2D)paramDimension2D1.clone(); 
    PTS1[0] = paramDimension2D1.getWidth();
    PTS1[1] = paramDimension2D1.getHeight();
    deltaTransform(PTS1, 0, PTS2, 0, 1);
    paramDimension2D2.setSize(PTS2[0], PTS2[1]);
    return paramDimension2D2;
  }
  
  public Dimension2D inverseTransform(Dimension2D paramDimension2D1, Dimension2D paramDimension2D2) {
    if (paramDimension2D2 == null)
      paramDimension2D2 = (Dimension2D)paramDimension2D1.clone(); 
    double d1 = paramDimension2D1.getWidth();
    double d2 = paramDimension2D1.getHeight();
    double d3 = getScaleX();
    double d4 = getScaleY();
    double d5 = getShearX();
    double d6 = getShearY();
    double d7 = d3 * d4 - d5 * d6;
    try {
      if (Math.abs(d7) <= Double.MIN_VALUE)
        throw new NoninvertibleTransformException("Determinant is " + d7); 
      paramDimension2D2.setSize((d1 * d4 - d2 * d5) / d7, (d2 * d3 - d1 * d6) / d7);
    } catch (NoninvertibleTransformException noninvertibleTransformException) {
      noninvertibleTransformException.printStackTrace();
    } 
    return paramDimension2D2;
  }
  
  public Rectangle2D transform(Rectangle2D paramRectangle2D1, Rectangle2D paramRectangle2D2) {
    double d;
    if (paramRectangle2D2 == null)
      paramRectangle2D2 = (Rectangle2D)paramRectangle2D1.clone(); 
    if (paramRectangle2D1.isEmpty()) {
      paramRectangle2D2.setRect(paramRectangle2D1);
      if (paramRectangle2D2 instanceof PBounds)
        ((PBounds)paramRectangle2D2).reset(); 
      return paramRectangle2D2;
    } 
    switch (getType()) {
      case 0:
        if (paramRectangle2D1 != paramRectangle2D2)
          paramRectangle2D2.setRect(paramRectangle2D1); 
        return paramRectangle2D2;
      case 1:
        paramRectangle2D2.setRect(paramRectangle2D1.getX() + getTranslateX(), paramRectangle2D1.getY() + getTranslateY(), paramRectangle2D1.getWidth(), paramRectangle2D1.getHeight());
        return paramRectangle2D2;
      case 2:
        d = getScaleX();
        paramRectangle2D2.setRect(paramRectangle2D1.getX() * d, paramRectangle2D1.getY() * d, paramRectangle2D1.getWidth() * d, paramRectangle2D1.getHeight() * d);
        return paramRectangle2D2;
      case 3:
        d = getScaleX();
        paramRectangle2D2.setRect(paramRectangle2D1.getX() * d + getTranslateX(), paramRectangle2D1.getY() * d + getTranslateY(), paramRectangle2D1.getWidth() * d, paramRectangle2D1.getHeight() * d);
        return paramRectangle2D2;
    } 
    double[] arrayOfDouble = rectToArray(paramRectangle2D1);
    transform(arrayOfDouble, 0, arrayOfDouble, 0, 4);
    rectFromArray(paramRectangle2D2, arrayOfDouble);
    return paramRectangle2D2;
  }
  
  public Rectangle2D inverseTransform(Rectangle2D paramRectangle2D1, Rectangle2D paramRectangle2D2) {
    double d;
    if (paramRectangle2D2 == null)
      paramRectangle2D2 = (Rectangle2D)paramRectangle2D1.clone(); 
    if (paramRectangle2D1.isEmpty()) {
      paramRectangle2D2.setRect(paramRectangle2D1);
      if (paramRectangle2D2 instanceof PBounds)
        ((PBounds)paramRectangle2D2).reset(); 
      return paramRectangle2D2;
    } 
    switch (getType()) {
      case 0:
        if (paramRectangle2D1 != paramRectangle2D2)
          paramRectangle2D2.setRect(paramRectangle2D1); 
        return paramRectangle2D2;
      case 1:
        paramRectangle2D2.setRect(paramRectangle2D1.getX() - getTranslateX(), paramRectangle2D1.getY() - getTranslateY(), paramRectangle2D1.getWidth(), paramRectangle2D1.getHeight());
        return paramRectangle2D2;
      case 2:
        d = 1.0D / getScaleX();
        paramRectangle2D2.setRect(paramRectangle2D1.getX() * d, paramRectangle2D1.getY() * d, paramRectangle2D1.getWidth() * d, paramRectangle2D1.getHeight() * d);
        return paramRectangle2D2;
      case 3:
        d = 1.0D / getScaleX();
        paramRectangle2D2.setRect((paramRectangle2D1.getX() - getTranslateX()) * d, (paramRectangle2D1.getY() - getTranslateY()) * d, paramRectangle2D1.getWidth() * d, paramRectangle2D1.getHeight() * d);
        return paramRectangle2D2;
    } 
    double[] arrayOfDouble = rectToArray(paramRectangle2D1);
    try {
      inverseTransform(arrayOfDouble, 0, arrayOfDouble, 0, 4);
    } catch (NoninvertibleTransformException noninvertibleTransformException) {
      noninvertibleTransformException.printStackTrace();
    } 
    rectFromArray(paramRectangle2D2, arrayOfDouble);
    return paramRectangle2D2;
  }
  
  private static double[] rectToArray(Rectangle2D paramRectangle2D) {
    PTS1[0] = paramRectangle2D.getX();
    PTS1[1] = paramRectangle2D.getY();
    PTS1[2] = PTS1[0] + paramRectangle2D.getWidth();
    PTS1[3] = PTS1[1];
    PTS1[4] = PTS1[0] + paramRectangle2D.getWidth();
    PTS1[5] = PTS1[1] + paramRectangle2D.getHeight();
    PTS1[6] = PTS1[0];
    PTS1[7] = PTS1[1] + paramRectangle2D.getHeight();
    return PTS1;
  }
  
  private static void rectFromArray(Rectangle2D paramRectangle2D, double[] paramArrayOfdouble) {
    double d1 = paramArrayOfdouble[0];
    double d2 = paramArrayOfdouble[1];
    double d3 = paramArrayOfdouble[0];
    double d4 = paramArrayOfdouble[1];
    for (byte b = 1; b < 4; b++) {
      double d5 = paramArrayOfdouble[2 * b];
      double d6 = paramArrayOfdouble[2 * b + 1];
      if (d5 < d1)
        d1 = d5; 
      if (d6 < d2)
        d2 = d6; 
      if (d5 > d3)
        d3 = d5; 
      if (d6 > d4)
        d4 = d6; 
    } 
    paramRectangle2D.setRect(d1, d2, d3 - d1, d4 - d2);
  }
}