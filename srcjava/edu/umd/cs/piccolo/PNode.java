package edu.umd.cs.piccolo;

import edu.umd.cs.piccolo.activities.PActivity;
import edu.umd.cs.piccolo.activities.PColorActivity;
import edu.umd.cs.piccolo.activities.PInterpolatingActivity;
import edu.umd.cs.piccolo.activities.PTransformActivity;
import edu.umd.cs.piccolo.event.PInputEventListener;
import edu.umd.cs.piccolo.util.PAffineTransform;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PNodeFilter;
import edu.umd.cs.piccolo.util.PObjectOutputStream;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolo.util.PPickPath;
import edu.umd.cs.piccolo.util.PUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.swing.event.EventListenerList;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;

public class PNode implements Cloneable, Serializable, Printable {
  public static final String PROPERTY_CLIENT_PROPERTIES = "clientProperties";
  
  public static final int PROPERTY_CODE_CLIENT_PROPERTIES = 1;
  
  public static final String PROPERTY_BOUNDS = "bounds";
  
  public static final int PROPERTY_CODE_BOUNDS = 2;
  
  public static final String PROPERTY_FULL_BOUNDS = "fullBounds";
  
  public static final int PROPERTY_CODE_FULL_BOUNDS = 4;
  
  public static final String PROPERTY_TRANSFORM = "transform";
  
  public static final int PROPERTY_CODE_TRANSFORM = 8;
  
  public static final String PROPERTY_VISIBLE = "visible";
  
  public static final int PROPERTY_CODE_VISIBLE = 16;
  
  public static final String PROPERTY_PAINT = "paint";
  
  public static final int PROPERTY_CODE_PAINT = 32;
  
  public static final String PROPERTY_TRANSPARENCY = "transparency";
  
  public static final int PROPERTY_CODE_TRANSPARENCY = 64;
  
  public static final String PROPERTY_PICKABLE = "pickable";
  
  public static final int PROPERTY_CODE_PICKABLE = 128;
  
  public static final String PROPERTY_CHILDREN_PICKABLE = "childrenPickable";
  
  public static final int PROPERTY_CODE_CHILDREN_PICKABLE = 256;
  
  public static final String PROPERTY_CHILDREN = "children";
  
  public static final int PROPERTY_CODE_CHILDREN = 512;
  
  public static final String PROPERTY_PARENT = "parent";
  
  public static final int PROPERTY_CODE_PARENT = 1024;
  
  private static final PBounds TEMP_REPAINT_BOUNDS = new PBounds();
  
  public static PSceneGraphDelegate SCENE_GRAPH_DELEGATE = null;
  
  private transient PNode parent;
  
  private List children;
  
  private PBounds bounds = new PBounds();
  
  private PAffineTransform transform;
  
  private Paint paint;
  
  private float transparency = 1.0F;
  
  private MutableAttributeSet clientProperties;
  
  private PBounds fullBoundsCache = new PBounds();
  
  private int propertyChangeParentMask = 0;
  
  private transient SwingPropertyChangeSupport changeSupport;
  
  private transient EventListenerList listenerList;
  
  private boolean pickable = true;
  
  private boolean childrenPickable = true;
  
  private boolean visible = true;
  
  private boolean childBoundsVolatile;
  
  private boolean paintInvalid;
  
  private boolean childPaintInvalid;
  
  private boolean boundsChanged;
  
  private boolean fullBoundsInvalid;
  
  private boolean childBoundsInvalid;
  
  private boolean occluded;
  
