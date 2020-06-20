package edu.umd.cs.piccolo;

import edu.umd.cs.piccolo.event.PInputEventListener;
import edu.umd.cs.piccolo.event.PPanEventHandler;
import edu.umd.cs.piccolo.event.PZoomEventHandler;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PDebug;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolo.util.PStack;
import edu.umd.cs.piccolo.util.PUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JComponent;
import javax.swing.RepaintManager;
import javax.swing.Timer;

public class PCanvas extends JComponent implements PComponent {
  public static final String INTERATING_CHANGED_NOTIFICATION = "INTERATING_CHANGED_NOTIFICATION";
  
  public static PCanvas CURRENT_ZCANVAS = null;
  
  private PCamera camera;
  
  private PStack cursorStack;
  
  private int interacting;
  
  private int defaultRenderQuality;
  
  private int animatingRenderQuality;
  
  private int interactingRenderQuality;
  
  private PPanEventHandler panEventHandler;
  
  private PZoomEventHandler zoomEventHandler;
  
  private boolean paintingImmediately;
  
  private boolean animatingOnLastPaint;
  
  private MouseListener mouseListener;
  
  private KeyListener keyListener;
  
  private MouseWheelListener mouseWheelListener;
  
  private MouseMotionListener mouseMotionListener;
  
  private boolean isButton1Pressed;
  
  private boolean isButton2Pressed;
  
  private boolean isButton3Pressed;
  
  public PCanvas() {
    CURRENT_ZCANVAS = this;
    this.cursorStack = new PStack();
    setCamera(createDefaultCamera());
    installInputSources();
    setDefaultRenderQuality(1);
    setAnimatingRenderQuality(0);
    setInteractingRenderQuality(0);
    setPanEventHandler(new PPanEventHandler());
    setZoomEventHandler(new PZoomEventHandler());
    setBackground(Color.WHITE);
  }
  
  protected PCamera createDefaultCamera() {
    return PUtil.createBasicScenegraph();
  }
  
  public PPanEventHandler getPanEventHandler() {
    return this.panEventHandler;
  }
  
  public void setPanEventHandler(PPanEventHandler paramPPanEventHandler) {
    if (this.panEventHandler != null)
      removeInputEventListener((PInputEventListener)this.panEventHandler); 
    this.panEventHandler = paramPPanEventHandler;
    if (this.panEventHandler != null)
      addInputEventListener((PInputEventListener)this.panEventHandler); 
  }
  
  public PZoomEventHandler getZoomEventHandler() {
    return this.zoomEventHandler;
  }
  
  public void setZoomEventHandler(PZoomEventHandler paramPZoomEventHandler) {
    if (this.zoomEventHandler != null)
      removeInputEventListener((PInputEventListener)this.zoomEventHandler); 
    this.zoomEventHandler = paramPZoomEventHandler;
    if (this.zoomEventHandler != null)
      addInputEventListener((PInputEventListener)this.zoomEventHandler); 
  }
  
  public PCamera getCamera() {
    return this.camera;
  }
  
