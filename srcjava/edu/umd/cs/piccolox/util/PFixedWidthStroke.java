package edu.umd.cs.piccolox.util;

import edu.umd.cs.piccolo.util.PAffineTransform;
import edu.umd.cs.piccolo.util.PDebug;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolo.util.PPickPath;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Arrays;
import sun.dc.path.FastPathProducer;
import sun.dc.path.PathConsumer;
import sun.dc.path.PathException;
import sun.dc.pr.PathDasher;
import sun.dc.pr.PathStroker;

public class PFixedWidthStroke implements Stroke, Serializable {
  private static PAffineTransform TEMP_TRANSFORM = new PAffineTransform();
  
  private static GeneralPath TEMP_PATH = new GeneralPath(1);
  
  public static final int JOIN_MITER = 0;
  
  public static final int JOIN_ROUND = 1;
  
  public static final int JOIN_BEVEL = 2;
  
  public static final int CAP_BUTT = 0;
  
  public static final int CAP_ROUND = 1;
  
  public static final int CAP_SQUARE = 2;
  
  private float width;
  
  private int join;
  
  private int cap;
  
  private float miterlimit;
  
  private float[] dash;
  
  private float dash_phase;
  
  public static final int[] RasterizerCaps = new int[] { 30, 10, 20 };
  
  public static final int[] RasterizerCorners = new int[] { 50, 10, 40 };
  
  public PFixedWidthStroke() {
    this(1.0F, 2, 0, 10.0F, null, 0.0F);
  }
  
  public PFixedWidthStroke(float paramFloat) {
    this(paramFloat, 2, 0, 10.0F, null, 0.0F);
  }
  
  public PFixedWidthStroke(float paramFloat, int paramInt1, int paramInt2) {
    this(paramFloat, paramInt1, paramInt2, 10.0F, null, 0.0F);
  }
  
  public PFixedWidthStroke(float paramFloat1, int paramInt1, int paramInt2, float paramFloat2) {
    this(paramFloat1, paramInt1, paramInt2, paramFloat2, null, 0.0F);
  }
  
