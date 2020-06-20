package edu.umd.cs.piccolo;

import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PObjectOutputStream;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PLayer extends PNode {
  public static final String PROPERTY_CAMERAS = "cameras";
  
  public static final int PROPERTY_CODE_CAMERAS = 8192;
  
  private transient List cameras = new ArrayList();
  
  public List getCamerasReference() {
    return this.cameras;
  }
  
  public int getCameraCount() {
    return (this.cameras == null) ? 0 : this.cameras.size();
  }
  
  public PCamera getCamera(int paramInt) {
    return this.cameras.get(paramInt);
  }
  
  public void addCamera(PCamera paramPCamera) {
    addCamera(this.cameras.size(), paramPCamera);
  }
  
  public void addCamera(int paramInt, PCamera paramPCamera) {
    this.cameras.add(paramInt, paramPCamera);
    invalidatePaint();
    firePropertyChange(8192, "cameras", null, this.cameras);
  }
  
  public PCamera removeCamera(PCamera paramPCamera) {
    return removeCamera(this.cameras.indexOf(paramPCamera));
  }
  
  public PCamera removeCamera(int paramInt) {
    PCamera pCamera = this.cameras.remove(paramInt);
    invalidatePaint();
    firePropertyChange(8192, "cameras", null, this.cameras);
    return pCamera;
  }
  
  public void repaintFrom(PBounds paramPBounds, PNode paramPNode) {
    if (paramPNode != this)
      localToParent((Rectangle2D)paramPBounds); 
    notifyCameras(paramPBounds);
    if (getParent() != null)
      getParent().repaintFrom(paramPBounds, paramPNode); 
  }
  
  protected void notifyCameras(PBounds paramPBounds) {
    int i = getCameraCount();
    for (byte b = 0; b < i; b++) {
      PCamera pCamera = this.cameras.get(b);
      pCamera.repaintFromLayer(paramPBounds, this);
    } 
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    int i = getCameraCount();
    for (byte b = 0; b < i; b++)
      ((PObjectOutputStream)paramObjectOutputStream).writeConditionalObject(this.cameras.get(b)); 
    paramObjectOutputStream.writeObject(Boolean.FALSE);
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    paramObjectInputStream.defaultReadObject();
    this.cameras = new ArrayList();
    while (true) {
      Object object = paramObjectInputStream.readObject();
      if (object != null) {
        if (object.equals(Boolean.FALSE))
          break; 
        this.cameras.add(object);
      } 
    } 
  }
}
