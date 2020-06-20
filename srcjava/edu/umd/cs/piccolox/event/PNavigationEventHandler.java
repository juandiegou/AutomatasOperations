package edu.umd.cs.piccolox.event;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.activities.PActivity;
import edu.umd.cs.piccolo.activities.PTransformActivity;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventFilter;
import edu.umd.cs.piccolo.event.PInputEventListener;
import edu.umd.cs.piccolo.util.PAffineTransform;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PDimension;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;

public class PNavigationEventHandler extends PBasicInputEventHandler {
  public static final int NORTH = 0;
  
  public static final int SOUTH = 1;
  
  public static final int EAST = 2;
  
  public static final int WEST = 3;
  
  public static final int IN = 4;
  
  public static final int OUT = 5;
  
  private static Hashtable NODE_TO_GLOBAL_NODE_CENTER_MAPPING = new Hashtable();
  
  private PNode focusNode;
  
  private PActivity navigationActivity;
  
  public PNavigationEventHandler() {
    setEventFilter(new PInputEventFilter(16));
  }
  
  public void keyPressed(PInputEvent paramPInputEvent) {
    PNode pNode = this.focusNode;
    switch (paramPInputEvent.getKeyCode()) {
      case 37:
        moveFocusLeft(paramPInputEvent);
        break;
      case 39:
        moveFocusRight(paramPInputEvent);
        break;
      case 33:
      case 38:
        if (paramPInputEvent.isAltDown()) {
          moveFocusOut(paramPInputEvent);
          break;
        } 
        moveFocusUp(paramPInputEvent);
        break;
      case 34:
      case 40:
        if (paramPInputEvent.isAltDown()) {
          moveFocusIn(paramPInputEvent);
          break;
        } 
        moveFocusDown(paramPInputEvent);
        break;
    } 
    if (this.focusNode != null && pNode != this.focusNode)
      directCameraViewToFocus(paramPInputEvent.getCamera(), this.focusNode, 500); 
  }
  
  public void mousePressed(PInputEvent paramPInputEvent) {
    moveFocusToMouseOver(paramPInputEvent);
    if (this.focusNode != null) {
      directCameraViewToFocus(paramPInputEvent.getCamera(), this.focusNode, 500);
      paramPInputEvent.getInputManager().setKeyboardFocus((PInputEventListener)paramPInputEvent.getPath());
    } 
  }
  
  public void moveFocusDown(PInputEvent paramPInputEvent) {
    PNode pNode = getNeighborInDirection(1);
    if (pNode != null)
      this.focusNode = pNode; 
  }
  
  public void moveFocusIn(PInputEvent paramPInputEvent) {
    PNode pNode = getNeighborInDirection(4);
    if (pNode != null)
      this.focusNode = pNode; 
  }
  
  public void moveFocusLeft(PInputEvent paramPInputEvent) {
    PNode pNode = getNeighborInDirection(3);
    if (pNode != null)
      this.focusNode = pNode; 
  }
  
  public void moveFocusOut(PInputEvent paramPInputEvent) {
    PNode pNode = getNeighborInDirection(5);
    if (pNode != null)
      this.focusNode = pNode; 
  }
  
  public void moveFocusRight(PInputEvent paramPInputEvent) {
    PNode pNode = getNeighborInDirection(2);
    if (pNode != null)
      this.focusNode = pNode; 
  }
  
  public void moveFocusUp(PInputEvent paramPInputEvent) {
    PNode pNode = getNeighborInDirection(0);
    if (pNode != null)
      this.focusNode = pNode; 
  }
  
  public void moveFocusToMouseOver(PInputEvent paramPInputEvent) {
    PNode pNode = paramPInputEvent.getPickedNode();
    if (!(pNode instanceof PCamera))
      this.focusNode = pNode; 
  }
  
  public PNode getNeighborInDirection(int paramInt) {
    if (this.focusNode == null)
      return null; 
    NODE_TO_GLOBAL_NODE_CENTER_MAPPING.clear();
    Point2D point2D = this.focusNode.getGlobalFullBounds().getCenter2D();
    NODE_TO_GLOBAL_NODE_CENTER_MAPPING.put(this.focusNode, point2D);
    List list = getNeighbors();
    sortNodesByDistanceFromPoint(list, point2D);
    for (PNode pNode : list) {
      if (nodeIsNeighborInDirection(pNode, paramInt))
        return pNode; 
    } 
    return null;
  }
  
  public List getNeighbors() {
    ArrayList arrayList = new ArrayList();
    if (this.focusNode == null)
      return arrayList; 
    if (this.focusNode.getParent() == null)
      return arrayList; 
    PNode pNode = this.focusNode.getParent();
    ListIterator listIterator = pNode.getChildrenIterator();
    while (listIterator.hasNext()) {
      PNode pNode1 = listIterator.next();
      if (pNode1 != this.focusNode && pNode1.getPickable())
        arrayList.add(pNode1); 
    } 
    arrayList.add(pNode);
    listIterator = this.focusNode.getChildrenIterator();
    while (listIterator.hasNext())
      arrayList.add(listIterator.next()); 
    return arrayList;
  }
  
