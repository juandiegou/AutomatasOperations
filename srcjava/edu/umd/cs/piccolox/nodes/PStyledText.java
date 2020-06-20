package edu.umd.cs.piccolox.nodes;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PPaintContext;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class PStyledText extends PNode {
  protected static FontRenderContext SWING_FRC = new FontRenderContext(null, true, false);
  
  protected static Line2D paintLine = new Line2D.Double();
  
  protected Document document;
  
  protected transient ArrayList stringContents;
  
  protected transient LineInfo[] lines;
  
  protected boolean editing;
  
  protected Insets insets = new Insets(0, 0, 0, 0);
  
  protected boolean constrainHeightToTextHeight = true;
  
  protected boolean constrainWidthToTextWidth = true;
  
  public void setConstrainWidthToTextWidth(boolean paramBoolean) {
    this.constrainWidthToTextWidth = paramBoolean;
    recomputeLayout();
  }
  
  public void setConstrainHeightToTextHeight(boolean paramBoolean) {
    this.constrainHeightToTextHeight = paramBoolean;
    recomputeLayout();
  }
  
  public boolean getConstrainWidthToTextWidth() {
    return this.constrainWidthToTextWidth;
  }
  
  public boolean getConstrainHeightToTextHeight() {
    return this.constrainHeightToTextHeight;
  }
  
  public Document getDocument() {
    return this.document;
  }
  
  public void setDocument(Document paramDocument) {
    this.document = paramDocument;
    syncWithDocument();
  }
  
  public void syncWithDocument() {
    ArrayList arrayList = null;
    int i = 0;
    try {
      this.stringContents = new ArrayList();
      arrayList = new ArrayList();
      String str = this.document.getText(0, this.document.getLength());
      StringTokenizer stringTokenizer = new StringTokenizer(str, "\n", true);
      boolean bool = true;
      for (byte b1 = 0; stringTokenizer.hasMoreTokens(); b1++) {
        String str1 = stringTokenizer.nextToken();
        if (str1.equals("\n")) {
          if (bool) {
            this.stringContents.add(new AttributedString(" "));
            arrayList.add(new RunInfo(i, i + 1));
            i++;
            bool = true;
          } else {
            i++;
            bool = true;
          } 
        } else if (str1.equals("")) {
          this.stringContents.add(new AttributedString(" "));
          arrayList.add(new RunInfo(i, i));
          bool = false;
        } else {
          this.stringContents.add(new AttributedString(str1));
          arrayList.add(new RunInfo(i, i + str1.length()));
          i += str1.length();
          bool = false;
        } 
      } 
      if (bool) {
        this.stringContents.add(new AttributedString(" "));
        arrayList.add(new RunInfo(i, i + 1));
        bool = false;
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    StyleContext styleContext = StyleContext.getDefaultStyleContext();
    RunInfo runInfo = null;
    for (byte b = 0; b < this.stringContents.size(); b++) {
      runInfo = arrayList.get(b);
      i = runInfo.runStart;
      Element element1 = null;
      Element element2 = this.document.getDefaultRootElement();
      if (runInfo.runStart != runInfo.runLimit) {
        while (i < runInfo.runLimit) {
          for (element1 = element2; !element1.isLeaf(); element1 = element1.getElement(element1.getElementIndex(i)));
          AttributeSet attributeSet = element1.getAttributes();
          Color color1 = styleContext.getForeground(attributeSet);
          ((AttributedString)this.stringContents.get(b)).addAttribute(TextAttribute.FOREGROUND, color1, Math.max(0, element1.getStartOffset() - runInfo.runStart), Math.min(runInfo.runLimit - runInfo.runStart, element1.getEndOffset() - runInfo.runStart));
          Font font = (attributeSet.isDefined(StyleConstants.FontSize) || attributeSet.isDefined(StyleConstants.FontFamily)) ? styleContext.getFont(attributeSet) : null;
          if (font == null)
            if (this.document instanceof DefaultStyledDocument) {
              font = styleContext.getFont(((DefaultStyledDocument)this.document).getCharacterElement(i).getAttributes());
              if (font == null)
                font = styleContext.getFont(((DefaultStyledDocument)this.document).getParagraphElement(i).getAttributes()); 
              if (font == null)
                font = styleContext.getFont(element2.getAttributes()); 
            } else {
              font = styleContext.getFont(element2.getAttributes());
            }  
          if (font != null)
            ((AttributedString)this.stringContents.get(b)).addAttribute(TextAttribute.FONT, font, Math.max(0, element1.getStartOffset() - runInfo.runStart), Math.min(runInfo.runLimit - runInfo.runStart, element1.getEndOffset() - runInfo.runStart)); 
          Color color2 = attributeSet.isDefined(StyleConstants.Background) ? styleContext.getBackground(attributeSet) : null;
          if (color2 != null)
            ((AttributedString)this.stringContents.get(b)).addAttribute(TextAttribute.BACKGROUND, color2, Math.max(0, element1.getStartOffset() - runInfo.runStart), Math.min(runInfo.runLimit - runInfo.runStart, element1.getEndOffset() - runInfo.runStart)); 
          boolean bool1 = StyleConstants.isUnderline(attributeSet);
          if (bool1)
            ((AttributedString)this.stringContents.get(b)).addAttribute(TextAttribute.UNDERLINE, Boolean.TRUE, Math.max(0, element1.getStartOffset() - runInfo.runStart), Math.min(runInfo.runLimit - runInfo.runStart, element1.getEndOffset() - runInfo.runStart)); 
          boolean bool2 = StyleConstants.isStrikeThrough(attributeSet);
          if (bool2)
            ((AttributedString)this.stringContents.get(b)).addAttribute(TextAttribute.STRIKETHROUGH, Boolean.TRUE, Math.max(0, element1.getStartOffset() - runInfo.runStart), Math.min(runInfo.runLimit - runInfo.runStart, element1.getEndOffset() - runInfo.runStart)); 
          i = element1.getEndOffset();
        } 
      } else {
        for (element1 = element2; !element1.isLeaf(); element1 = element1.getElement(element1.getElementIndex(i)));
        AttributeSet attributeSet = element1.getAttributes();
        Color color1 = styleContext.getForeground(attributeSet);
        ((AttributedString)this.stringContents.get(b)).addAttribute(TextAttribute.FOREGROUND, color1, Math.max(0, element1.getStartOffset() - runInfo.runStart), Math.min(runInfo.runLimit - runInfo.runStart, element1.getEndOffset() - runInfo.runStart));
        Font font = (attributeSet.isDefined(StyleConstants.FontSize) || attributeSet.isDefined(StyleConstants.FontFamily)) ? styleContext.getFont(attributeSet) : null;
        if (font == null)
          if (this.document instanceof DefaultStyledDocument) {
            font = styleContext.getFont(((DefaultStyledDocument)this.document).getCharacterElement(i).getAttributes());
            if (font == null)
              font = styleContext.getFont(((DefaultStyledDocument)this.document).getParagraphElement(i).getAttributes()); 
            if (font == null)
              font = styleContext.getFont(element2.getAttributes()); 
          } else {
            font = styleContext.getFont(element2.getAttributes());
          }  
        if (font != null)
          ((AttributedString)this.stringContents.get(b)).addAttribute(TextAttribute.FONT, font, Math.max(0, element1.getStartOffset() - runInfo.runStart), Math.min(runInfo.runLimit - runInfo.runStart, element1.getEndOffset() - runInfo.runStart)); 
        Color color2 = attributeSet.isDefined(StyleConstants.Background) ? styleContext.getBackground(attributeSet) : null;
        if (color2 != null)
          ((AttributedString)this.stringContents.get(b)).addAttribute(TextAttribute.BACKGROUND, color2, Math.max(0, element1.getStartOffset() - runInfo.runStart), Math.min(runInfo.runLimit - runInfo.runStart, element1.getEndOffset() - runInfo.runStart)); 
        boolean bool1 = StyleConstants.isUnderline(attributeSet);
        if (bool1)
          ((AttributedString)this.stringContents.get(b)).addAttribute(TextAttribute.UNDERLINE, Boolean.TRUE, Math.max(0, element1.getStartOffset() - runInfo.runStart), Math.min(runInfo.runLimit - runInfo.runStart, element1.getEndOffset() - runInfo.runStart)); 
        boolean bool2 = StyleConstants.isStrikeThrough(attributeSet);
        if (bool2)
          ((AttributedString)this.stringContents.get(b)).addAttribute(TextAttribute.STRIKETHROUGH, Boolean.TRUE, Math.max(0, element1.getStartOffset() - runInfo.runStart), Math.min(runInfo.runLimit - runInfo.runStart, element1.getEndOffset() - runInfo.runStart)); 
      } 
    } 
    recomputeLayout();
  }
  
  public void recomputeLayout() {
    if (this.stringContents == null)
      return; 
    ArrayList arrayList = new ArrayList();
    double d1 = 0.0D;
    double d2 = 0.0D;
    for (byte b = 0; b < this.stringContents.size(); b++) {
      AttributedString attributedString = this.stringContents.get(b);
      AttributedCharacterIterator attributedCharacterIterator = attributedString.getIterator();
      ArrayList arrayList1 = null;
      LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(attributedCharacterIterator, SWING_FRC);
      arrayList1 = new ArrayList();
      while (lineBreakMeasurer.getPosition() < attributedCharacterIterator.getEndIndex()) {
        if (this.constrainWidthToTextWidth) {
          lineBreakMeasurer.nextLayout(Float.MAX_VALUE);
        } else {
          lineBreakMeasurer.nextLayout((float)Math.ceil(getWidth() - this.insets.left - this.insets.right));
        } 
        arrayList1.add(new Integer(lineBreakMeasurer.getPosition()));
      } 
      lineBreakMeasurer = new LineBreakMeasurer(attributedCharacterIterator, PPaintContext.RENDER_QUALITY_HIGH_FRC);
      LineInfo lineInfo = null;
      boolean bool = true;
      double d3;
      for (d3 = 0.0D; lineBreakMeasurer.getPosition() < attributedCharacterIterator.getEndIndex(); d3 += textLayout.getAdvance()) {
        TextLayout textLayout = null;
        if (bool) {
          bool = false;
          double d = (lineInfo == null) ? 0.0D : (lineInfo.maxAscent + lineInfo.maxDescent + lineInfo.leading);
          d2 += d;
          d1 = Math.max(d1, d3);
          lineInfo = new LineInfo();
          arrayList.add(lineInfo);
        } 
        int i = ((Integer)arrayList1.get(0)).intValue();
        if (i <= attributedCharacterIterator.getRunLimit()) {
          arrayList1.remove(0);
          bool = true;
        } 
        textLayout = lineBreakMeasurer.nextLayout(Float.MAX_VALUE, Math.min(i, attributedCharacterIterator.getRunLimit()), false);
        SegmentInfo segmentInfo = new SegmentInfo();
        segmentInfo.font = (Font)attributedCharacterIterator.getAttribute(TextAttribute.FONT);
        segmentInfo.foreground = (Color)attributedCharacterIterator.getAttribute(TextAttribute.FOREGROUND);
        segmentInfo.background = (Color)attributedCharacterIterator.getAttribute(TextAttribute.BACKGROUND);
        segmentInfo.underline = (Boolean)attributedCharacterIterator.getAttribute(TextAttribute.UNDERLINE);
        segmentInfo.layout = textLayout;
        FontMetrics fontMetrics = StyleContext.getDefaultStyleContext().getFontMetrics((Font)attributedCharacterIterator.getAttribute(TextAttribute.FONT));
        lineInfo.maxAscent = Math.max(lineInfo.maxAscent, fontMetrics.getMaxAscent());
        lineInfo.maxDescent = Math.max(lineInfo.maxDescent, fontMetrics.getMaxDescent());
        lineInfo.leading = Math.max(lineInfo.leading, fontMetrics.getLeading());
        lineInfo.segments.add(segmentInfo);
        attributedCharacterIterator.setIndex(lineBreakMeasurer.getPosition());
      } 
      double d4 = (lineInfo == null) ? 0.0D : (lineInfo.maxAscent + lineInfo.maxDescent + lineInfo.leading);
      d2 += d4;
      d1 = Math.max(d1, d3);
    } 
    this.lines = arrayList.<LineInfo>toArray(new LineInfo[0]);
    if (this.constrainWidthToTextWidth || this.constrainHeightToTextHeight) {
      double d3 = getWidth();
      double d4 = getHeight();
      if (this.constrainWidthToTextWidth)
        d3 = d1 + this.insets.left + this.insets.right; 
      if (this.constrainHeightToTextHeight)
        d4 = Math.max(d2, getInitialFontHeight()) + this.insets.top + this.insets.bottom; 
      super.setBounds(getX(), getY(), d3, d4);
    } 
  }
  
  public double getInitialFontHeight() {
    Element element1 = this.document.getDefaultRootElement();
    Element element2;
    for (element2 = element1; !element2.isLeaf(); element2 = element2.getElement(element2.getElementIndex(0)));
    StyleContext styleContext = StyleContext.getDefaultStyleContext();
    Font font = styleContext.getFont(element2.getAttributes());
    FontMetrics fontMetrics = styleContext.getFontMetrics(font);
    return (fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent() + fontMetrics.getLeading());
  }
  
  protected void paint(PPaintContext paramPPaintContext) {
    float f1 = (float)(getX() + this.insets.left);
    float f2 = (float)(getY() + this.insets.top);
    float f3 = (float)(getY() + getHeight() - this.insets.bottom);
    if (this.lines == null || this.lines.length == 0)
      return; 
    Graphics2D graphics2D = paramPPaintContext.getGraphics();
    if (getPaint() != null) {
      graphics2D.setPaint(getPaint());
      graphics2D.fill((Shape)getBoundsReference());
    } 
    for (byte b = 0; b < this.lines.length; b++) {
      f2 = (float)(f2 + (this.lines[b]).maxAscent);
      float f = f1;
      if (f3 < f2)
        return; 
      for (byte b1 = 0; b1 < (this.lines[b]).segments.size(); b1++) {
        SegmentInfo segmentInfo = (this.lines[b]).segments.get(b1);
        float f4 = segmentInfo.layout.getAdvance();
        if (segmentInfo.background != null) {
          graphics2D.setPaint(segmentInfo.background);
          graphics2D.fill(new Rectangle2D.Double(f, f2 - (this.lines[b]).maxAscent, f4, (this.lines[b]).maxAscent + (this.lines[b]).maxDescent + (this.lines[b]).leading));
        } 
        if (segmentInfo.font != null)
          graphics2D.setFont(segmentInfo.font); 
        graphics2D.setPaint(segmentInfo.foreground);
        segmentInfo.layout.draw(graphics2D, f, f2);
        if (segmentInfo.underline != null) {
          paintLine.setLine(f1, f2 + (this.lines[b]).maxDescent / 2.0D, (f1 + f4), f2 + (this.lines[b]).maxDescent / 2.0D);
          graphics2D.draw(paintLine);
        } 
        f += f4;
      } 
      f2 = (float)(f2 + (this.lines[b]).maxDescent + (this.lines[b]).leading);
    } 
  }
  
  public void fullPaint(PPaintContext paramPPaintContext) {
    if (!this.editing)
      super.fullPaint(paramPPaintContext); 
  }
  
  public void setEditing(boolean paramBoolean) {
    this.editing = paramBoolean;
  }
  
  public boolean isEditing() {
    return this.editing;
  }
  
  public void setInsets(Insets paramInsets) {
    if (paramInsets != null) {
      this.insets.left = paramInsets.left;
      this.insets.right = paramInsets.right;
      this.insets.top = paramInsets.top;
      this.insets.bottom = paramInsets.bottom;
      recomputeLayout();
    } 
  }
  
  public Insets getInsets() {
    return (Insets)this.insets.clone();
  }
  
  public boolean setBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    if (this.document == null || !super.setBounds(paramDouble1, paramDouble2, paramDouble3, paramDouble4))
      return false; 
    recomputeLayout();
    return true;
  }
  
  protected static class SegmentInfo {
    public TextLayout layout;
    
    public Font font;
    
    public Color foreground;
    
    public Color background;
    
    public Boolean underline;
  }
  
  protected static class LineInfo {
    public List segments = new ArrayList();
    
    public double maxAscent;
    
    public double maxDescent;
    
    public double leading;
  }
  
  protected static class MetricsRunInfo extends RunInfo {
    public FontMetrics metrics;
  }
  
  protected static class RunInfo {
    public int runStart;
    
    public int runLimit;
    
    public RunInfo() {}
    
    public RunInfo(int param1Int1, int param1Int2) {
      this.runStart = param1Int1;
      this.runLimit = param1Int2;
    }
  }
}
