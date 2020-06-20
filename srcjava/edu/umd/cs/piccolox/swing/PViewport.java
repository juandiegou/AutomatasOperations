package edu.umd.cs.piccolox.swing;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.util.PBounds;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import javax.swing.JViewport;
import javax.swing.ViewportLayout;

public class PViewport extends JViewport {
  PScrollDirector scrollDirector;
  
  public PViewport() {
    setScrollDirector(createScrollDirector());
  }
  
  protected LayoutManager createLayoutManager() {
    return new PViewportLayout();
  }
  
  protected PScrollDirector createScrollDirector() {
    return new PDefaultScrollDirector();
  }
  
  public void setScrollDirector(PScrollDirector paramPScrollDirector) {
    if (this.scrollDirector != null)
      this.scrollDirector.unInstall(); 
    this.scrollDirector = paramPScrollDirector;
    if (paramPScrollDirector != null)
      this.scrollDirector.install(this, (PCanvas)getView()); 
  }
  
  public PScrollDirector getScrollDirector() {
    return this.scrollDirector;
  }
  
  public void setView(Component paramComponent) {
    if (!(paramComponent instanceof PCanvas))
      throw new UnsupportedOperationException("PViewport only supports ZCanvas"); 
    super.setView(paramComponent);
    if (this.scrollDirector != null)
      this.scrollDirector.install(this, (PCanvas)paramComponent); 
  }
  
  public void setViewPosition(Point paramPoint) {
    if (getView() == null)
      return; 
    double d1 = 0.0D;
    double d2 = 0.0D;
    double d3 = paramPoint.x;
    double d4 = paramPoint.y;
    Point point = getViewPosition();
    if (point != null) {
      d1 = point.getX();
      d2 = point.getY();
    } 
    double d5 = d3;
    double d6 = d4;
    if (d1 != d5 || d2 != d6) {
      this.scrollUnderway = true;
      this.scrollDirector.setViewPosition(d5, d6);
      fireStateChanged();
    } 
  }
  
  public Point getViewPosition() {
    if (this.scrollDirector != null) {
      Dimension dimension = getExtentSize();
      return this.scrollDirector.getViewPosition((Rectangle2D)new PBounds(0.0D, 0.0D, dimension.getWidth(), dimension.getHeight()));
    } 
    return null;
  }
  
  public Dimension getViewSize() {
    Dimension dimension = getExtentSize();
    return this.scrollDirector.getViewSize((Rectangle2D)new PBounds(0.0D, 0.0D, dimension.getWidth(), dimension.getHeight()));
  }
  
  public Dimension getViewSize(Rectangle2D paramRectangle2D) {
    return this.scrollDirector.getViewSize(paramRectangle2D);
  }
  
  public void fireStateChanged() {
    super.fireStateChanged();
  }
  
  public static class PViewportLayout extends ViewportLayout {
    public void layoutContainer(Container param1Container) {
      JViewport jViewport = (JViewport)param1Container;
      Component component = jViewport.getView();
      if (component == null)
        return; 
      Dimension dimension = jViewport.getSize();
      jViewport.setViewSize(dimension);
    }
  }
}
