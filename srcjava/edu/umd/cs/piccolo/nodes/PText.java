package edu.umd.cs.piccolo.nodes;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PPaintContext;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;

public class PText extends PNode {
  public static final String PROPERTY_TEXT = "text";
  
  public static final int PROPERTY_CODE_TEXT = 524288;
  
  public static final String PROPERTY_FONT = "font";
  
  public static final int PROPERTY_CODE_FONT = 1048576;
  
  public static Font DEFAULT_FONT = new Font("Helvetica", 0, 12);
  
  public static double DEFAULT_GREEK_THRESHOLD = 5.5D;
  
  private String text;
  
  private Paint textPaint;
  
  private Font font;
  
  protected double greekThreshold = DEFAULT_GREEK_THRESHOLD;
  
  private float justification = 0.0F;
  
  private boolean constrainHeightToTextHeight = true;
  
  private boolean constrainWidthToTextWidth = true;
  
  private transient TextLayout[] lines;
  
  private static final TextLayout[] EMPTY_TEXT_LAYOUT_ARRAY = new TextLayout[0];
  
  public PText() {
    setTextPaint(Color.BLACK);
  }
  
  public PText(String paramString) {
    this();
    setText(paramString);
  }
  
  public float getJustification() {
    return this.justification;
  }
  
  public void setJustification(float paramFloat) {
    this.justification = paramFloat;
    recomputeLayout();
  }
  
  public Paint getTextPaint() {
    return this.textPaint;
  }
  
  public void setTextPaint(Paint paramPaint) {
    this.textPaint = paramPaint;
    invalidatePaint();
  }
  
  public boolean isConstrainWidthToTextWidth() {
    return this.constrainWidthToTextWidth;
  }
  
  public void setConstrainWidthToTextWidth(boolean paramBoolean) {
    this.constrainWidthToTextWidth = paramBoolean;
    recomputeLayout();
  }
  
  public boolean isConstrainHeightToTextHeight() {
    return this.constrainHeightToTextHeight;
  }
  
  public void setConstrainHeightToTextHeight(boolean paramBoolean) {
    this.constrainHeightToTextHeight = paramBoolean;
    recomputeLayout();
  }
  
  public double getGreekThreshold() {
    return this.greekThreshold;
  }
  
  public void setGreekThreshold(double paramDouble) {
    this.greekThreshold = paramDouble;
    invalidatePaint();
  }
  
  public String getText() {
    return this.text;
  }
  
  public void setText(String paramString) {
    String str = this.text;
    this.text = paramString;
    this.lines = null;
    recomputeLayout();
    invalidatePaint();
    firePropertyChange(524288, "text", str, this.text);
  }
  
  public Font getFont() {
    if (this.font == null)
      this.font = DEFAULT_FONT; 
    return this.font;
  }
  
  public void setFont(Font paramFont) {
    Font font = this.font;
    this.font = paramFont;
    this.lines = null;
    recomputeLayout();
    invalidatePaint();
    firePropertyChange(1048576, "font", font, this.font);
  }
  
  public void recomputeLayout() {
    ArrayList arrayList = new ArrayList();
    double d1 = 0.0D;
    double d2 = 0.0D;
    if (this.text != null && this.text.length() > 0) {
      AttributedString attributedString = new AttributedString(this.text);
      attributedString.addAttribute(TextAttribute.FONT, getFont());
      AttributedCharacterIterator attributedCharacterIterator = attributedString.getIterator();
      LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(attributedCharacterIterator, PPaintContext.RENDER_QUALITY_HIGH_FRC);
      float f = this.constrainWidthToTextWidth ? Float.MAX_VALUE : (float)getWidth();
      int i = this.text.indexOf('\n');
      if (i == -1) {
        i = Integer.MAX_VALUE;
      } else {
        i++;
      } 
      while (lineBreakMeasurer.getPosition() < attributedCharacterIterator.getEndIndex()) {
        TextLayout textLayout = computeNextLayout(lineBreakMeasurer, f, i);
        if (i == lineBreakMeasurer.getPosition()) {
          i = this.text.indexOf('\n', lineBreakMeasurer.getPosition());
          if (i == -1) {
            i = Integer.MAX_VALUE;
          } else {
            i++;
          } 
        } 
        arrayList.add(textLayout);
        d2 += textLayout.getAscent();
        d2 += (textLayout.getDescent() + textLayout.getLeading());
        d1 = Math.max(d1, textLayout.getAdvance());
      } 
    } 
    this.lines = arrayList.<TextLayout>toArray(EMPTY_TEXT_LAYOUT_ARRAY);
    if (this.constrainWidthToTextWidth || this.constrainHeightToTextHeight) {
      double d3 = getWidth();
      double d4 = getHeight();
      if (this.constrainWidthToTextWidth)
        d3 = d1; 
      if (this.constrainHeightToTextHeight)
        d4 = d2; 
      setBounds(getX(), getY(), d3, d4);
    } 
  }
  
  protected TextLayout computeNextLayout(LineBreakMeasurer paramLineBreakMeasurer, float paramFloat, int paramInt) {
    return paramLineBreakMeasurer.nextLayout(paramFloat, paramInt, false);
  }
  
  protected void paint(PPaintContext paramPPaintContext) {
    super.paint(paramPPaintContext);
    float f = getFont().getSize() * (float)paramPPaintContext.getScale();
    if (this.textPaint != null && f > this.greekThreshold) {
      float f1 = (float)getX();
      float f2 = (float)getY();
      float f3 = (float)getHeight() + f2;
      Graphics2D graphics2D = paramPPaintContext.getGraphics();
      if (this.lines == null) {
        recomputeLayout();
        repaint();
        return;
      } 
      graphics2D.setPaint(this.textPaint);
      for (byte b = 0; b < this.lines.length; b++) {
        TextLayout textLayout = this.lines[b];
        f2 += textLayout.getAscent();
        if (f3 < f2)
          return; 
        float f4 = (float)(getWidth() - textLayout.getAdvance()) * this.justification;
        textLayout.draw(graphics2D, f1 + f4, f2);
        f2 += textLayout.getDescent() + textLayout.getLeading();
      } 
    } 
  }
  
  protected void internalUpdateBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    recomputeLayout();
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("text=" + ((this.text == null) ? "null" : this.text));
    stringBuffer.append(",font=" + ((this.font == null) ? "null" : this.font.toString()));
    stringBuffer.append(',');
    stringBuffer.append(super.paramString());
    return stringBuffer.toString();
  }
}
