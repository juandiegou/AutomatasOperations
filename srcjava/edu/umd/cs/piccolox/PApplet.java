package edu.umd.cs.piccolox;

import edu.umd.cs.piccolo.PCanvas;
import java.awt.Component;
import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public class PApplet extends JApplet {
  private PCanvas canvas;
  
  public void init() {
    setBackground(null);
    this.canvas = createCanvas();
    getContentPane().add((Component)this.canvas);
    validate();
    this.canvas.requestFocus();
    beforeInitialize();
    SwingUtilities.invokeLater(new Runnable(this) {
          private final PApplet this$0;
          
          public void run() {
            this.this$0.initialize();
            this.this$0.repaint();
          }
        });
  }
  
  public PCanvas getCanvas() {
    return this.canvas;
  }
  
  public PCanvas createCanvas() {
    return new PCanvas();
  }
  
  public void beforeInitialize() {}
  
  public void initialize() {}
}