package edu.umd.cs.piccolo.event;

import edu.umd.cs.piccolo.activities.PActivity;
import edu.umd.cs.piccolo.util.PUtil;
import java.awt.geom.Point2D;

public abstract class PDragSequenceEventHandler extends PBasicInputEventHandler {
  private double minDragStartDistance = 0.0D;
  
  private transient boolean isDragging = false;
  
  private transient Point2D mousePressedCanvasPoint;
  
  private transient PActivity dragActivity;
  
  private transient PInputEvent dragEvent;
  
  private transient int sequenceInitiatedButton = 0;
  
  public boolean isDragging() {
    return this.isDragging;
  }
  
  public void setIsDragging(boolean paramBoolean) {
    this.isDragging = paramBoolean;
  }
  
  public double getMinDragStartDistance() {
    return this.minDragStartDistance;
  }
  
  public void setMinDragStartDistance(double paramDouble) {
    this.minDragStartDistance = paramDouble;
  }
  
  public Point2D getMousePressedCanvasPoint() {
    if (this.mousePressedCanvasPoint == null)
      this.mousePressedCanvasPoint = new Point2D.Double(); 
    return this.mousePressedCanvasPoint;
  }
  
  protected void startDrag(PInputEvent paramPInputEvent) {
    this.dragEvent = paramPInputEvent;
    startDragActivity(paramPInputEvent);
    setIsDragging(true);
    paramPInputEvent.getComponent().setInteracting(true);
  }
  
  protected void drag(PInputEvent paramPInputEvent) {
    this.dragEvent = paramPInputEvent;
  }
  
  protected void endDrag(PInputEvent paramPInputEvent) {
    stopDragActivity(paramPInputEvent);
    this.dragEvent = null;
    paramPInputEvent.getComponent().setInteracting(false);
    setIsDragging(false);
  }
  
  protected boolean shouldStartDragInteraction(PInputEvent paramPInputEvent) {
    return (getMousePressedCanvasPoint().distance(paramPInputEvent.getCanvasPosition()) >= getMinDragStartDistance());
  }
  
  protected PActivity getDragActivity() {
    return this.dragActivity;
  }
  
  protected void startDragActivity(PInputEvent paramPInputEvent) {
    this.dragActivity = new PActivity(-1L, PUtil.DEFAULT_ACTIVITY_STEP_RATE);
    this.dragActivity.setDelegate(new PActivity.PActivityDelegate(this) {
          private final PDragSequenceEventHandler this$0;
          
          public void activityStarted(PActivity param1PActivity) {
            this.this$0.dragActivityFirstStep(this.this$0.dragEvent);
          }
          
          public void activityStepped(PActivity param1PActivity) {
            this.this$0.dragActivityStep(this.this$0.dragEvent);
          }
          
          public void activityFinished(PActivity param1PActivity) {
            this.this$0.dragActivityFinalStep(this.this$0.dragEvent);
          }
        });
    paramPInputEvent.getCamera().getRoot().addActivity(this.dragActivity);
  }
  
  protected void stopDragActivity(PInputEvent paramPInputEvent) {
    this.dragActivity.terminate();
    this.dragActivity = null;
  }
  
  protected void dragActivityFirstStep(PInputEvent paramPInputEvent) {}
  
  protected void dragActivityStep(PInputEvent paramPInputEvent) {}
  
  protected void dragActivityFinalStep(PInputEvent paramPInputEvent) {}
  
  public void mousePressed(PInputEvent paramPInputEvent) {
    super.mousePressed(paramPInputEvent);
    if (this.sequenceInitiatedButton == 0) {
      this.sequenceInitiatedButton = paramPInputEvent.getButton();
    } else {
      return;
    } 
    getMousePressedCanvasPoint().setLocation(paramPInputEvent.getCanvasPosition());
    if (!isDragging() && shouldStartDragInteraction(paramPInputEvent))
      startDrag(paramPInputEvent); 
  }
  
  public void mouseDragged(PInputEvent paramPInputEvent) {
    super.mouseDragged(paramPInputEvent);
    if (this.sequenceInitiatedButton != 0) {
      if (!isDragging()) {
        if (shouldStartDragInteraction(paramPInputEvent))
          startDrag(paramPInputEvent); 
        return;
      } 
      drag(paramPInputEvent);
    } 
  }
  
  public void mouseReleased(PInputEvent paramPInputEvent) {
    super.mouseReleased(paramPInputEvent);
    if (this.sequenceInitiatedButton == paramPInputEvent.getButton()) {
      if (isDragging())
        endDrag(paramPInputEvent); 
      this.sequenceInitiatedButton = 0;
    } 
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("minDragStartDistance=" + this.minDragStartDistance);
    stringBuffer.append(",mousePressedCanvasPoint=" + ((this.mousePressedCanvasPoint == null) ? "null" : this.mousePressedCanvasPoint.toString()));
    stringBuffer.append(",sequenceInitiatedButton=" + this.sequenceInitiatedButton);
    if (this.isDragging)
      stringBuffer.append(",dragging"); 
    stringBuffer.append(',');
    stringBuffer.append(super.paramString());
    return stringBuffer.toString();
  }
}
