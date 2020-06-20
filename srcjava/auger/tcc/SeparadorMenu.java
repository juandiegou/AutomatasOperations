package auger.tcc;

import java.awt.Graphics;
import javax.swing.JMenuItem;

class SeparadorMenu extends JMenuItem {
  public void repaint(Graphics g) {
    g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
  }
  
  public void onPaint(Graphics g) {
    g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
  }
}