package edu.umd.cs.piccolox.event;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventFilter;
import edu.umd.cs.piccolo.util.PBounds;
import java.awt.geom.Rectangle2D;

public class PZoomToEventHandler extends PBasicInputEventHandler {
  public PZoomToEventHandler() {
    setEventFilter(new PInputEventFilter(16));
  }
  
  public void mousePressed(PInputEvent paramPInputEvent) {
    zoomTo(paramPInputEvent);
  }
  
  protected void zoomTo(PInputEvent paramPInputEvent) {
    PBounds pBounds;
    PNode pNode = paramPInputEvent.getPickedNode();
    if (pNode instanceof PCamera) {
      PCamera pCamera = (PCamera)pNode;
      pBounds = pCamera.getUnionOfLayerFullBounds();
    } else {
      pBounds = pNode.getGlobalFullBounds();
    } 
    paramPInputEvent.getCamera().animateViewToCenterBounds((Rectangle2D)pBounds, true, 500L);
  }
}
