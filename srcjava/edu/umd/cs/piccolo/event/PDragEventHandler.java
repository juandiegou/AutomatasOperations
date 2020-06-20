package edu.umd.cs.piccolo.event;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PDimension;
import java.awt.geom.Dimension2D;

public class PDragEventHandler extends PDragSequenceEventHandler {
  private PNode draggedNode;
  
  private boolean moveToFrontOnPress = false;
  
  public PDragEventHandler() {
    setEventFilter(new PInputEventFilter(16));
  }
  
  protected PNode getDraggedNode() {
    return this.draggedNode;
  }
  
  protected void setDraggedNode(PNode paramPNode) {
    this.draggedNode = paramPNode;
  }
  
  protected boolean shouldStartDragInteraction(PInputEvent paramPInputEvent) {
    return super.shouldStartDragInteraction(paramPInputEvent) ? ((paramPInputEvent.getPickedNode() != paramPInputEvent.getTopCamera())) : false;
  }
  
  protected void startDrag(PInputEvent paramPInputEvent) {
    super.startDrag(paramPInputEvent);
    this.draggedNode = paramPInputEvent.getPickedNode();
    if (this.moveToFrontOnPress)
      this.draggedNode.moveToFront(); 
  }
  
  protected void drag(PInputEvent paramPInputEvent) {
    super.drag(paramPInputEvent);
    PDimension pDimension = paramPInputEvent.getDeltaRelativeTo(this.draggedNode);
    this.draggedNode.localToParent((Dimension2D)pDimension);
    this.draggedNode.offset(pDimension.getWidth(), pDimension.getHeight());
  }
  
  protected void endDrag(PInputEvent paramPInputEvent) {
    super.endDrag(paramPInputEvent);
    this.draggedNode = null;
  }
  
  public boolean getMoveToFrontOnPress() {
    return this.moveToFrontOnPress;
  }
  
  public void setMoveToFrontOnPress(boolean paramBoolean) {
    this.moveToFrontOnPress = paramBoolean;
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(("draggedNode=" + this.draggedNode == null) ? "null" : this.draggedNode.toString());
    if (this.moveToFrontOnPress)
      stringBuffer.append(",moveToFrontOnPress"); 
    stringBuffer.append(',');
    stringBuffer.append(super.paramString());
    return stringBuffer.toString();
  }
}
