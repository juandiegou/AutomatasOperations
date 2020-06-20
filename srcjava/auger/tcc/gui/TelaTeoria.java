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

public class TelaTeoria extends JDialog {
  static void mav$botaoVoltar_actionPerformed(TelaTeoria paramTelaTeoria, ActionEvent paramActionEvent) {
    paramTelaTeoria.botaoVoltar_actionPerformed(paramActionEvent);
  }
  
  static void mav$botaoAvancar_actionPerformed(TelaTeoria paramTelaTeoria, ActionEvent paramActionEvent) {
    paramTelaTeoria.botaoAvancar_actionPerformed(paramActionEvent);
  }
  
  static void mav$botaoSair_actionPerformed(TelaTeoria paramTelaTeoria, ActionEvent paramActionEvent) {
    paramTelaTeoria.botaoSair_actionPerformed(paramActionEvent);
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
  
  public TelaTeoria() {
    this((Frame)null, "", false, (String)null);
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public TelaTeoria(String ct) {
    this((Frame)null, "", false, ct);
  }
  
  public TelaTeoria(Frame parent, String title, boolean modal, String ct) {
    super(parent, title, modal);
    try {
      jbInit(ct);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void jbInit(String contextoAncora) throws Exception {
    setSize(new Dimension(700, 480));
    getContentPane().setLayout(this.borderLayout1);
    setTitle("Referencial te");
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
            TelaTeoria.mav$botaoVoltar_actionPerformed(TelaTeoria.this, e);
          }
        });
    this.botaoAvancar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            TelaTeoria.mav$botaoAvancar_actionPerformed(TelaTeoria.this, e);
          }
        });
    this.botaoSair.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            TelaTeoria.mav$botaoSair_actionPerformed(TelaTeoria.this, e);
          }
        });
    Properties p = System.getProperties();
    String diretorioAtual = p.getProperty("user.dir");
    this.enderecoBase = "file://" + diretorioAtual + "/help/teoria/ajuda.html";
    this.webBrowser = new WebBrowser();
    navega(contextoAncora);
    this.jPanelCentro.add((Component)this.webBrowser, "Center");
  }
  
  public void navega(String contextoAncora) {
    try {
      if (contextoAncora == null) {
        this.webBrowser.setURL(new URL(this.enderecoBase));
      } else {
        this.webBrowser.setURL(new URL(this.enderecoBase + "#" + contextoAncora));
      } 
    } catch (MalformedURLException ex) {
      JOptionPane.showMessageDialog(null, "Erro ao abrir arquivo de ajuda!", "Erro", 0);
    } 
  }
  
  private void botaoSair_actionPerformed(ActionEvent e) {
    setVisible(false);
  }
  
  private void botaoAvancar_actionPerformed(ActionEvent e) {
    this.webBrowser.forward();
  }
  
  private void botaoVoltar_actionPerformed(ActionEvent e) {
    this.webBrowser.back();
  }
  
  private void jbInit() throws Exception {}
}
