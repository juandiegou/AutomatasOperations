package edu.umd.cs.piccolox.swing;

import edu.umd.cs.piccolo.PCanvas;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public interface PScrollDirector {
  void install(PViewport paramPViewport, PCanvas paramPCanvas);
  
  void unInstall();
  
  Point getViewPosition(Rectangle2D paramRectangle2D);
  
  void setViewPosition(double paramDouble1, double paramDouble2);
  
  Dimension getViewSize(Rectangle2D paramRectangle2D);
}
