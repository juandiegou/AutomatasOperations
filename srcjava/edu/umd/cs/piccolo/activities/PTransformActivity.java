package edu.umd.cs.piccolo.activities;

import edu.umd.cs.piccolo.util.PAffineTransform;
import java.awt.geom.AffineTransform;

public class PTransformActivity extends PInterpolatingActivity {
  private static PAffineTransform STATIC_TRANSFORM = new PAffineTransform();
  
  private double[] source = new double[6];
  
  private double[] destination = new double[6];
  
  private Target target;
  
  public PTransformActivity(long paramLong1, long paramLong2, Target paramTarget) {
    this(paramLong1, paramLong2, paramTarget, null);
  }
  
  public PTransformActivity(long paramLong1, long paramLong2, Target paramTarget, AffineTransform paramAffineTransform) {
    this(paramLong1, paramLong2, 1, 1, paramTarget, paramAffineTransform);
  }
  
  public PTransformActivity(long paramLong1, long paramLong2, int paramInt1, int paramInt2, Target paramTarget, AffineTransform paramAffineTransform) {
    super(paramLong1, paramLong2, paramInt1, paramInt2);
    this.target = paramTarget;
    if (paramAffineTransform != null)
      paramAffineTransform.getMatrix(this.destination); 
  }
  
  protected boolean isAnimation() {
    return true;
  }
  
  public double[] getDestinationTransform() {
    return this.destination;
  }
  
  public void setDestinationTransform(double[] paramArrayOfdouble) {
    this.destination = paramArrayOfdouble;
  }
  
  protected void activityStarted() {
    if (getFirstLoop())
      this.target.getSourceMatrix(this.source); 
    super.activityStarted();
  }
  
  public void setRelativeTargetValue(float paramFloat) {
    super.setRelativeTargetValue(paramFloat);
    STATIC_TRANSFORM.setTransform(this.source[0] + paramFloat * (this.destination[0] - this.source[0]), this.source[1] + paramFloat * (this.destination[1] - this.source[1]), this.source[2] + paramFloat * (this.destination[2] - this.source[2]), this.source[3] + paramFloat * (this.destination[3] - this.source[3]), this.source[4] + paramFloat * (this.destination[4] - this.source[4]), this.source[5] + paramFloat * (this.destination[5] - this.source[5]));
    this.target.setTransform((AffineTransform)STATIC_TRANSFORM);
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("source=" + ((this.source == null) ? "null" : this.source.toString()));
    stringBuffer.append(",destination=" + ((this.destination == null) ? "null" : this.destination.toString()));
    stringBuffer.append(',');
    stringBuffer.append(super.paramString());
    return stringBuffer.toString();
  }
  
  public static interface Target {
    void setTransform(AffineTransform param1AffineTransform);
    
    void getSourceMatrix(double[] param1ArrayOfdouble);
  }
}
