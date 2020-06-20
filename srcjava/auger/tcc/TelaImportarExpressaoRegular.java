package auger.tcc;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class TelaImportarExpressaoRegular extends JDialog {
  static void mav$botaoImportar_actionPerformed(TelaImportarExpressaoRegular paramTelaImportarExpressaoRegular, ActionEvent paramActionEvent) {
    paramTelaImportarExpressaoRegular.botaoImportar_actionPerformed(paramActionEvent);
  }
  
  static void mav$textFieldExpressao_actionPerformed(TelaImportarExpressaoRegular paramTelaImportarExpressaoRegular, ActionEvent paramActionEvent) {
    paramTelaImportarExpressaoRegular.textFieldExpressao_actionPerformed(paramActionEvent);
  }
  
  private JButton botaoImportar = new JButton();
  
  private JLabel jLabel1 = new JLabel();
  
  private JTextField textFieldExpressao = new JTextField();
  
  private Algoritmos algoritmos;
  
  private String expressaoRegular = null;
  
  private String expressaoPosFixa = null;
  
  private JLabel jLabel2 = new JLabel();
  
  public TelaImportarExpressaoRegular(Algoritmos alg) {
    this((Frame)null, "", false);
    this.algoritmos = alg;
  }
  
  public TelaImportarExpressaoRegular(Frame parent, String title, boolean modal) {
    super(parent, title, modal);
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void jbInit() throws Exception {
    setSize(new Dimension(437, 173));
    getContentPane().setLayout((LayoutManager)null);
    setModal(true);
    setTitle("Importar expressregular");
    this.botaoImportar.setText("Importar");
    this.botaoImportar.setBounds(new Rectangle(168, 90, 95, 35));
    this.botaoImportar.setMnemonic('I');
    this.botaoImportar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            TelaImportarExpressaoRegular.mav$botaoImportar_actionPerformed(TelaImportarExpressaoRegular.this, e);
          }
        });
    this.jLabel1.setText("Express");
    this.jLabel1.setBounds(new Rectangle(10, 10, 80, 25));
    this.textFieldExpressao.setBounds(new Rectangle(10, 30, 410, 20));
    this.textFieldExpressao.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            TelaImportarExpressaoRegular.mav$textFieldExpressao_actionPerformed(TelaImportarExpressaoRegular.this, e);
          }
        });
    this.jLabel2.setText("Ex: (a | b)*w");
    this.jLabel2.setBounds(new Rectangle(10, 45, 185, 25));
    this.jLabel2.setFont(new Font("Tahoma", 0, 10));
    getContentPane().add(this.jLabel2, (Object)null);
    getContentPane().add(this.textFieldExpressao, (Object)null);
    getContentPane().add(this.jLabel1, (Object)null);
    getContentPane().add(this.botaoImportar, (Object)null);
  }
  
  private void botaoImportar_actionPerformed(ActionEvent e) {
    this.expressaoRegular = null;
    String retValida = this.algoritmos.validaExpressaoRegular(this.textFieldExpressao.getText());
    if (retValida == null) {
      this.expressaoRegular = this.textFieldExpressao.getText();
      String expressaoNova = procuraConcatenacao(this.expressaoRegular);
      String expressaoSaida = "";
      Stack<String> pilhaOperadores = new Stack();
      for (int i = 0; i < expressaoNova.length(); i++) {
        String elemAtual = "" + expressaoNova.charAt(i);
        if (isOperador(elemAtual)) {
          if (elemAtual.equals("(")) {
            pilhaOperadores.push(elemAtual);
          } else if (elemAtual.equals(")")) {
            String topoPilha = pilhaOperadores.peek();
            while (!topoPilha.equals("(")) {
              pilhaOperadores.pop();
              expressaoSaida = expressaoSaida + topoPilha;
              topoPilha = pilhaOperadores.peek();
            } 
            String tst = pilhaOperadores.pop();
          } else if (pilhaOperadores.size() > 0) {
            String opTopo = pilhaOperadores.peek();
            while (pilhaOperadores.size() > 0 && retornaPrioridadeOperador(opTopo) >= retornaPrioridadeOperador(elemAtual)) {
              pilhaOperadores.pop();
              expressaoSaida = expressaoSaida + opTopo;
              if (pilhaOperadores.size() > 0)
                opTopo = pilhaOperadores.peek(); 
            } 
            pilhaOperadores.push(elemAtual);
          } else {
            pilhaOperadores.push(elemAtual);
          } 
        } else {
          expressaoSaida = expressaoSaida + elemAtual;
        } 
      } 
      while (pilhaOperadores.size() > 0)
        expressaoSaida = expressaoSaida + pilhaOperadores.pop(); 
      this.expressaoPosFixa = expressaoSaida;
      setVisible(false);
    } else {
      JOptionPane.showMessageDialog(null, "Nfoi possimportar a expressregular!\nDescrido erro: " + retValida, "Erro", 0);
    } 
  }
  
  private boolean isOperador(String caracter) {
    return !(!caracter.equals("|") && !caracter.equals("*") && !caracter.equals(Constantes.OPERADOR_CONCATENACAO) && !caracter.equals("(") && !caracter.equals(")"));
  }
  
  private int retornaPrioridadeOperador(String elemento) {
    if (elemento.equals("(") || elemento.equals(")"))
      return 1; 
    if (elemento.equals("|"))
      return 2; 
    if (elemento.equals(Constantes.OPERADOR_CONCATENACAO))
      return 3; 
    if (elemento.equals("*"))
      return 4; 
    return -1;
  }
  
  private String procuraConcatenacao(String expReg) {
    String expSaida = "" + expReg.charAt(0);
    char caracAnt = expReg.charAt(0);
    for (int i = 1; i < expReg.length(); i++) {
      char caracAtual = expReg.charAt(i);
      if (caracAnt != '|' && caracAnt != '(')
        if (caracAtual != '|' && caracAtual != ')' && caracAtual != '*')
          expSaida = expSaida + Constantes.OPERADOR_CONCATENACAO;  
      expSaida = expSaida + caracAtual;
      caracAnt = caracAtual;
    } 
    return expSaida;
  }
  
  public String getExpressaoRegular() {
    return this.expressaoRegular;
  }
  
  public String getExpressaoPosFixa() {
    return this.expressaoPosFixa;
  }
  
  public void atualizaDados() {
    this.expressaoPosFixa = null;
  }
  
  private void textFieldExpressao_actionPerformed(ActionEvent e) {
    botaoImportar_actionPerformed(e);
  }
}
