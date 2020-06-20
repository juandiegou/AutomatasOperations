package edu.umd.cs.piccolo.util;

import edu.umd.cs.piccolo.PCamera;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PPaintContext {
  public static final int LOW_QUALITY_RENDERING = 0;
  
  public static final int HIGH_QUALITY_RENDERING = 1;
  
  public static FontRenderContext RENDER_QUALITY_LOW_FRC = new FontRenderContext(null, false, true);
  
  public static FontRenderContext RENDER_QUALITY_HIGH_FRC = new FontRenderContext(null, true, true);
  
  public static PPaintContext CURRENT_PAINT_CONTEXT;
  
  private static double[] PTS = new double[4];
  
  private Graphics2D graphics;
  
  protected PStack compositeStack;
  
  protected PStack clipStack;
  
  protected PStack localClipStack;
  
  protected PStack cameraStack;
  
  protected PStack transformStack;
  
  protected int renderQuality;
  
  public PPaintContext(Graphics2D paramGraphics2D) {
    this.graphics = paramGraphics2D;
    this.compositeStack = new PStack();
    this.clipStack = new PStack();
    this.localClipStack = new PStack();
    this.cameraStack = new PStack();
    this.transformStack = new PStack();
    this.renderQuality = 1;
    Shape shape = paramGraphics2D.getClip();
    if (shape == null) {
      shape = new PBounds(-1.073741823E9D, -1.073741823E9D, 2.147483647E9D, 2.147483647E9D);
      paramGraphics2D.setClip(shape);
    } 
    this.localClipStack.push(shape.getBounds2D());
    CURRENT_PAINT_CONTEXT = this;
  }
  
  public Graphics2D getGraphics() {
    return this.graphics;
  }
  
  public Rectangle2D getLocalClip() {
    return (Rectangle2D)this.localClipStack.peek();
  }
  
  public double getScale() {
    PTS[0] = 0.0D;
    PTS[1] = 0.0D;
    PTS[2] = 1.0D;
    PTS[3] = 0.0D;
    this.graphics.getTransform().transform(PTS, 0, PTS, 0, 2);
    return Point2D.distance(PTS[0], PTS[1], PTS[2], PTS[3]);
  }
  
  public void pushCamera(PCamera paramPCamera) {
    this.cameraStack.push(paramPCamera);
  }
  
  public void popCamera(PCamera paramPCamera) {
    this.cameraStack.pop();
  }
  
  public PCamera getCamera() {
    return (PCamera)this.cameraStack.peek();
  }
  
  public void pushClip(Shape paramShape) {
    Shape shape = this.graphics.getClip();
    this.clipStack.push(shape);
    this.graphics.clip(paramShape);
    Rectangle2D rectangle2D = paramShape.getBounds2D();
    Rectangle2D.intersect(getLocalClip(), rectangle2D, rectangle2D);
    this.localClipStack.push(rectangle2D);
  }
  
  public void popClip(Shape paramShape) {
    Shape shape = (Shape)this.clipStack.pop();
    this.graphics.setClip(shape);
    this.localClipStack.pop();
  }
  
  public void pushTransparency(float paramFloat) {
    if (paramFloat == 1.0F)
      return; 
    Composite composite = this.graphics.getComposite();
    float f = 1.0F;
    this.compositeStack.push(composite);
    if (composite instanceof AlphaComposite)
      f = ((AlphaComposite)composite).getAlpha(); 
    AlphaComposite alphaComposite = AlphaComposite.getInstance(3, f * paramFloat);
    this.graphics.setComposite(alphaComposite);
  }
  
  public void popTransparency(float paramFloat) {
    if (paramFloat == 1.0F)
      return; 
    Composite composite = (Composite)this.compositeStack.pop();
    this.graphics.setComposite(composite);
  }
  
  public void pushTransform(PAffineTransform paramPAffineTransform) {
    if (paramPAffineTransform == null)
      return; 
    Rectangle2D rectangle2D = (Rectangle2D)getLocalClip().clone();
    paramPAffineTransform.inverseTransform(rectangle2D, rectangle2D);
    this.transformStack.push(this.graphics.getTransform());
    this.localClipStack.push(rectangle2D);
    this.graphics.transform(paramPAffineTransform);
  }
  
  public void popTransform(PAffineTransform paramPAffineTransform) {
    if (paramPAffineTransform == null)
      return; 
    this.graphics.setTransform((AffineTransform)this.transformStack.pop());
    this.localClipStack.pop();
  }
  
  public int getRenderQuality() {
    return this.renderQuality;
  }
  
  public void setRenderQuality(int paramInt) {
    this.renderQuality = paramInt;
    switch (this.renderQuality) {
      case 1:
        this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        this.graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        break;
      case 0:
        this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        this.graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        this.graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        this.graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        break;
    } 
  }
}