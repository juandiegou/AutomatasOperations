package edu.umd.cs.piccolox.nodes;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PDimension;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolo.util.PPickPath;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Dimension2D;

public class PNodeCache extends PNode {
  private transient Image imageCache;
  
  private boolean validatingCache;
  
  public Image createImageCache(Dimension2D paramDimension2D) {
    return toImage();
  }
  
  public Image getImageCache() {
    if (this.imageCache == null) {
      PDimension pDimension = new PDimension();
      this.validatingCache = true;
      resetBounds();
      this.imageCache = createImageCache((Dimension2D)pDimension);
      PBounds pBounds = getFullBoundsReference();
      setBounds(pBounds.getX() + pDimension.getWidth(), pBounds.getY() + pDimension.getHeight(), this.imageCache.getWidth(null), this.imageCache.getHeight(null));
      this.validatingCache = false;
    } 
    return this.imageCache;
  }
  
  public void invalidateCache() {
    this.imageCache = null;
  }
  
  public void invalidatePaint() {
    if (!this.validatingCache)
      super.invalidatePaint(); 
  }
  
  public void repaintFrom(PBounds paramPBounds, PNode paramPNode) {
    if (!this.validatingCache) {
      super.repaintFrom(paramPBounds, paramPNode);
      invalidateCache();
    } 
  }
  
  public void fullPaint(PPaintContext paramPPaintContext) {
    if (this.validatingCache) {
      super.fullPaint(paramPPaintContext);
    } else {
      Graphics2D graphics2D = paramPPaintContext.getGraphics();
      graphics2D.drawImage(getImageCache(), (int)getX(), (int)getY(), null);
    } 
  }
  
  protected boolean pickAfterChildren(PPickPath paramPPickPath) {
    return false;
  }
}
