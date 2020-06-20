package edu.umd.cs.piccolo.nodes;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PAffineTransform;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolo.util.PUtil;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PPath extends PNode {
  public static final String PROPERTY_STROKE_PAINT = "strokePaint";
  
  public static final int PROPERTY_CODE_STROKE_PAINT = 65536;
  
  public static final String PROPERTY_STROKE = "stroke";
  
  public static final int PROPERTY_CODE_STROKE = 131072;
  
  public static final String PROPERTY_PATH = "path";
  
  public static final int PROPERTY_CODE_PATH = 262144;
  
  private static final Rectangle2D.Float TEMP_RECTANGLE = new Rectangle2D.Float();
  
  private static final Ellipse2D.Float TEMP_ELLIPSE = new Ellipse2D.Float();
  
  private static final PAffineTransform TEMP_TRANSFORM = new PAffineTransform();
  
  private static final BasicStroke DEFAULT_STROKE = new BasicStroke(1.0F);
  
  private static final Color DEFAULT_STROKE_PAINT = Color.black;
  
  private transient GeneralPath path = new GeneralPath();
  
  private transient GeneralPath resizePath;
  
  private transient Stroke stroke = DEFAULT_STROKE;
  
  private transient boolean updatingBoundsFromPath;
  
  private Paint strokePaint = DEFAULT_STROKE_PAINT;
  
  public static PPath createRectangle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    TEMP_RECTANGLE.setFrame(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    PPath pPath = new PPath(TEMP_RECTANGLE);
    pPath.setPaint(Color.white);
    return pPath;
  }
  
  public static PPath createEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    TEMP_ELLIPSE.setFrame(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    PPath pPath = new PPath(TEMP_ELLIPSE);
    pPath.setPaint(Color.white);
    return pPath;
  }
  
  public static PPath createLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    PPath pPath = new PPath();
    pPath.moveTo(paramFloat1, paramFloat2);
    pPath.lineTo(paramFloat3, paramFloat4);
    pPath.setPaint(Color.white);
    return pPath;
  }
  
  public static PPath createPolyline(Point2D[] paramArrayOfPoint2D) {
    PPath pPath = new PPath();
    pPath.setPathToPolyline(paramArrayOfPoint2D);
    pPath.setPaint(Color.white);
    return pPath;
  }
  
  public static PPath createPolyline(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
    PPath pPath = new PPath();
    pPath.setPathToPolyline(paramArrayOffloat1, paramArrayOffloat2);
    pPath.setPaint(Color.white);
    return pPath;
  }
  
  public PPath() {}
  
  public PPath(Shape paramShape) {
    this(paramShape, DEFAULT_STROKE);
  }
  
  public PPath(Shape paramShape, Stroke paramStroke) {
    this();
    this.stroke = paramStroke;
    if (paramShape != null)
      append(paramShape, false); 
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
    updateBoundsFromPath();
    invalidatePaint();
    firePropertyChange(131072, "stroke", stroke, this.stroke);
  }
  
  public void startResizeBounds() {
    this.resizePath = new GeneralPath(this.path);
  }
  
  public void endResizeBounds() {
    this.resizePath = null;
  }
  
  protected void internalUpdateBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    if (this.updatingBoundsFromPath)
      return; 
    if (this.path == null)
      return; 
    if (this.resizePath != null) {
      this.path.reset();
      this.path.append(this.resizePath, false);
    } 
    Rectangle2D rectangle2D1 = this.path.getBounds2D();
    Rectangle2D rectangle2D2 = getPathBoundsWithStroke();
    double d1 = Math.max(rectangle2D2.getWidth() - rectangle2D1.getWidth(), rectangle2D2.getHeight() - rectangle2D1.getHeight());
    paramDouble1 += d1 / 2.0D;
    paramDouble2 += d1 / 2.0D;
    paramDouble3 -= d1;
    paramDouble4 -= d1;
    double d2 = (paramDouble3 == 0.0D || rectangle2D1.getWidth() == 0.0D) ? 1.0D : (paramDouble3 / rectangle2D1.getWidth());
    double d3 = (paramDouble4 == 0.0D || rectangle2D1.getHeight() == 0.0D) ? 1.0D : (paramDouble4 / rectangle2D1.getHeight());
    TEMP_TRANSFORM.setToIdentity();
    TEMP_TRANSFORM.translate(paramDouble1, paramDouble2);
    TEMP_TRANSFORM.scale(d2, d3);
    TEMP_TRANSFORM.translate(-rectangle2D1.getX(), -rectangle2D1.getY());
    this.path.transform((AffineTransform)TEMP_TRANSFORM);
  }
  
  public boolean intersects(Rectangle2D paramRectangle2D) {
    if (super.intersects(paramRectangle2D)) {
      if (getPaint() != null && this.path.intersects(paramRectangle2D))
        return true; 
      if (this.stroke != null && this.strokePaint != null)
        return this.stroke.createStrokedShape(this.path).intersects(paramRectangle2D); 
    } 
    return false;
  }
  
  public Rectangle2D getPathBoundsWithStroke() {
    return (this.stroke != null) ? this.stroke.createStrokedShape(this.path).getBounds2D() : this.path.getBounds2D();
  }
  
  public void updateBoundsFromPath() {
    this.updatingBoundsFromPath = true;
    if (this.path == null) {
      resetBounds();
    } else {
      Rectangle2D rectangle2D = getPathBoundsWithStroke();
      setBounds(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    } 
    this.updatingBoundsFromPath = false;
  }
  
  protected void paint(PPaintContext paramPPaintContext) {
    Paint paint = getPaint();
    Graphics2D graphics2D = paramPPaintContext.getGraphics();
    if (paint != null) {
      graphics2D.setPaint(paint);
      graphics2D.fill(this.path);
    } 
    if (this.stroke != null && this.strokePaint != null) {
      graphics2D.setPaint(this.strokePaint);
      graphics2D.setStroke(this.stroke);
      graphics2D.draw(this.path);
    } 
  }
  
  public GeneralPath getPathReference() {
    return this.path;
  }
  
  public void moveTo(float paramFloat1, float paramFloat2) {
    this.path.moveTo(paramFloat1, paramFloat2);
    firePropertyChange(262144, "path", null, this.path);
    updateBoundsFromPath();
    invalidatePaint();
  }
  
  public void lineTo(float paramFloat1, float paramFloat2) {
    this.path.lineTo(paramFloat1, paramFloat2);
    firePropertyChange(262144, "path", null, this.path);
    updateBoundsFromPath();
    invalidatePaint();
  }
  
  public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this.path.quadTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    firePropertyChange(262144, "path", null, this.path);
    updateBoundsFromPath();
    invalidatePaint();
  }
  
  public void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    this.path.curveTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
    firePropertyChange(262144, "path", null, this.path);
    updateBoundsFromPath();
    invalidatePaint();
  }
  
  public void append(Shape paramShape, boolean paramBoolean) {
    this.path.append(paramShape, paramBoolean);
    firePropertyChange(262144, "path", null, this.path);
    updateBoundsFromPath();
    invalidatePaint();
  }
  
  public void setPathTo(Shape paramShape) {
    this.path.reset();
    append(paramShape, false);
  }
  
  public void setPathToRectangle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    TEMP_RECTANGLE.setFrame(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    setPathTo(TEMP_RECTANGLE);
  }
  
  public void setPathToEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    TEMP_ELLIPSE.setFrame(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    setPathTo(TEMP_ELLIPSE);
  }
  
  public void setPathToPolyline(Point2D[] paramArrayOfPoint2D) {
    this.path.reset();
    this.path.moveTo((float)paramArrayOfPoint2D[0].getX(), (float)paramArrayOfPoint2D[0].getY());
    for (byte b = 1; b < paramArrayOfPoint2D.length; b++)
      this.path.lineTo((float)paramArrayOfPoint2D[b].getX(), (float)paramArrayOfPoint2D[b].getY()); 
    firePropertyChange(262144, "path", null, this.path);
    updateBoundsFromPath();
    invalidatePaint();
  }
  
  public void setPathToPolyline(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
    this.path.reset();
    this.path.moveTo(paramArrayOffloat1[0], paramArrayOffloat2[0]);
    for (byte b = 1; b < paramArrayOffloat1.length; b++)
      this.path.lineTo(paramArrayOffloat1[b], paramArrayOffloat2[b]); 
    firePropertyChange(262144, "path", null, this.path);
    updateBoundsFromPath();
    invalidatePaint();
  }
  
  public void closePath() {
    this.path.closePath();
    firePropertyChange(262144, "path", null, this.path);
    updateBoundsFromPath();
    invalidatePaint();
  }
  
  public void reset() {
    this.path.reset();
    firePropertyChange(262144, "path", null, this.path);
    updateBoundsFromPath();
    invalidatePaint();
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    PUtil.writeStroke(this.stroke, paramObjectOutputStream);
    PUtil.writePath(this.path, paramObjectOutputStream);
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    paramObjectInputStream.defaultReadObject();
    this.stroke = PUtil.readStroke(paramObjectInputStream);
    this.path = PUtil.readPath(paramObjectInputStream);
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("path=" + ((this.path == null) ? "null" : this.path.toString()));
    stringBuffer.append(",stroke=" + ((this.stroke == null) ? "null" : this.stroke.toString()));
    stringBuffer.append(",strokePaint=" + ((this.strokePaint == null) ? "null" : this.strokePaint.toString()));
    stringBuffer.append(',');
    stringBuffer.append(super.paramString());
    return stringBuffer.toString();
  }
}
