package edu.umd.cs.piccolox.event;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolox.nodes.PStyledText;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class PStyledTextEventHandler extends PBasicInputEventHandler {
  protected PCanvas canvas;
  
  protected JTextComponent editor;
  
  protected DocumentListener docListener;
  
  protected PStyledText editedText;
  
  public PStyledTextEventHandler(PCanvas paramPCanvas) {
    this.canvas = paramPCanvas;
    initEditor(createDefaultEditor());
  }
  
  public PStyledTextEventHandler(PCanvas paramPCanvas, JTextComponent paramJTextComponent) {
    this.canvas = paramPCanvas;
    initEditor(paramJTextComponent);
  }
  
  protected void initEditor(JTextComponent paramJTextComponent) {
    this.editor = paramJTextComponent;
    this.canvas.setLayout(null);
    this.canvas.add(this.editor);
    this.editor.setVisible(false);
    this.docListener = createDocumentListener();
  }
  
  protected JTextComponent createDefaultEditor() {
    JTextPane jTextPane = new JTextPane(this) {
        private final PStyledTextEventHandler this$0;
        
        public void paint(Graphics param1Graphics) {
          Graphics2D graphics2D = (Graphics2D)param1Graphics;
          graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
          graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
          graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
          super.paint(param1Graphics);
        }
        
        public void scrollRectToVisible() {}
      };
    jTextPane.setBorder(new CompoundBorder(new LineBorder(Color.black), new EmptyBorder(3, 3, 3, 3)));
    return jTextPane;
  }
  
  protected DocumentListener createDocumentListener() {
    return new DocumentListener(this) {
        private final PStyledTextEventHandler this$0;
        
        public void removeUpdate(DocumentEvent param1DocumentEvent) {
          this.this$0.reshapeEditorLater();
        }
        
        public void insertUpdate(DocumentEvent param1DocumentEvent) {
          this.this$0.reshapeEditorLater();
        }
        
        public void changedUpdate(DocumentEvent param1DocumentEvent) {
          this.this$0.reshapeEditorLater();
        }
      };
  }
  
  public PStyledText createText() {
    PStyledText pStyledText = new PStyledText();
    Document document = this.editor.getUI().getEditorKit(this.editor).createDefaultDocument();
    if (document instanceof StyledDocument && (!document.getDefaultRootElement().getAttributes().isDefined(StyleConstants.FontFamily) || !document.getDefaultRootElement().getAttributes().isDefined(StyleConstants.FontSize))) {
      Font font = this.editor.getFont();
      SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
      simpleAttributeSet.addAttribute(StyleConstants.FontFamily, font.getFamily());
      simpleAttributeSet.addAttribute(StyleConstants.FontSize, new Integer(font.getSize()));
      ((StyledDocument)document).setParagraphAttributes(0, document.getLength(), simpleAttributeSet, false);
    } 
    pStyledText.setDocument(document);
    return pStyledText;
  }
  
  public void mousePressed(PInputEvent paramPInputEvent) {
    PNode pNode = paramPInputEvent.getPickedNode();
    stopEditing();
    if (pNode instanceof PStyledText) {
      startEditing(paramPInputEvent, (PStyledText)pNode);
    } else if (pNode instanceof edu.umd.cs.piccolo.PCamera) {
      PStyledText pStyledText = createText();
      Insets insets = pStyledText.getInsets();
      this.canvas.getLayer().addChild((PNode)pStyledText);
      pStyledText.translate(paramPInputEvent.getPosition().getX() - insets.left, paramPInputEvent.getPosition().getY() - insets.top);
      startEditing(paramPInputEvent, pStyledText);
    } 
  }
  
  public void startEditing(PInputEvent paramPInputEvent, PStyledText paramPStyledText) {
    Insets insets1 = paramPStyledText.getInsets();
    Point2D.Double double_ = new Point2D.Double(paramPStyledText.getX() + insets1.left, paramPStyledText.getY() + insets1.top);
    paramPStyledText.localToGlobal(double_);
    paramPInputEvent.getTopCamera().viewToLocal(double_);
    this.editor.setDocument(paramPStyledText.getDocument());
    this.editor.setVisible(true);
    Insets insets2 = this.editor.getBorder().getBorderInsets(this.editor);
    this.editor.setLocation((int)double_.getX() - insets2.left, (int)double_.getY() - insets2.top);
    reshapeEditorLater();
    dispatchEventToEditor(paramPInputEvent);
    this.canvas.repaint();
    paramPStyledText.setEditing(true);
    paramPStyledText.getDocument().addDocumentListener(this.docListener);
    this.editedText = paramPStyledText;
  }
  
  public void stopEditing() {
    if (this.editedText != null) {
      this.editedText.getDocument().removeDocumentListener(this.docListener);
      this.editedText.setEditing(false);
      if (this.editedText.getDocument().getLength() == 0) {
        this.editedText.removeFromParent();
      } else {
        this.editedText.syncWithDocument();
      } 
      this.editor.setVisible(false);
      this.canvas.repaint();
      this.editedText = null;
    } 
  }
  
  public void dispatchEventToEditor(PInputEvent paramPInputEvent) {
    SwingUtilities.invokeLater(new Runnable(this, paramPInputEvent) {
          private final PInputEvent val$e;
          
          private final PStyledTextEventHandler this$0;
          
          public void run() {
            SwingUtilities.invokeLater((Runnable)new Object(this));
          }
        });
  }
  
  public void reshapeEditor() {
    if (this.editedText != null) {
      Dimension dimension = this.editor.getPreferredSize();
      Insets insets1 = this.editedText.getInsets();
      Insets insets2 = this.editor.getInsets();
      int i = this.editedText.getConstrainWidthToTextWidth() ? (int)dimension.getWidth() : (int)(this.editedText.getWidth() - insets1.left - insets1.right + insets2.left + insets2.right + 3.0D);
      dimension.setSize(i, dimension.getHeight());
      this.editor.setSize(dimension);
      dimension = this.editor.getPreferredSize();
      int j = this.editedText.getConstrainHeightToTextHeight() ? (int)dimension.getHeight() : (int)(this.editedText.getHeight() - insets1.top - insets1.bottom + insets2.top + insets2.bottom + 3.0D);
      dimension.setSize(i, j);
      this.editor.setSize(dimension);
    } 
  }
  
  protected void reshapeEditorLater() {
    SwingUtilities.invokeLater(new Runnable(this) {
          private final PStyledTextEventHandler this$0;
          
          public void run() {
            this.this$0.reshapeEditor();
          }
        });
  }
}
