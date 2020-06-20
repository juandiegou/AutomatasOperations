package edu.umd.cs.piccolox.swt;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PPaintContext;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

public class PSWTText extends PNode {
  protected static final double DEFAULT_GREEK_THRESHOLD = 5.5D;
  
  protected static final Color DEFAULT_GREEK_COLOR = Color.gray;
  
  protected static final String DEFAULT_FONT_NAME = "Helvetica";
  
  protected static final int DEFAULT_FONT_STYLE = 0;
  
  protected static final int DEFAULT_FONT_SIZE = 12;
  
  protected static final Font DEFAULT_FONT = new Font("Helvetica", 0, 12);
  
  protected static final Color DEFAULT_PEN_COLOR = Color.black;
  
  protected static final String DEFAULT_TEXT = "";
  
  protected static final int DEFAULT_PADDING = 2;
  
  protected double greekThreshold = 5.5D;
  
  protected Color greekColor = DEFAULT_GREEK_COLOR;
  
  protected Color penColor = DEFAULT_PEN_COLOR;
  
  protected Font font = DEFAULT_FONT;
  
  protected int padding = 2;
  
  protected ArrayList lines = new ArrayList();
  
  protected double translateX = 0.0D;
  
  protected double translateY = 0.0D;
  
  public PSWTText() {
    this("", DEFAULT_FONT);
  }
  
  public PSWTText(String paramString) {
    this(paramString, DEFAULT_FONT);
  }
  
  public PSWTText(String paramString, Font paramFont) {
    setText(paramString);
    this.font = paramFont;
    recomputeBounds();
  }
  
  public Color getPenColor() {
    return this.penColor;
  }
  
  public void setPenColor(Color paramColor) {
    this.penColor = paramColor;
    repaint();
  }
  
  public Paint getPenPaint() {
    return this.penColor;
  }
  
  public void setPenPaint(Paint paramPaint) {
    this.penColor = (Color)paramPaint;
  }
  
  public Color getBackgroundColor() {
    return (Color)getPaint();
  }
  
  public void setBackgroundColor(Color paramColor) {
    setPaint(paramColor);
  }
  
  public double getGreekThreshold() {
    return this.greekThreshold;
  }
  
  public void setGreekThreshold(double paramDouble) {
    this.greekThreshold = paramDouble;
    repaint();
  }
  
  public Font getFont() {
    return this.font;
  }
  
  public String getText() {
    String str = new String();
    byte b = 0;
    Iterator iterator = this.lines.iterator();
    while (iterator.hasNext()) {
      if (b)
        str = str + '\n'; 
      String str1 = iterator.next();
      str = str + str1;
      b++;
    } 
    return str;
  }
  
  public void setFont(Font paramFont) {
    this.font = paramFont;
    recomputeBounds();
  }
  
  public void setText(String paramString) {
    boolean bool1 = false;
    boolean bool2 = false;
    this.lines = new ArrayList();
    while (true) {
      int i = paramString.indexOf('\n', bool1);
      if (i == -1) {
        this.lines.add(paramString);
        bool2 = true;
      } else {
        this.lines.add(paramString.substring(0, i));
        paramString = paramString.substring(i + 1);
      } 
      if (bool2) {
        recomputeBounds();
        return;
      } 
    } 
  }
  
  public void setTranslateX(double paramDouble) {
    setTranslation(paramDouble, this.translateY);
  }
  
  public double getTranslateX() {
    return this.translateX;
  }
  
  public void setTranslateY(double paramDouble) {
    setTranslation(this.translateX, paramDouble);
  }
  
  public double getTranslateY() {
    return this.translateY;
  }
  
  public void setTranslation(double paramDouble1, double paramDouble2) {
    this.translateX = paramDouble1;
    this.translateY = paramDouble2;
    recomputeBounds();
  }
  
  public void setTranslation(Point2D paramPoint2D) {
    setTranslation(paramPoint2D.getX(), paramPoint2D.getY());
  }
  