  public boolean nodeIsNeighborInDirection(PNode paramPNode, int paramInt) {
    switch (paramInt) {
      case 4:
        return paramPNode.isDescendentOf(this.focusNode);
      case 5:
        return paramPNode.isAncestorOf(this.focusNode);
    } 
    if (paramPNode.isAncestorOf(this.focusNode) || paramPNode.isDescendentOf(this.focusNode))
      return false; 
    Point2D point2D1 = (Point2D)NODE_TO_GLOBAL_NODE_CENTER_MAPPING.get(this.focusNode);
    Point2D point2D2 = (Point2D)NODE_TO_GLOBAL_NODE_CENTER_MAPPING.get(paramPNode);
    double d1 = point2D2.getX() - point2D1.getX() + point2D1.getY();
    double d2 = -point2D2.getX() + point2D1.getX() + point2D1.getY();
    switch (paramInt) {
      case 0:
        if (point2D2.getY() < point2D1.getY() && point2D2.getY() < d1 && point2D2.getY() < d2)
          return true; 
        break;
      case 2:
        if (point2D2.getX() > point2D1.getX() && point2D2.getY() < d1 && point2D2.getY() > d2)
          return true; 
        break;
      case 1:
        if (point2D2.getY() > point2D1.getY() && point2D2.getY() > d1 && point2D2.getY() > d2)
          return true; 
        break;
      case 3:
        if (point2D2.getX() < point2D1.getX() && point2D2.getY() > d1 && point2D2.getY() < d2)
          return true; 
        break;
    } 
    return false;
  }
  
  public void sortNodesByDistanceFromPoint(List paramList, Point2D paramPoint2D) {
    Collections.sort(paramList, new Comparator(this, paramPoint2D) {
          private final Point2D val$aPoint;
          
          private final PNavigationEventHandler this$0;
          
          public int compare(Object param1Object1, Object param1Object2) {
            PNode pNode1 = (PNode)param1Object1;
            PNode pNode2 = (PNode)param1Object2;
            Point2D point2D1 = pNode1.getGlobalFullBounds().getCenter2D();
            Point2D point2D2 = pNode2.getGlobalFullBounds().getCenter2D();
            PNavigationEventHandler.NODE_TO_GLOBAL_NODE_CENTER_MAPPING.put(pNode1, point2D1);
            PNavigationEventHandler.NODE_TO_GLOBAL_NODE_CENTER_MAPPING.put(pNode2, point2D2);
            double d1 = this.val$aPoint.distance(point2D1);
            double d2 = this.val$aPoint.distance(point2D2);
            return (d1 < d2) ? -1 : ((d1 == d2) ? 0 : 1);
          }
        });
  }
  
  protected PActivity animateCameraViewTransformTo(PCamera paramPCamera, AffineTransform paramAffineTransform, int paramInt) {
    boolean bool = false;
    if (this.navigationActivity != null) {
      this.navigationActivity.terminate();
      bool = true;
    } 
    if (paramInt == 0) {
      paramPCamera.setViewTransform(paramAffineTransform);
      return null;
    } 
    PAffineTransform pAffineTransform = paramPCamera.getViewTransformReference();
    if (!pAffineTransform.equals(paramAffineTransform)) {
      this.navigationActivity = (PActivity)paramPCamera.animateViewToTransform(paramAffineTransform, paramInt);
      ((PTransformActivity)this.navigationActivity).setSlowInSlowOut(!bool);
      return this.navigationActivity;
    } 
    return null;
  }
  
  public PActivity directCameraViewToFocus(PCamera paramPCamera, PNode paramPNode, int paramInt) {
    PAffineTransform pAffineTransform1 = paramPCamera.getViewTransform();
    PDimension pDimension = new PDimension(1.0D, 0.0D);
    this.focusNode.globalToLocal((Dimension2D)pDimension);
    double d = pDimension.getWidth() / paramPCamera.getViewScale();
    Point2D point2D = this.focusNode.getGlobalFullBounds().getCenter2D();
    if (d != 1.0D)
      paramPCamera.scaleViewAboutPoint(d, point2D.getX(), point2D.getY()); 
    paramPCamera.animateViewToPanToBounds((Rectangle2D)this.focusNode.getGlobalFullBounds(), 0L);
    PAffineTransform pAffineTransform2 = paramPCamera.getViewTransform();
    paramPCamera.setViewTransform((AffineTransform)pAffineTransform1);
    return animateCameraViewTransformTo(paramPCamera, (AffineTransform)pAffineTransform2, paramInt);
  }
  
  protected void fillViewWhiteSpace(PCamera paramPCamera) {
    PBounds pBounds1 = paramPCamera.getRoot().getFullBoundsReference();
    PBounds pBounds2 = paramPCamera.getViewBounds();
    if (!pBounds1.contains((Rectangle2D)paramPCamera.getViewBounds())) {
      paramPCamera.animateViewToPanToBounds((Rectangle2D)pBounds1, 0L);
      paramPCamera.animateViewToPanToBounds((Rectangle2D)this.focusNode.getGlobalFullBounds(), 0L);
      double d1 = 0.0D;
      double d2 = 0.0D;
      pBounds2 = paramPCamera.getViewBounds();
      if (pBounds2.getWidth() > pBounds1.getWidth()) {
        double d3 = pBounds1.getMinX() + pBounds1.getWidth() / 2.0D;
        double d4 = pBounds2.getMinX() + pBounds2.getWidth() / 2.0D;
        d1 = d4 - d3;
      } 
      if (pBounds2.getHeight() > pBounds1.getHeight()) {
        double d3 = pBounds1.getMinY() + pBounds1.getHeight() / 2.0D;
        double d4 = pBounds2.getMinY() + pBounds2.getHeight() / 2.0D;
        d2 = d4 - d3;
      } 
      paramPCamera.translateView(d1, d2);
    } 
  }
}
