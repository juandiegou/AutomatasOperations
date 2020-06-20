package auger.tcc.gui;

import auger.tcc.Algoritmos;
import auger.tcc.Automato;
import auger.tcc.MatrizTransicao;
import auger.tcc.Transicao;
import edu.umd.cs.piccolo.nodes.PText;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class VisualizaMatrizTransicao extends JDialog {
  static void mav$botaoOk_actionPerformed(VisualizaMatrizTransicao paramVisualizaMatrizTransicao, ActionEvent paramActionEvent) {
    paramVisualizaMatrizTransicao.botaoOk_actionPerformed(paramActionEvent);
  }
  
  private JButton botaoOk = new JButton();
  
  private Automato automatoAtual;
  
  private JScrollPane scrollPaneTransicoes = new JScrollPane();
  
  private JScrollPane scrollPaneTransicoesCompacta = new JScrollPane(20, 30);
  
  private Algoritmos algoritmosPrograma;
  
  private JScrollPane scrollPaneReconhecedores = new JScrollPane();
  
  private JLabel jLabel1 = new JLabel();
  
  private JLabel jLabel2 = new JLabel();
  
  private JLabel jLabel3 = new JLabel();
  
  private JTextField textFieldEstadoInicial = new JTextField();
  
  private JTable tabelaTransicoesDetalhada;
  
  private JTable tabelaTransicoesCompacta;
  
  private JList listReconhecedores = new JList();
  
  private FlowLayout flowLayout1 = new FlowLayout();
  
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  
  private JPanel jPanelCompacta = new JPanel();
  
  private JPanel jPanelDetalhada = new JPanel();
  
  public VisualizaMatrizTransicao(Algoritmos alg) {
    this((Frame)null, "Visualizar Matriz de Transi", true);
    this.algoritmosPrograma = alg;
  }
  
  public VisualizaMatrizTransicao(Frame parent, String title, boolean modal) {
    super(parent, title, modal);
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void jbInit() throws Exception {
    setSize(new Dimension(515, 376));
    getContentPane().setLayout((LayoutManager)null);
    setTitle("Visualizar Matriz de Transi");
    setModal(true);
    setResizable(false);
    this.botaoOk.setText("Ok");
    this.botaoOk.setBounds(new Rectangle(208, 300, 95, 35));
    this.botaoOk.setMnemonic('O');
    this.botaoOk.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            VisualizaMatrizTransicao.mav$botaoOk_actionPerformed(VisualizaMatrizTransicao.this, e);
          }
        });
    this.scrollPaneTransicoes.setBounds(new Rectangle(5, 5, 290, 215));
    this.scrollPaneTransicoesCompacta.setBounds(new Rectangle(5, 5, 290, 215));
    this.scrollPaneTransicoesCompacta.setHorizontalScrollBarPolicy(32);
    this.scrollPaneReconhecedores.setBounds(new Rectangle(330, 30, 120, 160));
    this.scrollPaneReconhecedores.getViewport().setLayout(this.flowLayout1);
    this.jLabel1.setText("Transi");
    this.jLabel1.setBounds(new Rectangle(10, 10, 125, 15));
    this.jLabel2.setText("Estados reconhecedores");
    this.jLabel2.setBounds(new Rectangle(330, 10, 150, 15));
    this.jLabel3.setText("Estado inicial");
    this.jLabel3.setBounds(new Rectangle(335, 215, 100, 15));
    this.textFieldEstadoInicial.setBounds(new Rectangle(335, 235, 65, 20));
    this.textFieldEstadoInicial.setHorizontalAlignment(0);
    this.listReconhecedores.setBounds(new Rectangle(270, 35, 110, 150));
    this.jTabbedPane1.setBounds(new Rectangle(10, 25, 305, 256));
    this.jTabbedPane1.setTabPlacement(3);
    this.jPanelCompacta.setLayout((LayoutManager)null);
    this.jPanelCompacta.setBounds(new Rectangle(2, 2, 300, 228));
    this.jPanelCompacta.add(this.scrollPaneTransicoesCompacta, (Object)null);
    this.jPanelDetalhada.setLayout((LayoutManager)null);
    this.jTabbedPane1.addTab("Compacta", this.jPanelCompacta);
    this.jPanelDetalhada.add(this.scrollPaneTransicoes, (Object)null);
    this.jTabbedPane1.addTab("Detalhada", this.jPanelDetalhada);
    getContentPane().add(this.jTabbedPane1, (Object)null);
    getContentPane().add(this.textFieldEstadoInicial, (Object)null);
    getContentPane().add(this.jLabel3, (Object)null);
    getContentPane().add(this.jLabel2, (Object)null);
    getContentPane().add(this.jLabel1, (Object)null);
    getContentPane().add(this.scrollPaneReconhecedores, (Object)null);
    getContentPane().add(this.botaoOk, (Object)null);
  }
  
  private void botaoOk_actionPerformed(ActionEvent e) {
    setVisible(false);
  }
  
  public void setaAutomato(Automato pai) {
    this.automatoAtual = pai;
  }
  
  public void atualizaDados() {
    MatrizTransicao matriz = this.automatoAtual.getMatrizTransicoes();
    ArrayList<Transicao> transicoes = matriz.getTransicoes();
    HashSet reconhecedores = this.automatoAtual.getEstadosReconhecedores();
    Vector<Vector<String>> linhasTabela = new Vector();
    Vector<String> colunasTabela = new Vector();
    colunasTabela.add("Origem");
    colunasTabela.add("Entrada");
    colunasTabela.add("Destino");
    for (int i = 0; i < transicoes.size(); i++) {
      Transicao t = transicoes.get(i);
      Vector<String> linhaAtual = new Vector();
      linhaAtual.addElement(t.getEstadoEntrada());
      linhaAtual.addElement(t.getValorEntrada());
      linhaAtual.addElement(t.getEstadoSaida());
      linhasTabela.add(linhaAtual);
    } 
    this.tabelaTransicoesDetalhada = new JTable((Vector)linhasTabela, colunasTabela);
    this.tabelaTransicoesDetalhada.setEnabled(false);
    this.tabelaTransicoesDetalhada.getTableHeader().setForeground(Color.BLUE);
    this.scrollPaneTransicoes.getViewport().add(this.tabelaTransicoesDetalhada, (Object)null);
    Vector<Vector<String>> linhasTabelaCompacta = new Vector();
    Vector<String> colunasTabelaCompacta = new Vector();
    colunasTabelaCompacta.add("Estados \\ Entradas");
    LinkedHashSet<String> listaEntradas = new LinkedHashSet();
    for (int j = 0; j < transicoes.size(); j++) {
      Transicao transicao = transicoes.get(j);
      if (listaEntradas.add(transicao.getValorEntrada()))
        colunasTabelaCompacta.add(transicao.getValorEntrada()); 
    } 
    ArrayList<String> estados = this.automatoAtual.getEstados();
    int[] vetorLarguraColunas = new int[colunasTabelaCompacta.size()];
    for (int k = 0; k < estados.size(); k++) {
      String estAtual = estados.get(k);
      String estadosSaida = "";
      Vector<String> linhaAtualCompacta = new Vector();
      linhaAtualCompacta.add(estAtual);
      Iterator<String> nav = listaEntradas.iterator();
      int ind = 1;
      while (nav.hasNext()) {
        String entradaAtual = nav.next();
        ArrayList<Transicao> transEnt = Algoritmos.retornaTransicoesEstado(transicoes, estAtual, entradaAtual);
        estadosSaida = "";
        for (int n = 0; n < transEnt.size(); n++) {
          Transicao tn = transEnt.get(n);
          if (estadosSaida.length() == 0) {
            estadosSaida = tn.getEstadoSaida();
          } else {
            estadosSaida = estadosSaida + ", " + tn.getEstadoSaida();
          } 
        } 
        PText txt = new PText(estadosSaida);
        if (txt.getWidth() > vetorLarguraColunas[ind])
          vetorLarguraColunas[ind] = (int)txt.getWidth() + 10; 
        linhaAtualCompacta.add(estadosSaida);
        ind++;
      } 
      linhasTabelaCompacta.add(linhaAtualCompacta);
    } 
    this.tabelaTransicoesCompacta = new JTable((Vector)linhasTabelaCompacta, colunasTabelaCompacta);
    this.tabelaTransicoesCompacta.setAutoResizeMode(0);
    this.tabelaTransicoesCompacta.getColumn("Estados \\ Entradas").setMinWidth(120);
    this.tabelaTransicoesCompacta.getColumn("Estados \\ Entradas").setPreferredWidth(120);
    int larguraTotalTabela = 120;
    for (int ct = 1; ct < this.tabelaTransicoesCompacta.getColumnCount(); ct++) {
      String nomeColuna = this.tabelaTransicoesCompacta.getColumnName(ct);
      int larg = vetorLarguraColunas[ct];
      TableColumn coluna = this.tabelaTransicoesCompacta.getColumn(nomeColuna);
      coluna.setMinWidth(larg);
      coluna.setPreferredWidth(larg);
      larguraTotalTabela += larg;
    } 
    JTableHeader thead = this.tabelaTransicoesCompacta.getTableHeader();
    thead.setForeground(Color.BLUE);
    this.tabelaTransicoesCompacta.setEnabled(false);
    int alturaTabela = linhasTabelaCompacta.size() * 20;
    this.tabelaTransicoesCompacta.setSize(larguraTotalTabela, alturaTabela);
    this.scrollPaneTransicoesCompacta.getViewport().setLayout((LayoutManager)null);
    this.scrollPaneTransicoesCompacta.getViewport().add(this.tabelaTransicoesCompacta, (Object)null);
    Vector vetorReconhecedores = new Vector();
    Iterator navegaRec = reconhecedores.iterator();
    while (navegaRec.hasNext())
      vetorReconhecedores.addElement(navegaRec.next()); 
    this.listReconhecedores.setListData(vetorReconhecedores);
    this.scrollPaneReconhecedores.getViewport().add(this.listReconhecedores, 0);
    this.textFieldEstadoInicial.setText(this.automatoAtual.getEstadoInicial());
    System.gc();
  }
}