  public PInterpolatingActivity animateToBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, long paramLong) {
    if (paramLong == 0L) {
      setBounds(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
      return null;
    } 
    PBounds pBounds = new PBounds(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    PInterpolatingActivity pInterpolatingActivity = new PInterpolatingActivity(this, paramLong, PUtil.DEFAULT_ACTIVITY_STEP_RATE, pBounds) {
        private PBounds src;
        
        private final PBounds val$dst;
        
        private final PNode this$0;
        
        protected void activityStarted() {
          this.src = this.this$0.getBounds();
          this.this$0.startResizeBounds();
          super.activityStarted();
        }
        
        public void setRelativeTargetValue(float param1Float) {
          this.this$0.setBounds(this.src.x + param1Float * (this.val$dst.x - this.src.x), this.src.y + param1Float * (this.val$dst.y - this.src.y), this.src.width + param1Float * (this.val$dst.width - this.src.width), this.src.height + param1Float * (this.val$dst.height - this.src.height));
        }
        
        protected void activityFinished() {
          super.activityFinished();
          this.this$0.endResizeBounds();
        }
      };
    addActivity((PActivity)pInterpolatingActivity);
    return pInterpolatingActivity;
  }
  
  public PTransformActivity animateTransformToBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, long paramLong) {
    PAffineTransform pAffineTransform = new PAffineTransform();
    pAffineTransform.setToScale(paramDouble3 / getWidth(), paramDouble4 / getHeight());
    pAffineTransform.setOffset(paramDouble1, paramDouble2);
    return animateToTransform((AffineTransform)pAffineTransform, paramLong);
  }
  
  public PTransformActivity animateToPositionScaleRotation(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, long paramLong) {
    PAffineTransform pAffineTransform = getTransform();
    pAffineTransform.setOffset(paramDouble1, paramDouble2);
    pAffineTransform.setScale(paramDouble3);
    pAffineTransform.setRotation(paramDouble4);
    return animateToTransform((AffineTransform)pAffineTransform, paramLong);
  }
  
  public PTransformActivity animateToTransform(AffineTransform paramAffineTransform, long paramLong) {
    if (paramLong == 0L) {
      setTransform(paramAffineTransform);
      return null;
    } 
    PTransformActivity.Target target = new PTransformActivity.Target(this) {
        private final PNode this$0;
        
        public void setTransform(AffineTransform param1AffineTransform) {
          this.this$0.setTransform(param1AffineTransform);
        }
        
        public void getSourceMatrix(double[] param1ArrayOfdouble) {
          this.this$0.getTransformReference(true).getMatrix(param1ArrayOfdouble);
        }
      };
    PTransformActivity pTransformActivity = new PTransformActivity(paramLong, PUtil.DEFAULT_ACTIVITY_STEP_RATE, target, paramAffineTransform);
    addActivity((PActivity)pTransformActivity);
    return pTransformActivity;
  }
  
  public PInterpolatingActivity animateToColor(Color paramColor, long paramLong) {
    if (paramLong == 0L) {
      setPaint(paramColor);
      return null;
    } 
    PColorActivity.Target target = new PColorActivity.Target(this) {
        private final PNode this$0;
        
        public Color getColor() {
          return (Color)this.this$0.getPaint();
        }
        
        public void setColor(Color param1Color) {
          this.this$0.setPaint(param1Color);
        }
      };
    PColorActivity pColorActivity = new PColorActivity(paramLong, PUtil.DEFAULT_ACTIVITY_STEP_RATE, target, paramColor);
    addActivity((PActivity)pColorActivity);
    return (PInterpolatingActivity)pColorActivity;
  }
  
  public PInterpolatingActivity animateToTransparency(float paramFloat, long paramLong) {
    if (paramLong == 0L) {
      setTransparency(paramFloat);
      return null;
    } 
    float f = paramFloat;
    PInterpolatingActivity pInterpolatingActivity = new PInterpolatingActivity(this, paramLong, PUtil.DEFAULT_ACTIVITY_STEP_RATE, f) {
        private float source;
        
        private final float val$dest;
        
        private final PNode this$0;
        
        protected void activityStarted() {
          this.source = this.this$0.getTransparency();
          super.activityStarted();
        }
        
        public void setRelativeTargetValue(float param1Float) {
          this.this$0.setTransparency(this.source + param1Float * (this.val$dest - this.source));
        }
      };
    addActivity((PActivity)pInterpolatingActivity);
    return pInterpolatingActivity;
  }
  
  public boolean addActivity(PActivity paramPActivity) {
    PRoot pRoot = getRoot();
    return (pRoot != null) ? pRoot.addActivity(paramPActivity) : false;
  }
  
  public MutableAttributeSet getClientProperties() {
    if (this.clientProperties == null)
      this.clientProperties = new SimpleAttributeSet(); 
    return this.clientProperties;
  }
  
  public Object getAttribute(Object paramObject) {
    return (this.clientProperties == null || paramObject == null) ? null : this.clientProperties.getAttribute(paramObject);
  }
  
  public void addAttribute(Object paramObject1, Object paramObject2) {
    if (paramObject2 == null && this.clientProperties == null)
      return; 
    Object object = getAttribute(paramObject1);
    if (paramObject2 != object) {
      if (this.clientProperties == null)
        this.clientProperties = new SimpleAttributeSet(); 
      if (paramObject2 == null) {
        this.clientProperties.removeAttribute(paramObject1);
      } else {
        this.clientProperties.addAttribute(paramObject1, paramObject2);
      } 
      if (this.clientProperties.getAttributeCount() == 0 && this.clientProperties.getResolveParent() == null)
        this.clientProperties = null; 
      firePropertyChange(1, "clientProperties", null, this.clientProperties);
      firePropertyChange(1, paramObject1.toString(), object, paramObject2);
    } 
  }
  
  public Enumeration getClientPropertyKeysEnumeration() {
    return (this.clientProperties == null) ? PUtil.NULL_ENUMERATION : this.clientProperties.getAttributeNames();
  }
  
  public Object getAttribute(Object paramObject1, Object paramObject2) {
    Object object = getAttribute(paramObject1);
    return (object == null) ? paramObject2 : object;
  }
  
  public boolean getBooleanAttribute(Object paramObject, boolean paramBoolean) {
    Boolean bool = (Boolean)getAttribute(paramObject);
    return (bool == null) ? paramBoolean : bool.booleanValue();
  }
  
  public int getIntegerAttribute(Object paramObject, int paramInt) {
    Number number = (Number)getAttribute(paramObject);
    return (number == null) ? paramInt : number.intValue();
  }
  
  public double getDoubleAttribute(Object paramObject, double paramDouble) {
    Number number = (Number)getAttribute(paramObject);
    return (number == null) ? paramDouble : number.doubleValue();
  }
  
  public Object getClientProperty(Object paramObject) {
    return getAttribute(paramObject);
  }
  
  public void addClientProperty(Object paramObject1, Object paramObject2) {
    addAttribute(paramObject1, paramObject2);
  }
  
  public Iterator getClientPropertyKeysIterator() {
    Enumeration enumeration = getClientPropertyKeysEnumeration();
    return new Iterator(this, enumeration) {
        private final Enumeration val$enumeration;
        
        private final PNode this$0;
        
        public boolean hasNext() {
          return this.val$enumeration.hasMoreElements();
        }
        
        public Object next() {
          return this.val$enumeration.nextElement();
        }
        
        public void remove() {
          throw new UnsupportedOperationException();
        }
      };
  }
  
  public Object clone() {
    try {
      byte[] arrayOfByte = PObjectOutputStream.toByteArray(this);
      return (new ObjectInputStream(new ByteArrayInputStream(arrayOfByte))).readObject();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } catch (ClassNotFoundException classNotFoundException) {
      classNotFoundException.printStackTrace();
    } 
    return null;
  }
  
  public Point2D localToParent(Point2D paramPoint2D) {
    return (this.transform == null) ? paramPoint2D : this.transform.transform(paramPoint2D, paramPoint2D);
  }
  
  public Dimension2D localToParent(Dimension2D paramDimension2D) {
    return (this.transform == null) ? paramDimension2D : this.transform.transform(paramDimension2D, paramDimension2D);
  }
  
  public Rectangle2D localToParent(Rectangle2D paramRectangle2D) {
    return (this.transform == null) ? paramRectangle2D : this.transform.transform(paramRectangle2D, paramRectangle2D);
  }
  
  public Point2D parentToLocal(Point2D paramPoint2D) {
    if (this.transform == null)
      return paramPoint2D; 
    try {
      return this.transform.inverseTransform(paramPoint2D, paramPoint2D);
    } catch (NoninvertibleTransformException noninvertibleTransformException) {
      noninvertibleTransformException.printStackTrace();
      return null;
    } 
  }
  
  public Dimension2D parentToLocal(Dimension2D paramDimension2D) {
    return (this.transform == null) ? paramDimension2D : this.transform.inverseTransform(paramDimension2D, paramDimension2D);
  }
  
  public Rectangle2D parentToLocal(Rectangle2D paramRectangle2D) {
    return (this.transform == null) ? paramRectangle2D : this.transform.inverseTransform(paramRectangle2D, paramRectangle2D);
  }
  
  public Point2D localToGlobal(Point2D paramPoint2D) {
    for (PNode pNode = this; pNode != null; pNode = pNode.parent)
      paramPoint2D = pNode.localToParent(paramPoint2D); 
    return paramPoint2D;
  }
  
  public Dimension2D localToGlobal(Dimension2D paramDimension2D) {
    for (PNode pNode = this; pNode != null; pNode = pNode.parent)
      paramDimension2D = pNode.localToParent(paramDimension2D); 
    return paramDimension2D;
  }
  
  public Rectangle2D localToGlobal(Rectangle2D paramRectangle2D) {
    for (PNode pNode = this; pNode != null; pNode = pNode.parent)
      paramRectangle2D = pNode.localToParent(paramRectangle2D); 
    return paramRectangle2D;
  }
  
  public Point2D globalToLocal(Point2D paramPoint2D) {
    if (this.parent != null)
      paramPoint2D = this.parent.globalToLocal(paramPoint2D); 
    return parentToLocal(paramPoint2D);
  }
  
  public Dimension2D globalToLocal(Dimension2D paramDimension2D) {
    if (this.parent != null)
      paramDimension2D = this.parent.globalToLocal(paramDimension2D); 
    return parentToLocal(paramDimension2D);
  }
  
  public Rectangle2D globalToLocal(Rectangle2D paramRectangle2D) {
    if (this.parent != null)
      paramRectangle2D = this.parent.globalToLocal(paramRectangle2D); 
    return parentToLocal(paramRectangle2D);
  }
  
  public PAffineTransform getLocalToGlobalTransform(PAffineTransform paramPAffineTransform) {
    if (this.parent != null) {
      paramPAffineTransform = this.parent.getLocalToGlobalTransform(paramPAffineTransform);
      if (this.transform != null)
        paramPAffineTransform.concatenate((AffineTransform)this.transform); 
    } else if (paramPAffineTransform == null) {
      paramPAffineTransform = getTransform();
    } else if (this.transform != null) {
      paramPAffineTransform.setTransform((AffineTransform)this.transform);
    } else {
      paramPAffineTransform.setToIdentity();
    } 
    return paramPAffineTransform;
  }
  
  public PAffineTransform getGlobalToLocalTransform(PAffineTransform paramPAffineTransform) {
    try {
      paramPAffineTransform = getLocalToGlobalTransform(paramPAffineTransform);
      paramPAffineTransform.setTransform(paramPAffineTransform.createInverse());
      return paramPAffineTransform;
    } catch (NoninvertibleTransformException noninvertibleTransformException) {
      noninvertibleTransformException.printStackTrace();
      return null;
    } 
  }
  
  public EventListenerList getListenerList() {
    return this.listenerList;
  }
  
  public void addInputEventListener(PInputEventListener paramPInputEventListener) {
    if (this.listenerList == null)
      this.listenerList = new EventListenerList(); 
    getListenerList().add(PInputEventListener.class, paramPInputEventListener);
  }
  
  public void removeInputEventListener(PInputEventListener paramPInputEventListener) {
    if (this.listenerList == null)
      return; 
    getListenerList().remove(PInputEventListener.class, paramPInputEventListener);
    if (this.listenerList.getListenerCount() == 0)
      this.listenerList = null; 
  }
  
  public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
    if (this.changeSupport == null)
      this.changeSupport = new SwingPropertyChangeSupport(this); 
    this.changeSupport.addPropertyChangeListener(paramPropertyChangeListener);
  }
  
  public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
    if (paramPropertyChangeListener == null)
      return; 
    if (this.changeSupport == null)
      this.changeSupport = new SwingPropertyChangeSupport(this); 
    this.changeSupport.addPropertyChangeListener(paramString, paramPropertyChangeListener);
  }
  
  public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
    if (this.changeSupport != null)
      this.changeSupport.removePropertyChangeListener(paramPropertyChangeListener); 
  }
  
  public void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
    if (paramPropertyChangeListener == null)
      return; 
    if (this.changeSupport == null)
      return; 
    this.changeSupport.removePropertyChangeListener(paramString, paramPropertyChangeListener);
  }
  
  public int getPropertyChangeParentMask() {
    return this.propertyChangeParentMask;
  }
  
  public void setPropertyChangeParentMask(int paramInt) {
    this.propertyChangeParentMask = paramInt;
  }
  
  protected void firePropertyChange(int paramInt, String paramString, Object paramObject1, Object paramObject2) {
    PropertyChangeEvent propertyChangeEvent = null;
    if (this.changeSupport != null) {
      propertyChangeEvent = new PropertyChangeEvent(this, paramString, paramObject1, paramObject2);
      this.changeSupport.firePropertyChange(propertyChangeEvent);
    } 
    if (this.parent != null && (paramInt & this.propertyChangeParentMask) != 0) {
      if (propertyChangeEvent == null)
        propertyChangeEvent = new PropertyChangeEvent(this, paramString, paramObject1, paramObject2); 
      this.parent.fireChildPropertyChange(propertyChangeEvent, paramInt);
    } 
  }
  
  protected void fireChildPropertyChange(PropertyChangeEvent paramPropertyChangeEvent, int paramInt) {
    if (this.changeSupport != null)
      this.changeSupport.firePropertyChange(paramPropertyChangeEvent); 
    if (this.parent != null && (paramInt & this.propertyChangeParentMask) != 0)
      this.parent.fireChildPropertyChange(paramPropertyChangeEvent, paramInt); 
  }
  
  public PBounds getBounds() {
    return (PBounds)getBoundsReference().clone();
  }
  
  public PBounds getBoundsReference() {
    return this.bounds;
  }
  
  public void startResizeBounds() {}
  
  public void endResizeBounds() {}
  
  public boolean setX(double paramDouble) {
    return setBounds(paramDouble, getY(), getWidth(), getHeight());
  }
  
  public boolean setY(double paramDouble) {
    return setBounds(getX(), paramDouble, getWidth(), getHeight());
  }
  
  public boolean setWidth(double paramDouble) {
    return setBounds(getX(), getY(), paramDouble, getHeight());
  }
  
  public boolean setHeight(double paramDouble) {
    return setBounds(getX(), getY(), getWidth(), paramDouble);
  }
  
  public boolean setBounds(Rectangle2D paramRectangle2D) {
    return setBounds(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
  }
  
  public boolean setBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    if (this.bounds.x != paramDouble1 || this.bounds.y != paramDouble2 || this.bounds.width != paramDouble3 || this.bounds.height != paramDouble4) {
      this.bounds.setRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
      if (paramDouble3 <= 0.0D || paramDouble4 <= 0.0D)
        this.bounds.reset(); 
      internalUpdateBounds(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
      invalidatePaint();
      signalBoundsChanged();
      return true;
    } 
    return false;
  }
  
  protected void internalUpdateBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {}
  
  public void resetBounds() {
    setBounds(0.0D, 0.0D, 0.0D, 0.0D);
  }
  
  public double getX() {
    return getBoundsReference().getX();
  }
  
  public double getY() {
    return getBoundsReference().getY();
  }
  
  public double getWidth() {
    return getBoundsReference().getWidth();
  }
  
  public double getHeight() {
    return getBoundsReference().getHeight();
  }
  
  public PBounds getGlobalBounds() {
    return (PBounds)localToGlobal((Rectangle2D)getBounds());
  }
  
  public boolean centerBoundsOnPoint(double paramDouble1, double paramDouble2) {
    double d1 = paramDouble1 - this.bounds.getCenterX();
    double d2 = paramDouble2 - this.bounds.getCenterY();
    return setBounds(this.bounds.x + d1, this.bounds.y + d2, this.bounds.width, this.bounds.height);
  }
  
  public void centerFullBoundsOnPoint(double paramDouble1, double paramDouble2) {
    double d1 = paramDouble1 - getFullBoundsReference().getCenterX();
    double d2 = paramDouble2 - getFullBoundsReference().getCenterY();
    offset(d1, d2);
  }
  
  public boolean intersects(Rectangle2D paramRectangle2D) {
    return (paramRectangle2D == null) ? true : getBoundsReference().intersects(paramRectangle2D);
  }
  
  public PBounds getFullBounds() {
    return (PBounds)getFullBoundsReference().clone();
  }
  
  public PBounds getFullBoundsReference() {
    validateFullBounds();
    return this.fullBoundsCache;
  }
  
  public PBounds computeFullBounds(PBounds paramPBounds) {
    PBounds pBounds = getUnionOfChildrenBounds(paramPBounds);
    pBounds.add(getBoundsReference());
    localToParent((Rectangle2D)pBounds);
    return pBounds;
  }
  
  public PBounds getUnionOfChildrenBounds(PBounds paramPBounds) {
    if (paramPBounds == null) {
      paramPBounds = new PBounds();
    } else {
      paramPBounds.resetToZero();
    } 
    int i = getChildrenCount();
    for (byte b = 0; b < i; b++) {
      PNode pNode = this.children.get(b);
      paramPBounds.add(pNode.getFullBoundsReference());
    } 
    return paramPBounds;
  }
  
  public PBounds getGlobalFullBounds() {
    PBounds pBounds = getFullBounds();
    if (this.parent != null)
      this.parent.localToGlobal((Rectangle2D)pBounds); 
    return pBounds;
  }
  
  public boolean fullIntersects(Rectangle2D paramRectangle2D) {
    return (paramRectangle2D == null) ? true : getFullBoundsReference().intersects(paramRectangle2D);
  }
  
  protected boolean getBoundsVolatile() {
    return false;
  }
  
  protected boolean getChildBoundsVolatile() {
    return this.childBoundsVolatile;
  }
  
  protected void setChildBoundsVolatile(boolean paramBoolean) {
    this.childBoundsVolatile = paramBoolean;
  }
  
  protected boolean getBoundsChanged() {
    return this.boundsChanged;
  }
  
  protected void setBoundsChanged(boolean paramBoolean) {
    this.boundsChanged = paramBoolean;
  }
  
  protected boolean getFullBoundsInvalid() {
    return this.fullBoundsInvalid;
  }
  
  protected void setFullBoundsInvalid(boolean paramBoolean) {
    this.fullBoundsInvalid = paramBoolean;
  }
  
  protected boolean getChildBoundsInvalid() {
    return this.childBoundsInvalid;
  }
  
  protected void setChildBoundsInvalid(boolean paramBoolean) {
    this.childBoundsInvalid = paramBoolean;
  }
  
  public void signalBoundsChanged() {
    invalidateFullBounds();
    setBoundsChanged(true);
    firePropertyChange(2, "bounds", null, this.bounds);
    int i = getChildrenCount();
    for (byte b = 0; b < i; b++) {
      PNode pNode = this.children.get(b);
      pNode.parentBoundsChanged();
    } 
  }
  
  public void invalidateLayout() {
    invalidateFullBounds();
  }
  
  protected void parentBoundsChanged() {}
  
  public void invalidateFullBounds() {
    setFullBoundsInvalid(true);
    for (PNode pNode = this.parent; pNode != null && !pNode.getChildBoundsInvalid(); pNode = pNode.parent)
      pNode.setChildBoundsInvalid(true); 
    if (SCENE_GRAPH_DELEGATE != null)
      SCENE_GRAPH_DELEGATE.nodeFullBoundsInvalidated(this); 
  }
  
  protected boolean validateFullBounds() {
    boolean bool = getBoundsVolatile();
    if (this.fullBoundsInvalid || this.childBoundsInvalid || bool || this.childBoundsVolatile) {
      if (bool && !this.boundsChanged)
        signalBoundsChanged(); 
      if (this.childBoundsInvalid || this.childBoundsVolatile) {
        this.childBoundsVolatile = false;
        int i = getChildrenCount();
        for (byte b = 0; b < i; b++) {
          PNode pNode = this.children.get(b);
          this.childBoundsVolatile |= pNode.validateFullBounds();
        } 
      } 
      layoutChildren();
      if (this.fullBoundsInvalid) {
        double d1 = this.fullBoundsCache.x;
        double d2 = this.fullBoundsCache.y;
        double d3 = this.fullBoundsCache.width;
        double d4 = this.fullBoundsCache.height;
        boolean bool1 = this.fullBoundsCache.isEmpty();
        this.fullBoundsCache = computeFullBounds(this.fullBoundsCache);
        boolean bool2 = (this.fullBoundsCache.x != d1 || this.fullBoundsCache.y != d2 || this.fullBoundsCache.width != d3 || this.fullBoundsCache.height != d4 || this.fullBoundsCache.isEmpty() != bool1) ? true : false;
        if (bool2) {
          if (this.parent != null)
            this.parent.invalidateFullBounds(); 
          firePropertyChange(4, "fullBounds", null, this.fullBoundsCache);
          if (this.paintInvalid && !bool1) {
            TEMP_REPAINT_BOUNDS.setRect(d1, d2, d3, d4);
            repaintFrom(TEMP_REPAINT_BOUNDS, this);
          } 
        } 
      } 
      this.boundsChanged = false;
      this.fullBoundsInvalid = false;
      this.childBoundsInvalid = false;
    } 
    return (bool || this.childBoundsVolatile);
  }
  
  protected void layoutChildren() {}
  
  public double getRotation() {
    return (this.transform == null) ? 0.0D : this.transform.getRotation();
  }
  
  public void setRotation(double paramDouble) {
    rotate(paramDouble - getRotation());
  }
  
  public void rotate(double paramDouble) {
    rotateAboutPoint(paramDouble, 0.0D, 0.0D);
  }
  
  public void rotateInPlace(double paramDouble) {
    PBounds pBounds = getFullBoundsReference();
    double d1 = pBounds.x;
    double d2 = pBounds.y;
    rotateAboutPoint(paramDouble, 0.0D, 0.0D);
    pBounds = getFullBoundsReference();
    offset(d1 - pBounds.x, d2 - pBounds.y);
  }
  
  public void rotateAboutPoint(double paramDouble, Point2D paramPoint2D) {
    rotateAboutPoint(paramDouble, paramPoint2D.getX(), paramPoint2D.getY());
  }
  
  public void rotateAboutPoint(double paramDouble1, double paramDouble2, double paramDouble3) {
    getTransformReference(true).rotate(paramDouble1, paramDouble2, paramDouble3);
    invalidatePaint();
    invalidateFullBounds();
    firePropertyChange(8, "transform", null, this.transform);
  }
  
  public double getGlobalRotation() {
    return getLocalToGlobalTransform(null).getRotation();
  }
  
  public void setGlobalRotation(double paramDouble) {
    if (this.parent != null) {
      setRotation(paramDouble - this.parent.getGlobalRotation());
    } else {
      setRotation(paramDouble);
    } 
  }
  
  public double getScale() {
    return (this.transform == null) ? 1.0D : this.transform.getScale();
  }
  
  public void setScale(double paramDouble) {
    if (paramDouble == 0.0D)
      throw new RuntimeException("Can't set scale to 0"); 
    scale(paramDouble / getScale());
  }
  
  public void scale(double paramDouble) {
    scaleAboutPoint(paramDouble, 0.0D, 0.0D);
  }
  
  public void scaleAboutPoint(double paramDouble, Point2D paramPoint2D) {
    scaleAboutPoint(paramDouble, paramPoint2D.getX(), paramPoint2D.getY());
  }
  
  public void scaleAboutPoint(double paramDouble1, double paramDouble2, double paramDouble3) {
    getTransformReference(true).scaleAboutPoint(paramDouble1, paramDouble2, paramDouble3);
    invalidatePaint();
    invalidateFullBounds();
    firePropertyChange(8, "transform", null, this.transform);
  }
  
  public double getGlobalScale() {
    return getLocalToGlobalTransform(null).getScale();
  }
  
  public void setGlobalScale(double paramDouble) {
    if (this.parent != null) {
      setScale(paramDouble / this.parent.getGlobalScale());
    } else {
      setScale(paramDouble);
    } 
  }
  
  public double getXOffset() {
    return (this.transform == null) ? 0.0D : this.transform.getTranslateX();
  }
  
  public double getYOffset() {
    return (this.transform == null) ? 0.0D : this.transform.getTranslateY();
  }
  
  public Point2D getOffset() {
    return (this.transform == null) ? new Point2D.Double() : new Point2D.Double(this.transform.getTranslateX(), this.transform.getTranslateY());
  }
  
  public void setOffset(Point2D paramPoint2D) {
    setOffset(paramPoint2D.getX(), paramPoint2D.getY());
  }
  
  public void setOffset(double paramDouble1, double paramDouble2) {
    getTransformReference(true).setOffset(paramDouble1, paramDouble2);
    invalidatePaint();
    invalidateFullBounds();
    firePropertyChange(8, "transform", null, this.transform);
  }
  
  public void offset(double paramDouble1, double paramDouble2) {
    getTransformReference(true);
    setOffset(this.transform.getTranslateX() + paramDouble1, this.transform.getTranslateY() + paramDouble2);
  }
  
  public void translate(double paramDouble1, double paramDouble2) {
    getTransformReference(true).translate(paramDouble1, paramDouble2);
    invalidatePaint();
    invalidateFullBounds();
    firePropertyChange(8, "transform", null, this.transform);
  }
  
  public Point2D getGlobalTranslation() {
    Point2D point2D = getOffset();
    if (this.parent != null)
      this.parent.localToGlobal(point2D); 
    return point2D;
  }
  
  public void setGlobalTranslation(Point2D paramPoint2D) {
    if (this.parent != null)
      this.parent.getGlobalToLocalTransform(null).transform(paramPoint2D, paramPoint2D); 
    setOffset(paramPoint2D);
  }
  
  public void transformBy(AffineTransform paramAffineTransform) {
    getTransformReference(true).concatenate(paramAffineTransform);
    invalidatePaint();
    invalidateFullBounds();
    firePropertyChange(8, "transform", null, this.transform);
  }
  
  public static double lerp(double paramDouble1, double paramDouble2, double paramDouble3) {
    return paramDouble2 + paramDouble1 * (paramDouble3 - paramDouble2);
  }
  
  public void position(Point2D paramPoint2D1, Point2D paramPoint2D2, Rectangle2D paramRectangle2D, int paramInt) {
    if (this.parent != null) {
      PBounds pBounds = getGlobalFullBounds();
      double d1 = lerp(paramPoint2D1.getX(), pBounds.getX(), pBounds.getX() + pBounds.getWidth());
      double d2 = lerp(paramPoint2D1.getY(), pBounds.getY(), pBounds.getY() + pBounds.getHeight());
      double d3 = lerp(paramPoint2D2.getX(), paramRectangle2D.getX(), paramRectangle2D.getX() + paramRectangle2D.getWidth());
      double d4 = lerp(paramPoint2D2.getY(), paramRectangle2D.getY(), paramRectangle2D.getY() + paramRectangle2D.getHeight());
      Point2D.Double double_1 = new Point2D.Double(d1, d2);
      globalToLocal(double_1);
      Point2D.Double double_2 = new Point2D.Double(d3, d4);
      globalToLocal(double_2);
      double d5 = double_2.getX() - double_1.getX();
      double d6 = double_2.getY() - double_1.getY();
      PAffineTransform pAffineTransform = new PAffineTransform((AffineTransform)getTransformReference(true));
      pAffineTransform.translate(d5, d6);
      animateToTransform((AffineTransform)pAffineTransform, paramInt);
    } 
  }
  
  public PAffineTransform getTransform() {
    return (this.transform == null) ? new PAffineTransform() : (PAffineTransform)this.transform.clone();
  }
  
  public PAffineTransform getTransformReference(boolean paramBoolean) {
    if (this.transform == null && paramBoolean)
      this.transform = new PAffineTransform(); 
    return this.transform;
  }
  
  public PAffineTransform getInverseTransform() {
    if (this.transform == null)
      return new PAffineTransform(); 
    try {
      return new PAffineTransform(this.transform.createInverse());
    } catch (NoninvertibleTransformException noninvertibleTransformException) {
      noninvertibleTransformException.printStackTrace();
      return null;
    } 
  }
  
  public void setTransform(AffineTransform paramAffineTransform) {
    if (paramAffineTransform == null) {
      this.transform = null;
    } else {
      getTransformReference(true).setTransform(paramAffineTransform);
    } 
    invalidatePaint();
    invalidateFullBounds();
    firePropertyChange(8, "transform", null, this.transform);
  }
  
  public boolean getPaintInvalid() {
    return this.paintInvalid;
  }
  
  public void setPaintInvalid(boolean paramBoolean) {
    this.paintInvalid = paramBoolean;
  }
  
  public boolean getChildPaintInvalid() {
    return this.childPaintInvalid;
  }
  
  public void setChildPaintInvalid(boolean paramBoolean) {
    this.childPaintInvalid = paramBoolean;
  }
  
  public void invalidatePaint() {
    setPaintInvalid(true);
    for (PNode pNode = this.parent; pNode != null && !pNode.getChildPaintInvalid(); pNode = pNode.parent)
      pNode.setChildPaintInvalid(true); 
    if (SCENE_GRAPH_DELEGATE != null)
      SCENE_GRAPH_DELEGATE.nodePaintInvalidated(this); 
  }
  
  public void validateFullPaint() {
    if (getPaintInvalid()) {
      repaint();
      setPaintInvalid(false);
    } 
    if (getChildPaintInvalid()) {
      int i = getChildrenCount();
      for (byte b = 0; b < i; b++) {
        PNode pNode = this.children.get(b);
        pNode.validateFullPaint();
      } 
      setChildPaintInvalid(false);
    } 
  }
  
  public void repaint() {
    TEMP_REPAINT_BOUNDS.setRect(getFullBoundsReference());
    repaintFrom(TEMP_REPAINT_BOUNDS, this);
  }
  
  public void repaintFrom(PBounds paramPBounds, PNode paramPNode) {
    if (this.parent != null) {
      if (paramPNode != this) {
        localToParent((Rectangle2D)paramPBounds);
      } else if (!getVisible()) {
        return;
      } 
      this.parent.repaintFrom(paramPBounds, this);
    } 
  }
  
  public boolean isOpaque(Rectangle2D paramRectangle2D) {
    return false;
  }
  
  public boolean getOccluded() {
    return this.occluded;
  }
  
  public void setOccluded(boolean paramBoolean) {
    this.occluded = paramBoolean;
  }
  
  public boolean getVisible() {
    return this.visible;
  }
  
  public void setVisible(boolean paramBoolean) {
    if (getVisible() != paramBoolean) {
      if (!paramBoolean)
        repaint(); 
      this.visible = paramBoolean;
      firePropertyChange(16, "visible", null, null);
      invalidatePaint();
    } 
  }
  
  public Paint getPaint() {
    return this.paint;
  }
  
  public void setPaint(Paint paramPaint) {
    if (this.paint == paramPaint)
      return; 
    Paint paint = this.paint;
    this.paint = paramPaint;
    invalidatePaint();
    firePropertyChange(32, "paint", paint, this.paint);
  }
  
  public float getTransparency() {
    return this.transparency;
  }
  
  public void setTransparency(float paramFloat) {
    if (this.transparency == paramFloat)
      return; 
    this.transparency = paramFloat;
    invalidatePaint();
    firePropertyChange(64, "transparency", null, null);
  }
  
  protected void paint(PPaintContext paramPPaintContext) {
    if (this.paint != null) {
      Graphics2D graphics2D = paramPPaintContext.getGraphics();
      graphics2D.setPaint(this.paint);
      graphics2D.fill((Shape)getBoundsReference());
    } 
  }
  
  public void fullPaint(PPaintContext paramPPaintContext) {
    if (getVisible() && fullIntersects(paramPPaintContext.getLocalClip())) {
      paramPPaintContext.pushTransform(this.transform);
      paramPPaintContext.pushTransparency(this.transparency);
      if (!getOccluded())
        paint(paramPPaintContext); 
      int i = getChildrenCount();
      for (byte b = 0; b < i; b++) {
        PNode pNode = this.children.get(b);
        pNode.fullPaint(paramPPaintContext);
      } 
      paintAfterChildren(paramPPaintContext);
      paramPPaintContext.popTransparency(this.transparency);
      paramPPaintContext.popTransform(this.transform);
    } 
  }
  
  protected void paintAfterChildren(PPaintContext paramPPaintContext) {}
  
  public Image toImage() {
    PBounds pBounds = getFullBoundsReference();
    return toImage((int)Math.ceil(pBounds.getWidth()), (int)Math.ceil(pBounds.getHeight()), null);
  }
  
  public Image toImage(int paramInt1, int paramInt2, Paint paramPaint) {
    PBounds pBounds = getFullBounds();
    pBounds.expandNearestIntegerDimensions();
    if (paramInt1 / pBounds.width < paramInt2 / pBounds.height) {
      double d = paramInt1 / pBounds.width;
      paramInt2 = (int)(pBounds.height * d);
    } else {
      double d = paramInt2 / pBounds.height;
      paramInt1 = (int)(pBounds.width * d);
    } 
    GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    BufferedImage bufferedImage = graphicsConfiguration.createCompatibleImage(paramInt1, paramInt2, 3);
    return toImage(bufferedImage, paramPaint);
  }
  
  public Image toImage(BufferedImage paramBufferedImage, Paint paramPaint) {
    int i = paramBufferedImage.getWidth();
    int j = paramBufferedImage.getHeight();
    Graphics2D graphics2D = paramBufferedImage.createGraphics();
    if (paramPaint != null) {
      graphics2D.setPaint(paramPaint);
      graphics2D.fillRect(0, 0, i, j);
    } 
    Paper paper = new Paper();
    paper.setSize(i, j);
    paper.setImageableArea(0.0D, 0.0D, i, j);
    PageFormat pageFormat = new PageFormat();
    pageFormat.setPaper(paper);
    print(graphics2D, pageFormat, 0);
    return paramBufferedImage;
  }
  
  public void print() {
    PrinterJob printerJob = PrinterJob.getPrinterJob();
    PageFormat pageFormat = printerJob.defaultPage();
    Book book = new Book();
    book.append(this, pageFormat);
    printerJob.setPageable(book);
    if (printerJob.printDialog())
      try {
        printerJob.print();
      } catch (Exception exception) {
        System.out.println("Error Printing");
        exception.printStackTrace();
      }  
  }
  
  public int print(Graphics paramGraphics, PageFormat paramPageFormat, int paramInt) {
    if (paramInt != 0)
      return 1; 
    Graphics2D graphics2D = (Graphics2D)paramGraphics;
    PBounds pBounds = getFullBounds();
    pBounds.expandNearestIntegerDimensions();
    graphics2D.setClip(0, 0, (int)paramPageFormat.getWidth(), (int)paramPageFormat.getHeight());
    graphics2D.translate(paramPageFormat.getImageableX(), paramPageFormat.getImageableY());
    double d = paramPageFormat.getImageableWidth() / pBounds.getWidth();
    if (paramPageFormat.getImageableHeight() / pBounds.getHeight() < d)
      d = paramPageFormat.getImageableHeight() / pBounds.getHeight(); 
    graphics2D.scale(d, d);
    graphics2D.translate(-pBounds.x, -pBounds.y);
    PPaintContext pPaintContext = new PPaintContext(graphics2D);
    pPaintContext.setRenderQuality(1);
    fullPaint(pPaintContext);
    return 0;
  }
  
  public boolean getPickable() {
    return this.pickable;
  }
  
  public void setPickable(boolean paramBoolean) {
    if (getPickable() != paramBoolean) {
      this.pickable = paramBoolean;
      firePropertyChange(128, "pickable", null, null);
    } 
  }
  
  public boolean getChildrenPickable() {
    return this.childrenPickable;
  }
  
  public void setChildrenPickable(boolean paramBoolean) {
    if (getChildrenPickable() != paramBoolean) {
      this.childrenPickable = paramBoolean;
      firePropertyChange(256, "childrenPickable", null, null);
    } 
  }
  
  protected boolean pick(PPickPath paramPPickPath) {
    return false;
  }
  
  public boolean fullPick(PPickPath paramPPickPath) {
    if ((getPickable() || getChildrenPickable()) && fullIntersects((Rectangle2D)paramPPickPath.getPickBounds())) {
      paramPPickPath.pushNode(this);
      paramPPickPath.pushTransform(this.transform);
      boolean bool = (getPickable() && paramPPickPath.acceptsNode(this)) ? true : false;
      if (bool && pick(paramPPickPath))
        return true; 
      if (getChildrenPickable()) {
        int i = getChildrenCount();
        for (int j = i - 1; j >= 0; j--) {
          PNode pNode = this.children.get(j);
          if (pNode.fullPick(paramPPickPath))
            return true; 
        } 
      } 
      if (bool && pickAfterChildren(paramPPickPath))
        return true; 
      paramPPickPath.popTransform(this.transform);
      paramPPickPath.popNode(this);
    } 
    return false;
  }
  
  public void findIntersectingNodes(Rectangle2D paramRectangle2D, ArrayList paramArrayList) {
    if (fullIntersects(paramRectangle2D)) {
      Rectangle2D rectangle2D = parentToLocal((Rectangle2D)paramRectangle2D.clone());
      if (intersects(rectangle2D))
        paramArrayList.add(this); 
      int i = getChildrenCount();
      for (int j = i - 1; j >= 0; j--) {
        PNode pNode = this.children.get(j);
        pNode.findIntersectingNodes(rectangle2D, paramArrayList);
      } 
    } 
  }
  
  protected boolean pickAfterChildren(PPickPath paramPPickPath) {
    return intersects((Rectangle2D)paramPPickPath.getPickBounds());
  }
  
  public void addChild(PNode paramPNode) {
    int i = getChildrenCount();
    if (paramPNode.parent == this)
      i--; 
    addChild(i, paramPNode);
  }
  
  public void addChild(int paramInt, PNode paramPNode) {
    PNode pNode = paramPNode.getParent();
    if (pNode != null)
      pNode.removeChild(paramPNode); 
    paramPNode.setParent(this);
    getChildrenReference().add(paramInt, paramPNode);
    paramPNode.invalidatePaint();
    invalidateFullBounds();
    firePropertyChange(512, "children", null, this.children);
  }
  
  public void addChildren(Collection paramCollection) {
    for (PNode pNode : paramCollection)
      addChild(pNode); 
  }
  
  public boolean isAncestorOf(PNode paramPNode) {
    for (PNode pNode = paramPNode.parent; pNode != null; pNode = pNode.parent) {
      if (pNode == this)
        return true; 
    } 
    return false;
  }
  
  public boolean isDescendentOf(PNode paramPNode) {
    for (PNode pNode = this.parent; pNode != null; pNode = pNode.parent) {
      if (pNode == paramPNode)
        return true; 
    } 
    return false;
  }
  
  public boolean isDescendentOfRoot() {
    return (getRoot() != null);
  }
  
  public void moveToBack() {
    PNode pNode = this.parent;
    if (pNode != null) {
      pNode.removeChild(this);
      pNode.addChild(0, this);
    } 
  }
  
  public void moveInBackOf(PNode paramPNode) {
    PNode pNode = this.parent;
    if (pNode != null && pNode == paramPNode.getParent()) {
      pNode.removeChild(this);
      int i = pNode.indexOfChild(paramPNode);
      pNode.addChild(i, this);
    } 
  }
  
  public void moveToFront() {
    PNode pNode = this.parent;
    if (pNode != null) {
      pNode.removeChild(this);
      pNode.addChild(this);
    } 
  }
  
  public void moveInFrontOf(PNode paramPNode) {
    PNode pNode = this.parent;
    if (pNode != null && pNode == paramPNode.getParent()) {
      pNode.removeChild(this);
      int i = pNode.indexOfChild(paramPNode);
      pNode.addChild(i + 1, this);
    } 
  }
  
  public PNode getParent() {
    return this.parent;
  }
  
  public void setParent(PNode paramPNode) {
    PNode pNode = this.parent;
    this.parent = paramPNode;
    firePropertyChange(1024, "parent", pNode, this.parent);
  }
  
  public int indexOfChild(PNode paramPNode) {
    return (this.children == null) ? -1 : this.children.indexOf(paramPNode);
  }
  
  public PNode removeChild(PNode paramPNode) {
    return removeChild(indexOfChild(paramPNode));
  }
  
  public PNode removeChild(int paramInt) {
    PNode pNode = this.children.remove(paramInt);
    if (this.children.size() == 0)
      this.children = null; 
    pNode.repaint();
    pNode.setParent(null);
    invalidateFullBounds();
    firePropertyChange(512, "children", null, this.children);
    return pNode;
  }
  
  public void removeChildren(Collection paramCollection) {
    for (PNode pNode : paramCollection)
      removeChild(pNode); 
  }
  
  public void removeAllChildren() {
    if (this.children != null) {
      int i = this.children.size();
      for (byte b = 0; b < i; b++) {
        PNode pNode = this.children.get(b);
        pNode.setParent(null);
      } 
      this.children = null;
      invalidatePaint();
      invalidateFullBounds();
      firePropertyChange(512, "children", null, this.children);
    } 
  }
  
  public void removeFromParent() {
    if (this.parent != null)
      this.parent.removeChild(this); 
  }
  
  public void reparent(PNode paramPNode) {
    PAffineTransform pAffineTransform1 = getLocalToGlobalTransform(null);
    PAffineTransform pAffineTransform2 = paramPNode.getGlobalToLocalTransform(null);
    pAffineTransform2.concatenate((AffineTransform)pAffineTransform1);
    removeFromParent();
    setTransform((AffineTransform)pAffineTransform2);
    paramPNode.addChild(this);
    computeFullBounds(this.fullBoundsCache);
  }
  
  public void replaceWith(PNode paramPNode) {
    if (this.parent != null) {
      PNode pNode = this.parent;
      int i = pNode.getChildrenReference().indexOf(this);
      pNode.removeChild(this);
      pNode.addChild(i, paramPNode);
    } 
  }
  
  public int getChildrenCount() {
    return (this.children == null) ? 0 : this.children.size();
  }
  
  public PNode getChild(int paramInt) {
    return this.children.get(paramInt);
  }
  
  public List getChildrenReference() {
    if (this.children == null)
      this.children = new ArrayList(); 
    return this.children;
  }
  
  public ListIterator getChildrenIterator() {
    return (this.children == null) ? Collections.EMPTY_LIST.listIterator() : Collections.unmodifiableList(this.children).listIterator();
  }
  
  public PRoot getRoot() {
    return (this.parent != null) ? this.parent.getRoot() : null;
  }
  
  public Collection getAllNodes() {
    return getAllNodes(null, null);
  }
  
  public Collection getAllNodes(PNodeFilter paramPNodeFilter, Collection paramCollection) {
    if (paramCollection == null)
      paramCollection = new ArrayList(); 
    if (paramPNodeFilter == null || paramPNodeFilter.accept(this))
      paramCollection.add(this); 
    if (paramPNodeFilter == null || paramPNodeFilter.acceptChildrenOf(this)) {
      int i = getChildrenCount();
      for (byte b = 0; b < i; b++) {
        PNode pNode = this.children.get(b);
        pNode.getAllNodes(paramPNodeFilter, paramCollection);
      } 
    } 
    return paramCollection;
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    ((PObjectOutputStream)paramObjectOutputStream).writeConditionalObject(this.parent);
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    paramObjectInputStream.defaultReadObject();
    this.parent = (PNode)paramObjectInputStream.readObject();
  }
  
  public String toString() {
    String str = super.toString().replaceAll(".*\\.", "");
    return str + "[" + paramString() + "]";
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("bounds=" + ((this.bounds == null) ? "null" : this.bounds.toString()));
    stringBuffer.append(",fullBounds=" + ((this.fullBoundsCache == null) ? "null" : this.fullBoundsCache.toString()));
    stringBuffer.append(",transform=" + ((this.transform == null) ? "null" : this.transform.toString()));
    stringBuffer.append(",paint=" + ((this.paint == null) ? "null" : this.paint.toString()));
    stringBuffer.append(",transparency=" + this.transparency);
    stringBuffer.append(",childrenCount=" + getChildrenCount());
    if (this.fullBoundsInvalid)
      stringBuffer.append(",fullBoundsInvalid"); 
    if (this.pickable)
      stringBuffer.append(",pickable"); 
    if (this.childrenPickable)
      stringBuffer.append(",childrenPickable"); 
    if (this.visible)
      stringBuffer.append(",visible"); 
    return stringBuffer.toString();
  }
  
  public static interface PSceneGraphDelegate {
    void nodePaintInvalidated(PNode param1PNode);
    
    void nodeFullBoundsInvalidated(PNode param1PNode);
  }
}
