package edu.umd.cs.piccolox.nodes;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEventListener;
import edu.umd.cs.piccolo.nodes.PPath;
import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PLens extends PNode {
  public static double LENS_DRAGBAR_HEIGHT = 20.0D;
  
  public static Paint DEFAULT_DRAGBAR_PAINT = Color.DARK_GRAY;
  
  public static Paint DEFAULT_LENS_PAINT = Color.LIGHT_GRAY;
  
  private PPath dragBar = PPath.createRectangle(0.0F, 0.0F, 100.0F, 100.0F);
  
  private PCamera camera;
  
  private PDragEventHandler lensDragger;
  
  public PLens() {
    this.dragBar.setPaint(DEFAULT_DRAGBAR_PAINT);
    this.dragBar.setPickable(false);
    addChild((PNode)this.dragBar);
    this.camera = new PCamera();
    this.camera.setPaint(DEFAULT_LENS_PAINT);
    addChild((PNode)this.camera);
    this.lensDragger = new PDragEventHandler();
    this.lensDragger.getEventFilter().setMarksAcceptedEventsAsHandled(true);
    addInputEventListener((PInputEventListener)this.lensDragger);
    addPropertyChangeListener("transform", new PropertyChangeListener(this) {
          private final PLens this$0;
          
          public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
            this.this$0.camera.setViewTransform((AffineTransform)this.this$0.getInverseTransform());
          }
        });
  }
  
  public PLens(PLayer paramPLayer) {
    this();
    addLayer(0, paramPLayer);
  }
  
  public PCamera getCamera() {
    return this.camera;
  }
  
  public PPath getDragBar() {
    return this.dragBar;
  }
  
  public PDragEventHandler getLensDraggerHandler() {
    return this.lensDragger;
  }
  
  public void addLayer(int paramInt, PLayer paramPLayer) {
    this.camera.addLayer(paramInt, paramPLayer);
  }
  
  public void removeLayer(PLayer paramPLayer) {
    this.camera.removeLayer(paramPLayer);
  }
  
  protected void layoutChildren() {
    this.dragBar.setPathToRectangle((float)getX(), (float)getY(), (float)getWidth(), (float)LENS_DRAGBAR_HEIGHT);
    this.camera.setBounds(getX(), getY() + LENS_DRAGBAR_HEIGHT, getWidth(), getHeight() - LENS_DRAGBAR_HEIGHT);
  }
}
