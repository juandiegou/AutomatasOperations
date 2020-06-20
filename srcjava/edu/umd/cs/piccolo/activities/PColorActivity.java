package edu.umd.cs.piccolo.activities;

import java.awt.Color;

public class PColorActivity extends PInterpolatingActivity {
  private Color source;
  
  private Color destination;
  
  private Target target;
  
  public PColorActivity(long paramLong1, long paramLong2, Target paramTarget) {
    this(paramLong1, paramLong2, paramTarget, null);
  }
  
  public PColorActivity(long paramLong1, long paramLong2, Target paramTarget, Color paramColor) {
    this(paramLong1, paramLong2, 1, 1, paramTarget, paramColor);
  }
  
  public PColorActivity(long paramLong1, long paramLong2, int paramInt1, int paramInt2, Target paramTarget, Color paramColor) {
    super(paramLong1, paramLong2, paramInt1, paramInt2);
    this.target = paramTarget;
    this.destination = paramColor;
  }
  
  protected boolean isAnimation() {
    return true;
  }
  
  public Color getDestinationColor() {
    return this.destination;
  }
  
  public void setDestinationColor(Color paramColor) {
    this.destination = paramColor;
  }
  
  protected void activityStarted() {
    if (getFirstLoop())
      this.source = this.target.getColor(); 
    super.activityStarted();
  }
  
  public void setRelativeTargetValue(float paramFloat) {
    super.setRelativeTargetValue(paramFloat);
    float f1 = this.source.getRed() + paramFloat * (this.destination.getRed() - this.source.getRed());
    float f2 = this.source.getGreen() + paramFloat * (this.destination.getGreen() - this.source.getGreen());
    float f3 = this.source.getBlue() + paramFloat * (this.destination.getBlue() - this.source.getBlue());
    float f4 = this.source.getAlpha() + paramFloat * (this.destination.getAlpha() - this.source.getAlpha());
    this.target.setColor(new Color(f1 / 255.0F, f2 / 255.0F, f3 / 255.0F, f4 / 255.0F));
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
    void setColor(Color param1Color);
    
    Color getColor();
  }
}