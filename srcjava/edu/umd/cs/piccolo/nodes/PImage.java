package edu.umd.cs.piccolo.nodes;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPaintContext;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class PImage extends PNode {
  public static final String PROPERTY_IMAGE = "image";
  
  public static final int PROPERTY_CODE_IMAGE = 32768;
  
  private transient Image image;
  
  public PImage() {}
  
  public PImage(Image paramImage) {
    this();
    setImage(paramImage);
  }
  
  public PImage(String paramString) {
    this(Toolkit.getDefaultToolkit().getImage(paramString));
  }
  
  public PImage(URL paramURL) {
    this();
    if (paramURL != null)
      setImage(Toolkit.getDefaultToolkit().getImage(paramURL)); 
  }
  
  public Image getImage() {
    return this.image;
  }
  
  public void setImage(String paramString) {
    setImage(Toolkit.getDefaultToolkit().getImage(paramString));
  }
  
  public void setImage(Image paramImage) {
    Image image = this.image;
    if (paramImage instanceof BufferedImage) {
      this.image = paramImage;
    } else {
      ImageIcon imageIcon = new ImageIcon(paramImage);
      switch (imageIcon.getImageLoadStatus()) {
        case 1:
          System.err.println("media tracker still loading image after requested to wait until finished");
        case 8:
          this.image = imageIcon.getImage();
          break;
        case 2:
          System.err.println("media tracker aborted image load");
          this.image = null;
          break;
        case 4:
          System.err.println("media tracker errored image load");
          this.image = null;
          break;
      } 
    } 
    if (this.image != null) {
      setBounds(0.0D, 0.0D, getImage().getWidth(null), getImage().getHeight(null));
      invalidatePaint();
    } else {
      this.image = null;
    } 
    firePropertyChange(32768, "image", image, this.image);
  }
  
  protected void paint(PPaintContext paramPPaintContext) {
    if (getImage() != null) {
      double d1 = this.image.getWidth(null);
      double d2 = this.image.getHeight(null);
      PBounds pBounds = getBoundsReference();
      Graphics2D graphics2D = paramPPaintContext.getGraphics();
      if (pBounds.x != 0.0D || pBounds.y != 0.0D || pBounds.width != d1 || pBounds.height != d2) {
        graphics2D.translate(pBounds.x, pBounds.y);
        graphics2D.scale(pBounds.width / d1, pBounds.height / d2);
        graphics2D.drawImage(this.image, 0, 0, (ImageObserver)null);
        graphics2D.scale(d1 / pBounds.width, d2 / pBounds.height);
        graphics2D.translate(-pBounds.x, -pBounds.y);
      } else {
        graphics2D.drawImage(this.image, 0, 0, (ImageObserver)null);
      } 
    } 
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    BufferedImage bufferedImage = toBufferedImage(this.image, false);
    if (bufferedImage != null)
      ImageIO.write(bufferedImage, "png", paramObjectOutputStream); 
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    paramObjectInputStream.defaultReadObject();
    this.image = ImageIO.read(paramObjectInputStream);
  }
  
  public static BufferedImage toBufferedImage(Image paramImage, boolean paramBoolean) {
    if (paramImage == null)
      return null; 
    if (!paramBoolean && paramImage instanceof BufferedImage)
      return (BufferedImage)paramImage; 
    GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    BufferedImage bufferedImage = graphicsConfiguration.createCompatibleImage(paramImage.getWidth(null), paramImage.getHeight(null));
    Graphics2D graphics2D = bufferedImage.createGraphics();
    graphics2D.drawImage(paramImage, 0, 0, (ImageObserver)null);
    graphics2D.dispose();
    return bufferedImage;
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("image=" + ((this.image == null) ? "null" : this.image.toString()));
    stringBuffer.append(',');
    stringBuffer.append(super.paramString());
    return stringBuffer.toString();
  }
}