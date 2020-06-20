package edu.umd.cs.piccolox.swt;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class SWTGraphics2D extends Graphics2D {
  protected static int CACHE_COUNT = 0;
  
  protected static HashMap FONT_CACHE = new HashMap();
  
  protected static HashMap COLOR_CACHE = new HashMap();
  
  protected static HashMap SHAPE_CACHE = new HashMap();
  
  protected static BufferedImage BUFFER = new BufferedImage(1, 1, 2);
  
  static Point PT = new Point();
  
  static Rectangle2D RECT = new Rectangle2D.Double();
  
  static Rectangle2D LINE_RECT = new Rectangle2D.Double();
  
  static Rectangle SWT_RECT = new Rectangle(0, 0, 0, 0);
  
  protected GC gc;
  
  protected Device device;
  
  protected AffineTransform transform = new AffineTransform();
  
  protected Font curFont;
  
  protected double lineWidth = 1.0D;
  
  public SWTGraphics2D(GC paramGC, Device paramDevice) {
    this.gc = paramGC;
    this.device = paramDevice;
  }
  
  public Rectangle getClipBounds() {
    Rectangle rectangle = this.gc.getClipping();
    Rectangle rectangle1 = new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    try {
      SWTShapeManager.transform(rectangle1, this.transform.createInverse());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return rectangle1;
  }
  
  public void clipRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    RECT.setRect(paramInt1, paramInt2, paramInt3, paramInt4);
    SWTShapeManager.transform(RECT, this.transform);
    SWTShapeManager.awtToSWT(RECT, SWT_RECT);
    Rectangle rectangle = this.gc.getClipping();
    rectangle = rectangle.intersection(SWT_RECT);
    this.gc.setClipping(rectangle);
  }
  
  public void setClip(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    RECT.setRect(paramInt1, paramInt2, paramInt3, paramInt4);
    SWTShapeManager.transform(RECT, this.transform);
    SWTShapeManager.awtToSWT(RECT, SWT_RECT);
    this.gc.setClipping(SWT_RECT);
  }
  
  public void clip(Shape paramShape) {
    Rectangle2D rectangle2D = paramShape.getBounds2D();
    SWTShapeManager.transform(rectangle2D, this.transform);
    SWTShapeManager.awtToSWT(rectangle2D, SWT_RECT);
    Rectangle rectangle = this.gc.getClipping();
    rectangle = rectangle.intersection(SWT_RECT);
    this.gc.setClipping(SWT_RECT);
  }
  
  public void setClip(Shape paramShape) {
    if (paramShape == null) {
      this.gc.setClipping((Rectangle)null);
    } else {
      Rectangle2D rectangle2D = paramShape.getBounds2D();
      SWTShapeManager.transform(rectangle2D, this.transform);
      SWTShapeManager.awtToSWT(rectangle2D, SWT_RECT);
      this.gc.setClipping(SWT_RECT);
    } 
  }
  
  public Shape getClip() {
    Rectangle rectangle = this.gc.getClipping();
    Rectangle2D.Double double_ = new Rectangle2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    try {
      SWTShapeManager.transform(double_, this.transform.createInverse());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return double_;
  }
  
  public GraphicsConfiguration getDeviceConfiguration() {
    return ((Graphics2D)BUFFER.getGraphics()).getDeviceConfiguration();
  }
  
  public Paint getPaint() {
    return getColor();
  }
  
  public void setPaint(Paint paramPaint) {
    if (paramPaint instanceof Color)
      setColor((Color)paramPaint); 
  }
  
  public Color getColor() {
    Color color = this.gc.getForeground();
    return new Color(color.getRed(), color.getGreen(), color.getBlue());
  }
  
  public void setColor(Color paramColor) {
    Color color = (Color)COLOR_CACHE.get(paramColor);
    if (color == null) {
      color = new Color(this.device, paramColor.getRed(), paramColor.getGreen(), paramColor.getBlue());
      COLOR_CACHE.put(paramColor, color);
    } 
    this.gc.setForeground(color);
  }
  
  public void setColor(Color paramColor) {
    this.gc.setForeground(paramColor);
  }
  
  public void setBackground(Color paramColor) {
    Color color = (Color)COLOR_CACHE.get(paramColor);
    if (color == null) {
      color = new Color(this.device, paramColor.getRed(), paramColor.getGreen(), paramColor.getBlue());
      COLOR_CACHE.put(paramColor, color);
    } 
    this.gc.setBackground(color);
  }
  
  public void setBackground(Color paramColor) {
    this.gc.setBackground(paramColor);
  }
  
  public Color getBackground() {
    Color color = this.gc.getBackground();
    return new Color(color.getRed(), color.getGreen(), color.getBlue());
  }
  
  public Font getSWTFont() {
    return this.curFont;
  }
  
  public FontMetrics getSWTFontMetrics() {
    this.gc.setFont(this.curFont);
    return this.gc.getFontMetrics();
  }
  
  public Font getFont() {
    if (this.curFont != null) {
      int i = 0;
      FontData[] arrayOfFontData = this.curFont.getFontData();
      if (arrayOfFontData.length > 0) {
        if ((arrayOfFontData[0].getStyle() & 0x1) != 0)
          i |= 0x1; 
        if ((arrayOfFontData[0].getStyle() & 0x2) != 0)
          i |= 0x2; 
        return new Font(arrayOfFontData[0].getName(), i, (arrayOfFontData[0]).height);
      } 
      return null;
    } 
    return null;
  }
  
  public void setFont(Font paramFont) {
    String str = "name=" + paramFont.getFamily() + ";bold=" + paramFont.isBold() + ";italic=" + paramFont.isItalic() + ";size=" + paramFont.getSize();
    this.curFont = getFont(str);
  }
  
  public void setFont(Font paramFont) {
    this.curFont = paramFont;
  }
  
  public Font getFont(String paramString) {
    Font font = (Font)FONT_CACHE.get(paramString);
    if (font == null) {
      int i = 0;
      if (paramString.indexOf("bold=true") != -1)
        i |= 0x1; 
      if (paramString.indexOf("italic=true") != -1)
        i |= 0x2; 
      String str1 = paramString.substring(0, paramString.indexOf(";"));
      String str2 = paramString.substring(paramString.lastIndexOf(";") + 1, paramString.length());
      int j = 12;
      try {
        j = Integer.parseInt(str2.substring(str2.indexOf("=") + 1, str2.length()));
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
      font = new Font(this.device, str1.substring(str1.indexOf("=") + 1, str1.length()), j, i);
      FONT_CACHE.put(paramString, font);
    } 
    return font;
  }
  
  protected Font getTransformedFont() {
    if (this.curFont != null) {
      FontData fontData = this.curFont.getFontData()[0];
      int i = fontData.getHeight();
      RECT.setRect(0.0D, 0.0D, i, i);
      SWTShapeManager.transform(RECT, this.transform);
      i = (int)(RECT.getHeight() + 0.5D);
      String str = "name=" + fontData.getName() + ";bold=" + (((fontData.getStyle() & 0x1) != 0) ? 1 : 0) + ";italic=" + (((fontData.getStyle() & 0x2) != 0) ? 1 : 0) + ";size=" + i;
      return getFont(str);
    } 
    return null;
  }
  
  public void translate(int paramInt1, int paramInt2) {
    this.transform.translate(paramInt1, paramInt2);
  }
  
  public void translate(double paramDouble1, double paramDouble2) {
    this.transform.translate(paramDouble1, paramDouble2);
  }
  
  public void rotate(double paramDouble) {
    this.transform.rotate(paramDouble);
  }
  
  public void rotate(double paramDouble1, double paramDouble2, double paramDouble3) {
    this.transform.rotate(paramDouble1, paramDouble2, paramDouble3);
  }
  
  public void scale(double paramDouble1, double paramDouble2) {
    this.transform.scale(paramDouble1, paramDouble2);
  }
  
  public void shear(double paramDouble1, double paramDouble2) {
    this.transform.shear(paramDouble1, paramDouble2);
  }
  
  public void transform(AffineTransform paramAffineTransform) {
    this.transform.concatenate(paramAffineTransform);
  }
  
  public void setTransform(AffineTransform paramAffineTransform) {
    this.transform = (AffineTransform)paramAffineTransform.clone();
  }
  
  public AffineTransform getTransform() {
    return (AffineTransform)this.transform.clone();
  }
  
  public void clearRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void draw(Shape paramShape) {
    if (paramShape instanceof Rectangle2D) {
      Rectangle2D rectangle2D = (Rectangle2D)paramShape;
      drawRect(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    } else if (paramShape instanceof Ellipse2D) {
      Ellipse2D ellipse2D = (Ellipse2D)paramShape;
      drawOval(ellipse2D.getX(), ellipse2D.getY(), ellipse2D.getWidth(), ellipse2D.getHeight());
    } else if (paramShape instanceof RoundRectangle2D) {
      RoundRectangle2D roundRectangle2D = (RoundRectangle2D)paramShape;
      drawRoundRect(roundRectangle2D.getX(), roundRectangle2D.getY(), roundRectangle2D.getWidth(), roundRectangle2D.getHeight(), roundRectangle2D.getArcWidth(), roundRectangle2D.getArcHeight());
    } else if (paramShape instanceof Arc2D) {
      Arc2D arc2D = (Arc2D)paramShape;
      drawArc(arc2D.getX(), arc2D.getY(), arc2D.getWidth(), arc2D.getHeight(), arc2D.getAngleStart(), arc2D.getAngleExtent());
    } else {
      double[] arrayOfDouble = (double[])SHAPE_CACHE.get(paramShape);
      if (arrayOfDouble == null) {
        arrayOfDouble = SWTShapeManager.shapeToPolyline(paramShape);
        SHAPE_CACHE.put(paramShape, arrayOfDouble);
      } 
      drawPolyline(arrayOfDouble);
    } 
  }
  
  public void fill(Shape paramShape) {
    if (paramShape instanceof Rectangle2D) {
      Rectangle2D rectangle2D = (Rectangle2D)paramShape;
      fillRect(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    } else if (paramShape instanceof Ellipse2D) {
      Ellipse2D ellipse2D = (Ellipse2D)paramShape;
      fillOval(ellipse2D.getX(), ellipse2D.getY(), ellipse2D.getWidth(), ellipse2D.getHeight());
    } else if (paramShape instanceof RoundRectangle2D) {
      RoundRectangle2D roundRectangle2D = (RoundRectangle2D)paramShape;
      fillRoundRect(roundRectangle2D.getX(), roundRectangle2D.getY(), roundRectangle2D.getWidth(), roundRectangle2D.getHeight(), roundRectangle2D.getArcWidth(), roundRectangle2D.getArcHeight());
    } else if (paramShape instanceof Arc2D) {
      Arc2D arc2D = (Arc2D)paramShape;
      fillArc(arc2D.getX(), arc2D.getY(), arc2D.getWidth(), arc2D.getHeight(), arc2D.getAngleStart(), arc2D.getAngleExtent());
    } else {
      double[] arrayOfDouble = (double[])SHAPE_CACHE.get(paramShape);
      if (arrayOfDouble == null) {
        arrayOfDouble = SWTShapeManager.shapeToPolyline(paramShape);
        SHAPE_CACHE.put(paramShape, arrayOfDouble);
      } 
      fillPolygon(arrayOfDouble);
    } 
  }
  
  public void drawPolyline(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
    int[] arrayOfInt = new int[2 * paramInt];
    for (byte b = 0; b < paramInt; b++) {
      PT.setLocation(paramArrayOfint1[b], paramArrayOfint2[b]);
      this.transform.transform(PT, PT);
      arrayOfInt[2 * b] = paramArrayOfint1[b];
      arrayOfInt[2 * b + 1] = paramArrayOfint2[b];
    } 
    this.gc.setLineWidth(getTransformedLineWidth());
    this.gc.drawPolyline(arrayOfInt);
  }
  
  public void drawPolyline(double[] paramArrayOfdouble) {
    int[] arrayOfInt = SWTShapeManager.transform(paramArrayOfdouble, this.transform);
    this.gc.drawPolyline(arrayOfInt);
  }
  
  public void drawPolygon(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
    int[] arrayOfInt = new int[2 * paramInt];
    for (byte b = 0; b < paramInt; b++) {
      PT.setLocation(paramArrayOfint1[b], paramArrayOfint2[b]);
      this.transform.transform(PT, PT);
      arrayOfInt[2 * b] = paramArrayOfint1[b];
      arrayOfInt[2 * b + 1] = paramArrayOfint2[b];
    } 
    this.gc.drawPolygon(arrayOfInt);
  }
  
  public void fillPolygon(double[] paramArrayOfdouble) {
    int[] arrayOfInt = SWTShapeManager.transform(paramArrayOfdouble, this.transform);
    this.gc.fillPolygon(arrayOfInt);
  }
  
  public void fillPolygon(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
    int[] arrayOfInt = new int[2 * paramInt];
    for (byte b = 0; b < paramInt; b++) {
      PT.setLocation(paramArrayOfint1[b], paramArrayOfint2[b]);
      this.transform.transform(PT, PT);
      arrayOfInt[2 * b] = paramArrayOfint1[b];
      arrayOfInt[2 * b + 1] = paramArrayOfint2[b];
    } 
    this.gc.fillPolygon(arrayOfInt);
  }
  
  public void drawLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    drawLine(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void drawLine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    PT.setLocation(paramDouble1, paramDouble2);
    this.transform.transform(PT, PT);
    paramDouble1 = (int)PT.getX();
    paramDouble2 = (int)PT.getY();
    PT.setLocation(paramDouble3, paramDouble4);
    this.transform.transform(PT, PT);
    paramDouble3 = (int)PT.getX();
    paramDouble4 = (int)PT.getY();
    this.gc.setLineWidth(getTransformedLineWidth());
    this.gc.drawLine((int)(paramDouble1 + 0.5D), (int)(paramDouble2 + 0.5D), (int)(paramDouble3 + 0.5D), (int)(paramDouble4 + 0.5D));
  }
  
  public void copyArea(Image paramImage, double paramDouble1, double paramDouble2) {
    PT.setLocation(paramDouble1, paramDouble2);
    this.transform.transform(PT, PT);
    this.gc.copyArea(paramImage, (int)(PT.getX() + 0.5D), (int)(PT.getY() + 0.5D));
  }
  
  public void copyArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    RECT.setRect(paramInt1, paramInt2, paramInt3, paramInt4);
    SWTShapeManager.transform(RECT, this.transform);
    PT.setLocation(paramInt5, paramInt6);
    this.transform.transform(PT, PT);
    this.gc.copyArea((int)RECT.getX(), (int)RECT.getY(), (int)RECT.getWidth(), (int)RECT.getHeight(), (int)PT.getX(), (int)PT.getY());
  }
  
  public void drawString(String paramString, double paramDouble1, double paramDouble2) {
    PT.setLocation(paramDouble1, paramDouble2);
    this.transform.transform(PT, PT);
    this.gc.setFont(getTransformedFont());
    this.gc.drawString(paramString, (int)(PT.getX() + 0.5D), (int)(PT.getY() + 0.5D), true);
  }
  
  public void drawString(String paramString, int paramInt1, int paramInt2) {
    drawString(paramString, paramInt1, paramInt2);
  }
  
  public void drawString(String paramString, float paramFloat1, float paramFloat2) {
    drawString(paramString, paramFloat1, paramFloat2);
  }
  
  public void drawText(String paramString, double paramDouble1, double paramDouble2) {
    PT.setLocation(paramDouble1, paramDouble2);
    this.transform.transform(PT, PT);
    this.gc.setFont(getTransformedFont());
    this.gc.drawText(paramString, (int)(PT.getX() + 0.5D), (int)(PT.getY() + 0.5D), true);
  }
  
  public void drawText(String paramString, double paramDouble1, double paramDouble2, int paramInt) {
    PT.setLocation(paramDouble1, paramDouble2);
    this.transform.transform(PT, PT);
    this.gc.setFont(getTransformedFont());
    this.gc.drawText(paramString, (int)(PT.getX() + 0.5D), (int)(PT.getY() + 0.5D), paramInt);
  }
  
  public void drawRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    drawRect(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void drawRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    RECT.setRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    SWTShapeManager.transform(RECT, this.transform);
    SWTShapeManager.awtToSWT(RECT, SWT_RECT);
    this.gc.setLineWidth(getTransformedLineWidth());
    this.gc.drawRectangle(SWT_RECT);
  }
  
  public void fillRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void fillRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    RECT.setRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    SWTShapeManager.transform(RECT, this.transform);
    SWTShapeManager.awtToSWT(RECT, SWT_RECT);
    this.gc.fillRectangle(SWT_RECT);
  }
  
  public void drawRoundRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    drawRoundRect(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public void drawRoundRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
    RECT.setRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    SWTShapeManager.transform(RECT, this.transform);
    paramDouble1 = RECT.getX();
    paramDouble2 = RECT.getY();
    paramDouble3 = RECT.getWidth();
    paramDouble4 = RECT.getHeight();
    RECT.setRect(0.0D, 0.0D, paramDouble5, paramDouble6);
    SWTShapeManager.transform(RECT, this.transform);
    paramDouble5 = RECT.getWidth();
    paramDouble6 = RECT.getHeight();
    this.gc.setLineWidth(getTransformedLineWidth());
    this.gc.drawRoundRectangle((int)(paramDouble1 + 0.5D), (int)(paramDouble2 + 0.5D), (int)(paramDouble3 + 0.5D), (int)(paramDouble4 + 0.5D), (int)(paramDouble5 + 0.5D), (int)(paramDouble6 + 0.5D));
  }
  
  public void fillRoundRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    fillRoundRect(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public void fillRoundRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
    RECT.setRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    SWTShapeManager.transform(RECT, this.transform);
    paramDouble1 = RECT.getX();
    paramDouble2 = RECT.getY();
    paramDouble3 = RECT.getWidth();
    paramDouble4 = RECT.getHeight();
    RECT.setRect(0.0D, 0.0D, paramDouble5, paramDouble6);
    SWTShapeManager.transform(RECT, this.transform);
    paramDouble5 = RECT.getWidth();
    paramDouble6 = RECT.getHeight();
    this.gc.setLineWidth(getTransformedLineWidth());
    this.gc.fillRoundRectangle((int)(paramDouble1 + 0.5D), (int)(paramDouble2 + 0.5D), (int)(paramDouble3 + 0.5D), (int)(paramDouble4 + 0.5D), (int)(paramDouble5 + 0.5D), (int)(paramDouble6 + 0.5D));
  }
  
  public void drawOval(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    drawOval(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void drawOval(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    RECT.setRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    SWTShapeManager.transform(RECT, this.transform);
    this.gc.setLineWidth(getTransformedLineWidth());
    this.gc.drawOval((int)(RECT.getX() + 0.5D), (int)(RECT.getY() + 0.5D), (int)(RECT.getWidth() + 0.5D), (int)(RECT.getHeight() + 0.5D));
  }
  
  public void fillOval(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    fillOval(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void fillOval(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    RECT.setRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    SWTShapeManager.transform(RECT, this.transform);
    this.gc.fillOval((int)(RECT.getX() + 0.5D), (int)(RECT.getY() + 0.5D), (int)(RECT.getWidth() + 0.5D), (int)(RECT.getHeight() + 0.5D));
  }
  
  public void drawArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    drawArc(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public void drawArc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
    RECT.setRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    SWTShapeManager.transform(RECT, this.transform);
    this.gc.setLineWidth(getTransformedLineWidth());
    this.gc.drawArc((int)(RECT.getX() + 0.5D), (int)(RECT.getY() + 0.5D), (int)(RECT.getWidth() + 0.5D), (int)(RECT.getHeight() + 0.5D), (int)(paramDouble5 + 0.5D), (int)(paramDouble5 + paramDouble6 + 0.5D));
  }
  
  public void fillArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    drawArc(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public void fillArc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
    RECT.setRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    SWTShapeManager.transform(RECT, this.transform);
    this.gc.drawArc((int)(RECT.getX() + 0.5D), (int)(RECT.getY() + 0.5D), (int)(RECT.getWidth() + 0.5D), (int)(RECT.getHeight() + 0.5D), (int)(paramDouble5 + 0.5D), (int)(paramDouble5 + paramDouble6 + 0.5D));
  }
  
  public void drawImage(Image paramImage, double paramDouble1, double paramDouble2) {
    Rectangle rectangle = paramImage.getBounds();
    RECT.setRect(paramDouble1, paramDouble2, rectangle.width, rectangle.height);
    SWTShapeManager.transform(RECT, this.transform);
    SWTShapeManager.awtToSWT(RECT, SWT_RECT);
    this.gc.drawImage(paramImage, 0, 0, rectangle.width, rectangle.height, SWT_RECT.x, SWT_RECT.y, SWT_RECT.width, SWT_RECT.height);
  }
  
  public void drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    RECT.setRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    SWTShapeManager.transform(RECT, this.transform);
    SWTShapeManager.awtToSWT(RECT, SWT_RECT);
    this.gc.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, SWT_RECT.x, SWT_RECT.y, SWT_RECT.width, SWT_RECT.height);
  }
  
  public void setLineWidth(double paramDouble) {
    this.lineWidth = paramDouble;
  }
  
  protected int getTransformedLineWidth() {
    LINE_RECT.setRect(0.0D, 0.0D, this.lineWidth, this.lineWidth);
    SWTShapeManager.transform(LINE_RECT, this.transform);
    return (int)(Math.max(LINE_RECT.getWidth(), 1.0D) + 0.5D);
  }
  
  public void fillGradientRectangle(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean) {
    RECT.setRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    SWTShapeManager.transform(RECT, this.transform);
    SWTShapeManager.awtToSWT(RECT, SWT_RECT);
    this.gc.fillGradientRectangle(SWT_RECT.x, SWT_RECT.y, SWT_RECT.width, SWT_RECT.height, paramBoolean);
  }
  
  public void setXORMode(boolean paramBoolean) {
    this.gc.setXORMode(paramBoolean);
  }
  
  public int getAdvanceWidth(char paramChar) {
    Font font = this.gc.getFont();
    this.gc.setFont(this.curFont);
    int i = this.gc.getAdvanceWidth(paramChar);
    this.gc.setFont(font);
    return i;
  }
  
  public int getCharWidth(char paramChar) {
    Font font = this.gc.getFont();
    this.gc.setFont(this.curFont);
    int i = this.gc.getCharWidth(paramChar);
    this.gc.setFont(font);
    return i;
  }
  
  public Point stringExtent(String paramString) {
    Font font = this.gc.getFont();
    this.gc.setFont(this.curFont);
    Point point = this.gc.stringExtent(paramString);
    this.gc.setFont(font);
    return point;
  }
  
  public Point textExtent(String paramString) {
    Font font = this.gc.getFont();
    this.gc.setFont(this.curFont);
    Point point = this.gc.textExtent(paramString);
    this.gc.setFont(font);
    return point;
  }
  
  public Point textExtent(String paramString, int paramInt) {
    Font font = this.gc.getFont();
    this.gc.setFont(this.curFont);
    Point point = this.gc.textExtent(paramString, paramInt);
    this.gc.setFont(font);
    return point;
  }
  
  public void drawString(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt1, int paramInt2) {}
  
  public void drawString(AttributedCharacterIterator paramAttributedCharacterIterator, float paramFloat1, float paramFloat2) {}
  
  public void drawGlyphVector(GlyphVector paramGlyphVector, float paramFloat1, float paramFloat2) {}
  
  public boolean hit(Rectangle paramRectangle, Shape paramShape, boolean paramBoolean) {
    return false;
  }
  
  public void setComposite(Composite paramComposite) {}
  
  public void setStroke(Stroke paramStroke) {}
  
  public void setRenderingHint(RenderingHints.Key paramKey, Object paramObject) {}
  
  public Object getRenderingHint(RenderingHints.Key paramKey) {
    return null;
  }
  
  public void setRenderingHints(Map paramMap) {}
  
  public void addRenderingHints(Map paramMap) {}
  
  public RenderingHints getRenderingHints() {
    return null;
  }
  
  public Composite getComposite() {
    return null;
  }
  
  public Stroke getStroke() {
    return null;
  }
  
  public FontRenderContext getFontRenderContext() {
    return null;
  }
  
  public Graphics create() {
    return null;
  }
  
  public void setPaintMode() {}
  
  public void setXORMode(Color paramColor) {}
  
  public FontMetrics getFontMetrics(Font paramFont) {
    return null;
  }
  
  public boolean drawImage(Image paramImage, AffineTransform paramAffineTransform, ImageObserver paramImageObserver) {
    return false;
  }
  
  public void drawImage(BufferedImage paramBufferedImage, BufferedImageOp paramBufferedImageOp, int paramInt1, int paramInt2) {}
  
  public void drawRenderedImage(RenderedImage paramRenderedImage, AffineTransform paramAffineTransform) {}
  
  public void drawRenderableImage(RenderableImage paramRenderableImage, AffineTransform paramAffineTransform) {}
  
  public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
    return false;
  }
  
  public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ImageObserver paramImageObserver) {
    return false;
  }
  
  public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, Color paramColor, ImageObserver paramImageObserver) {
    return false;
  }
  
  public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor, ImageObserver paramImageObserver) {
    return false;
  }
  
  public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, ImageObserver paramImageObserver) {
    return false;
  }
  
  public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Color paramColor, ImageObserver paramImageObserver) {
    return false;
  }
  
  public void dispose() {}
  
  public static void incrementGCCount() {
    CACHE_COUNT++;
  }
  
  public static void decrementGCCount() {
    CACHE_COUNT--;
    if (CACHE_COUNT == 0) {
      for (Font font : FONT_CACHE.values())
        font.dispose(); 
      for (Color color : COLOR_CACHE.values())
        color.dispose(); 
    } 
  }
}
