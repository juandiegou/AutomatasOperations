package edu.umd.cs.piccolo.util;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PBounds extends Rectangle2D.Double implements Serializable {
  private boolean isEmpty = true;
  
  public PBounds() {}
  
  public PBounds(PBounds paramPBounds) {
    this(paramPBounds.x, paramPBounds.y, paramPBounds.width, paramPBounds.height);
    this.isEmpty = paramPBounds.isEmpty();
  }
  
  public PBounds(Rectangle2D paramRectangle2D) {
    this(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
    this.isEmpty = paramRectangle2D.isEmpty();
  }
  
  public PBounds(Point2D paramPoint2D, double paramDouble1, double paramDouble2) {
    this(paramPoint2D.getX(), paramPoint2D.getY(), 0.0D, 0.0D);
    inset(paramDouble1, paramDouble2);
  }
  
  public PBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    super(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    this.isEmpty = false;
  }
  
  public Object clone() {
    return new PBounds(this);
  }
  
  public boolean isEmpty() {
    return this.isEmpty;
  }
  
  public PBounds reset() {
    this.isEmpty = true;
    return this;
  }
  
  public PBounds resetToZero() {
    this.x = 0.0D;
    this.y = 0.0D;
    this.width = 0.0D;
    this.height = 0.0D;
    this.isEmpty = true;
    return this;
  }
  
  public void setRect(Rectangle2D paramRectangle2D) {
    super.setRect(paramRectangle2D);
    this.isEmpty = false;
  }
  
  public void setRect(PBounds paramPBounds) {
    this.isEmpty = paramPBounds.isEmpty;
    this.x = paramPBounds.x;
    this.y = paramPBounds.y;
    this.width = paramPBounds.width;
    this.height = paramPBounds.height;
  }
  
  public void setRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    this.x = paramDouble1;
    this.y = paramDouble2;
    this.width = paramDouble3;
    this.height = paramDouble4;
    this.isEmpty = false;
  }
  
  public void add(double paramDouble1, double paramDouble2) {
    if (this.isEmpty) {
      setRect(paramDouble1, paramDouble2, 0.0D, 0.0D);
      this.isEmpty = false;
    } else {
      super.add(paramDouble1, paramDouble2);
    } 
  }
  
  public void add(Rectangle2D paramRectangle2D) {
    if (this.isEmpty) {
      setRect(paramRectangle2D);
    } else {
      super.add(paramRectangle2D);
    } 
  }
  
  public void add(PBounds paramPBounds) {
    if (paramPBounds.isEmpty)
      return; 
    if (this.isEmpty) {
      this.x = paramPBounds.x;
      this.y = paramPBounds.y;
      this.width = paramPBounds.width;
      this.height = paramPBounds.height;
      this.isEmpty = false;
    } else {
      double d1 = (this.x <= paramPBounds.x) ? this.x : paramPBounds.x;
      double d2 = (this.y <= paramPBounds.y) ? this.y : paramPBounds.y;
      double d3 = (this.x + this.width >= paramPBounds.x + paramPBounds.width) ? (this.x + this.width) : (paramPBounds.x + paramPBounds.width);
      double d4 = (this.y + this.height >= paramPBounds.y + paramPBounds.height) ? (this.y + this.height) : (paramPBounds.y + paramPBounds.height);
      this.x = d1;
      this.y = d2;
      this.width = d3 - d1;
      this.height = d4 - d2;
      this.isEmpty = false;
    } 
  }
  
  public Point2D getOrigin() {
    return new Point2D.Double(this.x, this.y);
  }
  
  public PBounds setOrigin(double paramDouble1, double paramDouble2) {
    this.x = paramDouble1;
    this.y = paramDouble2;
    this.isEmpty = false;
    return this;
  }
  
  public Dimension2D getSize() {
    return new PDimension(this.width, this.height);
  }
  
  public void setSize(double paramDouble1, double paramDouble2) {
    setRect(this.x, this.y, paramDouble1, paramDouble2);
  }
  
  public Point2D getCenter2D() {
    return new Point2D.Double(getCenterX(), getCenterY());
  }
  
  public PBounds moveBy(double paramDouble1, double paramDouble2) {
    setOrigin(this.x + paramDouble1, this.y + paramDouble2);
    return this;
  }
  
  public void expandNearestIntegerDimensions() {
    this.x = Math.floor(this.x);
    this.y = Math.floor(this.y);
    this.width = Math.ceil(this.width);
    this.height = Math.ceil(this.height);
  }
  
  public PBounds inset(double paramDouble1, double paramDouble2) {
    setRect(this.x + paramDouble1, this.y + paramDouble2, this.width - paramDouble1 * 2.0D, this.height - paramDouble2 * 2.0D);
    return this;
  }
  
  public PDimension deltaRequiredToCenter(Rectangle2D paramRectangle2D) {
    PDimension pDimension = new PDimension();
    double d1 = getCenterX() - paramRectangle2D.getCenterX();
    double d2 = getCenterY() - paramRectangle2D.getCenterY();
    pDimension.setSize(d1, d2);
    return pDimension;
  }
  
  public PDimension deltaRequiredToContain(Rectangle2D paramRectangle2D) {
    PDimension pDimension = new PDimension();
    if (!contains(paramRectangle2D)) {
      double d1 = paramRectangle2D.getMaxX();
      double d2 = paramRectangle2D.getMinX();
      double d3 = paramRectangle2D.getMaxY();
      double d4 = paramRectangle2D.getMinY();
      double d5 = getMaxX();
      double d6 = getMinX();
      double d7 = getMaxY();
      double d8 = getMinY();
      if ((d1 <= d5 || d2 >= d6) && (d1 > d5 || d2 < d6)) {
        double d9 = d1 - d5;
        double d10 = d2 - d6;
        if (Math.abs(d9) < Math.abs(d10)) {
          pDimension.width = d9;
        } else {
          pDimension.width = d10;
        } 
      } 
      if ((d3 <= d7 || d4 >= d8) && (d3 > d7 || d4 < d8)) {
        double d9 = d3 - d7;
        double d10 = d4 - d8;
        if (Math.abs(d9) < Math.abs(d10)) {
          pDimension.height = d9;
        } else {
          pDimension.height = d10;
        } 
      } 
    } 
    return pDimension;
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    paramObjectOutputStream.writeDouble(this.x);
    paramObjectOutputStream.writeDouble(this.y);
    paramObjectOutputStream.writeDouble(this.width);
    paramObjectOutputStream.writeDouble(this.height);
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    paramObjectInputStream.defaultReadObject();
    this.x = paramObjectInputStream.readDouble();
    this.y = paramObjectInputStream.readDouble();
    this.width = paramObjectInputStream.readDouble();
    this.height = paramObjectInputStream.readDouble();
  }
  
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(getClass().getName().replaceAll(".*\\.", ""));
    stringBuffer.append('[');
    if (this.isEmpty) {
      stringBuffer.append("EMPTY");
    } else {
      stringBuffer.append("x=");
      stringBuffer.append(this.x);
      stringBuffer.append(",y=");
      stringBuffer.append(this.y);
      stringBuffer.append(",width=");
      stringBuffer.append(this.width);
      stringBuffer.append(",height=");
      stringBuffer.append(this.height);
    } 
    stringBuffer.append(']');
    return stringBuffer.toString();
  }
}
