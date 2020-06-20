package auger.tcc.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jdesktop.jdic.browser.WebBrowser;

public class TelaAjuda extends JDialog {
  static void mav$botaoVoltar_actionPerformed(TelaAjuda paramTelaAjuda, ActionEvent paramActionEvent) {
    paramTelaAjuda.botaoVoltar_actionPerformed(paramActionEvent);
  }
  
  static void mav$botaoAvancar_actionPerformed(TelaAjuda paramTelaAjuda, ActionEvent paramActionEvent) {
    paramTelaAjuda.botaoAvancar_actionPerformed(paramActionEvent);
  }
  
  static void mav$botaoSair_actionPerformed(TelaAjuda paramTelaAjuda, ActionEvent paramActionEvent) {
    paramTelaAjuda.botaoSair_actionPerformed(paramActionEvent);
  }
  
  private BorderLayout borderLayout1 = new BorderLayout();
  
  private JPanel jPanelCentro = new JPanel();
  
  private JPanel jPanelInferior = new JPanel();
  
  private WebBrowser webBrowser;
  
  private BorderLayout borderLayout2 = new BorderLayout();
  
  private JButton botaoSair;
  
  private JButton botaoVoltar;
  
  private JButton botaoAvancar;
  
  private FlowLayout flowLayout1 = new FlowLayout();
  
  private String enderecoBase;
  
  public TelaAjuda() {
    this((Frame)null, "", true);
  }
  
  public TelaAjuda(Frame parent, String title, boolean modal) {
    super(parent, title, modal);
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void jbInit() throws Exception {
    setSize(new Dimension(562, 465));
    getContentPane().setLayout(this.borderLayout1);
    getContentPane().add(this.jPanelCentro, "Center");
    getContentPane().add(this.jPanelInferior, "South");
    this.jPanelCentro.setLayout(this.borderLayout2);
    this.jPanelInferior.setLayout(this.flowLayout1);
    this.botaoSair = new JButton("Sair");
    this.botaoSair.setMnemonic('S');
    this.jPanelInferior.add(this.botaoSair, 0);
    this.botaoAvancar = new JButton("Avan");
    this.botaoAvancar.setMnemonic('A');
    this.jPanelInferior.add(this.botaoAvancar, 0);
    this.botaoVoltar = new JButton("Voltar");
    this.botaoVoltar.setMnemonic('V');
    this.jPanelInferior.add(this.botaoVoltar, 0);
    this.botaoVoltar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            TelaAjuda.mav$botaoVoltar_actionPerformed(TelaAjuda.this, e);
          }
        });
    this.botaoAvancar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            TelaAjuda.mav$botaoAvancar_actionPerformed(TelaAjuda.this, e);
          }
        });
    this.botaoSair.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            TelaAjuda.mav$botaoSair_actionPerformed(TelaAjuda.this, e);
          }
        });
    Properties p = System.getProperties();
    String diretorioAtual = p.getProperty("user.dir");
    this.enderecoBase = "file://" + diretorioAtual + "/help/AugerAjuda/index.html";
    this.enderecoBase = this.enderecoBase.replace('\\', '/');
    System.out.println(this.enderecoBase);
    this.webBrowser = new WebBrowser();
    navega();
    this.jPanelCentro.add((Component)this.webBrowser, "Center");
  }
  
  public void navega() {
    if (this.webBrowser == null)
      this.webBrowser = new WebBrowser(); 
    try {
      this.webBrowser.setURL(new URL(this.enderecoBase));
    } catch (MalformedURLException ex) {
      JOptionPane.showMessageDialog(null, "Erro ao abrir arquivo de ajuda!", "Erro", 0);
    } 
  }
  
  private void botaoSair_actionPerformed(ActionEvent e) {
    setVisible(false);
    this.webBrowser.dispose();
    this.webBrowser = null;
  }
  
  private void botaoAvancar_actionPerformed(ActionEvent e) {
    this.webBrowser.forward();
  }
  
  private void botaoVoltar_actionPerformed(ActionEvent e) {
    this.webBrowser.back();
  }
}