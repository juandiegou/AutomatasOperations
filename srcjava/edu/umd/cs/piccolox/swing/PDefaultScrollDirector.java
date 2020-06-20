package edu.umd.cs.piccolox.swing;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PRoot;
import edu.umd.cs.piccolo.util.PAffineTransform;
import edu.umd.cs.piccolo.util.PBounds;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class PDefaultScrollDirector implements PScrollDirector, PropertyChangeListener {
  protected PViewport viewPort;
  
  protected PScrollPane scrollPane;
  
  protected PCanvas view;
  
  protected PCamera camera;
  
  protected PRoot root;
  
  protected boolean scrollInProgress = false;
  
  public void install(PViewport paramPViewport, PCanvas paramPCanvas) {
    this.scrollPane = (PScrollPane)paramPViewport.getParent();
    this.viewPort = paramPViewport;
    this.view = paramPCanvas;
    if (paramPCanvas != null) {
      this.camera = paramPCanvas.getCamera();
      this.root = paramPCanvas.getRoot();
    } 
    if (this.camera != null)
      this.camera.addPropertyChangeListener(this); 
    if (this.root != null)
      this.root.addPropertyChangeListener(this); 
    if (this.scrollPane != null)
      this.scrollPane.revalidate(); 
  }
  
  public void unInstall() {
    this.viewPort = null;
    this.view = null;
    if (this.camera != null)
      this.camera.removePropertyChangeListener(this); 
    if (this.root != null)
      this.root.removePropertyChangeListener(this); 
    this.camera = null;
    this.root = null;
  }
  
  public Point getViewPosition(Rectangle2D paramRectangle2D) {
    Point point = new Point();
    if (this.camera != null) {
      PBounds pBounds = new PBounds();
      List list = this.camera.getLayersReference();
      for (PLayer pLayer : list)
        pBounds.add(pLayer.getFullBoundsReference()); 
      this.camera.viewToLocal((Rectangle2D)pBounds);
      pBounds.add(paramRectangle2D);
      point.setLocation((int)(paramRectangle2D.getX() - pBounds.getX() + 0.5D), (int)(paramRectangle2D.getY() - pBounds.getY() + 0.5D));
    } 
    return point;
  }
  
  public Dimension getViewSize(Rectangle2D paramRectangle2D) {
    Dimension dimension = new Dimension();
    if (this.camera != null) {
      PBounds pBounds = new PBounds();
      List list = this.camera.getLayersReference();
      for (PLayer pLayer : list)
        pBounds.add(pLayer.getFullBoundsReference()); 
      if (!pBounds.isEmpty())
        this.camera.viewToLocal((Rectangle2D)pBounds); 
      pBounds.add(paramRectangle2D);
      dimension.setSize((int)(pBounds.getWidth() + 0.5D), (int)(pBounds.getHeight() + 0.5D));
    } 
    return dimension;
  }
  
  public void setViewPosition(double paramDouble1, double paramDouble2) {
    if (this.camera != null && !this.scrollInProgress) {
      this.scrollInProgress = true;
      PBounds pBounds1 = new PBounds();
      List list = this.camera.getLayersReference();
      for (PLayer pLayer : list)
        pBounds1.add(pLayer.getFullBoundsReference()); 
      PAffineTransform pAffineTransform = this.camera.getViewTransform();
      pAffineTransform.transform((Rectangle2D)pBounds1, (Rectangle2D)pBounds1);
      PBounds pBounds2 = this.camera.getBoundsReference();
      pBounds1.add(pBounds2);
      Point2D.Double double_ = new Point2D.Double(pBounds1.getX() + paramDouble1, pBounds1.getY() + paramDouble2);
      this.camera.localToView(double_);
      double d1 = -(pAffineTransform.getScaleX() * double_.getX() + pAffineTransform.getShearX() * double_.getY());
      double d2 = -(pAffineTransform.getShearY() * double_.getX() + pAffineTransform.getScaleY() * double_.getY());
      pAffineTransform.setTransform(pAffineTransform.getScaleX(), pAffineTransform.getShearY(), pAffineTransform.getShearX(), pAffineTransform.getScaleY(), d1, d2);
      this.camera.setViewTransform((AffineTransform)pAffineTransform);
      this.scrollInProgress = false;
    } 
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
    boolean bool1 = ("viewTransform" == paramPropertyChangeEvent.getPropertyName()) ? true : false;
    boolean bool2 = (("bounds" == paramPropertyChangeEvent.getPropertyName() || "fullBounds" == paramPropertyChangeEvent.getPropertyName()) && (paramPropertyChangeEvent.getSource() == this.camera || paramPropertyChangeEvent.getSource() == this.view.getRoot())) ? true : false;
    if (bool1 || bool2)
      if (shouldRevalidateScrollPane()) {
        this.scrollPane.revalidate();
      } else {
        this.viewPort.fireStateChanged();
      }  
  }
  
  public boolean shouldRevalidateScrollPane() {
    if (this.camera != null) {
      if (this.scrollPane.getHorizontalScrollBarPolicy() != 30 && this.scrollPane.getVerticalScrollBarPolicy() != 20)
        return false; 
      PBounds pBounds1 = new PBounds();
      List list = this.camera.getLayersReference();
      for (PLayer pLayer : list)
        pBounds1.add(pLayer.getFullBoundsReference()); 
      this.camera.viewToLocal((Rectangle2D)pBounds1);
      PBounds pBounds2 = this.camera.getBoundsReference();
      pBounds1.add(pBounds2);
      int i = (int)(pBounds1.getWidth() + 0.5D);
      int j = (int)(pBounds1.getHeight() + 0.5D);
      int k = (int)(pBounds2.getWidth() + 0.5D);
      int m = (int)(pBounds2.getHeight() + 0.5D);
      if ((this.scrollPane.getHorizontalScrollBar().isShowing() && i <= k) || (!this.scrollPane.getHorizontalScrollBar().isShowing() && i > k) || (this.scrollPane.getVerticalScrollBar().isShowing() && j <= m) || (!this.scrollPane.getVerticalScrollBar().isShowing() && j > m))
        return true; 
    } 
    return false;
  }
}
