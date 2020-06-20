package edu.umd.cs.piccolox.nodes;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PAffineTransform;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolo.util.PUtil;
import edu.umd.cs.piccolox.util.LineShape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PLine extends PNode {
  private static final PAffineTransform TEMP_TRANSFORM = new PAffineTransform();
  
  private static final BasicStroke DEFAULT_STROKE = new BasicStroke(1.0F);
  
  private static final Color DEFAULT_STROKE_PAINT = Color.black;
  
  private transient LineShape line;
  
  private transient Stroke stroke = DEFAULT_STROKE;
  
  private Paint strokePaint = DEFAULT_STROKE_PAINT;
  
  public PLine(LineShape paramLineShape) {
    if (paramLineShape == null)
      paramLineShape = new LineShape(null); 
    this.line = paramLineShape;
  }
  
  public PLine() {
    this((LineShape)null);
  }
  
  public PLine(LineShape paramLineShape, Stroke paramStroke) {
    this(paramLineShape);
    this.stroke = paramStroke;
  }
  
  public Paint getStrokePaint() {
    return this.strokePaint;
  }
  
  public void setStrokePaint(Paint paramPaint) {
    Paint paint = this.strokePaint;
    this.strokePaint = paramPaint;
    invalidatePaint();
    firePropertyChange(65536, "strokePaint", paint, this.strokePaint);
  }
  
  public Stroke getStroke() {
    return this.stroke;
  }
  
  public void setStroke(Stroke paramStroke) {
    Stroke stroke = this.stroke;
    this.stroke = paramStroke;
    updateBoundsFromLine();
    invalidatePaint();
    firePropertyChange(131072, "stroke", stroke, this.stroke);
  }
  
  public boolean setBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    if (this.line == null || !super.setBounds(paramDouble1, paramDouble2, paramDouble3, paramDouble4))
      return false; 
    Rectangle2D rectangle2D1 = this.line.getBounds2D();
    Rectangle2D rectangle2D2 = getLineBoundsWithStroke();
    double d = Math.max(rectangle2D2.getWidth() - rectangle2D1.getWidth(), rectangle2D2.getHeight() - rectangle2D1.getHeight());
    paramDouble1 += d / 2.0D;
    paramDouble2 += d / 2.0D;
    paramDouble3 -= d;
    paramDouble4 -= d;
    TEMP_TRANSFORM.setToIdentity();
    TEMP_TRANSFORM.translate(paramDouble1, paramDouble2);
    TEMP_TRANSFORM.scale(paramDouble3 / rectangle2D1.getWidth(), paramDouble4 / rectangle2D1.getHeight());
    TEMP_TRANSFORM.translate(-rectangle2D1.getX(), -rectangle2D1.getY());
    this.line.transformPoints((AffineTransform)TEMP_TRANSFORM);
    return true;
  }
  
  public boolean intersects(Rectangle2D paramRectangle2D) {
    if (super.intersects(paramRectangle2D)) {
      if (this.line.intersects(paramRectangle2D))
        return true; 
      if (this.stroke != null && this.strokePaint != null)
        return this.stroke.createStrokedShape((Shape)this.line).intersects(paramRectangle2D); 
    } 
    return false;
  }
  
  public Rectangle2D getLineBoundsWithStroke() {
    return (this.stroke != null) ? this.stroke.createStrokedShape((Shape)this.line).getBounds2D() : this.line.getBounds2D();
  }
  
  public void updateBoundsFromLine() {
    if (this.line.getPointCount() == 0) {
      resetBounds();
    } else {
      Rectangle2D rectangle2D = getLineBoundsWithStroke();
      super.setBounds(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    } 
  }
  
  protected void paint(PPaintContext paramPPaintContext) {
    Graphics2D graphics2D = paramPPaintContext.getGraphics();
    if (this.stroke != null && this.strokePaint != null) {
      graphics2D.setPaint(this.strokePaint);
      graphics2D.setStroke(this.stroke);
      graphics2D.draw((Shape)this.line);
    } 
  }
  
  public LineShape getLineReference() {
    return this.line;
  }
  
  public int getPointCount() {
    return this.line.getPointCount();
  }
  
  public Point2D getPoint(int paramInt, Point2D paramPoint2D) {
    if (paramPoint2D == null)
      paramPoint2D = new Point2D.Double(); 
    return this.line.getPoint(paramInt, paramPoint2D);
  }
  
  protected void lineChanged() {
    firePropertyChange(262144, "path", null, this.line);
    updateBoundsFromLine();
    invalidatePaint();
  }
  
  public void setPoint(int paramInt, double paramDouble1, double paramDouble2) {
    this.line.setPoint(paramInt, paramDouble1, paramDouble2);
    lineChanged();
  }
  
  public void addPoint(int paramInt, double paramDouble1, double paramDouble2) {
    this.line.addPoint(paramInt, paramDouble1, paramDouble2);
    lineChanged();
  }
  
  public void removePoints(int paramInt1, int paramInt2) {
    this.line.removePoints(paramInt1, paramInt2);
    lineChanged();
  }
  
  public void removeAllPoints() {
    this.line.removePoints(0, this.line.getPointCount());
    lineChanged();
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    PUtil.writeStroke(this.stroke, paramObjectOutputStream);
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    paramObjectInputStream.defaultReadObject();
    this.stroke = PUtil.readStroke(paramObjectInputStream);
  }
}