  public void setCamera(PCamera paramPCamera) {
    if (this.camera != null)
      this.camera.setComponent((PComponent)null); 
    this.camera = paramPCamera;
    if (this.camera != null) {
      this.camera.setComponent(this);
      this.camera.setBounds(getBounds());
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
  
  public boolean getInteracting() {
    return (this.interacting > 0);
  }
  
  public boolean getAnimating() {
    return getRoot().getActivityScheduler().getAnimating();
  }
  
  public void setInteracting(boolean paramBoolean) {
    boolean bool = getInteracting();
    if (paramBoolean) {
      this.interacting++;
    } else {
      this.interacting--;
    } 
    if (!getInteracting()) {
      int i = this.defaultRenderQuality;
      if (getAnimating())
        i = this.animatingRenderQuality; 
      if (i > this.interactingRenderQuality)
        repaint(); 
    } 
    paramBoolean = getInteracting();
    if (bool != paramBoolean)
      firePropertyChange("INTERATING_CHANGED_NOTIFICATION", bool, paramBoolean); 
  }
  
  public void setDefaultRenderQuality(int paramInt) {
    this.defaultRenderQuality = paramInt;
    repaint();
  }
  
  public void setAnimatingRenderQuality(int paramInt) {
    this.animatingRenderQuality = paramInt;
    if (getAnimating())
      repaint(); 
  }
  
  public void setInteractingRenderQuality(int paramInt) {
    this.interactingRenderQuality = paramInt;
    if (getInteracting())
      repaint(); 
  }
  
  public void pushCursor(Cursor paramCursor) {
    this.cursorStack.push(getCursor());
    setCursor(paramCursor);
  }
  
  public void popCursor() {
    setCursor((Cursor)this.cursorStack.pop());
  }
  
  public void setEnabled(boolean paramBoolean) {
    super.setEnabled(paramBoolean);
    if (isEnabled()) {
      installInputSources();
    } else {
      removeInputSources();
    } 
  }
  
  protected void installInputSources() {
    if (this.mouseListener == null) {
      this.mouseListener = new MouseListener(this) {
          private final PCanvas this$0;
          
          public void mouseClicked(MouseEvent param1MouseEvent) {
            this.this$0.sendInputEventToInputManager(param1MouseEvent, 500);
          }
          
          public void mouseEntered(MouseEvent param1MouseEvent) {
            MouseEvent mouseEvent = null;
            if ((param1MouseEvent.getModifiersEx() & 0x1C00) != 0) {
              mouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), 506, param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), param1MouseEvent.getButton());
            } else {
              mouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), 503, param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), param1MouseEvent.getButton());
            } 
            this.this$0.sendInputEventToInputManager(param1MouseEvent, 504);
            this.this$0.sendInputEventToInputManager(mouseEvent, mouseEvent.getID());
          }
          
          public void mouseExited(MouseEvent param1MouseEvent) {
            MouseEvent mouseEvent = null;
            if ((param1MouseEvent.getModifiersEx() & 0x1C00) != 0) {
              mouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), 506, param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), param1MouseEvent.getButton());
            } else {
              mouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), 503, param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), param1MouseEvent.getButton());
            } 
            this.this$0.sendInputEventToInputManager(mouseEvent, mouseEvent.getID());
            this.this$0.sendInputEventToInputManager(param1MouseEvent, 505);
          }
          
          public void mousePressed(MouseEvent param1MouseEvent) {
            this.this$0.requestFocus();
            boolean bool = false;
            if (param1MouseEvent.getButton() == 0)
              if ((param1MouseEvent.getModifiers() & 0x10) == 16) {
                param1MouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), 501, param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), 1);
              } else if ((param1MouseEvent.getModifiers() & 0x8) == 8) {
                param1MouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), 501, param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), 2);
              } else if ((param1MouseEvent.getModifiers() & 0x4) == 4) {
                param1MouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), 501, param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), 3);
              }  
            switch (param1MouseEvent.getButton()) {
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
              MouseEvent mouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), 502, param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), param1MouseEvent.getButton());
              this.this$0.sendInputEventToInputManager(mouseEvent, 502);
            } 
            this.this$0.sendInputEventToInputManager(param1MouseEvent, 501);
          }
          
          public void mouseReleased(MouseEvent param1MouseEvent) {
            boolean bool = false;
            if (param1MouseEvent.getButton() == 0)
              if ((param1MouseEvent.getModifiers() & 0x10) == 16) {
                param1MouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), 502, param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), 1);
              } else if ((param1MouseEvent.getModifiers() & 0x8) == 8) {
                param1MouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), 502, param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), 2);
              } else if ((param1MouseEvent.getModifiers() & 0x4) == 4) {
                param1MouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), 502, param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), 3);
              }  
            switch (param1MouseEvent.getButton()) {
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
              MouseEvent mouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), 501, param1MouseEvent.getWhen(), param1MouseEvent.getModifiers(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), param1MouseEvent.getButton());
              this.this$0.sendInputEventToInputManager(mouseEvent, 501);
            } 
            this.this$0.sendInputEventToInputManager(param1MouseEvent, 502);
          }
        };
      addMouseListener(this.mouseListener);
    } 
    if (this.mouseMotionListener == null) {
      this.mouseMotionListener = new MouseMotionListener(this) {
          private final PCanvas this$0;
          
          public void mouseDragged(MouseEvent param1MouseEvent) {
            this.this$0.sendInputEventToInputManager(param1MouseEvent, 506);
          }
          
          public void mouseMoved(MouseEvent param1MouseEvent) {
            this.this$0.sendInputEventToInputManager(param1MouseEvent, 503);
          }
        };
      addMouseMotionListener(this.mouseMotionListener);
    } 
    if (this.mouseWheelListener == null) {
      this.mouseWheelListener = new MouseWheelListener(this) {
          private final PCanvas this$0;
          
          public void mouseWheelMoved(MouseWheelEvent param1MouseWheelEvent) {
            this.this$0.sendInputEventToInputManager(param1MouseWheelEvent, param1MouseWheelEvent.getScrollType());
            if (!param1MouseWheelEvent.isConsumed() && this.this$0.getParent() != null)
              this.this$0.getParent().dispatchEvent(param1MouseWheelEvent); 
          }
        };
      addMouseWheelListener(this.mouseWheelListener);
    } 
    if (this.keyListener == null) {
      this.keyListener = new KeyListener(this) {
          private final PCanvas this$0;
          
          public void keyPressed(KeyEvent param1KeyEvent) {
            this.this$0.sendInputEventToInputManager(param1KeyEvent, 401);
          }
          
          public void keyReleased(KeyEvent param1KeyEvent) {
            this.this$0.sendInputEventToInputManager(param1KeyEvent, 402);
          }
          
          public void keyTyped(KeyEvent param1KeyEvent) {
            this.this$0.sendInputEventToInputManager(param1KeyEvent, 400);
          }
        };
      addKeyListener(this.keyListener);
    } 
  }
  
  protected void removeInputSources() {
    if (this.mouseListener != null)
      removeMouseListener(this.mouseListener); 
    if (this.mouseMotionListener != null)
      removeMouseMotionListener(this.mouseMotionListener); 
    if (this.mouseWheelListener != null)
      removeMouseWheelListener(this.mouseWheelListener); 
    if (this.keyListener != null)
      removeKeyListener(this.keyListener); 
    this.mouseListener = null;
    this.mouseMotionListener = null;
    this.mouseWheelListener = null;
    this.keyListener = null;
  }
  
  protected void sendInputEventToInputManager(InputEvent paramInputEvent, int paramInt) {
    getRoot().getDefaultInputManager().processEventFromCamera(paramInputEvent, paramInt, getCamera());
  }
  
  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.camera.setBounds(this.camera.getX(), this.camera.getY(), paramInt3, paramInt4);
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void repaint(PBounds paramPBounds) {
    PDebug.processRepaint();
    paramPBounds.expandNearestIntegerDimensions();
    paramPBounds.inset(-1.0D, -1.0D);
    repaint((int)paramPBounds.x, (int)paramPBounds.y, (int)paramPBounds.width, (int)paramPBounds.height);
  }
  
  public void paintComponent(Graphics paramGraphics) {
    PDebug.startProcessingOutput();
    Graphics2D graphics2D = (Graphics2D)paramGraphics.create();
    graphics2D.setColor(getBackground());
    graphics2D.fillRect(0, 0, getWidth(), getHeight());
    PPaintContext pPaintContext = new PPaintContext(graphics2D);
    if (getInteracting() || getAnimating()) {
      if (this.interactingRenderQuality < this.animatingRenderQuality) {
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
    PDebug.endProcessingOutput(graphics2D);
  }
  
  public void paintImmediately() {
    if (this.paintingImmediately)
      return; 
    this.paintingImmediately = true;
    RepaintManager.currentManager(this).paintDirtyRegions();
    this.paintingImmediately = false;
  }
  
  public Timer createTimer(int paramInt, ActionListener paramActionListener) {
    return new Timer(paramInt, paramActionListener);
  }
}
