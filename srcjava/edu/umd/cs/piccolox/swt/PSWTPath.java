package edu.umd.cs.piccolox.swt;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PAffineTransform;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPaintContext;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class PSWTPath extends PNode {
  public static final String PROPERTY_SHAPE = "shape";
  
  private static final double BOUNDS_TOLERANCE = 0.01D;
  
  private static final Rectangle2D.Float TEMP_RECTANGLE = new Rectangle2D.Float();
  
  private static final Ellipse2D.Float TEMP_ELLIPSE = new Ellipse2D.Float();
  
  private static final PAffineTransform TEMP_TRANSFORM = new PAffineTransform();
  
  private static final Color DEFAULT_STROKE_PAINT = Color.black;
  
  private static final BasicStroke BASIC_STROKE = new BasicStroke();
  
  private static final float PEN_WIDTH = 1.0F;
  
  private Paint strokePaint = DEFAULT_STROKE_PAINT;
  
  boolean updatingBoundsFromPath;
  
  Shape origShape;
  
  Shape shape;
  
  PAffineTransform internalXForm;
  
  AffineTransform inverseXForm;
  
  double[] shapePts;
  
  public static PSWTPath createRectangle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    TEMP_RECTANGLE.setFrame(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    PSWTPath pSWTPath = new PSWTPath(TEMP_RECTANGLE);
    pSWTPath.setPaint(Color.white);
    return pSWTPath;
  }
  
  public static PSWTPath createEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    TEMP_ELLIPSE.setFrame(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    PSWTPath pSWTPath = new PSWTPath(TEMP_ELLIPSE);
    pSWTPath.setPaint(Color.white);
    return pSWTPath;
  }
  
  public static PSWTPath createPolyline(Point2D[] paramArrayOfPoint2D) {
    PSWTPath pSWTPath = new PSWTPath();
    pSWTPath.setPathToPolyline(paramArrayOfPoint2D);
    pSWTPath.setPaint(Color.white);
    return pSWTPath;
  }
  
  public static PSWTPath createPolyline(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
    PSWTPath pSWTPath = new PSWTPath();
    pSWTPath.setPathToPolyline(paramArrayOffloat1, paramArrayOffloat2);
    pSWTPath.setPaint(Color.white);
    return pSWTPath;
  }
  
  public PSWTPath() {}
  
  public PSWTPath(Shape paramShape) {
    this();
    setShape(paramShape);
  }
  
  public Paint getStrokePaint() {
    return this.strokePaint;
  }
  
  public void setStrokeColor(Paint paramPaint) {
    Paint paint = this.strokePaint;
    this.strokePaint = paramPaint;
    invalidatePaint();
    firePropertyChange(65536, "strokePaint", paint, this.strokePaint);
  }
  
  protected void internalUpdateBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    if (this.updatingBoundsFromPath)
      return; 
    if (this.origShape == null)
      return; 
    Rectangle2D rectangle2D = this.origShape.getBounds2D();
    if (Math.abs(paramDouble1 - rectangle2D.getX()) / paramDouble1 < 0.01D && Math.abs(paramDouble2 - rectangle2D.getY()) / paramDouble2 < 0.01D && Math.abs(paramDouble3 - rectangle2D.getWidth()) / paramDouble3 < 0.01D && Math.abs(paramDouble4 - rectangle2D.getHeight()) / paramDouble4 < 0.01D)
      return; 
    if (this.internalXForm == null)
      this.internalXForm = new PAffineTransform(); 
    this.internalXForm.setToIdentity();
    this.internalXForm.translate(paramDouble1, paramDouble2);
    this.internalXForm.scale(paramDouble3 / rectangle2D.getWidth(), paramDouble4 / rectangle2D.getHeight());
    this.internalXForm.translate(-rectangle2D.getX(), -rectangle2D.getY());
    try {
      this.inverseXForm = this.internalXForm.createInverse();
    } catch (Exception exception) {}
  }
  
  public boolean intersects(Rectangle2D paramRectangle2D) {
    if (super.intersects(paramRectangle2D)) {
      PBounds pBounds;
      if (this.internalXForm != null) {
        pBounds = new PBounds(paramRectangle2D);
        this.internalXForm.inverseTransform((Rectangle2D)pBounds, (Rectangle2D)pBounds);
      } 
      if (getPaint() != null && this.shape.intersects((Rectangle2D)pBounds))
        return true; 
      if (this.strokePaint != null)
        return BASIC_STROKE.createStrokedShape(this.shape).intersects((Rectangle2D)pBounds); 
    } 
    return false;
  }
  
  public void updateBoundsFromPath() {
    this.updatingBoundsFromPath = true;
    if (this.origShape == null) {
      resetBounds();
    } else {
      Rectangle2D rectangle2D = this.origShape.getBounds2D();
      setBounds(rectangle2D.getX() - 1.0D, rectangle2D.getY() - 1.0D, rectangle2D.getWidth() + 2.0D, rectangle2D.getHeight() + 2.0D);
    } 
    this.updatingBoundsFromPath = false;
  }
  
  protected void paint(PPaintContext paramPPaintContext) {
    Paint paint = getPaint();
    SWTGraphics2D sWTGraphics2D = (SWTGraphics2D)paramPPaintContext.getGraphics();
    if (this.internalXForm != null)
      sWTGraphics2D.transform((AffineTransform)this.internalXForm); 
    if (paint != null) {
      sWTGraphics2D.setBackground((Color)paint);
      double d = sWTGraphics2D.getTransformedLineWidth();
      if (this.shape instanceof Rectangle2D) {
        sWTGraphics2D.fillRect(this.shapePts[0] + d / 2.0D, this.shapePts[1] + d / 2.0D, this.shapePts[2] - d, this.shapePts[3] - d);
      } else if (this.shape instanceof Ellipse2D) {
        sWTGraphics2D.fillOval(this.shapePts[0] + d / 2.0D, this.shapePts[1] + d / 2.0D, this.shapePts[2] - d, this.shapePts[3] - d);
      } else if (this.shape instanceof Arc2D) {
        sWTGraphics2D.fillArc(this.shapePts[0] + d / 2.0D, this.shapePts[1] + d / 2.0D, this.shapePts[2] - d, this.shapePts[3] - d, this.shapePts[4], this.shapePts[5]);
      } else if (this.shape instanceof RoundRectangle2D) {
        sWTGraphics2D.fillRoundRect(this.shapePts[0] + d / 2.0D, this.shapePts[1] + d / 2.0D, this.shapePts[2] - d, this.shapePts[3] - d, this.shapePts[4], this.shapePts[5]);
      } else {
        sWTGraphics2D.fillPolygon(this.shapePts);
      } 
    } 
    if (this.strokePaint != null) {
      sWTGraphics2D.setColor((Color)this.strokePaint);
      double d = sWTGraphics2D.getTransformedLineWidth();
      if (this.shape instanceof Rectangle2D) {
        sWTGraphics2D.drawRect(this.shapePts[0] + d / 2.0D, this.shapePts[1] + d / 2.0D, this.shapePts[2] - d, this.shapePts[3] - d);
      } else if (this.shape instanceof Ellipse2D) {
        sWTGraphics2D.drawOval(this.shapePts[0] + d / 2.0D, this.shapePts[1] + d / 2.0D, this.shapePts[2] - d, this.shapePts[3] - d);
      } else if (this.shape instanceof Arc2D) {
        sWTGraphics2D.drawArc(this.shapePts[0] + d / 2.0D, this.shapePts[1] + d / 2.0D, this.shapePts[2] - d, this.shapePts[3] - d, this.shapePts[4], this.shapePts[5]);
      } else if (this.shape instanceof RoundRectangle2D) {
        sWTGraphics2D.drawRoundRect(this.shapePts[0] + d / 2.0D, this.shapePts[1] + d / 2.0D, this.shapePts[2] - d, this.shapePts[3] - d, this.shapePts[4], this.shapePts[5]);
      } else {
        sWTGraphics2D.drawPolyline(this.shapePts);
      } 
    } 
    if (this.inverseXForm != null)
      sWTGraphics2D.transform(this.inverseXForm); 
  }
  
  public void setShape(Shape paramShape) {
    this.shape = cloneShape(paramShape);
    this.origShape = this.shape;
    updateShapePoints(paramShape);
    firePropertyChange(262144, "path", null, this.shape);
    updateBoundsFromPath();
    invalidatePaint();
  }
  
  public void updateShapePoints(Shape paramShape) {
    if (paramShape instanceof Rectangle2D) {
      if (this.shapePts == null || this.shapePts.length < 4)
        this.shapePts = new double[4]; 
      this.shapePts[0] = ((Rectangle2D)this.shape).getX();
      this.shapePts[1] = ((Rectangle2D)this.shape).getY();
      this.shapePts[2] = ((Rectangle2D)this.shape).getWidth();
      this.shapePts[3] = ((Rectangle2D)this.shape).getHeight();
    } else if (paramShape instanceof Ellipse2D) {
      if (this.shapePts == null || this.shapePts.length < 4)
        this.shapePts = new double[4]; 
      this.shapePts[0] = ((Ellipse2D)this.shape).getX();
      this.shapePts[1] = ((Ellipse2D)this.shape).getY();
      this.shapePts[2] = ((Ellipse2D)this.shape).getWidth();
      this.shapePts[3] = ((Ellipse2D)this.shape).getHeight();
    } else if (paramShape instanceof Arc2D) {
      if (this.shapePts == null || this.shapePts.length < 6)
        this.shapePts = new double[6]; 
      this.shapePts[0] = ((Arc2D)this.shape).getX();
      this.shapePts[1] = ((Arc2D)this.shape).getY();
      this.shapePts[2] = ((Arc2D)this.shape).getWidth();
      this.shapePts[3] = ((Arc2D)this.shape).getHeight();
      this.shapePts[4] = ((Arc2D)this.shape).getAngleStart();
      this.shapePts[5] = ((Arc2D)this.shape).getAngleExtent();
    } else if (paramShape instanceof RoundRectangle2D) {
      if (this.shapePts == null || this.shapePts.length < 6)
        this.shapePts = new double[6]; 
      this.shapePts[0] = ((RoundRectangle2D)this.shape).getX();
      this.shapePts[1] = ((RoundRectangle2D)this.shape).getY();
      this.shapePts[2] = ((RoundRectangle2D)this.shape).getWidth();
      this.shapePts[3] = ((RoundRectangle2D)this.shape).getHeight();
      this.shapePts[4] = ((RoundRectangle2D)this.shape).getArcWidth();
      this.shapePts[5] = ((RoundRectangle2D)this.shape).getArcHeight();
    } else {
      this.shapePts = SWTShapeManager.shapeToPolyline(this.shape);
    } 
  }
  
  public Shape cloneShape(Shape paramShape) {
    if (paramShape instanceof Rectangle2D)
      return (Shape)new PBounds((Rectangle2D)paramShape); 
    if (paramShape instanceof Ellipse2D) {
      Ellipse2D ellipse2D = (Ellipse2D)paramShape;
      return new Ellipse2D.Double(ellipse2D.getX(), ellipse2D.getY(), ellipse2D.getWidth(), ellipse2D.getHeight());
    } 
    if (paramShape instanceof Arc2D) {
      Arc2D arc2D = (Arc2D)paramShape;
      return new Arc2D.Double(arc2D.getX(), arc2D.getY(), arc2D.getWidth(), arc2D.getHeight(), arc2D.getAngleStart(), arc2D.getAngleExtent(), arc2D.getArcType());
    } 
    if (paramShape instanceof RoundRectangle2D) {
      RoundRectangle2D roundRectangle2D = (RoundRectangle2D)paramShape;
      return new RoundRectangle2D.Double(roundRectangle2D.getX(), roundRectangle2D.getY(), roundRectangle2D.getWidth(), roundRectangle2D.getHeight(), roundRectangle2D.getArcWidth(), roundRectangle2D.getArcHeight());
    } 
    (new Exception()).printStackTrace();
    GeneralPath generalPath = new GeneralPath();
    generalPath.append(paramShape, false);
    return generalPath;
  }
  
  public void setPathToRectangle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    TEMP_RECTANGLE.setFrame(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    setShape(TEMP_RECTANGLE);
  }
  
  public void setPathToEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    TEMP_ELLIPSE.setFrame(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    setShape(TEMP_ELLIPSE);
  }
  
  public void setPathToPolyline(Point2D[] paramArrayOfPoint2D) {
    GeneralPath generalPath = new GeneralPath();
    generalPath.reset();
    generalPath.moveTo((float)paramArrayOfPoint2D[0].getX(), (float)paramArrayOfPoint2D[0].getY());
    for (byte b = 1; b < paramArrayOfPoint2D.length; b++)
      generalPath.lineTo((float)paramArrayOfPoint2D[b].getX(), (float)paramArrayOfPoint2D[b].getY()); 
    setShape(generalPath);
  }
  
  public void setPathToPolyline(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
    GeneralPath generalPath = new GeneralPath();
    generalPath.reset();
    generalPath.moveTo(paramArrayOffloat1[0], paramArrayOffloat2[0]);
    for (byte b = 1; b < paramArrayOffloat1.length; b++)
      generalPath.lineTo(paramArrayOffloat1[b], paramArrayOffloat2[b]); 
    setShape(generalPath);
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("path=" + ((this.shape == null) ? "null" : this.shape.toString()));
    stringBuffer.append(",strokePaint=" + ((this.strokePaint == null) ? "null" : this.strokePaint.toString()));
    stringBuffer.append(',');
    stringBuffer.append(super.paramString());
    return stringBuffer.toString();
  }
}
