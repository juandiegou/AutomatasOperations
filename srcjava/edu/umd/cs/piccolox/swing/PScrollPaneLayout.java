package edu.umd.cs.piccolox.swing;

import edu.umd.cs.piccolo.util.PBounds;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.Border;

public class PScrollPaneLayout extends ScrollPaneLayout {
  public void layoutContainer(Container paramContainer) {
    Insets insets2;
    boolean bool1;
    boolean bool2;
    JScrollPane jScrollPane = (JScrollPane)paramContainer;
    this.vsbPolicy = jScrollPane.getVerticalScrollBarPolicy();
    this.hsbPolicy = jScrollPane.getHorizontalScrollBarPolicy();
    Rectangle rectangle1 = jScrollPane.getBounds();
    rectangle1.x = rectangle1.y = 0;
    Insets insets1 = paramContainer.getInsets();
    rectangle1.x = insets1.left;
    rectangle1.y = insets1.top;
    rectangle1.width -= insets1.left + insets1.right;
    rectangle1.height -= insets1.top + insets1.bottom;
    boolean bool = jScrollPane.getComponentOrientation().isLeftToRight();
    Rectangle rectangle2 = new Rectangle(0, rectangle1.y, 0, 0);
    if (this.colHead != null && this.colHead.isVisible()) {
      int i = (this.colHead.getPreferredSize()).height;
      rectangle2.height = i;
      rectangle1.y += i;
      rectangle1.height -= i;
    } 
    Rectangle rectangle3 = new Rectangle(0, 0, 0, 0);
    if (this.rowHead != null && this.rowHead.isVisible()) {
      int i = (this.rowHead.getPreferredSize()).width;
      rectangle3.width = i;
      rectangle1.width -= i;
      if (bool) {
        rectangle3.x = rectangle1.x;
        rectangle1.x += i;
      } else {
        rectangle1.x += rectangle1.width;
      } 
    } 
    Border border = jScrollPane.getViewportBorder();
    if (border != null) {
      insets2 = border.getBorderInsets(paramContainer);
      rectangle1.x += insets2.left;
      rectangle1.y += insets2.top;
      rectangle1.width -= insets2.left + insets2.right;
      rectangle1.height -= insets2.top + insets2.bottom;
    } else {
      insets2 = new Insets(0, 0, 0, 0);
    } 
    Dimension dimension1 = (this.viewport != null) ? this.viewport.toViewCoordinates(rectangle1.getSize()) : new Dimension(0, 0);
    PBounds pBounds = new PBounds(0.0D, 0.0D, dimension1.getWidth(), dimension1.getHeight());
    Dimension dimension2 = (this.viewport != null) ? ((PViewport)this.viewport).getViewSize((Rectangle2D)pBounds) : new Dimension(0, 0);
    Rectangle rectangle4 = new Rectangle(0, rectangle1.y - insets2.top, 0, 0);
    if (this.vsbPolicy == 22) {
      bool1 = true;
    } else if (this.vsbPolicy == 21) {
      bool1 = false;
    } else {
      bool1 = (dimension2.height > dimension1.height) ? true : false;
    } 
    if (this.vsb != null && bool1) {
      adjustForVSB(true, rectangle1, rectangle4, insets2, bool);
      dimension1 = this.viewport.toViewCoordinates(rectangle1.getSize());
      pBounds.setRect(0.0D, 0.0D, dimension1.getWidth(), dimension1.getHeight());
      dimension2 = ((PViewport)this.viewport).getViewSize((Rectangle2D)pBounds);
    } 
    Rectangle rectangle5 = new Rectangle(rectangle1.x - insets2.left, 0, 0, 0);
    if (this.hsbPolicy == 32) {
      bool2 = true;
    } else if (this.hsbPolicy == 31) {
      bool2 = false;
    } else {
      bool2 = (dimension2.width > dimension1.width) ? true : false;
    } 
    if (this.hsb != null && bool2) {
      adjustForHSB(true, rectangle1, rectangle5, insets2);
      if (this.vsb != null && !bool1 && this.vsbPolicy != 21) {
        dimension1 = this.viewport.toViewCoordinates(rectangle1.getSize());
        pBounds.setRect(0.0D, 0.0D, dimension1.getWidth(), dimension1.getHeight());
        dimension2 = ((PViewport)this.viewport).getViewSize((Rectangle2D)pBounds);
        bool1 = (dimension2.height > dimension1.height) ? true : false;
        if (bool1)
          adjustForVSB(true, rectangle1, rectangle4, insets2, bool); 
      } 
    } 
    if (this.viewport != null)
      this.viewport.setBounds(rectangle1); 
    rectangle4.height = rectangle1.height + insets2.top + insets2.bottom;
    rectangle5.width = rectangle1.width + insets2.left + insets2.right;
    rectangle3.height = rectangle1.height + insets2.top + insets2.bottom;
    rectangle1.y -= insets2.top;
    rectangle2.width = rectangle1.width + insets2.left + insets2.right;
    rectangle1.x -= insets2.left;
    if (this.rowHead != null)
      this.rowHead.setBounds(rectangle3); 
    if (this.colHead != null)
      this.colHead.setBounds(rectangle2); 
    if (this.vsb != null)
      if (bool1) {
        this.vsb.setVisible(true);
        this.vsb.setBounds(rectangle4);
      } else {
        this.vsb.setVisible(false);
      }  
    if (this.hsb != null)
      if (bool2) {
        this.hsb.setVisible(true);
        this.hsb.setBounds(rectangle5);
      } else {
        this.hsb.setVisible(false);
      }  
    if (this.lowerLeft != null)
      this.lowerLeft.setBounds(bool ? rectangle3.x : rectangle4.x, rectangle5.y, bool ? rectangle3.width : rectangle4.width, rectangle5.height); 
    if (this.lowerRight != null)
      this.lowerRight.setBounds(bool ? rectangle4.x : rectangle3.x, rectangle5.y, bool ? rectangle4.width : rectangle3.width, rectangle5.height); 
    if (this.upperLeft != null)
      this.upperLeft.setBounds(bool ? rectangle3.x : rectangle4.x, rectangle2.y, bool ? rectangle3.width : rectangle4.width, rectangle2.height); 
    if (this.upperRight != null)
      this.upperRight.setBounds(bool ? rectangle4.x : rectangle3.x, rectangle2.y, bool ? rectangle4.width : rectangle3.width, rectangle2.height); 
  }
  
  protected void adjustForVSB(boolean paramBoolean1, Rectangle paramRectangle1, Rectangle paramRectangle2, Insets paramInsets, boolean paramBoolean2) {
    int i = (this.vsb.getPreferredSize()).width;
    if (paramBoolean1) {
      paramRectangle1.width -= i;
      paramRectangle2.width = i;
      if (paramBoolean2) {
        paramRectangle2.x = paramRectangle1.x + paramRectangle1.width + paramInsets.right;
      } else {
        paramRectangle1.x -= paramInsets.left;
        paramRectangle1.x += i;
      } 
    } else {
      paramRectangle1.width += i;
    } 
  }
  
  protected void adjustForHSB(boolean paramBoolean, Rectangle paramRectangle1, Rectangle paramRectangle2, Insets paramInsets) {
    int i = (this.hsb.getPreferredSize()).height;
    if (paramBoolean) {
      paramRectangle1.height -= i;
      paramRectangle2.y = paramRectangle1.y + paramRectangle1.height + paramInsets.bottom;
      paramRectangle2.height = i;
    } else {
      paramRectangle1.height += i;
    } 
  }
  
  public static class UIResource extends PScrollPaneLayout implements javax.swing.plaf.UIResource {}
}
