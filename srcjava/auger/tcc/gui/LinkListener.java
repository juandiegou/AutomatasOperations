package auger.tcc.gui;

import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

class LinkListener implements HyperlinkListener {
  private JEditorPane browser;
  
  public LinkListener(JEditorPane brs, String endereco) {
    this.browser = brs;
  }
  
  public void hyperlinkUpdate(HyperlinkEvent ev) {
    HyperlinkEvent.EventType type = ev.getEventType();
    if (type == HyperlinkEvent.EventType.ACTIVATED)
      try {
        this.browser.setPage(ev.getURL());
      } catch (IOException ex) {
        ex.printStackTrace(System.err);
      }  
  }
}
