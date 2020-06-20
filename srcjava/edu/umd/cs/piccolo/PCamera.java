ackage edu.umd.cs.piccolo;

import edu.umd.cs.piccolo.activities.PActivity;
import edu.umd.cs.piccolo.activities.PTransformActivity;
import edu.umd.cs.piccolo.util.PAffineTransform;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PDebug;
import edu.umd.cs.piccolo.util.PDimension;
import edu.umd.cs.piccolo.util.PObjectOutputStream;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolo.util.PPickPath;
import edu.umd.cs.piccolo.util.PUtil;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PCamera extends PNode {
  public static final String PROPERTY_LAYERS = "layers";
  
  public static final int PROPERTY_CODE_LAYERS = 2048;
  
  public static final String PROPERTY_VIEW_TRANSFORM = "viewTransform";
  
  public static final int PROPERTY_CODE_VIEW_TRANSFORM = 4096;
  
  public static final int VIEW_CONSTRAINT_NONE = 0;
  
  public static final int VIEW_CONSTRAINT_ALL = 1;
  
  public static final int VIEW_CONSTRAINT_CENTER = 2;
  
  private transient PComponent component;
  
  private transient List layers = new ArrayList();
  
  private PAffineTransform viewTransform = new PAffineTransform();
  
  private int viewConstraint = 0;
  
  private static PBounds TEMP_REPAINT_RECT = new PBounds();
  
  public PComponent getComponent() {
    return this.component;
  }
  
  public void setComponent(PComponent paramPComponent) {
    this.component = paramPComponent;
    invalidatePaint();
  }
  
  public void repaintFrom(PBounds paramPBounds, PNode paramPNode) {
    if (getParent() != null) {
      if (paramPNode != this)
        localToParent((Rectangle2D)paramPBounds); 
      if (this.component != null)
        this.component.repaint(paramPBounds); 
      getParent().repaintFrom(paramPBounds, this);
    } 
  }
  
  public void repaintFromLayer(PBounds paramPBounds, PNode paramPNode) {
    TEMP_REPAINT_RECT.setRect(paramPBounds);
    viewToLocal((Rectangle2D)TEMP_REPAINT_RECT);
    if (getBoundsReference().intersects((Rectangle2D)TEMP_REPAINT_RECT)) {
      PBounds.intersect((Rectangle2D)TEMP_REPAINT_RECT, (Rectangle2D)getBoundsReference(), (Rectangle2D)TEMP_REPAINT_RECT);
      repaintFrom(TEMP_REPAINT_RECT, paramPNode);
    } 
  }
  
  public List getLayersReference() {
    return this.layers;
  }
  
  public int getLayerCount() {
    return this.layers.size();
  }
  
  public PLayer getLayer(int paramInt) {
    return this.layers.get(paramInt);
  }
  
  public int indexOfLayer(PLayer paramPLayer) {
    return this.layers.indexOf(paramPLayer);
  }
  
  public void addLayer(PLayer paramPLayer) {
    addLayer(this.layers.size(), paramPLayer);
  }
  
  public void addLayer(int paramInt, PLayer paramPLayer) {
    this.layers.add(paramInt, paramPLayer);
    paramPLayer.addCamera(this);
    invalidatePaint();
    firePropertyChange(2048, "layers", null, this.layers);
  }
  
  public PLayer removeLayer(PLayer paramPLayer) {
    return removeLayer(this.layers.indexOf(paramPLayer));
  }
  
  public PLayer removeLayer(int paramInt) {
    PLayer pLayer = this.layers.remove(paramInt);
    pLayer.removeCamera(this);
    invalidatePaint();
    firePropertyChange(2048, "layers", null, this.layers);
    return pLayer;
  }
  
  public PBounds getUnionOfLayerFullBounds() {
    PBounds pBounds = new PBounds();
    int i = getLayerCount();
    for (byte b = 0; b < i; b++) {
      PLayer pLayer = this.layers.get(b);
      pBounds.add(pLayer.getFullBoundsReference());
    } 
    return pBounds;
  }
  
  protected void paint(PPaintContext paramPPaintContext) {
    super.paint(paramPPaintContext);
    paramPPaintContext.pushClip((Shape)getBoundsReference());
    paramPPaintContext.pushTransform(this.viewTransform);
    paintCameraView(paramPPaintContext);
    paintDebugInfo(paramPPaintContext);
    paramPPaintContext.popTransform(this.viewTransform);
    paramPPaintContext.popClip((Shape)getBoundsReference());
  }
  
  protected void paintCameraView(PPaintContext paramPPaintContext) {
    int i = getLayerCount();
    for (byte b = 0; b < i; b++) {
      PLayer pLayer = this.layers.get(b);
      pLayer.fullPaint(paramPPaintContext);
    } 
  }
  
  protected void paintDebugInfo(PPaintContext paramPPaintContext) {
    if (PDebug.debugBounds || PDebug.debugFullBounds) {
      Graphics2D graphics2D = paramPPaintContext.getGraphics();
      paramPPaintContext.setRenderQuality(0);
      graphics2D.setStroke(new BasicStroke(0.0F));
      ArrayList arrayList = new ArrayList();
      PBounds pBounds = new PBounds();
      Color color1 = Color.red;
      Color color2 = new Color(1.0F, 0.0F, 0.0F, 0.2F);
      for (byte b = 0; b < getLayerCount(); b++)
        getLayer(b).getAllNodes(null, arrayList); 
      for (PNode pNode : getAllNodes(null, arrayList)) {
        if (PDebug.debugBounds) {
          graphics2D.setPaint(color1);
          pBounds.setRect(pNode.getBoundsReference());
          if (!pBounds.isEmpty()) {
            pNode.localToGlobal((Rectangle2D)pBounds);
            globalToLocal((Rectangle2D)pBounds);
            if (pNode == this || pNode.isDescendentOf(this))
              localToView((Rectangle2D)pBounds); 
            graphics2D.draw((Shape)pBounds);
          } 
        } 
        if (PDebug.debugFullBounds) {
          graphics2D.setPaint(color2);
          pBounds.setRect(pNode.getFullBoundsReference());
          if (!pBounds.isEmpty()) {
            if (pNode.getParent() != null)
              pNode.getParent().localToGlobal((Rectangle2D)pBounds); 
            globalToLocal((Rectangle2D)pBounds);
            if (pNode == this || pNode.isDescendentOf(this))
              localToView((Rectangle2D)pBounds); 
            graphics2D.fill((Shape)pBounds);
          } 
        } 
      } 
    } 
  }
  
  public void fullPaint(PPaintContext paramPPaintContext) {
    paramPPaintContext.pushCamera(this);
    super.fullPaint(paramPPaintContext);
    paramPPaintContext.popCamera(this);
  }
  
  public PPickPath pick(double paramDouble1, double paramDouble2, double paramDouble3) {
    PBounds pBounds = new PBounds(new Point2D.Double(paramDouble1, paramDouble2), -paramDouble3, -paramDouble3);
    PPickPath pPickPath = new PPickPath(this, pBounds);
    fullPick(pPickPath);
    if (pPickPath.getNodeStackReference().size() == 0) {
      pPickPath.pushNode(this);
      pPickPath.pushTransform(getTransformReference(false));
    } 
    return pPickPath;
  }
  
  protected boolean pickAfterChildren(PPickPath paramPPickPath) {
    if (intersects((Rectangle2D)paramPPickPath.getPickBounds())) {
      paramPPickPath.pushTransform(this.viewTransform);
      if (pickCameraView(paramPPickPath))
        return true; 
      paramPPickPath.popTransform(this.viewTransform);
      return true;
    } 
    return false;
  }
  
  protected boolean pickCameraView(PPickPath paramPPickPath) {
    int i = getLayerCount();
    for (int j = i - 1; j >= 0; j--) {
      PLayer pLayer = this.layers.get(j);
      if (pLayer.fullPick(paramPPickPath))
        return true; 
    } 
    return false;
  }
  
  public PBounds getViewBounds() {
    return (PBounds)localToView((Rectangle2D)getBounds());
  }
  
  public void setViewBounds(Rectangle2D paramRectangle2D) {
    animateViewToCenterBounds(paramRectangle2D, true, 0L);
  }
  
  public double getViewScale() {
    return this.viewTransform.getScale();
  }
  
  public void scaleView(double paramDouble) {
    scaleViewAboutPoint(paramDouble, 0.0D, 0.0D);
  }
  
  public void scaleViewAboutPoint(double paramDouble1, double paramDouble2, double paramDouble3) {
    this.viewTransform.scaleAboutPoint(paramDouble1, paramDouble2, paramDouble3);
    applyViewConstraints();
    invalidatePaint();
    firePropertyChange(4096, "viewTransform", null, this.viewTransform);
  }
  
  public void setViewScale(double paramDouble) {
    scaleView(paramDouble / getViewScale());
  }
  
  public void translateView(double paramDouble1, double paramDouble2) {
    this.viewTransform.translate(paramDouble1, paramDouble2);
    applyViewConstraints();
    invalidatePaint();
    firePropertyChange(4096, "viewTransform", null, this.viewTransform);
  }
  
  public void setViewOffset(double paramDouble1, double paramDouble2) {
    this.viewTransform.setOffset(paramDouble1, paramDouble2);
    applyViewConstraints();
    invalidatePaint();
    firePropertyChange(4096, "viewTransform", null, this.viewTransform);
  }
  
  public PAffineTransform getViewTransform() {
    return (PAffineTransform)this.viewTransform.clone();
  }
  
  public PAffineTransform getViewTransformReference() {
    return this.viewTransform;
  }
  
  public void setViewTransform(AffineTransform paramAffineTransform) {
    this.viewTransform.setTransform(paramAffineTransform);
    applyViewConstraints();
    invalidatePaint();
    firePropertyChange(4096, "viewTransform", null, this.viewTransform);
  }
  
  public PTransformActivity animateViewToCenterBounds(Rectangle2D paramRectangle2D, boolean paramBoolean, long paramLong) {
    PBounds pBounds = getViewBounds();
    PDimension pDimension = pBounds.deltaRequiredToCenter(paramRectangle2D);
    PAffineTransform pAffineTransform = getViewTransform();
    pAffineTransform.translate(pDimension.width, pDimension.height);
    if (paramBoolean) {
      double d = Math.min(pBounds.getWidth() / paramRectangle2D.getWidth(), pBounds.getHeight() / paramRectangle2D.getHeight());
      if (d != Double.POSITIVE_INFINITY && d != 0.0D)
        pAffineTransform.scaleAboutPoint(d, paramRectangle2D.getCenterX(), paramRectangle2D.getCenterY()); 
    } 
    return animateViewToTransform((AffineTransform)pAffineTransform, paramLong);
  }
  
  public PTransformActivity animateViewToPanToBounds(Rectangle2D paramRectangle2D, long paramLong) {
    PBounds pBounds = getViewBounds();
    PDimension pDimension = pBounds.deltaRequiredToContain(paramRectangle2D);
    if (pDimension.width != 0.0D || pDimension.height != 0.0D)
      if (paramLong == 0L) {
        translateView(-pDimension.width, -pDimension.height);
      } else {
        PAffineTransform pAffineTransform = getViewTransform();
        pAffineTransform.translate(-pDimension.width, -pDimension.height);
        return animateViewToTransform((AffineTransform)pAffineTransform, paramLong);
      }  
    return null;
  }
  
  public PTransformActivity animateViewToIncludeBounds(Rectangle2D paramRectangle2D, long paramLong) {
    return animateViewToPanToBounds(paramRectangle2D, paramLong);
  }
  
  public PTransformActivity animateViewToTransform(AffineTransform paramAffineTransform, long paramLong) {
    if (paramLong == 0L) {
      setViewTransform(paramAffineTransform);
      return null;
    } 
    PTransformActivity.Target target = new PTransformActivity.Target(this) {
        private final PCamera this$0;
        
        public void setTransform(AffineTransform param1AffineTransform) {
          this.this$0.setViewTransform(param1AffineTransform);
        }
        
        public void getSourceMatrix(double[] param1ArrayOfdouble) {
          this.this$0.viewTransform.getMatrix(param1ArrayOfdouble);
        }
      };
    PTransformActivity pTransformActivity = new PTransformActivity(paramLong, PUtil.DEFAULT_ACTIVITY_STEP_RATE, target, paramAffineTransform);
    PRoot pRoot = getRoot();
    if (pRoot != null)
      pRoot.getActivityScheduler().addActivity((PActivity)pTransformActivity); 
    return pTransformActivity;
  }
  
  public int getViewConstraint() {
    return this.viewConstraint;
  }
  
  public void setViewConstraint(int paramInt) {
    this.viewConstraint = paramInt;
    applyViewConstraints();
  }
  
  protected void applyViewConstraints() {
    if (this.viewConstraint == 0)
      return; 
    PBounds pBounds1 = getViewBounds();
    PBounds pBounds2 = (PBounds)globalToLocal((Rectangle2D)getUnionOfLayerFullBounds());
    PDimension pDimension = null;
    switch (this.viewConstraint) {
      case 1:
        pDimension = pBounds1.deltaRequiredToContain((Rectangle2D)pBounds2);
        break;
      case 2:
        pBounds2.setRect(pBounds2.getCenterX(), pBounds2.getCenterY(), 0.0D, 0.0D);
        pDimension = pBounds1.deltaRequiredToContain((Rectangle2D)pBounds2);
        break;
    } 
    this.viewTransform.translate(-pDimension.width, -pDimension.height);
  }
  
  public Point2D viewToLocal(Point2D paramPoint2D) {
    return this.viewTransform.transform(paramPoint2D, paramPoint2D);
  }
  
  public Dimension2D viewToLocal(Dimension2D paramDimension2D) {
    return this.viewTransform.transform(paramDimension2D, paramDimension2D);
  }
  
  public Rectangle2D viewToLocal(Rectangle2D paramRectangle2D) {
    return this.viewTransform.transform(paramRectangle2D, paramRectangle2D);
  }
  
  public Point2D localToView(Point2D paramPoint2D) {
    try {
      return this.viewTransform.inverseTransform(paramPoint2D, paramPoint2D);
    } catch (NoninvertibleTransformException noninvertibleTransformException) {
      noninvertibleTransformException.printStackTrace();
      return null;
    } 
  }
  
  public Dimension2D localToView(Dimension2D paramDimension2D) {
    return this.viewTransform.inverseTransform(paramDimension2D, paramDimension2D);
  }
  
  public Rectangle2D localToView(Rectangle2D paramRectangle2D) {
    return this.viewTransform.inverseTransform(paramRectangle2D, paramRectangle2D);
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    int i = getLayerCount();
    for (byte b = 0; b < i; b++)
      ((PObjectOutputStream)paramObjectOutputStream).writeConditionalObject(this.layers.get(b)); 
    paramObjectOutputStream.writeObject(Boolean.FALSE);
    ((PObjectOutputStream)paramObjectOutputStream).writeConditionalObject(this.component);
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    paramObjectInputStream.defaultReadObject();
    this.layers = new ArrayList();
    while (true) {
      Object object = paramObjectInputStream.readObject();
      if (object != null) {
        if (object.equals(Boolean.FALSE)) {
          this.component = (PComponent)paramObjectInputStream.readObject();
          return;
        } 
        this.layers.add(object);
      } 
    } 
  }
}
