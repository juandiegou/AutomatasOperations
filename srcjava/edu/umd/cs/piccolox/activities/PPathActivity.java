package edu.umd.cs.piccolox.activities;

import edu.umd.cs.piccolo.activities.PInterpolatingActivity;

public abstract class PPathActivity extends PInterpolatingActivity {
  protected float[] knots;
  
  public PPathActivity(long paramLong1, long paramLong2, float[] paramArrayOffloat) {
    this(paramLong1, paramLong2, 0, 1, paramArrayOffloat);
  }
  
  public PPathActivity(long paramLong1, long paramLong2, int paramInt1, int paramInt2, float[] paramArrayOffloat) {
    super(paramLong1, paramLong2, paramInt1, paramInt2);
    setKnots(paramArrayOffloat);
  }
  
  public int getKnotsLength() {
    return this.knots.length;
  }
  
  public void setKnots(float[] paramArrayOffloat) {
    this.knots = paramArrayOffloat;
  }
  
  public float[] getKnots() {
    return this.knots;
  }
  
  public void setKnot(int paramInt, float paramFloat) {
    this.knots[paramInt] = paramFloat;
  }
  
  public float getKnot(int paramInt) {
    return this.knots[paramInt];
  }
  
  public void setRelativeTargetValue(float paramFloat) {
    byte b;
    for (b = 0; paramFloat > this.knots[b]; b++);
    int i = b - 1;
    int j = b;
    if (i < 0)
      i = 0; 
    if (j > getKnotsLength() - 1)
      j = getKnotsLength() - 1; 
    float f1 = this.knots[j] - this.knots[i];
    float f2 = paramFloat - this.knots[i];
    float f3 = f2;
    if (f1 != 0.0F)
      f3 = f2 / f1; 
    setRelativeTargetValue(f3, i, j);
  }
  
  public abstract void setRelativeTargetValue(float paramFloat, int paramInt1, int paramInt2);
}
