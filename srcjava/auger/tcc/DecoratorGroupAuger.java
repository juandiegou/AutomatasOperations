package auger.tcc;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolo.util.PPaintContext;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

class DecoratorGroupAuger extends PNode {
  int INDENT = 10;
  
  PBounds cachedChildBounds = new PBounds();
  
  PBounds comparisonBounds = new PBounds();
  
  public void paint(PPaintContext ppc) {
    Paint paint = getPaint();
    if (paint != null) {
      Graphics2D g2 = ppc.getGraphics();
      g2.setPaint(paint);
      PBounds bounds = getUnionOfChildrenBounds(null);
      bounds.setRect(bounds.getX() - this.INDENT, bounds.getY() - this.INDENT, bounds.getWidth() + (2 * this.INDENT), bounds.getHeight() + (2 * this.INDENT));
      g2.fill((Shape)bounds);
    } 
  }
  
  public PBounds computeFullBounds(PBounds dstBounds) {
    PBounds result = getUnionOfChildrenBounds(dstBounds);
    this.cachedChildBounds.setRect(result);
    result.setRect(result.getX() - this.INDENT, result.getY() - this.INDENT, result.getWidth() + (2 * this.INDENT), result.getHeight() + (2 * this.INDENT));
    localToParent((Rectangle2D)result);
    return result;
  }
  
  public boolean validateFullBounds() {
    this.comparisonBounds = getUnionOfChildrenBounds(this.comparisonBounds);
    if (!this.cachedChildBounds.equals(this.comparisonBounds))
      setPaintInvalid(true); 
    return super.validateFullBounds();
  }
}
