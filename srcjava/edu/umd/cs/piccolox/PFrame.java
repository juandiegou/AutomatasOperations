package edu.umd.cs.piccolox;

import edu.umd.cs.piccolo.PCanvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PFrame extends JFrame {
  private PCanvas canvas;
  
  private GraphicsDevice graphicsDevice;
  
  private DisplayMode originalDisplayMode;
  
  private EventListener escapeFullScreenModeListener;
  
  public PFrame() {
    this("", false, (PCanvas)null);
  }
  
  public PFrame(String paramString, boolean paramBoolean, PCanvas paramPCanvas) {
    this(paramString, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(), paramBoolean, paramPCanvas);
  }
  
  public PFrame(String paramString, GraphicsDevice paramGraphicsDevice, boolean paramBoolean, PCanvas paramPCanvas) {
    super(paramString, paramGraphicsDevice.getDefaultConfiguration());
    this.graphicsDevice = paramGraphicsDevice;
    try {
      this.originalDisplayMode = this.graphicsDevice.getDisplayMode();
    } catch (InternalError internalError) {
      internalError.printStackTrace();
    } 
    setBounds(getDefaultFrameBounds());
    setBackground((Color)null);
    try {
      setDefaultCloseOperation(3);
    } catch (SecurityException securityException) {}
    if (paramPCanvas == null) {
      this.canvas = new PCanvas();
    } else {
      this.canvas = paramPCanvas;
    } 
    getContentPane().add((Component)this.canvas);
    validate();
    setFullScreenMode(paramBoolean);
    this.canvas.requestFocus();
    beforeInitialize();
    SwingUtilities.invokeLater(new Runnable(this) {
          private final PFrame this$0;
          
          public void run() {
            this.this$0.initialize();
            this.this$0.repaint();
          }
        });
  }
  
  public PCanvas getCanvas() {
    return this.canvas;
  }
  
  public Rectangle getDefaultFrameBounds() {
    return new Rectangle(100, 100, 400, 400);
  }
  
  public boolean isFullScreenMode() {
    return (this.graphicsDevice.getFullScreenWindow() != null);
  }
  
  public void setFullScreenMode(boolean paramBoolean) {
    if (paramBoolean) {
      addEscapeFullScreenModeListener();
      if (isDisplayable())
        dispose(); 
      setUndecorated(true);
      setResizable(false);
      this.graphicsDevice.setFullScreenWindow(this);
      if (this.graphicsDevice.isDisplayChangeSupported())
        chooseBestDisplayMode(this.graphicsDevice); 
      validate();
    } else {
      removeEscapeFullScreenModeListener();
      if (isDisplayable())
        dispose(); 
      setUndecorated(false);
      setResizable(true);
      this.graphicsDevice.setFullScreenWindow(null);
      validate();
      setVisible(true);
    } 
  }
  
  protected void chooseBestDisplayMode(GraphicsDevice paramGraphicsDevice) {
    DisplayMode displayMode = getBestDisplayMode(paramGraphicsDevice);
    if (displayMode != null)
      paramGraphicsDevice.setDisplayMode(displayMode); 
  }
  
  protected DisplayMode getBestDisplayMode(GraphicsDevice paramGraphicsDevice) {
    for (DisplayMode displayMode : getPreferredDisplayModes(paramGraphicsDevice)) {
      DisplayMode[] arrayOfDisplayMode = paramGraphicsDevice.getDisplayModes();
      for (byte b = 0; b < arrayOfDisplayMode.length; b++) {
        if (arrayOfDisplayMode[b].getWidth() == displayMode.getWidth() && arrayOfDisplayMode[b].getHeight() == displayMode.getHeight() && arrayOfDisplayMode[b].getBitDepth() == displayMode.getBitDepth())
          return displayMode; 
      } 
    } 
    return null;
  }
  
  protected Collection getPreferredDisplayModes(GraphicsDevice paramGraphicsDevice) {
    ArrayList arrayList = new ArrayList();
    arrayList.add(paramGraphicsDevice.getDisplayMode());
    return arrayList;
  }
  
  public void addEscapeFullScreenModeListener() {
    removeEscapeFullScreenModeListener();
    this.escapeFullScreenModeListener = new KeyAdapter(this) {
        private final PFrame this$0;
        
        public void keyPressed(KeyEvent param1KeyEvent) {
          if (param1KeyEvent.getKeyCode() == 27)
            this.this$0.setFullScreenMode(false); 
        }
      };
    this.canvas.addKeyListener((KeyListener)this.escapeFullScreenModeListener);
  }
  
  public void removeEscapeFullScreenModeListener() {
    if (this.escapeFullScreenModeListener != null) {
      this.canvas.removeKeyListener((KeyListener)this.escapeFullScreenModeListener);
      this.escapeFullScreenModeListener = null;
    } 
  }
  
  public void beforeInitialize() {}
  
  public void initialize() {}
  
  public static void main(String[] paramArrayOfString) {
    new PFrame();
  }
}
