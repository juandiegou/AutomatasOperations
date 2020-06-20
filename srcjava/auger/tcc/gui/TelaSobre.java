package auger.tcc.gui;

import auger.tcc.Auger;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class TelaSobre extends JDialog {
  private Icon imgTela;
  
  static void mav$this_keyPressed(TelaSobre paramTelaSobre, KeyEvent paramKeyEvent) {
    paramTelaSobre.this_keyPressed(paramKeyEvent);
  }
  
  private JLabel lblTelaSobre = new JLabel();
  
  public TelaSobre() {
    this((Frame)null, "", false);
  }
  
  public TelaSobre(Frame parent, String title, boolean modal) {
    super(parent, title, modal);
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void jbInit() throws Exception {
    this.imgTela = new ImageIcon(Auger.getImagem("images/tela_sobre.jpg"));
    setSize(new Dimension(483, 278));
    getContentPane().setLayout((LayoutManager)null);
    setTitle("Sobre o Auger...");
    setResizable(false);
    addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            TelaSobre.mav$this_keyPressed(TelaSobre.this, e);
          }
        });
    this.lblTelaSobre.setBounds(new Rectangle(0, -1, 480, 248));
    this.lblTelaSobre.setIcon(this.imgTela);
    getContentPane().add(this.lblTelaSobre, (Object)null);
  }
  
  private void this_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == 27)
      setVisible(false); 
  }
}
