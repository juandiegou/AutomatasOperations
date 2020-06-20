package edu.umd.cs.piccolox.swt;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PComponent;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.PRoot;
import edu.umd.cs.piccolo.event.PInputEventListener;
import edu.umd.cs.piccolo.event.PPanEventHandler;
import edu.umd.cs.piccolo.event.PZoomEventHandler;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PDebug;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolo.util.PStack;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.InputEvent;
import java.awt.geom.Rectangle2D;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

public class PSWTCanvas extends Composite implements PComponent {
  public static PSWTCanvas CURRENT_CANVAS = null;
  
  private Image backBuffer;
  
  private boolean doubleBuffered = true;
  
  private PCamera camera;
  
  private PStack cursorStack;
  
  private Cursor curCursor;
  
  private int interacting;
  
  private int defaultRenderQuality;
  
  private int animatingRenderQuality;
  
  private int interactingRenderQuality;
  
  private PPanEventHandler panEventHandler;
  
  private PZoomEventHandler zoomEventHandler;
  
  private boolean paintingImmediately;
  
  private boolean animatingOnLastPaint;
  
  private boolean processInputsScheduled;
  
  private boolean isButton1Pressed;
  
  private boolean isButton2Pressed;
  
  private boolean isButton3Pressed;
  
  private boolean lastModifiers;
  
  public PSWTCanvas(Composite paramComposite, int paramInt) {
    super(paramComposite, paramInt | 0x40000 | 0x100000);
    CURRENT_CANVAS = this;
    this.cursorStack = new PStack();
    setCamera(createBasicSceneGraph());
    installInputSources();
    setDefaultRenderQuality(1);
    setAnimatingRenderQuality(0);
    setInteractingRenderQuality(0);
    this.panEventHandler = new PPanEventHandler();
    this.zoomEventHandler = new PZoomEventHandler();
    addInputEventListener((PInputEventListener)this.panEventHandler);
    addInputEventListener((PInputEventListener)this.zoomEventHandler);
    addPaintListener(new PaintListener(this) {
          private final PSWTCanvas this$0;
          
          public void paintControl(PaintEvent param1PaintEvent) {
            this.this$0.paintComponent(param1PaintEvent.gc, param1PaintEvent.x, param1PaintEvent.y, param1PaintEvent.width, param1PaintEvent.height);
          }
        });
    SWTGraphics2D.incrementGCCount();
    addDisposeListener(new DisposeListener(this) {
          private final PSWTCanvas this$0;
          
          public void widgetDisposed(DisposeEvent param1DisposeEvent) {
            this.this$0.getRoot().getActivityScheduler().removeAllActivities();
            SWTGraphics2D.decrementGCCount();
          }
        });
  }
  
  public PPanEventHandler getPanEventHandler() {
    return this.panEventHandler;
  }
  
  public PZoomEventHandler getZoomEventHandler() {
    return this.zoomEventHandler;
  }
  
  public PCamera getCamera() {
    return this.camera;
  }
  
