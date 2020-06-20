package edu.umd.cs.piccolo.util;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class PDimension extends Dimension2D implements Serializable {
  public double width;
  
  public double height;
  
  public PDimension() {}
  
  public PDimension(Dimension2D paramDimension2D) {
    this(paramDimension2D.getWidth(), paramDimension2D.getHeight());
  }
  
  public PDimension(double paramDouble1, double paramDouble2) {
    this.width = paramDouble1;
    this.height = paramDouble2;
  }
  
  public PDimension(Point2D paramPoint2D1, Point2D paramPoint2D2) {
    this.width = paramPoint2D2.getX() - paramPoint2D1.getX();
    this.height = paramPoint2D2.getY() - paramPoint2D1.getY();
  }
  
  public double getHeight() {
    return this.height;
  }
  
  public double getWidth() {
    return this.width;
  }
  
  public void setSize(double paramDouble1, double paramDouble2) {
    this.width = paramDouble1;
    this.height = paramDouble2;
  }
  
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(super.toString().replaceAll(".*\\.", ""));
    stringBuffer.append('[');
    stringBuffer.append("width=");
    stringBuffer.append(this.width);
    stringBuffer.append(",height=");
    stringBuffer.append(this.height);
    stringBuffer.append(']');
    return stringBuffer.toString();
  }