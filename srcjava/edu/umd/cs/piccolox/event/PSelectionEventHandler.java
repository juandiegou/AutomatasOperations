package edu.umd.cs.piccolox.event;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PDragSequenceEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PDimension;
import edu.umd.cs.piccolo.util.PNodeFilter;
import edu.umd.cs.piccolox.handles.PBoundsHandle;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PSelectionEventHandler extends PDragSequenceEventHandler {
  public static final String SELECTION_CHANGED_NOTIFICATION = "SELECTION_CHANGED_NOTIFICATION";
  
  static final int DASH_WIDTH = 5;
  
  static final int NUM_STROKES = 10;
  
  private HashMap selection = null;
  
  private List selectableParents = null;
  
  private PPath marquee = null;
  
  private PNode marqueeParent = null;
  
  private Point2D presspt = null;
  
  private Point2D canvasPressPt = null;
  
  private float strokeNum = 0.0F;
  
  private Stroke[] strokes = null;
  
  private HashMap allItems = null;
  
  private ArrayList unselectList = null;
  
  private HashMap marqueeMap = null;
  
  private PNode pressNode = null;
  
  private boolean deleteKeyActive = true;
  
  private Paint marqueePaint;
  
  private float marqueePaintTransparency = 1.0F;
  
  public PSelectionEventHandler(PNode paramPNode1, PNode paramPNode2) {
    this.marqueeParent = paramPNode1;
    this.selectableParents = new ArrayList();
    this.selectableParents.add(paramPNode2);
    init();
  }
  
  public PSelectionEventHandler(PNode paramPNode, List paramList) {
    this.marqueeParent = paramPNode;
    this.selectableParents = paramList;
    init();
  }
  
  protected void init() {
    float[] arrayOfFloat = { 5.0F, 5.0F };
    this.strokes = new Stroke[10];
    for (byte b = 0; b < 10; b++)
      this.strokes[b] = new BasicStroke(1.0F, 0, 0, 1.0F, arrayOfFloat, b); 
    this.selection = new HashMap();
    this.allItems = new HashMap();
    this.unselectList = new ArrayList();
    this.marqueeMap = new HashMap();
  }
  
  public void select(Collection paramCollection) {
    boolean bool = false;
    for (PNode pNode : paramCollection)
      bool |= internalSelect(pNode); 
    if (bool)
      postSelectionChanged(); 
  }
  
  public void select(Map paramMap) {
    select(paramMap.keySet());
  }
  
  private boolean internalSelect(PNode paramPNode) {
    if (isSelected(paramPNode))
      return false; 
    this.selection.put(paramPNode, Boolean.TRUE);
    decorateSelectedNode(paramPNode);
    return true;
  }
  
  private void postSelectionChanged() {
    PNotificationCenter.defaultCenter().postNotification("SELECTION_CHANGED_NOTIFICATION", this);
  }
  
  public void select(PNode paramPNode) {
    if (internalSelect(paramPNode))
      postSelectionChanged(); 
  }
  
  public void decorateSelectedNode(PNode paramPNode) {
    PBoundsHandle.addBoundsHandlesTo(paramPNode);
  }
  
  public void unselect(Collection paramCollection) {
    boolean bool = false;
    for (PNode pNode : paramCollection)
      bool |= internalUnselect(pNode); 
    if (bool)
      postSelectionChanged(); 
  }
  
  private boolean internalUnselect(PNode paramPNode) {
    if (!isSelected(paramPNode))
      return false; 
    undecorateSelectedNode(paramPNode);
    this.selection.remove(paramPNode);
    return true;
  }
  
  public void unselect(PNode paramPNode) {
    if (internalUnselect(paramPNode))
      postSelectionChanged(); 
  }
  
  public void undecorateSelectedNode(PNode paramPNode) {
    PBoundsHandle.removeBoundsHandlesFrom(paramPNode);
  }
  
  public void unselectAll() {
    ArrayList arrayList = new ArrayList(this.selection.keySet());
    unselect(arrayList);
  }
  
  public boolean isSelected(PNode paramPNode) {
    return (paramPNode != null && this.selection.containsKey(paramPNode));
  }
  
  public Collection getSelection() {
    return new ArrayList(this.selection.keySet());
  }
  
  public Collection getSelectionReference() {
    return Collections.unmodifiableCollection(this.selection.keySet());
  }
  
  protected boolean isSelectable(PNode paramPNode) {
    boolean bool = false;
    for (PNode pNode : this.selectableParents) {
      if (pNode.getChildrenReference().contains(paramPNode)) {
        bool = true;
        break;
      } 
      if (pNode instanceof PCamera)
        for (byte b = 0; b < ((PCamera)pNode).getLayerCount(); b++) {
          PLayer pLayer = ((PCamera)pNode).getLayer(b);
          if (pLayer.getChildrenReference().contains(paramPNode)) {
            bool = true;
            break;
          } 
        }  
    } 
    return bool;
  }
  
  public void addSelectableParent(PNode paramPNode) {
    this.selectableParents.add(paramPNode);
  }
  
  public void removeSelectableParent(PNode paramPNode) {
    this.selectableParents.remove(paramPNode);
  }
  
  public void setSelectableParent(PNode paramPNode) {
    this.selectableParents.clear();
    this.selectableParents.add(paramPNode);
  }
  
  public void setSelectableParents(Collection paramCollection) {
    this.selectableParents.clear();
    this.selectableParents.addAll(paramCollection);
  }
  
  public Collection getSelectableParents() {
    return new ArrayList(this.selectableParents);
  }
  
  protected void startDrag(PInputEvent paramPInputEvent) {
    super.startDrag(paramPInputEvent);
    initializeSelection(paramPInputEvent);
    if (isMarqueeSelection(paramPInputEvent)) {
      initializeMarquee(paramPInputEvent);
      if (!isOptionSelection(paramPInputEvent)) {
        startMarqueeSelection(paramPInputEvent);
      } else {
        startOptionMarqueeSelection(paramPInputEvent);
      } 
    } else if (!isOptionSelection(paramPInputEvent)) {
      startStandardSelection(paramPInputEvent);
    } else {
      startStandardOptionSelection(paramPInputEvent);
    } 
  }
  
  protected void drag(PInputEvent paramPInputEvent) {
    super.drag(paramPInputEvent);
    if (isMarqueeSelection(paramPInputEvent)) {
      updateMarquee(paramPInputEvent);
      if (!isOptionSelection(paramPInputEvent)) {
        computeMarqueeSelection(paramPInputEvent);
      } else {
        computeOptionMarqueeSelection(paramPInputEvent);
      } 
    } else {
      dragStandardSelection(paramPInputEvent);
    } 
  }
  
  protected void endDrag(PInputEvent paramPInputEvent) {
    super.endDrag(paramPInputEvent);
    if (isMarqueeSelection(paramPInputEvent)) {
      endMarqueeSelection(paramPInputEvent);
    } else {
      endStandardSelection(paramPInputEvent);
    } 
  }
  
  public boolean isOptionSelection(PInputEvent paramPInputEvent) {
    return paramPInputEvent.isShiftDown();
  }
  
  protected boolean isMarqueeSelection(PInputEvent paramPInputEvent) {
    return (this.pressNode == null);
  }
  
  protected void initializeSelection(PInputEvent paramPInputEvent) {
    this.canvasPressPt = paramPInputEvent.getCanvasPosition();
    this.presspt = paramPInputEvent.getPosition();
    this.pressNode = paramPInputEvent.getPath().getPickedNode();
    if (this.pressNode instanceof PCamera)
      this.pressNode = null; 
  }
  
  protected void initializeMarquee(PInputEvent paramPInputEvent) {
    this.marquee = PPath.createRectangle((float)this.presspt.getX(), (float)this.presspt.getY(), 0.0F, 0.0F);
    this.marquee.setPaint(this.marqueePaint);
    this.marquee.setTransparency(this.marqueePaintTransparency);
    this.marquee.setStrokePaint(Color.black);
    this.marquee.setStroke(this.strokes[0]);
    this.marqueeParent.addChild((PNode)this.marquee);
    this.marqueeMap.clear();
  }
  
  protected void startOptionMarqueeSelection(PInputEvent paramPInputEvent) {}
  
  protected void startMarqueeSelection(PInputEvent paramPInputEvent) {
    unselectAll();
  }
  
  protected void startStandardSelection(PInputEvent paramPInputEvent) {
    if (!isSelected(this.pressNode)) {
      unselectAll();
      if (isSelectable(this.pressNode))
        select(this.pressNode); 
    } 
  }
  
  protected void startStandardOptionSelection(PInputEvent paramPInputEvent) {
    if (isSelectable(this.pressNode))
      if (isSelected(this.pressNode)) {
        unselect(this.pressNode);
      } else {
        select(this.pressNode);
      }  
  }
  
  protected void updateMarquee(PInputEvent paramPInputEvent) {
    PBounds pBounds = new PBounds();
    if (this.marqueeParent instanceof PCamera) {
      pBounds.add(this.canvasPressPt);
      pBounds.add(paramPInputEvent.getCanvasPosition());
    } else {
      pBounds.add(this.presspt);
      pBounds.add(paramPInputEvent.getPosition());
    } 
    this.marquee.globalToLocal((Rectangle2D)pBounds);
    this.marquee.setPathToRectangle((float)pBounds.x, (float)pBounds.y, (float)pBounds.width, (float)pBounds.height);
    pBounds.reset();
    pBounds.add(this.presspt);
    pBounds.add(paramPInputEvent.getPosition());
    this.allItems.clear();
    PNodeFilter pNodeFilter = createNodeFilter(pBounds);
    for (PNode pNode : this.selectableParents) {
      Collection collection;
      if (pNode instanceof PCamera) {
        collection = new ArrayList();
        for (byte b = 0; b < ((PCamera)pNode).getLayerCount(); b++)
          ((PCamera)pNode).getLayer(b).getAllNodes(pNodeFilter, collection); 
      } else {
        collection = pNode.getAllNodes(pNodeFilter, null);
      } 
      Iterator iterator = collection.iterator();
      while (iterator.hasNext())
        this.allItems.put(iterator.next(), Boolean.TRUE); 
    } 
  }
  
  protected void computeMarqueeSelection(PInputEvent paramPInputEvent) {
    this.unselectList.clear();
    for (PNode pNode : this.selection.keySet()) {
      if (!this.allItems.containsKey(pNode))
        this.unselectList.add(pNode); 
    } 
    unselect(this.unselectList);
    Iterator iterator = this.allItems.keySet().iterator();
    while (iterator.hasNext()) {
      PNode pNode = iterator.next();
      if (!this.selection.containsKey(pNode) && !this.marqueeMap.containsKey(pNode) && isSelectable(pNode)) {
        this.marqueeMap.put(pNode, Boolean.TRUE);
        continue;
      } 
      if (!isSelectable(pNode))
        iterator.remove(); 
    } 
    select(this.allItems);
  }
  
  protected void computeOptionMarqueeSelection(PInputEvent paramPInputEvent) {
    this.unselectList.clear();
    for (PNode pNode : this.selection.keySet()) {
      if (!this.allItems.containsKey(pNode) && this.marqueeMap.containsKey(pNode)) {
        this.marqueeMap.remove(pNode);
        this.unselectList.add(pNode);
      } 
    } 
    unselect(this.unselectList);
    Iterator iterator = this.allItems.keySet().iterator();
    while (iterator.hasNext()) {
      PNode pNode = iterator.next();
      if (!this.selection.containsKey(pNode) && !this.marqueeMap.containsKey(pNode) && isSelectable(pNode)) {
        this.marqueeMap.put(pNode, Boolean.TRUE);
        continue;
      } 
      if (!isSelectable(pNode))
        iterator.remove(); 
    } 
    select(this.allItems);
  }
  
  protected PNodeFilter createNodeFilter(PBounds paramPBounds) {
    return new BoundsFilter(this, paramPBounds);
  }
  
  protected PBounds getMarqueeBounds() {
    return (this.marquee != null) ? this.marquee.getBounds() : new PBounds();
  }
  
  protected void dragStandardSelection(PInputEvent paramPInputEvent) {
    PDimension pDimension1 = paramPInputEvent.getCanvasDelta();
    paramPInputEvent.getTopCamera().localToView((Dimension2D)pDimension1);
    PDimension pDimension2 = new PDimension();
    for (PNode pNode : getSelection()) {
      pDimension2.setSize((Dimension2D)pDimension1);
      pNode.getParent().globalToLocal((Dimension2D)pDimension2);
      pNode.offset(pDimension2.getWidth(), pDimension2.getHeight());
    } 
  }
  
  protected void endMarqueeSelection(PInputEvent paramPInputEvent) {
    this.marquee.removeFromParent();
    this.marquee = null;
  }
  
  protected void endStandardSelection(PInputEvent paramPInputEvent) {
    this.pressNode = null;
  }
  
  protected void dragActivityStep(PInputEvent paramPInputEvent) {
    if (this.marquee != null) {
      float f = this.strokeNum;
      this.strokeNum = (this.strokeNum + 0.5F) % 10.0F;
      if ((int)this.strokeNum != (int)f)
        this.marquee.setStroke(this.strokes[(int)this.strokeNum]); 
    } 
  }
  
  public void keyPressed(PInputEvent paramPInputEvent) {
    switch (paramPInputEvent.getKeyCode()) {
      case 127:
        if (this.deleteKeyActive) {
          for (PNode pNode : this.selection.keySet())
            pNode.removeFromParent(); 
          this.selection.clear();
        } 
        break;
    } 
  }
  
  public boolean getSupportDeleteKey() {
    return this.deleteKeyActive;
  }
  
  public boolean isDeleteKeyActive() {
    return this.deleteKeyActive;
  }
  
  public void setDeleteKeyActive(boolean paramBoolean) {
    this.deleteKeyActive = paramBoolean;
  }
  
  public Paint getMarqueePaint() {
    return this.marqueePaint;
  }
  
  public void setMarqueePaint(Paint paramPaint) {
    this.marqueePaint = paramPaint;
  }
  
  public float getMarqueePaintTransparency() {
    return this.marqueePaintTransparency;
  }
  
  public void setMarqueePaintTransparency(float paramFloat) {
    this.marqueePaintTransparency = paramFloat;
  }
  
  protected class BoundsFilter implements PNodeFilter {
    PBounds localBounds;
    
    PBounds bounds;
    
    private final PSelectionEventHandler this$0;
    
    protected BoundsFilter(PSelectionEventHandler this$0, PBounds param1PBounds) {
      this.this$0 = this$0;
      this.localBounds = new PBounds();
      this.bounds = param1PBounds;
    }
    
    public boolean accept(PNode param1PNode) {
      this.localBounds.setRect(this.bounds);
      param1PNode.globalToLocal((Rectangle2D)this.localBounds);
      boolean bool = param1PNode.intersects((Rectangle2D)this.localBounds);
      boolean bool1 = (param1PNode == this.this$0.marquee) ? true : false;
      return (param1PNode.getPickable() && bool && !bool1 && !this.this$0.selectableParents.contains(param1PNode) && !isCameraLayer(param1PNode));
    }
    
    public boolean acceptChildrenOf(PNode param1PNode) {
      return (this.this$0.selectableParents.contains(param1PNode) || isCameraLayer(param1PNode));
    }
    
    public boolean isCameraLayer(PNode param1PNode) {
      if (param1PNode instanceof PLayer)
        for (PNode pNode : this.this$0.selectableParents) {
          if (pNode instanceof PCamera && ((PCamera)pNode).indexOfLayer((PLayer)param1PNode) != -1)
            return true; 
        }  
      return false;
    }
  }
}
