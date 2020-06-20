package edu.umd.cs.piccolox.nodes;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolox.PFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

public class P3DRect extends PNode {
  private Color topLeftOuterColor;
  
  private Color topLeftInnerColor;
  
  private Color bottomRightInnerColor;
  
  private Color bottomRightOuterColor;
  
  private GeneralPath path = new GeneralPath();
  
  private Stroke stroke = new BasicStroke(0.0F);
  
  private boolean raised = true;
  
  public P3DRect() {}
  
  public P3DRect(Rectangle2D paramRectangle2D) {
    this(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
  }
  
  public P3DRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    this();
    setBounds(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
  }
  
  public void setRaised(boolean paramBoolean) {
    this.raised = paramBoolean;
    setPaint(getPaint());
  }
  
  public boolean getRaised() {
    return this.raised;
  }
  
  protected void paint(PPaintContext paramPPaintContext) {
    Graphics2D graphics2D = paramPPaintContext.getGraphics();
    double d1 = getX();
    double d2 = getY();
    double d3 = getWidth();
    double d4 = getHeight();
    double d5 = graphics2D.getTransform().getScaleX();
    double d6 = graphics2D.getTransform().getScaleY();
    double d7 = (float)(1.0D / d5);
    double d8 = (float)(1.0D / d6);
    PBounds pBounds = getBounds();
    graphics2D.setPaint(getPaint());
    graphics2D.fill((Shape)pBounds);
    graphics2D.setStroke(this.stroke);
    this.path.reset();
    this.path.moveTo((float)(d1 + d3), (float)d2);
    this.path.lineTo((float)d1, (float)d2);
    this.path.lineTo((float)d1, (float)(d2 + d4));
    graphics2D.setPaint(this.topLeftOuterColor);
    graphics2D.draw(this.path);
    this.path.reset();
    this.path.moveTo((float)(d1 + d3), (float)(d2 + d8));
    this.path.lineTo((float)(d1 + d7), (float)(d2 + d8));
    this.path.lineTo((float)(d1 + d7), (float)(d2 + d4));
    graphics2D.setPaint(this.topLeftInnerColor);
    graphics2D.draw(this.path);
    this.path.reset();
    this.path.moveTo((float)(d1 + d3), (float)d2);
    this.path.lineTo((float)(d1 + d3), (float)(d2 + d4));
    this.path.lineTo((float)d1, (float)(d2 + d4));
    graphics2D.setPaint(this.bottomRightOuterColor);
    graphics2D.draw(this.path);
    this.path.reset();
    this.path.moveTo((float)(d1 + d3 - d7), (float)(d2 + d8));
    this.path.lineTo((float)(d1 + d3 - d7), (float)(d2 + d4 - d8));
    this.path.lineTo((float)d1, (float)(d2 + d4 - d8));
    graphics2D.setPaint(this.bottomRightInnerColor);
    graphics2D.draw(this.path);
  }
  
  public void setPaint(Paint paramPaint) {
    super.setPaint(paramPaint);
    if (paramPaint instanceof Color) {
      Color color = (Color)paramPaint;
      if (this.raised) {
        this.topLeftOuterColor = color.brighter();
        this.topLeftInnerColor = this.topLeftOuterColor.brighter();
        this.bottomRightInnerColor = color.darker();
        this.bottomRightOuterColor = this.bottomRightInnerColor.darker();
      } else {
        this.topLeftOuterColor = color.darker();
        this.topLeftInnerColor = this.topLeftOuterColor.darker();
        this.bottomRightInnerColor = color.brighter();
        this.bottomRightOuterColor = this.bottomRightInnerColor.brighter();
      } 
    } else {
      this.topLeftOuterColor = null;
      this.topLeftInnerColor = null;
      this.bottomRightInnerColor = null;
      this.bottomRightOuterColor = null;
    } 
  }
  
  public static void main(String[] paramArrayOfString) {
    new PFrame() {
        public void initialize() {
          getCanvas().setDefaultRenderQuality(0);
          P3DRect p3DRect1 = new P3DRect(50.0D, 50.0D, 100.0D, 100.0D);
          p3DRect1.setPaint(new Color(239, 235, 222));
          P3DRect p3DRect2 = new P3DRect(50.0D, 50.0D, 100.0D, 100.0D);
          p3DRect2.setPaint(new Color(239, 235, 222));
          p3DRect2.translate(110.0D, 0.0D);
          p3DRect2.setRaised(false);
          getCanvas().getLayer().addChild(p3DRect1);
          getCanvas().getLayer().addChild(p3DRect2);
        }
      };
  }
}
