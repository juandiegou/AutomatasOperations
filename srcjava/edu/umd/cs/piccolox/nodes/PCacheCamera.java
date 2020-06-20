package edu.umd.cs.piccolox.nodes;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PRoot;
import edu.umd.cs.piccolo.activities.PActivity;
import edu.umd.cs.piccolo.activities.PTransformActivity;
import edu.umd.cs.piccolo.util.PAffineTransform;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PDimension;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolo.util.PUtil;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class PCacheCamera extends PCamera {
  private BufferedImage paintBuffer;
  
  private boolean imageAnimate;
  
  private PBounds imageAnimateBounds;
  
  protected BufferedImage getPaintBuffer() {
    PBounds pBounds = getFullBoundsReference();
    if (this.paintBuffer == null || this.paintBuffer.getWidth() < pBounds.getWidth() || this.paintBuffer.getHeight() < pBounds.getHeight())
      this.paintBuffer = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage((int)Math.ceil(pBounds.getWidth()), (int)Math.ceil(pBounds.getHeight())); 
    return this.paintBuffer;
  }
  
  private AffineTransform cacheViewBounds(Rectangle2D paramRectangle2D, boolean paramBoolean) {
    PBounds pBounds1 = getViewBounds();
    PBounds pBounds2 = new PBounds(pBounds1);
    pBounds2.add(paramRectangle2D);
    animateViewToCenterBounds((Rectangle2D)pBounds2, paramBoolean, 0L);
    this.imageAnimateBounds = getViewBounds();
    BufferedImage bufferedImage = getPaintBuffer();
    Paint paint = Color.white;
    if (getPaint() != null)
      paint = getPaint(); 
    toImage(bufferedImage, paint);
    this.imageAnimate = true;
    animateViewToCenterBounds((Rectangle2D)pBounds1, paramBoolean, 0L);
    PDimension pDimension = pBounds1.deltaRequiredToCenter(paramRectangle2D);
    PAffineTransform pAffineTransform = getViewTransform();
    pAffineTransform.translate(pDimension.width, pDimension.height);
    if (paramBoolean) {
      double d = Math.min(pBounds1.getWidth() / paramRectangle2D.getWidth(), pBounds1.getHeight() / paramRectangle2D.getHeight());
      pAffineTransform.scaleAboutPoint(d, paramRectangle2D.getCenterX(), paramRectangle2D.getCenterY());
    } 
    return (AffineTransform)pAffineTransform;
  }
  
  private void clearViewCache() {
    this.imageAnimate = false;
    this.imageAnimateBounds = null;
  }
  
  public PTransformActivity animateStaticViewToCenterBoundsFast(Rectangle2D paramRectangle2D, boolean paramBoolean, long paramLong) {
    if (paramLong == 0L)
      return animateViewToCenterBounds(paramRectangle2D, paramBoolean, paramLong); 
    AffineTransform affineTransform = cacheViewBounds(paramRectangle2D, paramBoolean);
    return animateStaticViewToTransformFast(affineTransform, paramLong);
  }
  
  protected PTransformActivity animateStaticViewToTransformFast(AffineTransform paramAffineTransform, long paramLong) {
    if (paramLong == 0L) {
      setViewTransform(paramAffineTransform);
      return null;
    } 
    PTransformActivity.Target target = new PTransformActivity.Target(this) {
        private final PCacheCamera this$0;
        
        public void setTransform(AffineTransform param1AffineTransform) {
          this.this$0.setViewTransform(param1AffineTransform);
        }
        
        public void getSourceMatrix(double[] param1ArrayOfdouble) {
          this.this$0.getViewTransformReference().getMatrix(param1ArrayOfdouble);
        }
      };
    PTransformActivity pTransformActivity = new PTransformActivity(this, paramLong, PUtil.DEFAULT_ACTIVITY_STEP_RATE, target, paramAffineTransform) {
        private final PCacheCamera this$0;
        
        protected void activityFinished() {
          this.this$0.clearViewCache();
          this.this$0.repaint();
          super.activityFinished();
        }
      };
    PRoot pRoot = getRoot();
    if (pRoot != null)
      pRoot.getActivityScheduler().addActivity((PActivity)pTransformActivity); 
    return pTransformActivity;
  }
  
  public void fullPaint(PPaintContext paramPPaintContext) {
    if (this.imageAnimate) {
      PBounds pBounds1 = getFullBoundsReference();
      PBounds pBounds2 = getViewBounds();
      double d1 = getFullBoundsReference().getWidth() / this.imageAnimateBounds.getWidth();
      double d2 = (pBounds2.getX() - this.imageAnimateBounds.getX()) * d1;
      double d3 = (pBounds2.getY() - this.imageAnimateBounds.getY()) * d1;
      double d4 = pBounds2.getWidth() * d1;
      double d5 = pBounds2.getHeight() * d1;
      paramPPaintContext.getGraphics().drawImage(this.paintBuffer, 0, 0, (int)Math.ceil(pBounds1.getWidth()), (int)Math.ceil(pBounds1.getHeight()), (int)Math.floor(d2), (int)Math.floor(d3), (int)Math.ceil(d2 + d4), (int)Math.ceil(d3 + d5), null);
    } else {
      super.fullPaint(paramPPaintContext);
    } 
  }
}
