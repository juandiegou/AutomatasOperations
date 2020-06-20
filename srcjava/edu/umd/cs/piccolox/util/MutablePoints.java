package edu.umd.cs.piccolox.util;

import java.awt.geom.AffineTransform;

public interface MutablePoints extends Points {
  void setPoint(int paramInt, double paramDouble1, double paramDouble2);
  
  void addPoint(int paramInt, double paramDouble1, double paramDouble2);
  
  void removePoints(int paramInt1, int paramInt2);
  
  void transformPoints(AffineTransform paramAffineTransform);
}