  public PFixedWidthStroke(float paramFloat1, int paramInt1, int paramInt2, float paramFloat2, float[] paramArrayOffloat, float paramFloat3) {
    if (paramFloat1 < 0.0F)
      throw new IllegalArgumentException("negative width"); 
    if (paramInt1 != 0 && paramInt1 != 1 && paramInt1 != 2)
      throw new IllegalArgumentException("illegal end cap value"); 
    if (paramInt2 == 0) {
      if (paramFloat2 < 1.0F)
        throw new IllegalArgumentException("miter limit < 1"); 
    } else if (paramInt2 != 1 && paramInt2 != 2) {
      throw new IllegalArgumentException("illegal line join value");
    } 
    if (paramArrayOffloat != null) {
      if (paramFloat3 < 0.0F)
        throw new IllegalArgumentException("negative dash phase"); 
      boolean bool = true;
      for (byte b = 0; b < paramArrayOffloat.length; b++) {
        float f = paramArrayOffloat[b];
        if (f > 0.0D) {
          bool = false;
        } else if (f < 0.0D) {
          throw new IllegalArgumentException("negative dash length");
        } 
      } 
      if (bool)
        throw new IllegalArgumentException("dash lengths all zero"); 
    } 
    this.width = paramFloat1;
    this.cap = paramInt1;
    this.join = paramInt2;
    this.miterlimit = paramFloat2;
    if (paramArrayOffloat != null)
      this.dash = (float[])paramArrayOffloat.clone(); 
    this.dash_phase = paramFloat3;
  }
  
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      cloneNotSupportedException.printStackTrace();
      return null;
    } 
  }
  
  public Shape createStrokedShape(Shape paramShape) {
    PathStroker pathStroker2;
    FillAdapter fillAdapter = new FillAdapter(this);
    PathStroker pathStroker1 = new PathStroker(fillAdapter);
    float f = 1.0F;
    if (PDebug.getProcessingOutput()) {
      if (PPaintContext.CURRENT_PAINT_CONTEXT != null)
        f = this.width / (float)PPaintContext.CURRENT_PAINT_CONTEXT.getScale(); 
    } else if (PPickPath.CURRENT_PICK_PATH != null) {
      f = this.width / (float)PPickPath.CURRENT_PICK_PATH.getScale();
    } 
    Rectangle2D rectangle2D = paramShape.getBounds2D();
    double d = 1.0D;
    if (rectangle2D.getWidth() > rectangle2D.getHeight()) {
      if (rectangle2D.getWidth() != 0.0D)
        d = (rectangle2D.getWidth() - f) / rectangle2D.getWidth(); 
    } else if (rectangle2D.getHeight() != 0.0D) {
      d = (rectangle2D.getHeight() - f) / rectangle2D.getHeight();
    } 
    TEMP_TRANSFORM.setToIdentity();
    TEMP_TRANSFORM.scaleAboutPoint(d, rectangle2D.getCenterX(), rectangle2D.getCenterY());
    pathStroker1.setPenDiameter(f);
    PathIterator pathIterator = paramShape.getPathIterator((AffineTransform)TEMP_TRANSFORM);
    pathStroker1.setPenT4(null);
    pathStroker1.setCaps(RasterizerCaps[this.cap]);
    pathStroker1.setCorners(RasterizerCorners[this.join], this.miterlimit);
    if (this.dash != null) {
      PathDasher pathDasher2 = new PathDasher((PathConsumer)pathStroker1);
      pathDasher2.setDash(this.dash, this.dash_phase);
      pathDasher2.setDashT4(null);
      PathDasher pathDasher1 = pathDasher2;
    } else {
      pathStroker2 = pathStroker1;
    } 
    try {
      pathStroker2.beginPath();
      boolean bool = false;
      float f1 = 0.0F;
      float f2 = 0.0F;
      float[] arrayOfFloat = new float[6];
      while (!pathIterator.isDone()) {
        int i = pathIterator.currentSegment(arrayOfFloat);
        if (bool == true) {
          bool = false;
          if (i != 0)
            pathStroker2.beginSubpath(f1, f2); 
        } 
        switch (i) {
          case 0:
            f1 = arrayOfFloat[0];
            f2 = arrayOfFloat[1];
            pathStroker2.beginSubpath(arrayOfFloat[0], arrayOfFloat[1]);
            break;
          case 1:
            pathStroker2.appendLine(arrayOfFloat[0], arrayOfFloat[1]);
            break;
          case 2:
            pathStroker2.appendQuadratic(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
            break;
          case 3:
            pathStroker2.appendCubic(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
            break;
          case 4:
            pathStroker2.closedSubpath();
            bool = true;
            break;
        } 
        pathIterator.next();
      } 
      pathStroker2.endPath();
    } catch (PathException pathException) {
      throw new InternalError("Unable to Stroke shape (" + pathException.getMessage() + ")");
    } 
    return fillAdapter.getShape();
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof PFixedWidthStroke))
      return false; 
    PFixedWidthStroke pFixedWidthStroke = (PFixedWidthStroke)paramObject;
    if (this.width != pFixedWidthStroke.width)
      return false; 
    if (this.join != pFixedWidthStroke.join)
      return false; 
    if (this.cap != pFixedWidthStroke.cap)
      return false; 
    if (this.miterlimit != pFixedWidthStroke.miterlimit)
      return false; 
    if (this.dash != null) {
      if (this.dash_phase != pFixedWidthStroke.dash_phase)
        return false; 
      if (!Arrays.equals(this.dash, pFixedWidthStroke.dash))
        return false; 
    } else if (pFixedWidthStroke.dash != null) {
      return false;
    } 
    return true;
  }
  
  public float[] getDashArray() {
    return (this.dash == null) ? null : (float[])this.dash.clone();
  }
  
  public float getDashPhase() {
    return this.dash_phase;
  }
  
  public int getEndCap() {
    return this.cap;
  }
  
  public int getLineJoin() {
    return this.join;
  }
  
  public float getLineWidth() {
    return this.width;
  }
  
  public float getMiterLimit() {
    return this.miterlimit;
  }
  
  public int hashCode() {
    int i = Float.floatToIntBits(this.width);
    i = i * 31 + this.join;
    i = i * 31 + this.cap;
    i = i * 31 + Float.floatToIntBits(this.miterlimit);
    if (this.dash != null) {
      i = i * 31 + Float.floatToIntBits(this.dash_phase);
      for (byte b = 0; b < this.dash.length; b++)
        i = i * 31 + Float.floatToIntBits(this.dash[b]); 
    } 
    return i;
  }
  
  public class FillAdapter implements PathConsumer {
    boolean closed;
    
    GeneralPath path;
    
    private final PFixedWidthStroke this$0;
    
    public FillAdapter(PFixedWidthStroke this$0) {
      this.this$0 = this$0;
      this.path = PFixedWidthStroke.TEMP_PATH;
      this.path.reset();
    }
    
    public Shape getShape() {
      return this.path;
    }
    
    public void beginPath() {}
    
    public void beginSubpath(float param1Float1, float param1Float2) {
      if (this.closed) {
        this.path.closePath();
        this.closed = false;
      } 
      this.path.moveTo(param1Float1, param1Float2);
    }
    
    public void appendLine(float param1Float1, float param1Float2) {
      this.path.lineTo(param1Float1, param1Float2);
    }
    
    public void appendQuadratic(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
      this.path.quadTo(param1Float1, param1Float2, param1Float3, param1Float4);
    }
    
    public void appendCubic(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
      this.path.curveTo(param1Float1, param1Float2, param1Float3, param1Float4, param1Float5, param1Float6);
    }
    
    public void closedSubpath() {
      this.closed = true;
    }
    
    public void endPath() {
      if (this.closed) {
        this.path.closePath();
        this.closed = false;
      } 
    }
    
    public void useProxy(FastPathProducer param1FastPathProducer) throws PathException {
      param1FastPathProducer.sendTo(this);
    }
    
    public long getCPathConsumer() {
      return 0L;
    }
    
    public void dispose() {}
    
    public PathConsumer getConsumer() {
      return null;
    }
  }
}
