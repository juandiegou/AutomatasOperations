package edu.umd.cs.piccolo.event;

import edu.umd.cs.piccolo.PCamera;
import java.awt.geom.Point2D;

public class PZoomEventHandler extends PDragSequenceEventHandler {
  private double minScale = 0.0D;
  
  private double maxScale = Double.MAX_VALUE;
  
  private Point2D viewZoomPoint;
  
  public PZoomEventHandler() {
    setEventFilter(new PInputEventFilter(4));
  }
  
  public double getMinScale() {
    return this.minScale;
  }
  
  public void setMinScale(double paramDouble) {
    this.minScale = paramDouble;
  }
  
  public double getMaxScale() {
    return this.maxScale;
  }
  
  public void setMaxScale(double paramDouble) {
    this.maxScale = paramDouble;
  }
  
  protected void dragActivityFirstStep(PInputEvent paramPInputEvent) {
    this.viewZoomPoint = paramPInputEvent.getPosition();
    super.dragActivityFirstStep(paramPInputEvent);
  }
  
  protected void dragActivityStep(PInputEvent paramPInputEvent) {
    PCamera pCamera = paramPInputEvent.getCamera();
    double d1 = paramPInputEvent.getCanvasPosition().getX() - getMousePressedCanvasPoint().getX();
    double d2 = 1.0D + 0.001D * d1;
    double d3 = pCamera.getViewScale();
    double d4 = d3 * d2;
    if (d4 < this.minScale)
      d2 = this.minScale / d3; 
    if (this.maxScale > 0.0D && d4 > this.maxScale)
      d2 = this.maxScale / d3; 
    pCamera.scaleViewAboutPoint(d2, this.viewZoomPoint.getX(), this.viewZoomPoint.getY());
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("minScale=" + this.minScale);
    stringBuffer.append(",maxScale=" + this.maxScale);
    stringBuffer.append(",viewZoomPoint=" + ((this.viewZoomPoint == null) ? "null" : this.viewZoomPoint.toString()));
    stringBuffer.append(',');
    stringBuffer.append(super.paramString());
    return stringBuffer.toString();
  }
}