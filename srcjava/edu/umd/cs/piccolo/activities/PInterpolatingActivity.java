package edu.umd.cs.piccolo.activities;

public class PInterpolatingActivity extends PActivity {
  public static final int SOURCE_TO_DESTINATION = 1;
  
  public static final int DESTINATION_TO_SOURCE = 2;
  
  public static final int SOURCE_TO_DESTINATION_TO_SOURCE = 3;
  
  private int mode;
  
  private boolean slowInSlowOut;
  
  private int loopCount;
  
  private boolean firstLoop;
  
  public PInterpolatingActivity(long paramLong1, long paramLong2) {
    this(paramLong1, paramLong2, 1, 1);
  }
  
  public PInterpolatingActivity(long paramLong1, long paramLong2, int paramInt1, int paramInt2) {
    this(paramLong1, paramLong2, System.currentTimeMillis(), paramInt1, paramInt2);
  }
  
  public PInterpolatingActivity(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2) {
    super(paramLong1, paramLong2, paramLong3);
    this.loopCount = paramInt1;
    this.mode = paramInt2;
    this.slowInSlowOut = true;
    this.firstLoop = true;
  }
  
  public void setDuration(long paramLong) {
    if (paramLong <= 0L)
      throw new IllegalArgumentException("Duration for PInterpolatingActivity must be greater then 0"); 
    super.setDuration(paramLong);
  }
  
  public int getMode() {
    return this.mode;
  }
  
  public void setMode(int paramInt) {
    this.mode = paramInt;
  }
  
  public int getLoopCount() {
    return this.loopCount;
  }
  
  public void setLoopCount(int paramInt) {
    this.loopCount = paramInt;
  }
  
  public boolean getFirstLoop() {
    return this.firstLoop;
  }
  
  public void setFirstLoop(boolean paramBoolean) {
    this.firstLoop = paramBoolean;
  }
  
  public boolean getSlowInSlowOut() {
    return this.slowInSlowOut;
  }
  
  public void setSlowInSlowOut(boolean paramBoolean) {
    this.slowInSlowOut = paramBoolean;
  }
  
  protected void activityStarted() {
    super.activityStarted();
    setRelativeTargetValueAdjustingForMode(0.0F);
  }
  
  protected void activityStep(long paramLong) {
    super.activityStep(paramLong);
    float f = (float)paramLong / (float)getDuration();
    f = Math.min(1.0F, f);
    f = Math.max(0.0F, f);
    if (getSlowInSlowOut())
      f = computeSlowInSlowOut(f); 
    setRelativeTargetValueAdjustingForMode(f);
  }
  
  protected void activityFinished() {
    setRelativeTargetValueAdjustingForMode(1.0F);
    super.activityFinished();
    PActivityScheduler pActivityScheduler = getActivityScheduler();
    if (this.loopCount > 1) {
      if (this.loopCount != Integer.MAX_VALUE)
        this.loopCount--; 
      this.firstLoop = false;
      setStartTime(pActivityScheduler.getRoot().getGlobalTime());
      pActivityScheduler.addActivity(this);
    } 
  }
  
  public void terminate() {
    this.loopCount = 0;
    super.terminate();
  }
  
  public void setRelativeTargetValue(float paramFloat) {}
  
  public float computeSlowInSlowOut(float paramFloat) {
    if (paramFloat < 0.5D)
      return 2.0F * paramFloat * paramFloat; 
    float f = 1.0F - paramFloat;
    return 1.0F - 2.0F * f * f;
  }
  
  protected void setRelativeTargetValueAdjustingForMode(float paramFloat) {
    switch (this.mode) {
      case 2:
        paramFloat = 1.0F - paramFloat;
        break;
      case 3:
        if (paramFloat <= 0.5D) {
          paramFloat *= 2.0F;
          break;
        } 
        paramFloat = 1.0F - (paramFloat - 0.5F) * 2.0F;
        break;
    } 
    setRelativeTargetValue(paramFloat);
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    if (this.slowInSlowOut)
      stringBuffer.append("slowinSlowOut,"); 
    stringBuffer.append(super.paramString());
    return stringBuffer.toString();
  }
}