  public Point2D getTranslation() {
    return new Point2D.Double(this.translateX, this.translateY);
  }
  
  public void paint(PPaintContext paramPPaintContext) {
    Graphics2D graphics2D = paramPPaintContext.getGraphics();
    AffineTransform affineTransform = null;
    boolean bool = false;
    if (!this.lines.isEmpty()) {
      if (this.translateX != 0.0D || this.translateY != 0.0D) {
        affineTransform = graphics2D.getTransform();
        graphics2D.translate(this.translateX, this.translateY);
        bool = true;
      } 
      double d = this.font.getSize() * paramPPaintContext.getScale();
      if (d < 0.5D || d < this.greekThreshold) {
        paintAsGreek(paramPPaintContext);
      } else {
        paintAsText(paramPPaintContext);
      } 
      if (bool)
        graphics2D.setTransform(affineTransform); 
    } 
  }
  
  public void paintAsGreek(PPaintContext paramPPaintContext) {
    Graphics2D graphics2D = paramPPaintContext.getGraphics();
    if (this.greekColor != null) {
      graphics2D.setBackground(this.greekColor);
      ((SWTGraphics2D)graphics2D).fillRect(0.0D, 0.0D, getWidth(), getHeight());
    } 
  }
  
  public void paintAsText(PPaintContext paramPPaintContext) {
    SWTGraphics2D sWTGraphics2D = (SWTGraphics2D)paramPPaintContext.getGraphics();
    if (getPaint() != null) {
      sWTGraphics2D.setBackground((Color)getPaint());
      Rectangle2D.Double double_ = new Rectangle2D.Double(0.0D, 0.0D, getWidth(), getHeight());
      sWTGraphics2D.fillRect(double_.getX(), double_.getY(), double_.getWidth(), double_.getHeight());
    } 
    sWTGraphics2D.translate(this.padding, this.padding);
    double d1 = Math.min(sWTGraphics2D.getTransform().getScaleX(), sWTGraphics2D.getTransform().getScaleY());
    double d2 = d1 * this.font.getSize();
    double d3 = Math.floor(d2) / d2;
    sWTGraphics2D.scale(d3, d3);
    sWTGraphics2D.setColor(this.penColor);
    sWTGraphics2D.setFont(this.font);
    byte b = 0;
    FontMetrics fontMetrics = sWTGraphics2D.getSWTFontMetrics();
    for (String str : this.lines) {
      str = str.equals("") ? " " : str;
      double d = (b * fontMetrics.getHeight());
      sWTGraphics2D.drawString(str, 0.0D, d);
      b++;
    } 
    sWTGraphics2D.scale(1.0D / d3, 1.0D / d3);
    sWTGraphics2D.translate(-this.padding, -this.padding);
  }
  
  protected void recomputeBounds() {
    double d1 = 0.0D;
    double d2 = 0.0D;
    boolean bool = true;
    if (this.lines.size() == 1 && ((String)this.lines.get(0)).equals(""))
      bool = false; 
    GC gC = new GC((Drawable)Display.getDefault());
    SWTGraphics2D sWTGraphics2D = new SWTGraphics2D(gC, (Device)Display.getDefault());
    sWTGraphics2D.setFont(this.font);
    FontMetrics fontMetrics = sWTGraphics2D.getSWTFontMetrics();
    if (!this.lines.isEmpty() && bool) {
      byte b = 0;
      for (String str : this.lines) {
        Point point = gC.stringExtent(str);
        double d = point.x;
        if (d > d1)
          d1 = d; 
        if (!b) {
          d2 += (fontMetrics.getAscent() + fontMetrics.getDescent() + fontMetrics.getLeading());
        } else {
          d2 += fontMetrics.getHeight();
        } 
        b++;
      } 
    } else {
      Point point = gC.stringExtent(" ");
      d1 = point.x;
      d2 = point.y;
    } 
    gC.dispose();
    setBounds(this.translateX, this.translateY, d1 + 4.0D, d2 + 4.0D);
  }
  
  protected void internalUpdateBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    recomputeBounds();
  }
}