  public void setCamera(PCamera paramPCamera) {
    if (this.camera != null)
      this.camera.setComponent(null); 
    this.camera = paramPCamera;
    if (this.camera != null) {
      this.camera.setComponent(this);
      Rectangle rectangle = getBounds();
      this.camera.setBounds(new Rectangle2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height));
    } 
  }
  
  public PRoot getRoot() {
    return this.camera.getRoot();
  }
  
  public PLayer getLayer() {
    return this.camera.getLayer(0);
  }
  
  public void addInputEventListener(PInputEventListener paramPInputEventListener) {
    getCamera().addInputEventListener(paramPInputEventListener);
  }
  
  public void removeInputEventListener(PInputEventListener paramPInputEventListener) {
    getCamera().removeInputEventListener(paramPInputEventListener);
  }
  
  public PCamera createBasicSceneGraph() {
    PSWTRoot pSWTRoot = new PSWTRoot(this);
    PLayer pLayer = new PLayer();
    PCamera pCamera = new PCamera();
    pSWTRoot.addChild((PNode)pCamera);
    pSWTRoot.addChild((PNode)pLayer);
    pCamera.addLayer(pLayer);
    return pCamera;
  }
  
  public boolean getInteracting() {
    return (this.interacting > 0);
  }
  
  public boolean getAnimating() {
    return getRoot().getActivityScheduler().getAnimating();
  }
  
  public void setInteracting(boolean paramBoolean) {
    if (paramBoolean) {
      this.interacting++;
    } else {
      this.interacting--;
    } 
    if (!getInteracting())
      repaint(); 
  }
  
  public boolean getDoubleBuffered() {
    return this.doubleBuffered;
  }
  
  public void setDoubleBuffered(boolean paramBoolean) {
    this.doubleBuffered = paramBoolean;
  }
  
  public void setDefaultRenderQuality(int paramInt) {
    this.defaultRenderQuality = paramInt;
    repaint();
  }
  
  public void setAnimatingRenderQuality(int paramInt) {
    this.animatingRenderQuality = paramInt;
    repaint();
  }
  
  public void setInteractingRenderQuality(int paramInt) {
    this.interactingRenderQuality = paramInt;
    repaint();
  }
  
  public void pushCursor(Cursor paramCursor) {
    Cursor cursor = null;
    if (paramCursor.getType() == 8) {
      cursor = new Cursor((Device)getDisplay(), 10);
    } else if (paramCursor.getType() == 7) {
      cursor = new Cursor((Device)getDisplay(), 14);
    } else if (paramCursor.getType() == 6) {
      cursor = new Cursor((Device)getDisplay(), 17);
    } else if (paramCursor.getType() == 9) {
      cursor = new Cursor((Device)getDisplay(), 11);
    } else if (paramCursor.getType() == 5) {
      cursor = new Cursor((Device)getDisplay(), 15);
    } else if (paramCursor.getType() == 4) {
      cursor = new Cursor((Device)getDisplay(), 16);
    } else if (paramCursor.getType() == 11) {
      cursor = new Cursor((Device)getDisplay(), 12);
    } else if (paramCursor.getType() == 10) {
      cursor = new Cursor((Device)getDisplay(), 13);
    } else if (paramCursor.getType() == 2) {
      cursor = new Cursor((Device)getDisplay(), 19);
    } else if (paramCursor.getType() == 12) {
      cursor = new Cursor((Device)getDisplay(), 21);
    } else if (paramCursor.getType() == 13) {
      cursor = new Cursor((Device)getDisplay(), 5);
    } else if (paramCursor.getType() == 1) {
      cursor = new Cursor((Device)getDisplay(), 2);
    } else if (paramCursor.getType() == 3) {
      cursor = new Cursor((Device)getDisplay(), 1);
    } 
    if (cursor != null) {
      if (this.curCursor != null)
        this.cursorStack.push(this.curCursor); 
      this.curCursor = cursor;
      setCursor(cursor);
    } 
  }
  
  public void popCursor() {
    if (this.curCursor != null)
      this.curCursor.dispose(); 
    if (!this.cursorStack.isEmpty()) {
      this.curCursor = (Cursor)this.cursorStack.pop();
    } else {
      this.curCursor = null;
    } 
    setCursor(this.curCursor);
  }
  
  protected void installInputSources() {
    addMouseListener(new MouseListener(this) {
          private final PSWTCanvas this$0;
          
          public void mouseDown(MouseEvent param1MouseEvent) {
            boolean bool = false;
            switch (param1MouseEvent.button) {
              case 1:
                if (this.this$0.isButton1Pressed)
                  bool = true; 
                this.this$0.isButton1Pressed = true;
                break;
              case 2:
                if (this.this$0.isButton2Pressed)
                  bool = true; 
                this.this$0.isButton2Pressed = true;
                break;
              case 3:
                if (this.this$0.isButton3Pressed)
                  bool = true; 
                this.this$0.isButton3Pressed = true;
                break;
            } 
            if (bool) {
              PSWTMouseEvent pSWTMouseEvent1 = new PSWTMouseEvent(param1MouseEvent, 502, 1);
              this.this$0.sendInputEventToInputManager(pSWTMouseEvent1, 502);
            } 
            PSWTMouseEvent pSWTMouseEvent = new PSWTMouseEvent(param1MouseEvent, 501, 1);
            this.this$0.sendInputEventToInputManager(pSWTMouseEvent, 501);
          }
          
          public void mouseUp(MouseEvent param1MouseEvent) {
            boolean bool = false;
            switch (param1MouseEvent.button) {
              case 1:
                if (!this.this$0.isButton1Pressed)
                  bool = true; 
                this.this$0.isButton1Pressed = false;
                break;
              case 2:
                if (!this.this$0.isButton2Pressed)
                  bool = true; 
                this.this$0.isButton2Pressed = false;
                break;
              case 3:
                if (!this.this$0.isButton3Pressed)
                  bool = true; 
                this.this$0.isButton3Pressed = false;
                break;
            } 
            if (bool) {
              PSWTMouseEvent pSWTMouseEvent1 = new PSWTMouseEvent(param1MouseEvent, 501, 1);
              this.this$0.sendInputEventToInputManager(pSWTMouseEvent1, 501);
            } 
            PSWTMouseEvent pSWTMouseEvent = new PSWTMouseEvent(param1MouseEvent, 502, 1);
            this.this$0.sendInputEventToInputManager(pSWTMouseEvent, 502);
          }
          
          public void mouseDoubleClick(MouseEvent param1MouseEvent) {
            PSWTMouseEvent pSWTMouseEvent = new PSWTMouseEvent(param1MouseEvent, 501, 2);
            this.this$0.sendInputEventToInputManager(pSWTMouseEvent, 501);
            pSWTMouseEvent = new PSWTMouseEvent(param1MouseEvent, 502, 2);
            this.this$0.sendInputEventToInputManager(pSWTMouseEvent, 502);
          }
        });
    addMouseMoveListener(new MouseMoveListener(this) {
          private final PSWTCanvas this$0;
          
          public void mouseMove(MouseEvent param1MouseEvent) {
            if (this.this$0.isButton1Pressed || this.this$0.isButton2Pressed || this.this$0.isButton3Pressed) {
              PSWTMouseEvent pSWTMouseEvent = new PSWTMouseEvent(param1MouseEvent, 506, 1);
              this.this$0.sendInputEventToInputManager(pSWTMouseEvent, 506);
            } else {
              PSWTMouseEvent pSWTMouseEvent = new PSWTMouseEvent(param1MouseEvent, 503, 1);
              this.this$0.sendInputEventToInputManager(pSWTMouseEvent, 503);
            } 
          }
        });
    addKeyListener(new KeyListener(this) {
          private final PSWTCanvas this$0;
          
          public void keyPressed(KeyEvent param1KeyEvent) {
            PSWTKeyEvent pSWTKeyEvent = new PSWTKeyEvent(param1KeyEvent, 401);
            this.this$0.sendInputEventToInputManager(pSWTKeyEvent, 401);
          }
          
          public void keyReleased(KeyEvent param1KeyEvent) {
            PSWTKeyEvent pSWTKeyEvent = new PSWTKeyEvent(param1KeyEvent, 402);
            this.this$0.sendInputEventToInputManager(pSWTKeyEvent, 402);
          }
        });
  }
  
  protected void sendInputEventToInputManager(InputEvent paramInputEvent, int paramInt) {
    getRoot().getDefaultInputManager().processEventFromCamera(paramInputEvent, paramInt, getCamera());
  }
  
  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.camera.setBounds(this.camera.getX(), this.camera.getY(), paramInt3, paramInt4);
    if (this.backBuffer == null || (this.backBuffer.getBounds()).width < paramInt3 || (this.backBuffer.getBounds()).height < paramInt4)
      this.backBuffer = new Image((Device)getDisplay(), paramInt3, paramInt4); 
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void repaint() {
    redraw();
  }
  
  public void repaint(PBounds paramPBounds) {
    paramPBounds.expandNearestIntegerDimensions();
    paramPBounds.inset(-1.0D, -1.0D);
    redraw((int)paramPBounds.x, (int)paramPBounds.y, (int)paramPBounds.width, (int)paramPBounds.height, true);
  }
  
  public void paintComponent(GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    PDebug.startProcessingOutput();
    GC gC = null;
    SWTGraphics2D sWTGraphics2D = null;
    if (this.doubleBuffered) {
      gC = new GC((Drawable)this.backBuffer);
      sWTGraphics2D = new SWTGraphics2D(gC, (Device)getDisplay());
    } else {
      sWTGraphics2D = new SWTGraphics2D(paramGC, (Device)getDisplay());
    } 
    sWTGraphics2D.setColor(Color.white);
    sWTGraphics2D.setBackground(Color.white);
    Rectangle rectangle = getBounds();
    sWTGraphics2D.fillRect(0, 0, rectangle.width, rectangle.height);
    if (PDebug.debugRegionManagement) {
      Rectangle rectangle1 = paramGC.getClipping();
      Rectangle2D.Double double_ = new Rectangle2D.Double(rectangle1.x, rectangle1.y, rectangle1.width, rectangle1.height);
      sWTGraphics2D.setBackground(PDebug.getDebugPaintColor());
      sWTGraphics2D.fill(double_);
    } 
    PPaintContext pPaintContext = new PPaintContext(sWTGraphics2D);
    if (getInteracting() || getAnimating()) {
      if (this.interactingRenderQuality > this.animatingRenderQuality) {
        pPaintContext.setRenderQuality(this.interactingRenderQuality);
      } else {
        pPaintContext.setRenderQuality(this.animatingRenderQuality);
      } 
    } else {
      pPaintContext.setRenderQuality(this.defaultRenderQuality);
    } 
    this.camera.fullPaint(pPaintContext);
    if (!getAnimating() && this.animatingOnLastPaint)
      repaint(); 
    this.animatingOnLastPaint = getAnimating();
    boolean bool = PDebug.debugRegionManagement;
    PDebug.debugRegionManagement = false;
    PDebug.endProcessingOutput(sWTGraphics2D);
    PDebug.debugRegionManagement = bool;
    if (this.doubleBuffered) {
      paramGC.drawImage(this.backBuffer, 0, 0);
      gC.dispose();
    } 
  }
  
  public void paintImmediately() {
    if (this.paintingImmediately)
      return; 
    this.paintingImmediately = true;
    redraw();
    update();
    this.paintingImmediately = false;
  }
}
