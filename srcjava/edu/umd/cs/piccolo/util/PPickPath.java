package edu.umd.cs.piccolo.util;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventListener;
import java.awt.geom.Dimension2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import javax.swing.event.EventListenerList;

public class PPickPath implements PInputEventListener {
  public static PPickPath CURRENT_PICK_PATH;
  
  private static double[] PTS = new double[4];
  
  private PStack nodeStack;
  
  private PStack transformStack;
  
  private PStack pickBoundsStack = new PStack();
  
  private PCamera topCamera;
  
  private PCamera bottomCamera;
  
  private HashMap excludedNodes;
  
  public PPickPath(PCamera paramPCamera, PBounds paramPBounds) {
    this.topCamera = paramPCamera;
    this.nodeStack = new PStack();
    this.transformStack = new PStack();
    this.pickBoundsStack.push(paramPBounds);
    CURRENT_PICK_PATH = this;
  }
  
  public PBounds getPickBounds() {
    return (PBounds)this.pickBoundsStack.peek();
  }
  
  public boolean acceptsNode(PNode paramPNode) {
    return (this.excludedNodes != null) ? (!this.excludedNodes.containsKey(paramPNode)) : true;
  }
  
  public void pushNode(PNode paramPNode) {
    this.nodeStack.push(paramPNode);
  }
  
  public void popNode(PNode paramPNode) {
    this.nodeStack.pop();
  }
  
  public PNode getPickedNode() {
    return (PNode)this.nodeStack.peek();
  }
  
  public PNode nextPickedNode() {
    PNode pNode = getPickedNode();
    if (pNode == this.topCamera)
      return null; 
    if (this.excludedNodes == null)
      this.excludedNodes = new HashMap(); 
    this.excludedNodes.put(pNode, pNode);
    E e = this.pickBoundsStack.get(0);
    this.pickBoundsStack = new PStack();
    this.nodeStack = new PStack();
    this.transformStack = new PStack();
    this.pickBoundsStack = new PStack();
    this.pickBoundsStack.push(e);
    this.topCamera.fullPick(this);
    if (getNodeStackReference().size() == 0) {
      pushNode((PNode)this.topCamera);
      pushTransform(this.topCamera.getTransformReference(false));
    } 
    return getPickedNode();
  }
  
  public PCamera getTopCamera() {
    return this.topCamera;
  }
  
  public PCamera getBottomCamera() {
    if (this.bottomCamera == null)
      for (int i = this.nodeStack.size() - 1; i >= 0; i--) {
        PNode pNode = (PNode)this.nodeStack.get(i);
        if (pNode instanceof PCamera) {
          this.bottomCamera = (PCamera)pNode;
          return this.bottomCamera;
        } 
      }  
    return this.bottomCamera;
  }
  
  public PStack getNodeStackReference() {
    return this.nodeStack;
  }
  
  public double getScale() {
    PTS[0] = 0.0D;
    PTS[1] = 0.0D;
    PTS[2] = 1.0D;
    PTS[3] = 0.0D;
    int i = this.transformStack.size();
    for (byte b = 0; b < i; b++) {
      PAffineTransform pAffineTransform = ((PTuple)this.transformStack.get(b)).transform;
      if (pAffineTransform != null)
        pAffineTransform.transform(PTS, 0, PTS, 0, 2); 
    } 
    return Point2D.distance(PTS[0], PTS[1], PTS[2], PTS[3]);
  }
  
  public void pushTransform(PAffineTransform paramPAffineTransform) {
    this.transformStack.push(new PTuple(getPickedNode(), paramPAffineTransform));
    if (paramPAffineTransform != null) {
      Rectangle2D rectangle2D = (Rectangle2D)getPickBounds().clone();
      paramPAffineTransform.inverseTransform(rectangle2D, rectangle2D);
      this.pickBoundsStack.push(rectangle2D);
    } 
  }
  
  public void popTransform(PAffineTransform paramPAffineTransform) {
    this.transformStack.pop();
    if (paramPAffineTransform != null)
      this.pickBoundsStack.pop(); 
  }
  
  public PAffineTransform getPathTransformTo(PNode paramPNode) {
    PAffineTransform pAffineTransform = new PAffineTransform();
    int i = this.transformStack.size();
    for (byte b = 0; b < i; b++) {
      PTuple pTuple = (PTuple)this.transformStack.get(b);
      if (pTuple.transform != null)
        pAffineTransform.concatenate(pTuple.transform); 
      if (paramPNode == pTuple.node)
        return pAffineTransform; 
    } 
    throw new RuntimeException("Node could not be found on pick path");
  }
  
  public void processEvent(PInputEvent paramPInputEvent, int paramInt) {
    paramPInputEvent.setPath(this);
    for (int i = this.nodeStack.size() - 1; i >= 0; i--) {
      PNode pNode = (PNode)this.nodeStack.get(i);
      EventListenerList eventListenerList = pNode.getListenerList();
      if (eventListenerList != null) {
        Object[] arrayOfObject = eventListenerList.getListeners((Class)PInputEventListener.class);
        for (byte b = 0; b < arrayOfObject.length; b++) {
          PInputEventListener pInputEventListener = (PInputEventListener)arrayOfObject[b];
          pInputEventListener.processEvent(paramPInputEvent, paramInt);
        } 
      } 
    } 
  }
  
  public Point2D canvasToLocal(Point2D paramPoint2D, PNode paramPNode) {
    try {
      return getPathTransformTo(paramPNode).inverseTransform(paramPoint2D, paramPoint2D);
    } catch (NoninvertibleTransformException noninvertibleTransformException) {
      noninvertibleTransformException.printStackTrace();
      return null;
    } 
  }
  
  public Dimension2D canvasToLocal(Dimension2D paramDimension2D, PNode paramPNode) {
    return getPathTransformTo(paramPNode).inverseTransform(paramDimension2D, paramDimension2D);
  }
  
  public Rectangle2D canvasToLocal(Rectangle2D paramRectangle2D, PNode paramPNode) {
    return getPathTransformTo(paramPNode).inverseTransform(paramRectangle2D, paramRectangle2D);
  }
  
  private static class PTuple {
    public PNode node;
    
    public PAffineTransform transform;
    
    public PTuple(PNode param1PNode, PAffineTransform param1PAffineTransform) {
      this.node = param1PNode;
      this.transform = param1PAffineTransform;
    }
  }
}