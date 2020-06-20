package edu.umd.cs.piccolox.swt;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPaintContext;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

public class PSWTImage extends PNode {
  private transient PSWTCanvas canvas;
  
  private transient Image image;
  
  public PSWTImage(PSWTCanvas paramPSWTCanvas) {
    this.canvas = paramPSWTCanvas;
    paramPSWTCanvas.addDisposeListener(new DisposeListener(this) {
          private final PSWTImage this$0;
          
          public void widgetDisposed(DisposeEvent param1DisposeEvent) {
            if (this.this$0.image != null)
              this.this$0.image.dispose(); 
          }
        });
  }
  
  public PSWTImage(PSWTCanvas paramPSWTCanvas, Image paramImage) {
    this(paramPSWTCanvas);
    setImage(paramImage);
  }
  
  public PSWTImage(PSWTCanvas paramPSWTCanvas, String paramString) {
    this(paramPSWTCanvas);
    setImage(paramString);
  }
  
  public Image getImage() {
    return this.image;
  }
  
  public void setImage(String paramString) {
    setImage(new Image((Device)this.canvas.getDisplay(), paramString));
  }
  
  public void setImage(Image paramImage) {
    Image image = this.image;
    this.image = paramImage;
    if (this.image != null) {
      Rectangle rectangle = getImage().getBounds();
      setBounds(0.0D, 0.0D, rectangle.width, rectangle.height);
      invalidatePaint();
    } else {
      this.image = null;
    } 
    firePropertyChange(32768, "image", image, this.image);
  }
  
  protected void paint(PPaintContext paramPPaintContext) {
    if (getImage() != null) {
      Rectangle rectangle = this.image.getBounds();
      double d1 = rectangle.width;
      double d2 = rectangle.height;
      PBounds pBounds = getBoundsReference();
      SWTGraphics2D sWTGraphics2D = (SWTGraphics2D)paramPPaintContext.getGraphics();
      if (pBounds.x != 0.0D || pBounds.y != 0.0D || pBounds.width != d1 || pBounds.height != d2) {
        sWTGraphics2D.translate(pBounds.x, pBounds.y);
        sWTGraphics2D.scale(pBounds.width / d1, pBounds.height / d2);
        sWTGraphics2D.drawImage(this.image, 0.0D, 0.0D);
        sWTGraphics2D.scale(d1 / pBounds.width, d2 / pBounds.height);
        sWTGraphics2D.translate(-pBounds.x, -pBounds.y);
      } else {
        sWTGraphics2D.drawImage(this.image, 0.0D, 0.0D);
      } 
    } 
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("image=" + ((this.image == null) ? "null" : this.image.toString()));
    stringBuffer.append(',');
    stringBuffer.append(super.paramString());
    return stringBuffer.toString();
  }
}
