package auger.tcc.gui;

import auger.tcc.*;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolox.swing.PScrollDirector;
import edu.umd.cs.piccolox.swing.PViewport;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

public class Editor extends JFrame implements VersionadorListener {
  static void mav$fechaEditor(Editor paramEditor) {
    paramEditor.fechaEditor();
  }
  
  static void mav$jMenuItem9_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.jMenuItem9_actionPerformed(paramActionEvent);
  }
  
  static void mav$this_componentResized(Editor paramEditor, ComponentEvent paramComponentEvent) {
    paramEditor.this_componentResized(paramComponentEvent);
  }
  
  static void mav$menuNovo_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuNovo_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuAbrir_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuAbrir_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuSair_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuSair_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuImportarExpReg_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuImportarExpReg_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuImportarGramReg_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuImportarGramReg_actionPerformed(paramActionEvent);
  }
  
  static void mav$botaoSeta_mouseClicked(Editor paramEditor, MouseEvent paramMouseEvent) {
    paramEditor.botaoSeta_mouseClicked(paramMouseEvent);
  }
  
  static void mav$botaoSeta_mousePressed(Editor paramEditor, MouseEvent paramMouseEvent) {
    paramEditor.botaoSeta_mousePressed(paramMouseEvent);
  }
  
  static void mav$botaoSeta_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.botaoSeta_actionPerformed(paramActionEvent);
  }
  
  static void mav$botaoEstado_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.botaoEstado_actionPerformed(paramActionEvent);
  }
  
  static void mav$botaoDispersar_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.botaoDispersar_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuItemFerramentaSelecao_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuItemFerramentaSelecao_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuItemFerramentaCriacaoEstados_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuItemFerramentaCriacaoEstados_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuItemFerramentaCriacaoTransicoes_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuItemFerramentaCriacaoTransicoes_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuItemFerramentaExclusao_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuItemFerramentaExclusao_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuItemDesfazer_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuItemDesfazer_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuItemAlterarCorFundo_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuItemAlterarCorFundo_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuItemZoom150_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuItemZoom150_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuItemZoom125_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuItemZoom125_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuItemZoom175_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuItemZoom175_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuMatrizTransicoes_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuMatrizTransicoes_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuZoom25_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuZoom25_actionPerformed(paramActionEvent);
  }
  
  static void mav$jMenuItem5_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.jMenuItem5_actionPerformed(paramActionEvent);
  }
  
  static void mav$jMenuItem6_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.jMenuItem6_actionPerformed(paramActionEvent);
  }
  
  static void mav$jMenuItem7_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.jMenuItem7_actionPerformed(paramActionEvent);
  }
  
  static void mav$jMenuItem8_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.jMenuItem8_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuAFNDToAFD_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuAFNDToAFD_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuEliminarETransicoes_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuEliminarETransicoes_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuEliminarEstadosMortos_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuEliminarEstadosMortos_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuEliminarEstadosInalcancaveis_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuEliminarEstadosInalcancaveis_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuMinimizar_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuMinimizar_actionPerformed(paramActionEvent);
  }
  
  static void mav$jMenuItem16_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.jMenuItem16_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuReferencialTeorico_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuReferencialTeorico_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuSobreAuger_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuSobreAuger_actionPerformed(paramActionEvent);
  }
  
  static void mav$botaoTransicao_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.botaoTransicao_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuSalvar_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuSalvar_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuGerarProgramaReconhecedor_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuGerarProgramaReconhecedor_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuExportarExpReg_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuExportarExpReg_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuExportarGramReg_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuExportarGramReg_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuExportarDiagramaEstados_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuExportarDiagramaEstados_actionPerformed(paramActionEvent);
  }
  
  static void mav$botaoProximoCaracter_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.botaoProximoCaracter_actionPerformed(paramActionEvent);
  }
  
  static void mav$botaoTodaEntrada_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.botaoTodaEntrada_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuReconhecerSentenca_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuReconhecerSentenca_actionPerformed(paramActionEvent);
  }
  
  static void mav$botaoFinalizar_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.botaoFinalizar_actionPerformed(paramActionEvent);
  }
  
  static void mav$menuReferencialTeoricoContexto_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.menuReferencialTeoricoContexto_actionPerformed(paramActionEvent);
  }
  
  static void mav$botaoExcluir_actionPerformed(Editor paramEditor, ActionEvent paramActionEvent) {
    paramEditor.botaoExcluir_actionPerformed(paramActionEvent);
  }
  
  private Automato automatoAtual = new Automato(1, this, true);
  
  private PCanvas canvasAutomato = this.automatoAtual.getGrafoAutomato().getCanvas();
  
  private VisualizaMatrizTransicao telaMatrizTransicao;
  
  private TelaImportarExpressaoRegular telaImportarExpressaoRegular;
  
  private Algoritmos algoritmosPrograma = new Algoritmos(this.automatoAtual);
  
  private List<String> estadosAtuaisReconhecimento = new ArrayList<String>();
  
  private int situacaoAntesTeste = 0;
  
  private PScrollPane scrollPaneCanvas = null;
  
  private final PScrollDirectorAuger janelaSD = new PScrollDirectorAuger();
  
  private JMenuBar menuPrincipal = new JMenuBar();
  
  private JMenu menuArquivo = new JMenu();
  
  private JMenuItem menuNovo = new JMenuItem();
  
  private JMenuItem menuAbrir = new JMenuItem();
  
  private JMenuItem menuSair = new JMenuItem();
  
  private JMenu jMenu1 = new JMenu();
  
  private JMenuItem menuImportarExpReg = new JMenuItem();
  
  private JMenuItem menuImportarGramReg = new JMenuItem();
  
  private JPanel barraFerramentas = new JPanel();
  
  private JToggleButton botaoSeta = new JToggleButton();
  
  private JToggleButton botaoEstado = new JToggleButton();
  
  private Icon imgSeta;
  
  private Icon imgEstado;
  
  private Icon imgTransicao;
  
  private Icon imgNovo;
  
  private Icon imgAbrir;
  
  private Icon imgSalvar;
  
  private Icon imgDesfazer;
  
  private Icon imgCorFundo;
  
  private Icon imgMatriz;
  
  private Icon imgZoom;
  
  private Icon imgApagar;
  
  private Icon imgDispersar;
  
  private JMenu menuVisualizar = new JMenu();
  
  private JMenuItem menuMatrizTransicoes = new JMenuItem();
  
  private JMenu menuZoom = new JMenu();
  
  private JRadioButtonMenuItem menuItemZoom25 = new JRadioButtonMenuItem();
  
  private JRadioButtonMenuItem menuItemZoom50 = new JRadioButtonMenuItem();
  
  private JRadioButtonMenuItem menuItemZoom75 = new JRadioButtonMenuItem();
  
  private JRadioButtonMenuItem menuItemZoom100 = new JRadioButtonMenuItem();
  
  private JRadioButtonMenuItem menuItemZoom125 = new JRadioButtonMenuItem();
  
  private JRadioButtonMenuItem menuItemZoom150 = new JRadioButtonMenuItem();
  
  private JRadioButtonMenuItem menuItemZoom175 = new JRadioButtonMenuItem();
  
  private JRadioButtonMenuItem menuItemZoom200 = new JRadioButtonMenuItem();
  
  private JRadioButtonMenuItem menuItemZoom400 = new JRadioButtonMenuItem();
  
  private JMenu menuAlgoritmos = new JMenu();
  
  private JMenuItem menuAFNDToAFD = new JMenuItem();
  
  private JMenuItem menuEliminarETransicoes = new JMenuItem();
  
  private JMenuItem menuEliminarEstadosMortos = new JMenuItem();
  
  private JMenuItem menuEliminarEstadosInalcancaveis = new JMenuItem();
  
  private JMenuItem menuMinimizar = new JMenuItem();
  
  private JMenu menuAjuda = new JMenu();
  
  private JMenuItem jMenuItem16 = new JMenuItem();
  
  private JMenuItem menuReferencialTeorico = new JMenuItem();
  
  private JMenuItem jMenuItem18 = new JMenuItem();
  
  private JMenuItem menuSobreAuger = new JMenuItem();
  
  private JToggleButton botaoTransicao = new JToggleButton();
  
  private JMenuItem menuSalvar = new JMenuItem();
  
  private JMenuItem menuGerarProgramaReconhecedor = new JMenuItem();
  
  private JMenu jMenu2 = new JMenu();
  
  private JMenuItem menuExportarExpReg = new JMenuItem();
  
  private JMenuItem menuExportarGramReg = new JMenuItem();
  
  private JMenuItem menuExportarDiagramaEstados = new JMenuItem();
  
  private JTextField textFieldEntrada = new JTextField();
  
  private JPanel painelReconhecerSentenca = new JPanel();
  
  private JButton botaoProximoCaracter = new JButton();
  
  private JTextField textFieldEntradaReconhecida = new JTextField();
  
  private JButton botaoTodaEntrada = new JButton();
  
  private JMenuItem menuReconhecerSentenca = new JMenuItem();
  
  private JButton botaoFinalizar = new JButton();
  
  private JToggleButton botaoExcluir = new JToggleButton();
  
  private JMenuItem menuReferencialTeoricoContexto = new JMenuItem();
  
  private int contextoAtual;
  
  private String nomeArquivo;
  
  private TelaTeoria telaTeoria = null;
  
  private TelaAjuda telaAjuda = null;
  
  private TelaSobre telaSobre = null;
  
  private JButton botaoDispersar = new JButton();
  
  private JPanel jPanel1 = new JPanel();
  
  private JMenu menuFerramentas = new JMenu();
  
  private JMenuItem menuItemFerramentaSelecao = new JMenuItem();
  
  private JMenuItem menuItemFerramentaCriacaoEstados = new JMenuItem();
  
  private JMenuItem menuItemFerramentaCriacaoTransicoes = new JMenuItem();
  
  private JMenuItem menuItemFerramentaExclusao = new JMenuItem();
  
  private Stack<AutomatoArquivo> pilhaAutomato = new Stack<AutomatoArquivo>();
  
  private JMenuItem menuItemDesfazer = new JMenuItem();
  
  private JMenu jMenu3 = new JMenu();
  
  private JMenuItem menuItemAlterarCorFundo = new JMenuItem();
  
  private Collection<PNode> objetosIniciais = null;
  
  public Editor() {
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void jbInit() throws Exception {
    addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            Editor.mav$fechaEditor(Editor.this);
          }
        });
    this.menuItemZoom400.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$jMenuItem9_actionPerformed(Editor.this, e);
          }
        });
    getContentPane().setLayout((LayoutManager)null);
    setSize(new Dimension(543, 435));
    setTitle("Auger");
    setJMenuBar(this.menuPrincipal);
    setIconImage((new ImageIcon(getClass().getResource("/images/logo_ico.png"))).getImage());
    this.imgSeta = new ImageIcon(Auger.getImagem("images/seta.gif"));
    this.imgEstado = new ImageIcon(Auger.getImagem("images/estado.gif"));
    this.imgTransicao = new ImageIcon(Auger.getImagem("images/transicao.gif"));
    this.imgNovo = new ImageIcon(Auger.getImagem("images/novo.gif"));
    this.imgAbrir = new ImageIcon(Auger.getImagem("images/abrir.gif"));
    this.imgSalvar = new ImageIcon(Auger.getImagem("images/salvar.gif"));
    this.imgApagar = new ImageIcon(Auger.getImagem("images/apagar.gif"));
    this.imgDispersar = new ImageIcon(Auger.getImagem("images/dispersar.gif"));
    this.imgDesfazer = new ImageIcon(Auger.getImagem("images/desfazer.gif"));
    this.imgCorFundo = new ImageIcon(Auger.getImagem("images/alterar_cor.gif"));
    this.imgMatriz = new ImageIcon(Auger.getImagem("images/matriz.gif"));
    this.imgZoom = new ImageIcon(Auger.getImagem("images/zoom.gif"));
    addComponentListener(new ComponentAdapter() {
          public void componentResized(ComponentEvent e) {
            Editor.mav$this_componentResized(Editor.this, e);
          }
        });
    this.canvasAutomato.setAutoscrolls(true);
    this.canvasAutomato.setLayout(null);
    this.canvasAutomato.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    this.canvasAutomato.setSize(new Dimension(517, 297));
    this.canvasAutomato.setDefaultRenderQuality(1);
    this.menuArquivo.setText("Arquivo");
    this.menuArquivo.setMnemonic('A');
    this.menuNovo.setText("Novo");
    this.menuNovo.setMnemonic('N');
    this.menuNovo.setIcon(this.imgNovo);
    this.menuNovo.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuNovo_actionPerformed(Editor.this, e);
          }
        });
    this.menuAbrir.setText("Abrir");
    this.menuAbrir.setMnemonic('A');
    this.menuAbrir.setIcon(this.imgAbrir);
    this.menuAbrir.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuAbrir_actionPerformed(Editor.this, e);
          }
        });
    this.menuSair.setText("Sair");
    this.menuSair.setMnemonic('S');
    this.menuSair.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuSair_actionPerformed(Editor.this, e);
          }
        });
    this.jMenu1.setText("Importar");
    this.jMenu1.setMnemonic('I');
    this.menuImportarExpReg.setText("Expressregular");
    this.menuImportarExpReg.setMnemonic('E');
    this.menuImportarExpReg.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuImportarExpReg_actionPerformed(Editor.this, e);
          }
        });
    this.menuImportarGramReg.setText("Gramregular");
    this.menuImportarGramReg.setMnemonic('G');
    this.menuImportarGramReg.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuImportarGramReg_actionPerformed(Editor.this, e);
          }
        });
    this.barraFerramentas.setBorder(BorderFactory.createBevelBorder(0));
    this.barraFerramentas.setLayout((LayoutManager)null);
    this.barraFerramentas.setBounds(new Rectangle(5, 0, 265, 45));
    this.botaoSeta.setBounds(new Rectangle(5, 5, 35, 35));
    this.botaoSeta.setIcon(this.imgSeta);
    this.botaoSeta.setSelected(true);
    this.automatoAtual.setSituacao(0);
    this.botaoSeta.addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            Editor.mav$botaoSeta_mousePressed(Editor.this, e);
          }
          
          public void mouseClicked(MouseEvent e) {
            Editor.mav$botaoSeta_mouseClicked(Editor.this, e);
          }
        });
    this.botaoSeta.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$botaoSeta_actionPerformed(Editor.this, e);
          }
        });
    this.botaoEstado.setBounds(new Rectangle(45, 5, 35, 35));
    this.botaoEstado.setIcon(this.imgEstado);
    this.botaoEstado.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$botaoEstado_actionPerformed(Editor.this, e);
          }
        });
    this.botaoEstado.addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            Editor.mav$botaoSeta_mousePressed(Editor.this, e);
          }
          
          public void mouseClicked(MouseEvent e) {
            Editor.mav$botaoSeta_mouseClicked(Editor.this, e);
          }
        });
    this.botaoDispersar.setBounds(new Rectangle(225, 5, 35, 35));
    this.botaoDispersar.setIcon(this.imgDispersar);
    this.botaoDispersar.setToolTipText("Posicionar estados aleatoriamente");
    this.botaoDispersar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$botaoDispersar_actionPerformed(Editor.this, e);
          }
        });
    this.jPanel1.setBounds(new Rectangle(190, 2, 5, 40));
    this.jPanel1.setBorder(BorderFactory.createBevelBorder(1));
    this.menuFerramentas.setText("Ferramentas");
    this.menuFerramentas.setMnemonic('F');
    this.menuItemFerramentaSelecao.setText("Selede objetos");
    this.menuItemFerramentaSelecao.setMnemonic('S');
    this.menuItemFerramentaSelecao.setAccelerator(KeyStroke.getKeyStroke(114, 0, false));
    this.menuItemFerramentaSelecao.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuItemFerramentaSelecao_actionPerformed(Editor.this, e);
          }
        });
    this.menuItemFerramentaCriacaoEstados.setText("Criade estados");
    this.menuItemFerramentaCriacaoEstados.setMnemonic('C');
    this.menuItemFerramentaCriacaoEstados.setAccelerator(KeyStroke.getKeyStroke(115, 0, false));
    this.menuItemFerramentaCriacaoEstados.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuItemFerramentaCriacaoEstados_actionPerformed(Editor.this, e);
          }
        });
    this.menuItemFerramentaCriacaoTransicoes.setText("Criade transi");
    this.menuItemFerramentaCriacaoTransicoes.setMnemonic('t');
    this.menuItemFerramentaCriacaoTransicoes.setAccelerator(KeyStroke.getKeyStroke(116, 0, false));
    this.menuItemFerramentaCriacaoTransicoes.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuItemFerramentaCriacaoTransicoes_actionPerformed(Editor.this, e);
          }
        });
    this.menuItemFerramentaExclusao.setText("Exclusde objetos");
    this.menuItemFerramentaExclusao.setMnemonic('e');
    this.menuItemFerramentaExclusao.setAccelerator(KeyStroke.getKeyStroke(117, 0, false));
    this.menuItemFerramentaExclusao.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuItemFerramentaExclusao_actionPerformed(Editor.this, e);
          }
        });
    this.menuItemDesfazer.setText("Desfazer");
    this.menuItemDesfazer.setMnemonic('D');
    this.menuItemDesfazer.setAccelerator(KeyStroke.getKeyStroke(90, 2, false));
    this.menuItemDesfazer.setIcon(this.imgDesfazer);
    this.menuItemDesfazer.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuItemDesfazer_actionPerformed(Editor.this, e);
          }
        });
    this.jMenu3.setText("Editar");
    this.jMenu3.setMnemonic('E');
    this.menuItemAlterarCorFundo.setText("Alterar cor de fundo");
    this.menuItemAlterarCorFundo.setMnemonic('A');
    this.menuItemAlterarCorFundo.setIcon(this.imgCorFundo);
    this.menuItemAlterarCorFundo.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuItemAlterarCorFundo_actionPerformed(Editor.this, e);
          }
        });
    this.menuItemZoom150.setText("150%");
    this.menuItemZoom150.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuItemZoom150_actionPerformed(Editor.this, e);
          }
        });
    this.menuItemZoom125.setText("125%");
    this.menuItemZoom125.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuItemZoom125_actionPerformed(Editor.this, e);
          }
        });
    this.menuItemZoom175.setText("175%");
    this.menuItemZoom175.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuItemZoom175_actionPerformed(Editor.this, e);
          }
        });
    this.menuVisualizar.setText("Visualizar");
    this.menuVisualizar.setMnemonic('V');
    this.menuMatrizTransicoes.setText("Matriz de transi");
    this.menuMatrizTransicoes.setMnemonic('M');
    this.menuMatrizTransicoes.setIcon(this.imgMatriz);
    this.menuMatrizTransicoes.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuMatrizTransicoes_actionPerformed(Editor.this, e);
          }
        });
    this.menuZoom.setText("Zoom");
    this.menuZoom.setMnemonic('Z');
    this.menuZoom.setIcon(this.imgZoom);
    this.menuItemZoom25.setText("25%");
    this.menuItemZoom25.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuZoom25_actionPerformed(Editor.this, e);
          }
        });
    this.menuItemZoom50.setText("50%");
    this.menuItemZoom50.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$jMenuItem5_actionPerformed(Editor.this, e);
          }
        });
    this.menuItemZoom75.setText("75%");
    this.menuItemZoom75.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$jMenuItem6_actionPerformed(Editor.this, e);
          }
        });
    this.menuItemZoom100.setText("100%");
    this.menuItemZoom100.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$jMenuItem7_actionPerformed(Editor.this, e);
          }
        });
    this.menuItemZoom200.setText("200%");
    this.menuItemZoom200.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$jMenuItem8_actionPerformed(Editor.this, e);
          }
        });
    this.menuItemZoom400.setText("400%");
    this.menuAlgoritmos.setText("Algoritmos");
    this.menuAlgoritmos.setMnemonic('g');
    this.menuAFNDToAFD.setText("AFND => AFD");
    this.menuAFNDToAFD.setMnemonic('A');
    this.menuAFNDToAFD.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuAFNDToAFD_actionPerformed(Editor.this, e);
          }
        });
    this.menuEliminarETransicoes.setText("Eliminar e-transi");
    this.menuEliminarETransicoes.setMnemonic('E');
    this.menuEliminarETransicoes.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuEliminarETransicoes_actionPerformed(Editor.this, e);
          }
        });
    this.menuEliminarEstadosMortos.setText("Eliminar estados mortos");
    this.menuEliminarEstadosMortos.setMnemonic('o');
    this.menuEliminarEstadosMortos.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuEliminarEstadosMortos_actionPerformed(Editor.this, e);
          }
        });
    this.menuEliminarEstadosInalcancaveis.setText("Eliminar estados inalcan");
    this.menuEliminarEstadosInalcancaveis.setMnemonic('i');
    this.menuEliminarEstadosInalcancaveis.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuEliminarEstadosInalcancaveis_actionPerformed(Editor.this, e);
          }
        });
    this.menuMinimizar.setText("Minimizar");
    this.menuMinimizar.setMnemonic('M');
    this.menuMinimizar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuMinimizar_actionPerformed(Editor.this, e);
          }
        });
    this.menuAjuda.setText("Ajuda");
    this.menuAjuda.setMnemonic('j');
    this.jMenuItem16.setText("Ajuda do Auger");
    this.jMenuItem16.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$jMenuItem16_actionPerformed(Editor.this, e);
          }
        });
    this.menuReferencialTeorico.setText("Referencial teindexado");
    this.menuReferencialTeorico.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuReferencialTeorico_actionPerformed(Editor.this, e);
          }
        });
    this.jMenuItem18.setText("----------------------");
    this.menuSobreAuger.setText("Sobre o auger...");
    this.menuSobreAuger.setMnemonic('S');
    this.menuSobreAuger.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuSobreAuger_actionPerformed(Editor.this, e);
          }
        });
    this.botaoTransicao.setBounds(new Rectangle(85, 5, 35, 35));
    this.botaoTransicao.setIcon(this.imgTransicao);
    this.botaoTransicao.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$botaoTransicao_actionPerformed(Editor.this, e);
          }
        });
    this.botaoTransicao.addMouseListener((MouseListener) new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            Editor.mav$botaoSeta_mousePressed(Editor.this, e);
          }
          
          public void mouseClicked(MouseEvent e) {
            Editor.mav$botaoSeta_mouseClicked(Editor.this, e);
          }
        });
    this.menuSalvar.setText("Salvar");
    this.menuSalvar.setMnemonic('S');
    this.menuSalvar.setIcon(this.imgSalvar);
    this.menuSalvar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuSalvar_actionPerformed(Editor.this, e);
          }
        });
    this.menuGerarProgramaReconhecedor.setText("Gerar programa reconhecedor");
    this.menuGerarProgramaReconhecedor.setMnemonic('G');
    this.menuGerarProgramaReconhecedor.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuGerarProgramaReconhecedor_actionPerformed(Editor.this, e);
          }
        });
    this.jMenu2.setText("Exportar");
    this.jMenu2.setMnemonic('E');
    this.menuExportarExpReg.setText("Expressregular");
    this.menuExportarExpReg.setMnemonic('E');
    this.menuExportarExpReg.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuExportarExpReg_actionPerformed(Editor.this, e);
          }
        });
    this.menuExportarGramReg.setText("Gramregular");
    this.menuExportarGramReg.setMnemonic('G');
    this.menuExportarGramReg.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuExportarGramReg_actionPerformed(Editor.this, e);
          }
        });
    this.menuExportarDiagramaEstados.setText("Diagrama de estados");
    this.menuExportarDiagramaEstados.setMnemonic('D');
    this.menuExportarDiagramaEstados.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuExportarDiagramaEstados_actionPerformed(Editor.this, e);
          }
        });
    this.textFieldEntrada.setBounds(new Rectangle(5, 4, 115, 18));
    this.textFieldEntrada.setEditable(false);
    this.textFieldEntrada.setSize(new Dimension(115, 17));
    this.textFieldEntrada.setFont(new Font("Tahoma", 0, 10));
    this.painelReconhecerSentenca.setBorder(BorderFactory.createBevelBorder(0));
    this.painelReconhecerSentenca.setLayout((LayoutManager)null);
    this.painelReconhecerSentenca.setBounds(6, 0, 204, 45);
    this.painelReconhecerSentenca.setVisible(false);
    this.botaoProximoCaracter.setText(">");
    this.botaoProximoCaracter.setBounds(new Rectangle(140, 4, 30, 16));
    this.botaoProximoCaracter.setToolTipText("Prcaracter");
    this.botaoProximoCaracter.setBorder(BorderFactory.createLineBorder(new Color(133, 122, 153), 1));
    this.botaoProximoCaracter.setSize(new Dimension(30, 16));
    this.botaoProximoCaracter.setMargin(new Insets(0, 0, 0, 0));
    this.botaoProximoCaracter.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$botaoProximoCaracter_actionPerformed(Editor.this, e);
          }
        });
    this.textFieldEntradaReconhecida.setBounds(new Rectangle(5, 24, 115, 16));
    this.textFieldEntradaReconhecida.setEditable(false);
    this.textFieldEntradaReconhecida.setSize(new Dimension(115, 17));
    this.textFieldEntradaReconhecida.setFont(new Font("Tahoma", 0, 10));
    this.botaoTodaEntrada.setText(">|");
    this.botaoTodaEntrada.setBounds(new Rectangle(140, 24, 30, 20));
    this.botaoTodaEntrada.setToolTipText("Percorrer toda a entrada");
    this.botaoTodaEntrada.setBorder(BorderFactory.createLineBorder(new Color(133, 122, 153), 1));
    this.botaoTodaEntrada.setSize(new Dimension(30, 16));
    this.botaoTodaEntrada.setMargin(new Insets(0, 0, 0, 0));
    this.botaoTodaEntrada.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$botaoTodaEntrada_actionPerformed(Editor.this, e);
          }
        });
    this.menuReconhecerSentenca.setText("Testar aut");
    this.menuReconhecerSentenca.setMnemonic('T');
    this.menuReconhecerSentenca.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuReconhecerSentenca_actionPerformed(Editor.this, e);
          }
        });
    this.botaoFinalizar.setText("X");
    this.botaoFinalizar.setBounds(new Rectangle(175, 4, 20, 37));
    this.botaoFinalizar.setBorder(BorderFactory.createLineBorder(new Color(133, 122, 153), 1));
    this.botaoFinalizar.setToolTipText("Finalizar teste");
    this.botaoFinalizar.setForeground(Color.red);
    this.botaoFinalizar.setSize(new Dimension(20, 37));
    this.botaoFinalizar.setMargin(new Insets(0, 0, 0, 0));
    this.botaoFinalizar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$botaoFinalizar_actionPerformed(Editor.this, e);
          }
        });
    this.menuReferencialTeoricoContexto.setText("Referencial tecontextual");
    this.menuReferencialTeoricoContexto.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$menuReferencialTeoricoContexto_actionPerformed(Editor.this, e);
          }
        });
    this.menuArquivo.add(this.menuNovo);
    this.menuArquivo.add(this.menuAbrir);
    this.jMenu1.add(this.menuImportarExpReg);
    this.jMenu1.add(this.menuImportarGramReg);
    this.menuArquivo.add(this.jMenu1);
    this.menuArquivo.add(this.jMenu2);
    this.menuArquivo.add(this.menuSalvar);
    this.menuArquivo.addSeparator();
    this.menuArquivo.add(this.menuSair);
    this.jMenu2.add(this.menuExportarExpReg);
    this.jMenu2.add(this.menuExportarGramReg);
    this.jMenu2.add(this.menuExportarDiagramaEstados);
    this.menuPrincipal.add(this.menuArquivo);
    this.jMenu3.add(this.menuItemDesfazer);
    this.jMenu3.add(this.menuItemAlterarCorFundo);
    this.menuPrincipal.add(this.jMenu3);
    this.menuFerramentas.add(this.menuItemFerramentaSelecao);
    this.menuFerramentas.add(this.menuItemFerramentaCriacaoEstados);
    this.menuFerramentas.add(this.menuItemFerramentaCriacaoTransicoes);
    this.menuFerramentas.add(this.menuItemFerramentaExclusao);
    this.menuPrincipal.add(this.menuFerramentas);
    this.menuZoom.add(this.menuItemZoom25);
    this.menuZoom.add(this.menuItemZoom50);
    this.menuZoom.add(this.menuItemZoom75);
    this.menuZoom.add(this.menuItemZoom100);
    this.menuZoom.add(this.menuItemZoom125);
    this.menuZoom.add(this.menuItemZoom150);
    this.menuZoom.add(this.menuItemZoom175);
    this.menuZoom.add(this.menuItemZoom200);
    this.menuZoom.add(this.menuItemZoom400);
    this.menuVisualizar.add(this.menuZoom);
    this.menuVisualizar.add(this.menuMatrizTransicoes);
    this.menuPrincipal.add(this.menuVisualizar);
    this.menuAlgoritmos.add(this.menuAFNDToAFD);
    this.menuAlgoritmos.add(this.menuEliminarETransicoes);
    this.menuAlgoritmos.add(this.menuEliminarEstadosMortos);
    this.menuAlgoritmos.add(this.menuEliminarEstadosInalcancaveis);
    this.menuAlgoritmos.add(this.menuMinimizar);
    this.menuAlgoritmos.addSeparator();
    this.menuAlgoritmos.add(this.menuGerarProgramaReconhecedor);
    this.menuAlgoritmos.add(this.menuReconhecerSentenca);
    this.menuPrincipal.add(this.menuAlgoritmos);
    this.menuAjuda.add(this.menuSobreAuger);
    this.menuPrincipal.add(this.menuAjuda);
    this.barraFerramentas.add(this.jPanel1, (Object)null);
    this.barraFerramentas.add(this.botaoSeta, (Object)null);
    this.barraFerramentas.add(this.botaoEstado, (Object)null);
    this.barraFerramentas.add(this.botaoTransicao, (Object)null);
    this.barraFerramentas.add(this.botaoExcluir, (Object)null);
    this.barraFerramentas.add(this.botaoDispersar, (Object)null);
    this.painelReconhecerSentenca.add(this.botaoFinalizar, (Object)null);
    this.painelReconhecerSentenca.add(this.botaoTodaEntrada, (Object)null);
    this.painelReconhecerSentenca.add(this.textFieldEntradaReconhecida, (Object)null);
    this.painelReconhecerSentenca.add(this.botaoProximoCaracter, (Object)null);
    this.painelReconhecerSentenca.add(this.textFieldEntrada, (Object)null);
    getContentPane().add(this.painelReconhecerSentenca, (Object)null);
    getContentPane().add(this.barraFerramentas, (Object)null);
    setScrollPane(this.canvasAutomato);
    this.objetosIniciais = this.canvasAutomato.getLayer().getAllNodes();
    this.botaoExcluir.setToolTipText("Exclusde objetos");
    this.botaoTransicao.setToolTipText("Criade transi");
    this.botaoEstado.setToolTipText("Criade estados");
    this.botaoSeta.setToolTipText("Selede objetos");
    this.botaoExcluir.setIcon(this.imgApagar);
    this.botaoExcluir.addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            Editor.mav$botaoSeta_mousePressed(Editor.this, e);
          }
          
          public void mouseClicked(MouseEvent e) {
            Editor.mav$botaoSeta_mouseClicked(Editor.this, e);
          }
        });
    this.botaoExcluir.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Editor.mav$botaoExcluir_actionPerformed(Editor.this, e);
          }
        });
    this.botaoExcluir.setBounds(new Rectangle(125, 5, 35, 35));
    setNomeArquivo((String)null);
    this.automatoAtual.setStatus(0);
    setContextoAtual(0);
    atualizaZoomSelecionado();
  }
  
  private void setScrollPane(PCanvas pc) {
    Rectangle boundsAnterior = null;
    if (this.scrollPaneCanvas != null) {
      boundsAnterior = this.scrollPaneCanvas.getBounds();
      getContentPane().remove((Component)this.scrollPaneCanvas);
    } 
    this.scrollPaneCanvas = new PScrollPane((Component)pc);
    if (boundsAnterior != null) {
      this.scrollPaneCanvas.setBounds(boundsAnterior);
    } else {
      this.scrollPaneCanvas.setBounds(new Rectangle(6, 50, getWidth() - 20, getHeight() - 115));
    } 
    getContentPane().add((Component)this.scrollPaneCanvas, (Object)null);
    PViewport viewport = (PViewport)this.scrollPaneCanvas.getViewport();
    viewport.setScrollDirector((PScrollDirector)this.janelaSD);
  }
  
  private void menuSair_actionPerformed(ActionEvent e) {
    fechaEditor();
  }
  
  private void fechaEditor() {
    if (!salvaArquivo(true))
      return; 
    System.exit(0);
  }
  
  private void atualizaBotoesPressionados(JToggleButton botaoPressionado) {
    int nr_comp = this.barraFerramentas.getComponentCount();
    for (int i = 0; i < nr_comp; i++) {
      if (this.barraFerramentas.getComponent(i).getClass().getName().equals("javax.swing.JToggleButton")) {
        JToggleButton cmp_atual = (JToggleButton)this.barraFerramentas.getComponent(i);
        if (cmp_atual != botaoPressionado)
          cmp_atual.setSelected(false); 
      } 
    } 
    if (this.botaoSeta.isSelected())
      this.automatoAtual.setSituacao(0); 
    if (this.botaoEstado.isSelected())
      this.automatoAtual.setSituacao(1); 
    if (this.botaoTransicao.isSelected())
      this.automatoAtual.setSituacao(2); 
    if (this.botaoExcluir.isSelected())
      this.automatoAtual.setSituacao(3); 
  }
  
  private void botaoSeta_mousePressed(MouseEvent e) {
    JToggleButton botaoAtual = (JToggleButton)e.getSource();
    botaoAtual.setSelected(true);
    atualizaBotoesPressionados(botaoAtual);
  }
  
  private void menuSalvar_actionPerformed(ActionEvent e) {
    if (this.nomeArquivo.equals("[semnome]"))
      this.automatoAtual.setStatus(1); 
    if (salvaArquivo(false))
      JOptionPane.showMessageDialog(this, "Arquivo salvo!", "Sucesso", 1); 
  }
  
  private boolean salvaArquivo(boolean pedirConfirmacao) {
    if (this.automatoAtual.getStatus() == 1) {
      if (pedirConfirmacao) {
        Object[] botoesSimNao = { "Sim", "N"};
        if (JOptionPane.showOptionDialog(this, "Deseja salvar as alterano autatual?", "Confirma", 0, 3, null, botoesSimNao, botoesSimNao[0]) != 0) {
          this.automatoAtual.setStatus(0);
          return true;
        } 
      } 
      try {
        String arq = "";
        if (this.nomeArquivo.equals("[semnome]")) {
          JFileChooser fc = new JFileChooser();
          fc.setFileFilter(new FiltroAtm());
          fc.showSaveDialog(this);
          if (fc.getSelectedFile() != null) {
            arq = fc.getSelectedFile().getPath();
          } else {
            return false;
          } 
          if (arq.toLowerCase().indexOf(".atm") == -1)
            arq = arq + ".atm"; 
        } else {
          arq = this.nomeArquivo;
        } 
        FileOutputStream f = new FileOutputStream(arq);
        ObjectOutputStream so = new ObjectOutputStream(f);
        AutomatoArquivo autArquivo = new AutomatoArquivo(this.automatoAtual);
        so.writeObject(autArquivo);
        so.close();
        setNomeArquivo(arq);
        this.automatoAtual.setStatus(0);
        return true;
      } catch (Exception e2) {
        JOptionPane.showMessageDialog(this, "Nfoi posssalvar o arquivo!\n\nDescrido erro:\n" + e2, "Erro", 0);
        e2.printStackTrace();
        return false;
      } 
    } 
    return true;
  }
  
  private void menuMatrizTransicoes_actionPerformed(ActionEvent e) {
    if (this.telaMatrizTransicao == null)
      this.telaMatrizTransicao = new VisualizaMatrizTransicao(this.algoritmosPrograma); 
    this.telaMatrizTransicao.setaAutomato(this.automatoAtual);
    this.telaMatrizTransicao.atualizaDados();
    this.telaMatrizTransicao.setVisible(true);
  }
  
  private void botaoDispersar_actionPerformed(ActionEvent e) {
    empilhaAutomatoAtual();
    this.automatoAtual.getGrafoAutomato().reposicionaEstadosAleatoriamente();
  }
  
  private void menuZoom25_actionPerformed(ActionEvent e) {
    this.automatoAtual.getGrafoAutomato().setEscalaAtual(0.25D);
    atualizaZoomSelecionado();
  }
  
  private void jMenuItem5_actionPerformed(ActionEvent e) {
    this.automatoAtual.getGrafoAutomato().setEscalaAtual(0.5D);
    atualizaZoomSelecionado();
  }
  
  private void jMenuItem6_actionPerformed(ActionEvent e) {
    this.automatoAtual.getGrafoAutomato().setEscalaAtual(0.75D);
    atualizaZoomSelecionado();
  }
  
  private void jMenuItem7_actionPerformed(ActionEvent e) {
    this.automatoAtual.getGrafoAutomato().setEscalaAtual(1.0D);
    atualizaZoomSelecionado();
  }
  
  private void jMenuItem8_actionPerformed(ActionEvent e) {
    this.automatoAtual.getGrafoAutomato().setEscalaAtual(2.0D);
    atualizaZoomSelecionado();
  }
  
  private void jMenuItem9_actionPerformed(ActionEvent e) {
    this.automatoAtual.getGrafoAutomato().setEscalaAtual(4.0D);
    atualizaZoomSelecionado();
  }
  
  private void botaoSeta_actionPerformed(ActionEvent e) {
    this.automatoAtual.setSituacao(0);
  }
  
  private void botaoTransicao_actionPerformed(ActionEvent e) {
    this.automatoAtual.setSituacao(2);
  }
  
  private void botaoEstado_actionPerformed(ActionEvent e) {
    this.automatoAtual.setSituacao(1);
  }
  
  private void menuAFNDToAFD_actionPerformed(ActionEvent e) {
    this.automatoAtual.setArmazenarVersoes(false);
    empilhaAutomatoAtual();
    setContextoAtual(2);
    RelatorioAFNDParaAFD relatorio = this.algoritmosPrograma.transformaEmAFD(null, 0);
    if (relatorio.nrEstadosAlterados > 0) {
      this.automatoAtual.setStatus(1);
      if (relatorio.nrEstadosAlterados == 1) {
        JOptionPane.showMessageDialog(this, "As transide 1 estado foram alteradas.", "Relat", 1);
      } else {
        JOptionPane.showMessageDialog(this, "As transide " + relatorio.nrEstadosAlterados + " estados foram alteradas.", "Relat", 1);
      } 
    } else if (relatorio.nrTransicoesEliminadas > 1) {
      JOptionPane.showMessageDialog(this, "O autficou determinapa eliminade " + relatorio.nrTransicoesEliminadas + " transivazias!", "Relat", 2);
    } else if (relatorio.nrTransicoesEliminadas == 1) {
      JOptionPane.showMessageDialog(this, "O autficou determinapa eliminade 1 transivazia!", "Relat", 2);
    } else {
      descartaUltimaVersaoAutomato();
      JOptionPane.showMessageDialog(this, "O autatual jdetermin", "Relat", 2);
    } 
    this.automatoAtual.setArmazenarVersoes(true);
  }
  
  private void menuEliminarEstadosInalcancaveis_actionPerformed(ActionEvent e) {
    this.automatoAtual.setArmazenarVersoes(false);
    empilhaAutomatoAtual();
    int eliminados = this.algoritmosPrograma.eliminaEstadosInalcancaveis();
    if (eliminados > 0) {
      JOptionPane.showMessageDialog(this, "Quantidade de estados eliminados: " + eliminados, "Relat", 1);
    } else {
      descartaUltimaVersaoAutomato();
      JOptionPane.showMessageDialog(this, "O autatual npossui estados inalcan", "Relat", 2);
    } 
    this.automatoAtual.setArmazenarVersoes(true);
  }
  
  private void menuEliminarEstadosMortos_actionPerformed(ActionEvent e) {
    if (this.automatoAtual.getEstadosReconhecedores().size() > 0) {
      this.automatoAtual.setArmazenarVersoes(false);
      empilhaAutomatoAtual();
      int eliminados = this.algoritmosPrograma.eliminaEstadosMortos();
      if (eliminados > 0) {
        JOptionPane.showMessageDialog(this, "Quantidade de estados eliminados: " + eliminados, "Relat", 1);
      } else {
        descartaUltimaVersaoAutomato();
        JOptionPane.showMessageDialog(this, "O autatual npossui estados mortos!", "Relat", 2);
      } 
      this.automatoAtual.setArmazenarVersoes(true);
    } else {
      JOptionPane.showMessageDialog(this, "Para executar este algoritmo vocprecisa definir pelo menos 1 estado reconhecedor!", "Relat", 2);
    } 
  }
  
  private void menuEliminarETransicoes_actionPerformed(ActionEvent e) {
    this.automatoAtual.setArmazenarVersoes(false);
    empilhaAutomatoAtual();
    setContextoAtual(3);
    int eliminadas = this.algoritmosPrograma.eliminaTransicoesVazias();
    if (eliminadas > 0) {
      this.automatoAtual.setStatus(1);
      JOptionPane.showMessageDialog(this, "Quantidade de transieliminadas: " + eliminadas, "Relat", 1);
    } else {
      descartaUltimaVersaoAutomato();
      JOptionPane.showMessageDialog(this, "O autatual npossui transivazias!", "Relat", 2);
    } 
    this.automatoAtual.setArmazenarVersoes(true);
  }
  
  private void menuMinimizar_actionPerformed(ActionEvent e) {
    this.automatoAtual.setArmazenarVersoes(false);
    empilhaAutomatoAtual();
    int nrEstadosAnt = this.automatoAtual.getNrEstados();
    setContextoAtual(4);
    int reduzidos = this.algoritmosPrograma.minimiza();
    if (reduzidos > 0) {
      JOptionPane.showMessageDialog(this, "Quantidade de estados equivalentes eliminados: " + reduzidos, "Relat", 1);
    } else {
      int difNrEstados = nrEstadosAnt - this.automatoAtual.getNrEstados();
      if (difNrEstados > 0)
        JOptionPane.showMessageDialog(this, "Quantidade de estados eliminados: " + difNrEstados, "Relat", 1); 
      if (difNrEstados < 0)
        JOptionPane.showMessageDialog(this, "Quantidade de estados criados: " + Math.abs(reduzidos), "Relat", 1); 
      if (difNrEstados == 0) {
        descartaUltimaVersaoAutomato();
        JOptionPane.showMessageDialog(this, "O autatual jm", "Relat", 2);
      } 
    } 
    this.automatoAtual.setArmazenarVersoes(true);
  }
  
  private void menuImportarExpReg_actionPerformed(ActionEvent e) {
    if (this.telaImportarExpressaoRegular == null)
      this.telaImportarExpressaoRegular = new TelaImportarExpressaoRegular(this.algoritmosPrograma); 
    this.telaImportarExpressaoRegular.atualizaDados();
    this.telaImportarExpressaoRegular.setVisible(true);
    setContextoAtual(7);
    String exp = this.telaImportarExpressaoRegular.getExpressaoPosFixa();
    if (exp != null) {
      if (!salvaArquivo(true))
        return; 
      Automato autImp = this.algoritmosPrograma.importaExpressaoRegular(exp);
      if (autImp != null) {
        setAutomato(autImp, (String)null);
        this.automatoAtual.getGrafoAutomato().reposicionaEstadosAleatoriamente();
        JOptionPane.showMessageDialog(this, "Quantidade de estados criados: " + autImp.getNrEstados(), "Relat", 1);
      } else {
        JOptionPane.showMessageDialog(this, "Nfoi possimportar a expressregular!", "Erro", 0);
      } 
    } 
  }
  
  private void setAutomato(Automato autNovo, String nomeArquivo) {
    PCanvas cvImp = autNovo.getGrafoAutomato().getCanvas();
    cvImp.setBounds(this.canvasAutomato.getBounds());
    cvImp.setAutoscrolls(this.canvasAutomato.getAutoscrolls());
    cvImp.setLayout(this.canvasAutomato.getLayout());
    cvImp.setBorder(this.canvasAutomato.getBorder());
    cvImp.setSize(this.canvasAutomato.getSize());
    setScrollPane(cvImp);
    this.canvasAutomato = cvImp;
    this.automatoAtual = autNovo;
    this.algoritmosPrograma.setAutomato(this.automatoAtual);
    if (nomeArquivo != null)
      setNomeArquivo(nomeArquivo); 
  }
  
  private void setNomeArquivo(String nome) {
    if (nome == null) {
      this.nomeArquivo = "[semnome]";
    } else {
      this.nomeArquivo = nome;
    } 
    setTitle("Auger - " + this.nomeArquivo);
  }
  
  private void menuExportarGramReg_actionPerformed(ActionEvent e) {
    try {
      if (this.automatoAtual.getNrEstados() == 0) {
        JOptionPane.showMessageDialog(this, "O autatual npossui estados!", "Aviso", 2);
        return;
      } 
      setContextoAtual(6);
      String gramaticaGerada = this.algoritmosPrograma.geraGramaticaRegular();
      JTextArea memo = new JTextArea(gramaticaGerada);
      JOptionPane.showMessageDialog(this, memo, "Gramexportada", 1);
      String arq = "";
      JFileChooser fc = new JFileChooser();
      fc.setFileFilter(new FiltroGrm());
      fc.showSaveDialog(this);
      if (fc.getSelectedFile() != null) {
        FileOutputStream fileOutputStream;
        arq = fc.getSelectedFile().getPath();
        if (arq.toLowerCase().indexOf(".grm") == -1) {
          fileOutputStream = new FileOutputStream(arq + ".grm", false);
        } else {
          fileOutputStream = new FileOutputStream(arq, false);
        } 
        PrintStream ps = new PrintStream(fileOutputStream);
        ps.print(gramaticaGerada);
        ps.close();
      } 
    } catch (Exception e2) {
      JOptionPane.showMessageDialog(this, "Nfoi possexportar a gram\n\nDescrido erro:\n" + e2, "Erro", 0);
      e2.printStackTrace();
    } 
  }
  
  private void menuImportarGramReg_actionPerformed(ActionEvent e) {
    try {
      setContextoAtual(5);
      String arq = "";
      JFileChooser fc = new JFileChooser();
      fc.setFileFilter(new FiltroGrm());
      fc.showOpenDialog(this);
      if (fc.getSelectedFile() != null) {
        if (!salvaArquivo(true))
          return; 
        arq = fc.getSelectedFile().getPath();
        FileInputStream f = new FileInputStream(arq);
        ArrayList<String> linhasArquivo = new ArrayList();
        BufferedInputStream leArquivo = new BufferedInputStream(f);
        StringBuffer strTemp = new StringBuffer();
        char penultLido = Character.MIN_VALUE;
        while (leArquivo.available() > 0) {
          char ultLido = (char)leArquivo.read();
          if (ultLido == '\n' || ultLido == '\r') {
            if (penultLido != '\n' && penultLido != '\r') {
              linhasArquivo.add(strTemp.toString());
              strTemp = new StringBuffer();
            } 
          } else {
            strTemp.append(ultLido);
          } 
          penultLido = ultLido;
        } 
        leArquivo.close();
        Automato automatoGerado = this.algoritmosPrograma.importaGramaticaReglar(linhasArquivo);
        int ret = automatoGerado.getNrEstados();
        if (ret > 0) {
          this.automatoAtual.getGrafoAutomato().reposicionaEstadosAleatoriamente();
          setAutomato(automatoGerado, (String)null);
          if (ret == 1) {
            JOptionPane.showMessageDialog(this, "Gramimportada!\n\n1 estado criado.", "Ok", 1);
          } else {
            JOptionPane.showMessageDialog(this, "Gramimportada!\n\n" + ret + " estados criados.", "Ok", 1);
          } 
        } 
        switch (ret) {
          case -1:
            return;
        } 
        JOptionPane.showMessageDialog(this, "Nfoi possimportar a gram\nConfira a grame tente novamente.", "Erro", 0);
      } 
    } catch (Exception e2) {
      JOptionPane.showMessageDialog(this, "Nfoi possimportar a gram\n\nDescrido erro:\n" + e2, "Erro", 0);
      e2.printStackTrace();
    } 
  }
  
  private void menuAbrir_actionPerformed(ActionEvent e) {
    if (!salvaArquivo(true))
      return; 
    try {
      String arq = "";
      JFileChooser fc = new JFileChooser();
      fc.setFileFilter(new FiltroAtm());
      fc.showOpenDialog(this);
      if (fc.getSelectedFile() == null)
        return; 
      arq = fc.getSelectedFile().getPath();
      if (arq.toLowerCase().indexOf(".atm") == -1)
        arq = arq + ".atm"; 
      FileInputStream f = new FileInputStream(arq);
      ObjectInputStream so = new ObjectInputStream(f);
      Object obj = so.readObject();
      AutomatoArquivo autNovo = (AutomatoArquivo)obj;
      menuNovo_actionPerformed(e);
      abreAutomato(autNovo, arq);
      setNomeArquivo(arq);
      this.automatoAtual.setStatus(0);
    } catch (Exception e2) {
      JOptionPane.showMessageDialog(this, "Nfoi possabrir arquivo!\n\nDescrido erro:\n" + e2, "Erro", 0);
      e2.printStackTrace();
    } 
  }
  
  private void menuNovo_actionPerformed(ActionEvent e) {
    if (!salvaArquivo(true))
      return; 
    criaNovoAutomato();
    this.pilhaAutomato.clear();
  }
  
  private void menuExportarExpReg_actionPerformed(ActionEvent e) {
    try {
      if (this.automatoAtual.getNrEstados() == 0) {
        JOptionPane.showMessageDialog(this, "O autatual npossui estados!", "Aviso", 2);
        return;
      } 
      Automato atmExp = new Automato(0, this, false);
      ArrayList<String> estAtu = this.automatoAtual.getEstados();
      for (int i = 0; i < estAtu.size(); i++) {
        String est = estAtu.get(i);
        atmExp.criaEstado(est, i, i);
      } 
      atmExp.setEstadoInicial(this.automatoAtual.getEstadoInicial());
      ArrayList<Transicao> transAtu = this.automatoAtual.getMatrizTransicoes().getTransicoes();
      for (int j = 0; j < transAtu.size(); j++) {
        Transicao tr = transAtu.get(j);
        atmExp.criaTransicao(tr.getEstadoEntrada(), tr.getValorEntrada(), tr.getEstadoSaida());
      } 
      HashSet recAtu = this.automatoAtual.getEstadosReconhecedores();
      Iterator<String> nav = recAtu.iterator();
      while (nav.hasNext()) {
        String est = nav.next();
        atmExp.adicionaEstadoReconhecedor(est);
      } 
      this.algoritmosPrograma.setAutomato(atmExp);
      setContextoAtual(8);
      String expressaoGerada = this.algoritmosPrograma.geraExpressaoRegular();
      if (expressaoGerada.length() > 0) {
        int opSimplificar = JOptionPane.showOptionDialog(this, "Deseja gerar uma expressregular simplificada?", "Confirma", 0, 3, null, null, Integer.valueOf(0));
        if (opSimplificar == 0)
          expressaoGerada = this.algoritmosPrograma.simplificaExpressaoRegular(expressaoGerada); 
      } 
      JTextArea memoSaida = new JTextArea(expressaoGerada);
      JOptionPane.showMessageDialog(this, memoSaida, "Expressregular gerada", 1);
      atmExp = null;
      this.algoritmosPrograma.setAutomato(this.automatoAtual);
      String arq = "";
      JFileChooser fc = new JFileChooser();
      fc.setFileFilter(new FiltroExp());
      fc.showSaveDialog(this);
      if (fc.getSelectedFile() != null) {
        FileOutputStream fileOutputStream;
        arq = fc.getSelectedFile().getPath();
        if (arq.toLowerCase().indexOf(".exp") == -1) {
          fileOutputStream = new FileOutputStream(arq + ".exp", false);
        } else {
          fileOutputStream = new FileOutputStream(arq, false);
        } 
        PrintStream ps = new PrintStream(fileOutputStream);
        ps.print(expressaoGerada);
        ps.close();
      } 
    } catch (Exception e2) {
      JOptionPane.showMessageDialog(this, "Nfoi possexportar a express\n\nDescrido erro:\n" + e2, "Erro", 0);
      this.algoritmosPrograma.setAutomato(this.automatoAtual);
      e2.printStackTrace();
    } 
  }
  
  private void menuReconhecerSentenca_actionPerformed(ActionEvent e) {
    String valorEntrada = JOptionPane.showInputDialog(null, "Informe a cadeia de entrada:", "Testar aut", 3);
    if (valorEntrada != null) {
      if (this.automatoAtual.getSituacao() == 5)
        botaoFinalizar_actionPerformed(e); 
      this.textFieldEntrada.setText(valorEntrada);
      this.textFieldEntradaReconhecida.setText("");
      this.estadosAtuaisReconhecimento.clear();
      this.estadosAtuaisReconhecimento.add(this.automatoAtual.getEstadoInicial());
      ArrayList<Transicao> listaTransicoes = Algoritmos.retornaTransicoesEstado(this.automatoAtual.getMatrizTransicoes().getTransicoes(), this.automatoAtual.getEstadoInicial(), null);
      for (Transicao trans : listaTransicoes) {
        if (trans.getValorEntrada().equals(Constantes.STRING_VAZIA))
          this.estadosAtuaisReconhecimento.add(trans.getEstadoSaida()); 
      } 
      for (String estadoAtualRec : this.estadosAtuaisReconhecimento)
        this.automatoAtual.getGrafoAutomato().entraNoEstado(estadoAtualRec); 
      this.painelReconhecerSentenca.setVisible(true);
      this.situacaoAntesTeste = this.automatoAtual.getSituacao();
      this.automatoAtual.setSituacao(5);
      this.barraFerramentas.setVisible(false);
      getContentPane().validate();
      this.painelReconhecerSentenca.validate();
    } 
  }
  
  private void botaoFinalizar_actionPerformed(ActionEvent e) {
    this.automatoAtual.setSituacao(this.situacaoAntesTeste);
    this.barraFerramentas.setVisible(true);
    this.painelReconhecerSentenca.setVisible(false);
  }
  
  private void botaoProximoCaracter_actionPerformed(ActionEvent e) {
    if (this.textFieldEntrada.getText().length() == 0)
      return; 
    String proximoCaracter = this.textFieldEntrada.getText().charAt(0) + "";
    if (executaTransicao(proximoCaracter)) {
      this.textFieldEntradaReconhecida.setText(this.textFieldEntradaReconhecida.getText() + this.textFieldEntrada.getText().charAt(0));
      if (this.textFieldEntrada.getText().length() > 1) {
        this.textFieldEntrada.setText(this.textFieldEntrada.getText().substring(1));
      } else {
        this.textFieldEntrada.setText("");
      } 
      if (this.textFieldEntrada.getText().length() == 0) {
        boolean reconhecida = false;
        Iterator<String> nav = this.estadosAtuaisReconhecimento.iterator();
        while (nav.hasNext() && reconhecida == false) {
          String estadoAtual = nav.next();
          if (this.automatoAtual.isEstadoReconhecedor(estadoAtual))
            reconhecida = true; 
        } 
        if (reconhecida) {
          JOptionPane.showMessageDialog(this, "Sentenreconhecida!", "Resultado", 1);
        } else {
          JOptionPane.showMessageDialog(this, "Sentennreconhecida!", "Resultado", 0);
        } 
      } 
    } else {
      JOptionPane.showMessageDialog(this, "Nfoi possencontrar uma transipara a entrada \"" + proximoCaracter + "\" a partir dos estados atuais.\nSentennreconhecida!", "Falha", 0);
    } 
  }
  
  private boolean executaTransicao(String proximoCaracter) {
    ArrayList<Transicao> transicoes = this.automatoAtual.getMatrizTransicoes().getTransicoes();
    List<String> novosEstadosAtuais = new ArrayList<String>();
    Iterator<String> nav = this.estadosAtuaisReconhecimento.iterator();
    while (nav.hasNext()) {
      String estadoAtual = nav.next();
      for (int j = 0; j < transicoes.size(); j++) {
        Transicao tr = transicoes.get(j);
        if (tr.getEstadoEntrada().equals(estadoAtual) && tr.getValorEntrada().equals(proximoCaracter))
          novosEstadosAtuais.add(tr.getEstadoSaida()); 
      } 
    } 
    for (int i = 0; i < novosEstadosAtuais.size(); i++) {
      String estAtu = novosEstadosAtuais.get(i);
      ArrayList<Transicao> trans = Algoritmos.retornaTransicoesEstado(transicoes, estAtu, Constantes.STRING_VAZIA);
      for (Transicao trAtual : trans) {
        if (!novosEstadosAtuais.contains(trAtual.getEstadoSaida()))
          novosEstadosAtuais.add(trAtual.getEstadoSaida()); 
      } 
    } 
    if (novosEstadosAtuais.size() > 0) {
      nav = this.estadosAtuaisReconhecimento.iterator();
      while (nav.hasNext()) {
        String estadoAtual = nav.next();
        this.automatoAtual.getGrafoAutomato().saiDoEstado(estadoAtual);
      } 
      nav = novosEstadosAtuais.iterator();
      while (nav.hasNext()) {
        String estadoAtual = nav.next();
        this.automatoAtual.getGrafoAutomato().entraNoEstado(estadoAtual);
      } 
      this.estadosAtuaisReconhecimento = novosEstadosAtuais;
      return true;
    } 
    return false;
  }
  
  private void botaoTodaEntrada_actionPerformed(ActionEvent e) {
    String proximoCaracter = this.textFieldEntrada.getText().charAt(0) + "";
    while (this.textFieldEntrada.getText().length() > 0 && executaTransicao(proximoCaracter)) {
      this.textFieldEntradaReconhecida.setText(this.textFieldEntradaReconhecida.getText() + this.textFieldEntrada.getText().charAt(0));
      if (this.textFieldEntrada.getText().length() > 1) {
        this.textFieldEntrada.setText(this.textFieldEntrada.getText().substring(1));
      } else {
        this.textFieldEntrada.setText("");
      } 
      if (this.textFieldEntrada.getText().length() > 0)
        proximoCaracter = this.textFieldEntrada.getText().charAt(0) + ""; 
    } 
    boolean reconhecida = false;
    Iterator<String> nav = this.estadosAtuaisReconhecimento.iterator();
    while (nav.hasNext() && reconhecida == false) {
      String estadoAtual = nav.next();
      if (this.automatoAtual.isEstadoReconhecedor(estadoAtual))
        reconhecida = true; 
    } 
    if (this.textFieldEntrada.getText().length() > 0) {
      JOptionPane.showMessageDialog(this, "Nfoi possencontrar uma transipara a entrada \"" + proximoCaracter + "\" a partir dos estados atuais.\nSentennreconhecida!", "Falha", 0);
    } else if (reconhecida) {
      JOptionPane.showMessageDialog(this, "Sentenreconhecida!", "Resultado", 1);
    } else {
      JOptionPane.showMessageDialog(this, "Sentennreconhecida!", "Resultado", 0);
    } 
  }
  
  private void botaoExcluir_actionPerformed(ActionEvent e) {
    this.automatoAtual.setSituacao(3);
  }
  
  private void botaoSeta_mouseClicked(MouseEvent e) {
    JToggleButton botaoAtual = (JToggleButton)e.getSource();
    if (!botaoAtual.isSelected())
      botaoAtual.setSelected(true); 
    atualizaBotoesPressionados(botaoAtual);
    setContextoAtual(1);
  }
  
  private void menuReferencialTeorico_actionPerformed(ActionEvent e) {
    int contextoAnterior = this.contextoAtual;
    setContextoAtual(10);
    menuReferencialTeoricoContexto_actionPerformed(e);
    setContextoAtual(contextoAnterior);
  }
  
  private void menuGerarProgramaReconhecedor_actionPerformed(ActionEvent e) {
    try {
      String nomeClasse;
      setContextoAtual(9);
      String arq = "";
      JFileChooser fc = new JFileChooser();
      fc.setFileFilter(new FiltroJava());
      fc.setDialogTitle("Salvar programa reconhecedor");
      fc.showSaveDialog(this);
      if (fc.getSelectedFile() != null) {
        arq = fc.getSelectedFile().getPath();
        nomeClasse = fc.getSelectedFile().getName();
      } else {
        return;
      } 
      int posExt = arq.toLowerCase().indexOf(".java");
      if (posExt == -1) {
        arq = arq + ".java";
      } else {
        posExt = nomeClasse.toLowerCase().indexOf(".java");
        nomeClasse = nomeClasse.substring(0, posExt);
      } 
      FileOutputStream f = new FileOutputStream(arq, false);
      String programaGerado = this.algoritmosPrograma.geraProgramaReconhecedor(nomeClasse);
      PrintStream ps = new PrintStream(f);
      ps.print(programaGerado);
      ps.close();
      JOptionPane.showMessageDialog(this, "Programa gerado!", "Ok", 1);
    } catch (Exception e2) {
      JOptionPane.showMessageDialog(this, "Nfoi possgerar o programa reconhecedor!\n\nDescrido erro:\n" + e2, "Erro", 0);
      e2.printStackTrace();
    } 
  }
  
  private void menuReferencialTeoricoContexto_actionPerformed(ActionEvent e) {
    if (this.telaTeoria == null)
      this.telaTeoria = new TelaTeoria(this, "Referencial te", true, getContextoAtual()); 
    this.telaTeoria.navega(getContextoAtual());
    this.telaTeoria.setVisible(true);
  }
  
  private String getContextoAtual() {
    if (this.contextoAtual == 0)
      return "_Toc120047279"; 
    if (this.contextoAtual == 1)
      return "_Toc120047290"; 
    if (this.contextoAtual == 2)
      return "_Toc120047294"; 
    if (this.contextoAtual == 3)
      return "_Toc120047296"; 
    if (this.contextoAtual == 4)
      return "_Toc120047297"; 
    if (this.contextoAtual == 5)
      return "_Toc120047299"; 
    if (this.contextoAtual == 6)
      return "_Toc120047300"; 
    if (this.contextoAtual == 7)
      return "_Toc120047301"; 
    if (this.contextoAtual == 8)
      return "_Toc120047302"; 
    if (this.contextoAtual == 9)
      return "_Toc120047307"; 
    if (this.contextoAtual == 10)
      return "sumario"; 
    return null;
  }
  
  private void setContextoAtual(int contexto) {
    this.contextoAtual = contexto;
  }
  
  private void this_componentResized(ComponentEvent e) {
    this.scrollPaneCanvas.setSize(getWidth() - 20, getHeight() - 115);
    this.scrollPaneCanvas.revalidate();
  }
  
  private void jMenuItem16_actionPerformed(ActionEvent e) {
    this.telaAjuda = new TelaAjuda(this, "Ajuda do Auger", true);
    this.telaAjuda.setVisible(true);
  }
  
  private void menuSobreAuger_actionPerformed(ActionEvent e) {
    if (this.telaSobre == null)
      this.telaSobre = new TelaSobre(this, "", true); 
    Global.centralizaFrame(this.telaSobre, getSize());
    this.telaSobre.setVisible(true);
  }
  
  private void menuExportarDiagramaEstados_actionPerformed(ActionEvent e) {
    try {
      if (this.automatoAtual.getNrEstados() == 0) {
        JOptionPane.showMessageDialog(this, "O autatual npossui estados!", "Aviso", 2);
        return;
      } 
      String arq = "";
      JFileChooser fc = new JFileChooser();
      fc.setFileFilter(new FiltroJpeg());
      fc.showSaveDialog(this);
      if (fc.getSelectedFile() != null) {
        arq = fc.getSelectedFile().getAbsolutePath();
        if (arq.toLowerCase().indexOf(".jpg") == -1)
          arq = arq + ".jpg"; 
        ExportaImagem.salvaJPG(arq, this.canvasAutomato);
        JOptionPane.showMessageDialog(this, "Diagrama exportado com sucesso!", "Informa", 1);
      } 
    } catch (Exception e2) {
      JOptionPane.showMessageDialog(this, "Nfoi possexportar o diagrama de estados!\n\nDescrido erro:\n" + e2, "Erro", 0);
      e2.printStackTrace();
    } 
  }
  
  private void menuItemFerramentaSelecao_actionPerformed(ActionEvent e) {
    this.automatoAtual.setSituacao(0);
    selecionaBotaoFerramenta(this.botaoSeta);
  }
  
  private void menuItemFerramentaCriacaoEstados_actionPerformed(ActionEvent e) {
    this.automatoAtual.setSituacao(1);
    selecionaBotaoFerramenta(this.botaoEstado);
  }
  
  private void menuItemFerramentaCriacaoTransicoes_actionPerformed(ActionEvent e) {
    this.automatoAtual.setSituacao(2);
    selecionaBotaoFerramenta(this.botaoTransicao);
  }
  
  private void menuItemFerramentaExclusao_actionPerformed(ActionEvent e) {
    this.automatoAtual.setSituacao(3);
    selecionaBotaoFerramenta(this.botaoExcluir);
  }
  
  private void selecionaBotaoFerramenta(JToggleButton botao) {
    this.botaoSeta.setSelected(false);
    this.botaoEstado.setSelected(false);
    this.botaoTransicao.setSelected(false);
    this.botaoExcluir.setSelected(false);
    botao.setSelected(true);
  }
  
  public void empilhaAutomatoAtual() {
    AutomatoArquivo autArquivoAux = new AutomatoArquivo(this.automatoAtual);
    byte[] arqBytes = Auger.objectToByteArray(autArquivoAux);
    AutomatoArquivo autArquivoSerial = (AutomatoArquivo)Auger.byteArrayToObject(arqBytes);
    this.pilhaAutomato.push(autArquivoSerial);
  }
  
  private void abreAutomato(AutomatoArquivo autNovo, String arq) {
    Automato atualAbrir = new Automato(1, this, true);
    atualAbrir.setArmazenarVersoes(false);
    atualAbrir.setSituacao(this.automatoAtual.getSituacao());
    atualAbrir.alteraCorFundoDiagramaEstados(autNovo.getCorFundoDiagramaEstados());
    atualAbrir.getGrafoAutomato().setEscalaAtual(autNovo.getGrafoAutomato().getEscalaAtual());
    setAutomato(atualAbrir, arq);
    atualizaZoomSelecionado();
    for (int i = 0; i < autNovo.posicaoEstados.size(); i++) {
      Object[] objArq = autNovo.posicaoEstados.get(i);
      String nomeEstado = (String)objArq[0];
      double x = Double.parseDouble(objArq[1] + "");
      double y = Double.parseDouble(objArq[2] + "");
      this.automatoAtual.criaEstado(nomeEstado, x, y, false);
    } 
    this.automatoAtual.setEstadoInicial(autNovo.getEstadoInicial());
    Iterator<String> nav = autNovo.getEstadosReconhecedores().iterator();
    while (nav.hasNext()) {
      String nomeEstado = nav.next();
      this.automatoAtual.adicionaEstadoReconhecedor(nomeEstado);
    } 
    ArrayList<Transicao> transicoes = autNovo.getMatrizTransicoes().getTransicoes();
    for (int j = 0; j < transicoes.size(); j++) {
      Transicao tr = transicoes.get(j);
      this.automatoAtual.criaTransicao(tr.getEstadoEntrada(), tr.getValorEntrada(), tr.getEstadoSaida());
    } 
    this.automatoAtual.setArmazenarVersoes(true);
  }
  
  private void criaNovoAutomato() {
    Automato atmNovo = new Automato(1, this, true);
    setAutomato(atmNovo, (String)null);
    setContextoAtual(1);
    setNomeArquivo((String)null);
  }
  
  private void menuItemDesfazer_actionPerformed(ActionEvent e) {
    if (this.pilhaAutomato.size() > 0) {
      AutomatoArquivo autPilha = this.pilhaAutomato.pop();
      autPilha.setSituacao(this.automatoAtual.getSituacao());
      abreAutomato(autPilha, (String)null);
    } 
  }
  
  public void guardaVersaoAutomato() {
    empilhaAutomatoAtual();
  }
  
  public void descartaUltimaVersaoAutomato() {
    if (this.pilhaAutomato.size() > 0)
      this.pilhaAutomato.pop(); 
  }
  
  private void menuItemAlterarCorFundo_actionPerformed(ActionEvent e) {
    JColorChooser colorChooser = new JColorChooser();
    Color corNova = JColorChooser.showDialog(this, "Selecione a cor", this.automatoAtual.getCorFundoDiagramaEstados());
    if (corNova != null)
      this.automatoAtual.alteraCorFundoDiagramaEstados(corNova); 
  }
  
  private void menuItemZoom125_actionPerformed(ActionEvent e) {
    this.automatoAtual.getGrafoAutomato().setEscalaAtual(1.25D);
    atualizaZoomSelecionado();
  }
  
  private void menuItemZoom150_actionPerformed(ActionEvent e) {
    this.automatoAtual.getGrafoAutomato().setEscalaAtual(1.5D);
    atualizaZoomSelecionado();
  }
  
  private void menuItemZoom175_actionPerformed(ActionEvent e) {
    this.automatoAtual.getGrafoAutomato().setEscalaAtual(1.75D);
    atualizaZoomSelecionado();
  }
  
  private void atualizaZoomSelecionado() {
    double escala = this.automatoAtual.getGrafoAutomato().getEscalaAtual();
    this.menuItemZoom25.setSelected(false);
    this.menuItemZoom50.setSelected(false);
    this.menuItemZoom75.setSelected(false);
    this.menuItemZoom100.setSelected(false);
    this.menuItemZoom125.setSelected(false);
    this.menuItemZoom150.setSelected(false);
    this.menuItemZoom175.setSelected(false);
    this.menuItemZoom200.setSelected(false);
    this.menuItemZoom400.setSelected(false);
    if (escala == 0.25D) {
      this.menuItemZoom25.setSelected(true);
    } else if (escala == 0.5D) {
      this.menuItemZoom50.setSelected(true);
    } else if (escala == 0.75D) {
      this.menuItemZoom75.setSelected(true);
    } else if (escala == 1.0D) {
      this.menuItemZoom100.setSelected(true);
    } else if (escala == 1.25D) {
      this.menuItemZoom125.setSelected(true);
    } else if (escala == 1.5D) {
      this.menuItemZoom150.setSelected(true);
    } else if (escala == 1.75D) {
      this.menuItemZoom175.setSelected(true);
    } else if (escala == 2.0D) {
      this.menuItemZoom200.setSelected(true);
    } else if (escala == 4.0D) {
      this.menuItemZoom400.setSelected(true);
    } 
  }
}
