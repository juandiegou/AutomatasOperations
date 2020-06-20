package edu.umd.cs.piccolo.event;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PDimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class PPanEventHandler extends PDragSequenceEventHandler {
  private boolean autopan;
  
  private double minAutopanSpeed = 250.0D;
  
  private double maxAutopanSpeed = 750.0D;
  
  public PPanEventHandler() {
    setEventFilter(new PInputEventFilter(16));
    setAutopan(true);
  }
  
  protected void drag(PInputEvent paramPInputEvent) {
    super.drag(paramPInputEvent);
    pan(paramPInputEvent);
  }
  
  protected void pan(PInputEvent paramPInputEvent) {
    PCamera pCamera = paramPInputEvent.getCamera();
    Point2D point2D = paramPInputEvent.getPosition();
    if (pCamera.getViewBounds().contains(point2D)) {
      PDimension pDimension = paramPInputEvent.getDelta();
      pCamera.translateView(pDimension.getWidth(), pDimension.getHeight());
    } 
  }
  
  public void setAutopan(boolean paramBoolean) {
    this.autopan = paramBoolean;
  }
  
  public boolean getAutopan() {
    return this.autopan;
  }
  
  public void setMinAutopanSpeed(double paramDouble) {
    this.minAutopanSpeed = paramDouble;
  }
  
  public void setMaxAutopanSpeed(double paramDouble) {
    this.maxAutopanSpeed = paramDouble;
  }
  
  protected void dragActivityStep(PInputEvent paramPInputEvent) {
    if (!this.autopan)
      return; 
    PCamera pCamera = paramPInputEvent.getCamera();
    PBounds pBounds = pCamera.getBoundsReference();
    Point2D point2D = paramPInputEvent.getPositionRelativeTo((PNode)pCamera);
    int i = pBounds.outcode(point2D);
    PDimension pDimension = new PDimension();
    if ((i & 0x2) != 0) {
      pDimension.height = validatePanningSpeed(-1.0D - 0.5D * Math.abs(point2D.getY() - pBounds.getY()));
    } else if ((i & 0x8) != 0) {
      pDimension.height = validatePanningSpeed(1.0D + 0.5D * Math.abs(point2D.getY() - pBounds.getY() + pBounds.getHeight()));
    } 
    if ((i & 0x4) != 0) {
      pDimension.width = validatePanningSpeed(1.0D + 0.5D * Math.abs(point2D.getX() - pBounds.getX() + pBounds.getWidth()));
    } else if ((i & 0x1) != 0) {
      pDimension.width = validatePanningSpeed(-1.0D - 0.5D * Math.abs(point2D.getX() - pBounds.getX()));
    } 
    pCamera.localToView((Dimension2D)pDimension);
    if (pDimension.width != 0.0D || pDimension.height != 0.0D)
      pCamera.translateView(pDimension.width, pDimension.height); 
  }
  
  protected double validatePanningSpeed(double paramDouble) {
    double d1 = this.minAutopanSpeed / (1000L / getDragActivity().getStepRate());
    double d2 = this.maxAutopanSpeed / (1000L / getDragActivity().getStepRate());
    boolean bool = (paramDouble < 0.0D) ? true : false;
    paramDouble = Math.abs(paramDouble);
    if (paramDouble < d1)
      paramDouble = d1; 
    if (paramDouble > d2)
      paramDouble = d2; 
    if (bool)
      paramDouble = -paramDouble; 
    return paramDouble;
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("minAutopanSpeed=" + this.minAutopanSpeed);
    stringBuffer.append(",maxAutopanSpeed=" + this.maxAutopanSpeed);
    if (this.autopan)
      stringBuffer.append(",autopan"); 
    stringBuffer.append(',');
    stringBuffer.append(super.paramString());
    return stringBuffer.toString();
  }
}
