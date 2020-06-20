package auger.tcc;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventListener;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.event.PSelectionEventHandler;
import edu.umd.cs.piccolox.nodes.PClip;
import edu.umd.cs.piccolox.nodes.PLine;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class GrafoAutomato implements Serializable {
  private transient PCanvas canvasAutomato;
  
  public transient PNode estadoEntrada;
  
  public transient PNode estadoSaida;
  
  static String ra$nomeEstadoEntrada(GrafoAutomato paramGrafoAutomato) {
    return paramGrafoAutomato.nomeEstadoEntrada;
  }
  
  static PCanvas ra$canvasAutomato(GrafoAutomato paramGrafoAutomato) {
    return paramGrafoAutomato.canvasAutomato;
  }
  
  static void mav$setaNomeEstados(GrafoAutomato paramGrafoAutomato) {
    paramGrafoAutomato.setaNomeEstados();
  }
  
  static void wa$valorEntrada(GrafoAutomato paramGrafoAutomato, String paramString) {
    paramGrafoAutomato.valorEntrada = paramString;
  }
  
  static String ra$valorEntrada(GrafoAutomato paramGrafoAutomato) {
    return paramGrafoAutomato.valorEntrada;
  }
  
  static String ra$nomeEstadoSaida(GrafoAutomato paramGrafoAutomato) {
    return paramGrafoAutomato.nomeEstadoSaida;
  }
  
  static void wa$saiuDaBola(GrafoAutomato paramGrafoAutomato, boolean paramBoolean) {
    paramGrafoAutomato.saiuDaBola = paramBoolean;
  }
  
  static void wa$mouseX(GrafoAutomato paramGrafoAutomato, double paramDouble) {
    paramGrafoAutomato.mouseX = paramDouble;
  }
  
  static void wa$mouseY(GrafoAutomato paramGrafoAutomato, double paramDouble) {
    paramGrafoAutomato.mouseY = paramDouble;
  }
  
  static void mav$renomeiaEstado(GrafoAutomato paramGrafoAutomato) {
    paramGrafoAutomato.renomeiaEstado();
  }
  
  static boolean ra$saiuDaBola(GrafoAutomato paramGrafoAutomato) {
    return paramGrafoAutomato.saiuDaBola;
  }
  
  static void mav$criaTransicaoEmSerie(GrafoAutomato paramGrafoAutomato) {
    paramGrafoAutomato.criaTransicaoEmSerie();
  }
  
  static void wa$nomeEstadoEntrada(GrafoAutomato paramGrafoAutomato, String paramString) {
    paramGrafoAutomato.nomeEstadoEntrada = paramString;
  }
  
  static void wa$nomeEstadoSaida(GrafoAutomato paramGrafoAutomato, String paramString) {
    paramGrafoAutomato.nomeEstadoSaida = paramString;
  }
  
  static JCheckBoxMenuItem ra$menuEstadoFinal(GrafoAutomato paramGrafoAutomato) {
    return paramGrafoAutomato.menuEstadoFinal;
  }
  
  static JCheckBoxMenuItem ra$menuEstadoInicial(GrafoAutomato paramGrafoAutomato) {
    return paramGrafoAutomato.menuEstadoInicial;
  }
  
  static JPopupMenu ra$menuMouse(GrafoAutomato paramGrafoAutomato) {
    return paramGrafoAutomato.menuMouse;
  }
  
  static double ra$mouseX(GrafoAutomato paramGrafoAutomato) {
    return paramGrafoAutomato.mouseX;
  }
  
  static double ra$mouseY(GrafoAutomato paramGrafoAutomato) {
    return paramGrafoAutomato.mouseY;
  }
  
  private transient ArrayList vetorLinha = new ArrayList();
  
  private transient ArrayList vetorEntradasGraficas = new ArrayList();
  
  private transient double mouseX;
  
  private transient double mouseY;
  
  public transient Automato automatoAtual;
  
  private transient String nomeEstadoEntrada;
  
  private transient String valorEntrada;
  
  private transient String nomeEstadoSaida;
  
  private transient int LARGURA_ESTADO = 40;
  
  private transient int ALTURA_ESTADO = 40;
  
  private static final long serialVersionUID = 1L;
  
  public transient ArrayList vetorEstados;
  
  private double escalaAtual;
  
  private transient JPopupMenu menuMouse;
  
  private transient JCheckBoxMenuItem menuEstadoInicial;
  
  private transient JCheckBoxMenuItem menuEstadoFinal;
  
  private transient JMenuItem menuRenomear;
  
  private transient JMenuItem menuExcluir;
  
  public transient boolean criandoTransicao;
  
  public transient PLine linhaDesenhaTransicao = null;
  
  private transient boolean saiuDaBola = false;
  
  private transient PLine setaEstadoInicial = null;
  
  public transient PClip bolaEstadoInicial = null;
  
  private transient ArrayList objSelecionaveis = new ArrayList();
  
  private transient PSelectionEventHandler ps;
  
  private transient DecoratorGroup objetoAgrupador;
  
  private transient VersionadorListener versionador;
  
  public GrafoAutomato(VersionadorListener versionador) {
    this.versionador = versionador;
    this.canvasAutomato = new PCanvas();
    this.canvasAutomato.removeInputEventListener((PInputEventListener)this.canvasAutomato.getPanEventHandler());
    this.canvasAutomato.removeInputEventListener((PInputEventListener)this.canvasAutomato.getZoomEventHandler());
    this.canvasAutomato.addInputEventListener((PInputEventListener)new PBasicInputEventHandler() {
          public void mouseEntered(PInputEvent ev) {
            if (GrafoAutomato.ra$saiuDaBola(GrafoAutomato.this))
              GrafoAutomato.this.estadoSaida = null; 
          }
        });
    this.canvasAutomato.addInputEventListener((PInputEventListener)new PBasicInputEventHandler() {
          public void mousePressed(PInputEvent ev) {
            if (GrafoAutomato.this.automatoAtual.getSituacao() == 1)
              if (ev.getButton() == 1)
                GrafoAutomato.this.automatoAtual.criaEstado((int)ev.getPosition().getX(), (int)ev.getPosition().getY());  
          }
        });
    SelecaoGrupo sg = new SelecaoGrupo((PNode)this.canvasAutomato.getLayer(), this.objSelecionaveis, this);
    this.canvasAutomato.addInputEventListener((PInputEventListener)sg);
    this.objetoAgrupador = new DecoratorGroup();
    this.canvasAutomato.getLayer().addChild(this.objetoAgrupador);
    this.escalaAtual = 1.0D;
    this.vetorEstados = new ArrayList();
    this.menuMouse = new JPopupMenu();
    this.menuEstadoInicial = new JCheckBoxMenuItem("Estado inicial", false);
    this.menuEstadoInicial.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (GrafoAutomato.ra$menuEstadoInicial(GrafoAutomato.this).getState())
              GrafoAutomato.this.automatoAtual.setEstadoInicial(GrafoAutomato.ra$nomeEstadoEntrada(GrafoAutomato.this)); 
          }
        });
    this.menuEstadoFinal = new JCheckBoxMenuItem("Estado final", false);
    this.menuEstadoFinal.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (GrafoAutomato.ra$menuEstadoFinal(GrafoAutomato.this).getState()) {
              GrafoAutomato.this.automatoAtual.adicionaEstadoReconhecedor(GrafoAutomato.ra$nomeEstadoEntrada(GrafoAutomato.this));
            } else {
              GrafoAutomato.this.automatoAtual.removeEstadoReconhecedor(GrafoAutomato.ra$nomeEstadoEntrada(GrafoAutomato.this));
            } 
          }
        });
    this.menuRenomear = new JMenuItem("Renomear");
    this.menuRenomear.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            GrafoAutomato.mav$renomeiaEstado(GrafoAutomato.this);
          }
        });
    this.menuExcluir = new JMenuItem("Excluir");
    this.menuExcluir.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            GrafoAutomato.mav$setaNomeEstados(GrafoAutomato.this);
            GrafoAutomato.this.automatoAtual.deletaEstado(GrafoAutomato.ra$nomeEstadoEntrada(GrafoAutomato.this));
          }
        });
    this.menuMouse.add(this.menuEstadoInicial);
    this.menuMouse.add(this.menuEstadoFinal);
    this.menuMouse.add(this.menuRenomear);
    this.menuMouse.add(this.menuExcluir);
  }
  
  private void renomeiaEstado() {
    String novoNome = JOptionPane.showInputDialog(null, "Informe o novo nome:", "Renomeando estado", 3);
    if (novoNome == null || novoNome.length() == 0)
      return; 
    this.automatoAtual.renomeiaEstado(this.nomeEstadoEntrada, novoNome);
  }
  
  public void setaAutomato(Automato pai) {
    this.automatoAtual = pai;
  }
  
  public void setEstadoInicial(String nomeEstado) {
    PClip bolaEstado = null;
    if (this.setaEstadoInicial == null) {
      this.setaEstadoInicial = new PLine();
      this.setaEstadoInicial.addPoint(0, 1.0D, 1.0D);
      this.setaEstadoInicial.addPoint(1, 2.0D, 2.0D);
      this.setaEstadoInicial.addPoint(2, 3.0D, 3.0D);
      this.setaEstadoInicial.addPoint(3, 4.0D, 4.0D);
      this.canvasAutomato.getLayer().addChild((PNode)this.setaEstadoInicial);
    } 
    for (int i = 0; i < this.vetorEstados.size(); i++) {
      EstadoGrafico eg = this.vetorEstados.get(i);
      if (eg.getNome().equals(nomeEstado)) {
        bolaEstado = eg.getBola();
        break;
      } 
    } 
    this.bolaEstadoInicial = bolaEstado;
    atualizaSetaEstadoInicial();
  }
  
  public void atualizaSetaEstadoInicial() {
    double meioY = this.bolaEstadoInicial.getY() + this.bolaEstadoInicial.getHeight() / 2.0D;
    this.setaEstadoInicial.setPoint(0, this.bolaEstadoInicial.getX(), meioY);
    this.setaEstadoInicial.setPoint(1, this.bolaEstadoInicial.getX() - this.bolaEstadoInicial.getWidth() / 2.0D, meioY - 10.0D);
    this.setaEstadoInicial.setPoint(2, this.bolaEstadoInicial.getX() - this.bolaEstadoInicial.getWidth() / 2.0D, meioY + 10.0D);
    this.setaEstadoInicial.setPoint(3, this.bolaEstadoInicial.getX(), meioY);
  }
  
  public double getEscalaAtual() {
    return this.escalaAtual;
  }
  
  public void setEscalaAtual(double escala) {
    this.escalaAtual = 1.0D / this.escalaAtual;
    this.canvasAutomato.getCamera().scaleView(this.escalaAtual);
    this.escalaAtual = escala;
    this.canvasAutomato.getCamera().scaleView(this.escalaAtual);
  }
  
  public PCanvas getCanvas() {
    return this.canvasAutomato;
  }
  
  public void reset() {
    for (int i = 0; i < this.vetorEstados.size(); i++) {
      EstadoGrafico eg = this.vetorEstados.get(i);
      eg.getBola().setPaint(Color.CYAN);
      if (eg.getBola().getChildrenCount() > 1)
        eg.getBola().getChild(0).setPaint(Color.CYAN); 
    } 
  }
  
  public void saiDoEstado(String estadoAtual) {
    for (int i = 0; i < this.vetorEstados.size(); i++) {
      EstadoGrafico eg = this.vetorEstados.get(i);
      if (eg.getNome().equals(estadoAtual)) {
        eg.getBola().setPaint(Color.CYAN);
        if (eg.getBola().getChildrenCount() > 1)
          eg.getBola().getChild(0).setPaint(Color.CYAN); 
      } 
    } 
  }
  
  public void entraNoEstado(String estadoAtual) {
    for (int i = 0; i < this.vetorEstados.size(); i++) {
      EstadoGrafico eg = this.vetorEstados.get(i);
      if (eg.getNome().equals(estadoAtual)) {
        eg.getBola().setPaint(Color.RED);
        if (eg.getBola().getChildrenCount() > 1)
          eg.getBola().getChild(0).setPaint(Color.RED); 
      } 
    } 
  }
  
  public String criaEstado(double x, double y) {
    return criaEstadoNXY(null, x, y, true);
  }
  
  public void criaEstado(String nome, double x, double y) {
    criaEstadoNXY(nome, x, y, true);
  }
  
  public void criaEstado(String nome, double x, double y, boolean posicionamentoXYCentral) {
    criaEstadoNXY(nome, x, y, posicionamentoXYCentral);
  }
  
  private String criaEstadoNXY(String nomeNovoEstado, double x, double y, boolean posicionamentoXYCentral) {
    PText pText;
    double d1, d2;
    PClip estado = new PClip();
    String nomeEstadoCriado = null;
    if (nomeNovoEstado == null) {
      boolean existe = true;
      int cont = 0;
      while (existe) {
        nomeEstadoCriado = "q" + cont;
        if (!this.automatoAtual.existeEstado(nomeEstadoCriado)) {
          existe = false;
          continue;
        } 
        cont++;
      } 
      pText = new PText(nomeEstadoCriado);
    } else {
      pText = new PText(nomeNovoEstado);
    } 
    estado.setPathToEllipse((float)x, (float)y, this.LARGURA_ESTADO, this.ALTURA_ESTADO);
    if (posicionamentoXYCentral) {
      d1 = x - (int)(estado.getWidth() / 2.0D);
      d2 = y - (int)(estado.getHeight() / 2.0D);
    } else {
      d1 = x;
      d2 = y;
    } 
    estado.setBounds(d1, d2, estado.getWidth(), estado.getHeight());
    estado.addChild((PNode)pText);
    estado.setPaint(Color.CYAN);
    double leftTexto = d1 + estado.getWidth() / 2.0D - pText.getWidth() / 2.0D;
    double topTexto = d2 + estado.getHeight() / 2.0D - pText.getHeight() / 2.0D;
    pText.setBounds(leftTexto, topTexto, pText.getWidth(), pText.getHeight());
    estado.addInputEventListener((PInputEventListener)preparaEventoEstado(estado));
    this.objetoAgrupador.addChild((PNode)estado);
    this.objSelecionaveis.add(this.objetoAgrupador);
    this.vetorEstados.add(new EstadoGrafico(estado, pText.getText()));
    return nomeEstadoCriado;
  }
  
  private PBasicInputEventHandler preparaEventoEstado(PClip estadoAtual) {
    return new PBasicInputEventHandler(estadoAtual) {
        private int botaoMousePressionado;
        
        public void mouseDragged(PInputEvent ev) {
          if (this.botaoMousePressionado == 1)
            if (GrafoAutomato.this.automatoAtual.getSituacao() == 0) {
              this.v$estadoAtual.setBounds(ev.getPosition().getX() - GrafoAutomato.ra$mouseX(GrafoAutomato.this), ev.getPosition().getY() - GrafoAutomato.ra$mouseY(GrafoAutomato.this), this.v$estadoAtual.getWidth(), this.v$estadoAtual.getHeight());
              if (this.v$estadoAtual == GrafoAutomato.this.bolaEstadoInicial)
                GrafoAutomato.this.atualizaSetaEstadoInicial(); 
              GrafoAutomato.this.posicionaNomeEstado((PNode)this.v$estadoAtual);
              if (this.v$estadoAtual.getChildrenCount() == 2)
                GrafoAutomato.this.posicionaBolaInterna((PNode)this.v$estadoAtual); 
              if (GrafoAutomato.this.estadoSaida == null)
                GrafoAutomato.this.estadoSaida = GrafoAutomato.this.estadoEntrada; 
              GrafoAutomato.this.atualizaLinhas();
              GrafoAutomato.this.automatoAtual.setStatus(1);
            } else if (GrafoAutomato.this.linhaDesenhaTransicao != null) {
              double d1, d2, posXAtualLinha = ev.getPosition().getX();
              double posYAtualLinha = ev.getPosition().getY();
              if (posXAtualLinha >= GrafoAutomato.this.estadoEntrada.getX()) {
                d1 = posXAtualLinha - 2.0D;
              } else {
                d1 = posXAtualLinha + 2.0D;
              } 
              if (posYAtualLinha >= GrafoAutomato.this.estadoEntrada.getY()) {
                d2 = posYAtualLinha - 2.0D;
              } else {
                d2 = posYAtualLinha + 2.0D;
              } 
              GrafoAutomato.this.linhaDesenhaTransicao.setPoint(1, d1, d2);
            }  
        }
        
        public void mouseReleased(PInputEvent ev) {
          if (GrafoAutomato.this.estadoSaida == null && !GrafoAutomato.ra$saiuDaBola(GrafoAutomato.this)) {
            GrafoAutomato.this.estadoSaida = ev.getPickedNode();
            if (GrafoAutomato.this.estadoSaida.getChildrenCount() == 0)
              GrafoAutomato.this.estadoSaida = GrafoAutomato.this.estadoSaida.getParent(); 
          } 
          if (GrafoAutomato.this.automatoAtual.getSituacao() == 2 && this.botaoMousePressionado == 1) {
            GrafoAutomato.ra$canvasAutomato(GrafoAutomato.this).getLayer().removeChild((PNode)GrafoAutomato.this.linhaDesenhaTransicao);
            GrafoAutomato.this.linhaDesenhaTransicao = null;
            if (this.botaoMousePressionado == 1)
              if (GrafoAutomato.this.estadoSaida != null) {
                GrafoAutomato.mav$setaNomeEstados(GrafoAutomato.this);
                GrafoAutomato.wa$valorEntrada(GrafoAutomato.this, JOptionPane.showInputDialog(null, "Informe a entrada:", "Criando transi, 3));
                String nomeEnt = "", valEnt = "", nomeSai = "";
                boolean transicaoJaCriada = false;
                if (GrafoAutomato.ra$valorEntrada(GrafoAutomato.this) != null) {
                  if (GrafoAutomato.ra$valorEntrada(GrafoAutomato.this).length() == 0) {
                    GrafoAutomato.wa$valorEntrada(GrafoAutomato.this, Constantes.STRING_VAZIA);
                  } else if (GrafoAutomato.ra$valorEntrada(GrafoAutomato.this).length() > 1) {
                    GrafoAutomato.wa$valorEntrada(GrafoAutomato.this, GrafoAutomato.this.trataTipoValorEntrada(GrafoAutomato.ra$valorEntrada(GrafoAutomato.this)));
                    boolean criarEmSerie = (JOptionPane.showOptionDialog(null, "Deseja criar transiem spara os caracteres?", "Confirma, 0, 3, null, null, "Sim") == 0);
                    if (criarEmSerie) {
                      GrafoAutomato.mav$criaTransicaoEmSerie(GrafoAutomato.this);
                      transicaoJaCriada = true;
                    } else {
                      nomeEnt = GrafoAutomato.ra$nomeEstadoEntrada(GrafoAutomato.this);
                      valEnt = GrafoAutomato.ra$valorEntrada(GrafoAutomato.this);
                      nomeSai = GrafoAutomato.ra$nomeEstadoSaida(GrafoAutomato.this);
                      for (int c = 0; c < GrafoAutomato.ra$valorEntrada(GrafoAutomato.this).length(); c++) {
                        GrafoAutomato.this.automatoAtual.criaTransicao(GrafoAutomato.ra$nomeEstadoEntrada(GrafoAutomato.this), GrafoAutomato.ra$valorEntrada(GrafoAutomato.this).charAt(c) + "", GrafoAutomato.ra$nomeEstadoSaida(GrafoAutomato.this));
                        GrafoAutomato.wa$nomeEstadoEntrada(GrafoAutomato.this, nomeEnt);
                        GrafoAutomato.wa$valorEntrada(GrafoAutomato.this, valEnt);
                        GrafoAutomato.wa$nomeEstadoSaida(GrafoAutomato.this, nomeSai);
                      } 
                      transicaoJaCriada = true;
                    } 
                  } 
                } else {
                  return;
                } 
                if (!transicaoJaCriada) {
                  GrafoAutomato.this.automatoAtual.criaTransicao(GrafoAutomato.ra$nomeEstadoEntrada(GrafoAutomato.this), GrafoAutomato.ra$valorEntrada(GrafoAutomato.this), GrafoAutomato.ra$nomeEstadoSaida(GrafoAutomato.this));
                  GrafoAutomato.wa$nomeEstadoEntrada(GrafoAutomato.this, nomeEnt);
                  GrafoAutomato.wa$valorEntrada(GrafoAutomato.this, valEnt);
                  GrafoAutomato.wa$nomeEstadoSaida(GrafoAutomato.this, nomeSai);
                } 
                GrafoAutomato.this.criandoTransicao = false;
              }  
          } else if (GrafoAutomato.this.automatoAtual.getSituacao() == 0 || GrafoAutomato.this.automatoAtual.getSituacao() == 1) {
            if (ev.getButton() == 3) {
              GrafoAutomato.mav$setaNomeEstados(GrafoAutomato.this);
              if (GrafoAutomato.this.automatoAtual.isEstadoReconhecedor(GrafoAutomato.ra$nomeEstadoEntrada(GrafoAutomato.this))) {
                GrafoAutomato.ra$menuEstadoFinal(GrafoAutomato.this).setState(true);
              } else {
                GrafoAutomato.ra$menuEstadoFinal(GrafoAutomato.this).setState(false);
              } 
              if (GrafoAutomato.this.automatoAtual.isEstadoInicial(GrafoAutomato.ra$nomeEstadoEntrada(GrafoAutomato.this))) {
                GrafoAutomato.ra$menuEstadoInicial(GrafoAutomato.this).setState(true);
              } else {
                GrafoAutomato.ra$menuEstadoInicial(GrafoAutomato.this).setState(false);
              } 
              int xDeslocBola = 0;
              int yDeslocBola = 0;
              GrafoAutomato.ra$menuMouse(GrafoAutomato.this).show((JComponent)ev.getComponent(), (int)ev.getCanvasPosition().getX() - xDeslocBola, (int)ev.getCanvasPosition().getY() - yDeslocBola);
            } 
          } 
          if (GrafoAutomato.this.estadoSaida != null && GrafoAutomato.this.estadoEntrada != null && this.botaoMousePressionado == 1)
            GrafoAutomato.this.atualizaLinhas(); 
        }
        
        public void mousePressed(PInputEvent ev) {
          GrafoAutomato.this.estadoEntrada = ev.getPickedNode();
          if (GrafoAutomato.this.estadoEntrada.getChildrenCount() == 0)
            GrafoAutomato.this.estadoEntrada = GrafoAutomato.this.estadoEntrada.getParent(); 
          this.botaoMousePressionado = ev.getButton();
          if (this.botaoMousePressionado != 1) {
            if (GrafoAutomato.this.linhaDesenhaTransicao != null) {
              GrafoAutomato.ra$canvasAutomato(GrafoAutomato.this).getLayer().removeChild((PNode)GrafoAutomato.this.linhaDesenhaTransicao);
              GrafoAutomato.this.linhaDesenhaTransicao = null;
            } 
            return;
          } 
          GrafoAutomato.wa$mouseX(GrafoAutomato.this, ev.getPosition().getX() - this.v$estadoAtual.getX());
          GrafoAutomato.wa$mouseY(GrafoAutomato.this, ev.getPosition().getY() - this.v$estadoAtual.getY());
          if (GrafoAutomato.this.automatoAtual.getSituacao() == 0) {
            if (ev.getClickCount() == 2)
              GrafoAutomato.mav$renomeiaEstado(GrafoAutomato.this); 
          } else if (GrafoAutomato.this.automatoAtual.getSituacao() == 2) {
            GrafoAutomato.this.criandoTransicao = true;
            GrafoAutomato.this.linhaDesenhaTransicao = new PLine();
            GrafoAutomato.this.linhaDesenhaTransicao.addPoint(0, ev.getPosition().getX() - 1.0D, ev.getPosition().getY() - 1.0D);
            GrafoAutomato.this.linhaDesenhaTransicao.addPoint(1, ev.getPosition().getX() - 1.0D, ev.getPosition().getY() - 1.0D);
            GrafoAutomato.ra$canvasAutomato(GrafoAutomato.this).getLayer().addChild((PNode)GrafoAutomato.this.linhaDesenhaTransicao);
          } 
        }
        
        public void mouseEntered(PInputEvent ev) {
          if (!ev.getPickedNode().getClass().toString().equals("PLine")) {
            GrafoAutomato.wa$saiuDaBola(GrafoAutomato.this, false);
            GrafoAutomato.this.estadoSaida = ev.getPickedNode();
            if (GrafoAutomato.this.estadoSaida.getChildrenCount() == 0)
              GrafoAutomato.this.estadoSaida = GrafoAutomato.this.estadoSaida.getParent(); 
          } 
        }
        
        public void mouseExited(PInputEvent ev) {
          if (ev.getPickedNode().getChildrenCount() > 0)
            GrafoAutomato.wa$saiuDaBola(GrafoAutomato.this, true); 
        }
        
        public void mouseClicked(PInputEvent ev) {
          if (this.botaoMousePressionado != 1)
            return; 
          if (GrafoAutomato.this.automatoAtual.getSituacao() == 3)
            GrafoAutomato.this.automatoAtual.deletaEstado(GrafoAutomato.ra$nomeEstadoEntrada(GrafoAutomato.this)); 
          if (GrafoAutomato.this.automatoAtual.getSituacao() == 2) {
            if (GrafoAutomato.this.linhaDesenhaTransicao != null) {
              GrafoAutomato.ra$canvasAutomato(GrafoAutomato.this).getLayer().removeChild((PNode)GrafoAutomato.this.linhaDesenhaTransicao);
              GrafoAutomato.this.linhaDesenhaTransicao = null;
            } 
            GrafoAutomato.this.estadoSaida = ev.getPickedNode();
            if (GrafoAutomato.this.estadoSaida != null && this.botaoMousePressionado == 1) {
              GrafoAutomato.mav$setaNomeEstados(GrafoAutomato.this);
              GrafoAutomato.wa$valorEntrada(GrafoAutomato.this, JOptionPane.showInputDialog(null, "Informe a entrada:", "Criando transi", 3));
              if (GrafoAutomato.ra$valorEntrada(GrafoAutomato.this) != null) {
                if (GrafoAutomato.ra$valorEntrada(GrafoAutomato.this).length() == 0) {
                  GrafoAutomato.wa$valorEntrada(GrafoAutomato.this, Constantes.STRING_VAZIA);
                } else if (GrafoAutomato.ra$valorEntrada(GrafoAutomato.this).length() > 1) {
                  GrafoAutomato.wa$valorEntrada(GrafoAutomato.this, "" + GrafoAutomato.ra$valorEntrada(GrafoAutomato.this).charAt(0));
                } 
              } else {
                return;
              } 
              GrafoAutomato.this.automatoAtual.criaTransicao(GrafoAutomato.ra$nomeEstadoEntrada(GrafoAutomato.this), GrafoAutomato.ra$valorEntrada(GrafoAutomato.this), GrafoAutomato.ra$nomeEstadoSaida(GrafoAutomato.this));
              GrafoAutomato.this.criandoTransicao = false;
            } 
          } 
        }
      };
  }
  
  protected String trataTipoValorEntrada(String valorEntrada) {
    if (valorEntrada.equals("a..z")) {
      valorEntrada = "";
      for (int i = 97; i < 123; i++)
        valorEntrada = valorEntrada + (char)i + ""; 
    } else if (valorEntrada.equals("A..Z")) {
      valorEntrada = "";
      for (int i = 65; i < 91; i++)
        valorEntrada = valorEntrada + (char)i + ""; 
    } else if (valorEntrada.equals("0..9")) {
      valorEntrada = "";
      for (int i = 48; i < 58; i++)
        valorEntrada = valorEntrada + (char)i + ""; 
    } 
    return valorEntrada;
  }
  
  private void criaTransicaoEmSerie() {
    String nomeEnt = this.nomeEstadoEntrada;
    String valEnt = this.valorEntrada;
    String nomeSai = this.nomeEstadoSaida;
    String nomeCriadoAnterior = null;
    String nomeCriadoAtual = null;
    for (int ne = 0; ne < this.valorEntrada.length() - 1; ne++) {
      nomeCriadoAtual = this.automatoAtual.criaEstado((20 + this.LARGURA_ESTADO * ne), (20 + this.ALTURA_ESTADO * ne));
      if (ne == 0) {
        this.automatoAtual.criaTransicao(this.nomeEstadoEntrada, this.valorEntrada.charAt(ne) + "", nomeCriadoAtual);
        this.nomeEstadoEntrada = nomeEnt;
        this.valorEntrada = valEnt;
        this.nomeEstadoSaida = nomeSai;
      } else {
        this.automatoAtual.criaTransicao(nomeCriadoAnterior, this.valorEntrada.charAt(ne) + "", nomeCriadoAtual);
        this.nomeEstadoEntrada = nomeEnt;
        this.valorEntrada = valEnt;
        this.nomeEstadoSaida = nomeSai;
      } 
      nomeCriadoAnterior = nomeCriadoAtual;
    } 
    this.automatoAtual.criaTransicao(nomeCriadoAtual, this.valorEntrada.charAt(this.valorEntrada.length() - 1) + "", this.nomeEstadoSaida);
    this.nomeEstadoEntrada = nomeEnt;
    this.valorEntrada = valEnt;
    this.nomeEstadoSaida = nomeSai;
  }
  
  public void posicionaNomeEstado(PNode estadoAtual) {
    PText nomeEstado = (PText)estadoAtual.getChild(0);
    double leftTexto = estadoAtual.getX() + estadoAtual.getWidth() / 2.0D - nomeEstado.getWidth() / 2.0D;
    double topTexto = estadoAtual.getY() + estadoAtual.getHeight() / 2.0D - nomeEstado.getHeight() / 2.0D;
    nomeEstado.setBounds(leftTexto, topTexto, nomeEstado.getWidth(), nomeEstado.getHeight());
  }
  
  public void posicionaBolaInterna(PNode estadoAtual) {
    PNode bolaInterna = estadoAtual.getChild(1);
    bolaInterna.setBounds((float)(estadoAtual.getX() + estadoAtual.getXOffset() + (float)(this.LARGURA_ESTADO * 0.1D)), (float)(estadoAtual.getY() + estadoAtual.getYOffset() + this.ALTURA_ESTADO * 0.1D), (float)(this.LARGURA_ESTADO * 0.8D), (float)(this.ALTURA_ESTADO * 0.8D));
  }
  
  public void renomeiaEstado(String nomeAntigo, String novoNome) {
    for (int i = 0; i < this.vetorEstados.size(); i++) {
      EstadoGrafico eg = this.vetorEstados.get(i);
      if (eg.getNome().equals(nomeAntigo)) {
        eg.setNome(novoNome);
        this.vetorEstados.set(i, eg);
        break;
      } 
    } 
    for (int j = 0; j < this.vetorEntradasGraficas.size(); j++) {
      EntradaGrafica en = this.vetorEntradasGraficas.get(j);
      if (en.getNomeEstadoEntrada().equals(nomeAntigo))
        en.setNomeEstadoEntrada(novoNome); 
      if (en.getNomeEstadoSaida().equals(nomeAntigo))
        en.setNomeEstadoSaida(novoNome); 
      this.vetorEntradasGraficas.set(j, en);
    } 
  }
  
  public void criaTransicao(String estadoEnt, String valorEnt, String estadoFin) {
    for (int i = 0; i < this.vetorEstados.size(); i++) {
      EstadoGrafico est = this.vetorEstados.get(i);
      if (est.getNome().equals(estadoEnt))
        this.estadoEntrada = (PNode)est.getBola(); 
      if (est.getNome().equals(estadoFin))
        this.estadoSaida = (PNode)est.getBola(); 
    } 
    this.valorEntrada = valorEnt;
    EntradaGrafica entBusca = null;
    int nrLinhasIguais = 0;
    int indEntrada = 0;
    while (indEntrada < this.vetorEntradasGraficas.size()) {
      entBusca = this.vetorEntradasGraficas.get(indEntrada);
      PNode bola1 = entBusca.estadoEntrada;
      PNode bola2 = entBusca.estadoSaida;
      PNode filho1 = bola1.getChild(0);
      PNode filho2 = bola2.getChild(0);
      PText nm1 = (PText)filho1;
      PText nm2 = (PText)filho2;
      String nome1 = nm1.getText();
      String nome2 = nm2.getText();
      if (nome1.equals(estadoEnt) && nome2.equals(estadoFin))
        nrLinhasIguais++; 
      indEntrada++;
    } 
    int posicaoLinha = 1;
    if (existeLinhaVolta(estadoEnt, estadoFin))
      posicaoLinha = 2; 
    if (estadoEnt.equals(estadoFin)) {
      if (nrLinhasIguais == 0) {
        float larg = (this.ALTURA_ESTADO / 2);
        float alt = (this.ALTURA_ESTADO + 10);
        Arc2D.Float tstArco = new Arc2D.Float((float)this.estadoEntrada.getX() + 10.0F, (float)this.estadoEntrada.getY() - 22.0F, larg, alt, 0.0F, 180.0F, 0);
        BasicStroke arcoStroke = new BasicStroke();
        arcoStroke.createStrokedShape(tstArco);
        PPath linhaArco = new PPath(tstArco);
        this.canvasAutomato.getLayer().addChild((PNode)linhaArco);
        double xOrigemSetinha = this.estadoEntrada.getX() + 10.0D;
        double yOrigemSetinha = this.estadoEntrada.getY() + 3.0D;
        PLine linhaCimaSetinha = new PLine();
        linhaCimaSetinha.addPoint(0, xOrigemSetinha, yOrigemSetinha);
        linhaCimaSetinha.addPoint(1, xOrigemSetinha - 6.0D, yOrigemSetinha - 9.0D);
        this.canvasAutomato.getLayer().addChild((PNode)linhaCimaSetinha);
        PLine linhaBaixoSetinha = new PLine();
        linhaBaixoSetinha.addPoint(0, xOrigemSetinha, yOrigemSetinha);
        linhaBaixoSetinha.addPoint(1, xOrigemSetinha + 7.0D, yOrigemSetinha - 9.0D);
        this.canvasAutomato.getLayer().addChild((PNode)linhaBaixoSetinha);
        PText valorGraficoEntrada = new PText(this.valorEntrada);
        double centroX = this.estadoEntrada.getX() + this.estadoEntrada.getWidth() / 2.0D;
        double meioX = centroX - valorGraficoEntrada.getWidth() / 2.0D;
        double distCentroY = this.estadoEntrada.getY() - 40.0D;
        double meioY = distCentroY - nrLinhasIguais * valorGraficoEntrada.getHeight();
        valorGraficoEntrada.setBounds((float)meioX, (float)meioY, valorGraficoEntrada.getWidth(), valorGraficoEntrada.getHeight());
        this.canvasAutomato.getLayer().addChild((PNode)valorGraficoEntrada);
        this.vetorEntradasGraficas.add(new EntradaGrafica(this.estadoEntrada, valorGraficoEntrada, this.estadoSaida, posicaoLinha, nrLinhasIguais + 1, this.automatoAtual));
        this.vetorLinha.add(new LinhaConexao(this.estadoEntrada, linhaArco, linhaCimaSetinha, linhaBaixoSetinha, this.estadoSaida, nrLinhasIguais + 1));
      } else {
        PText valorGraficoEntrada = new PText(this.valorEntrada);
        double centroX = this.estadoEntrada.getX() + this.estadoEntrada.getWidth() / 2.0D;
        double meioX = centroX - valorGraficoEntrada.getWidth() / 2.0D;
        double distCentroY = this.estadoEntrada.getY() - 40.0D;
        double meioY = distCentroY - nrLinhasIguais * valorGraficoEntrada.getHeight();
        valorGraficoEntrada.setBounds((float)meioX, (float)meioY, valorGraficoEntrada.getWidth(), valorGraficoEntrada.getHeight());
        this.canvasAutomato.getLayer().addChild((PNode)valorGraficoEntrada);
        this.vetorEntradasGraficas.add(new EntradaGrafica(this.estadoEntrada, valorGraficoEntrada, this.estadoSaida, posicaoLinha, nrLinhasIguais + 1, this.automatoAtual));
      } 
    } else if (nrLinhasIguais == 0) {
      PLine linhaConexao = new PLine();
      linhaConexao.addPoint(0, (float)(this.estadoEntrada.getX() + this.estadoEntrada.getWidth() / 2.0D), (float)(this.estadoEntrada.getY() + this.estadoEntrada.getHeight() / 2.0D));
      linhaConexao.addPoint(0, (float)(this.estadoSaida.getX() + this.estadoSaida.getWidth() / 2.0D), (float)(this.estadoSaida.getY() + this.estadoSaida.getHeight() / 2.0D));
      this.canvasAutomato.getLayer().addChild((PNode)linhaConexao);
      PLine linhaCimaSetinha = new PLine();
      linhaCimaSetinha.addPoint(0, 0.0D, 0.0D);
      linhaCimaSetinha.addPoint(1, 0.0D, 0.0D);
      this.canvasAutomato.getLayer().addChild((PNode)linhaCimaSetinha);
      PLine linhaBaixoSetinha = new PLine();
      linhaBaixoSetinha.addPoint(0, 0.0D, 0.0D);
      linhaBaixoSetinha.addPoint(1, 0.0D, 0.0D);
      this.canvasAutomato.getLayer().addChild((PNode)linhaBaixoSetinha);
      PText valorGraficoEntrada = new PText(this.valorEntrada);
      double meioX = (this.estadoEntrada.getX() + this.estadoSaida.getX()) / 2.0D;
      double meioY = (this.estadoEntrada.getY() + this.estadoSaida.getY()) / 2.0D;
      valorGraficoEntrada.setBounds((float)meioX, (float)meioY, valorGraficoEntrada.getWidth(), valorGraficoEntrada.getHeight());
      this.canvasAutomato.getLayer().addChild((PNode)valorGraficoEntrada);
      this.vetorEntradasGraficas.add(new EntradaGrafica(this.estadoEntrada, valorGraficoEntrada, this.estadoSaida, posicaoLinha, nrLinhasIguais + 1, this.automatoAtual));
      this.vetorLinha.add(new LinhaConexao(this.estadoEntrada, linhaConexao, linhaCimaSetinha, linhaBaixoSetinha, this.estadoSaida, posicaoLinha, nrLinhasIguais + 1));
    } else {
      PText valorGraficoEntrada = new PText(this.valorEntrada);
      double meioX = (this.estadoEntrada.getX() + this.estadoSaida.getX()) / 2.0D;
      double meioY = (this.estadoEntrada.getY() + this.estadoSaida.getY()) / 2.0D;
      valorGraficoEntrada.setBounds((float)meioX, (float)meioY, valorGraficoEntrada.getWidth(), valorGraficoEntrada.getHeight());
      this.canvasAutomato.getLayer().addChild((PNode)valorGraficoEntrada);
      this.vetorEntradasGraficas.add(new EntradaGrafica(this.estadoEntrada, valorGraficoEntrada, this.estadoSaida, posicaoLinha, nrLinhasIguais + 1, this.automatoAtual));
    } 
    atualizaLinhas();
    PNode nodeAux = this.estadoEntrada;
    this.estadoEntrada = this.estadoSaida;
    this.estadoSaida = nodeAux;
    atualizaLinhas();
    this.estadoEntrada = null;
    this.estadoSaida = null;
  }
  
  private boolean existeLinhaVolta(String nomeEstadoEntrada, String nomeEstadoSaida) {
    ArrayList<Transicao> transicoes = this.automatoAtual.getMatrizTransicoes().getTransicoes();
    for (int p = 0; p < transicoes.size(); p++) {
      Transicao t = transicoes.get(p);
      if (t.getEstadoEntrada().equals(nomeEstadoSaida) && 
        t.getEstadoSaida().equals(nomeEstadoEntrada))
        return true; 
    } 
    return false;
  }
  
  public void deletaTransicao(String estadoEnt, String valorEnt, String estadoFin) {
    int seqCriacaoDeletar = -1;
    EntradaGrafica entBusca = null;
    for (int i = 0; i < this.vetorEntradasGraficas.size(); i++) {
      entBusca = this.vetorEntradasGraficas.get(i);
      String nomeEstado1 = ((PText)entBusca.estadoEntrada.getChild(0)).getText();
      String nomeEstado2 = ((PText)entBusca.estadoSaida.getChild(0)).getText();
      String nomeEntrada = entBusca.valorGraficoEntrada.getText();
      if (nomeEstado1.equals(estadoEnt) && nomeEntrada.equals(valorEnt) && nomeEstado2.equals(estadoFin)) {
        seqCriacaoDeletar = entBusca.seqCriacao;
        deletaEntradaGraficaNoIndice(i);
        break;
      } 
    } 
    entBusca = null;
    boolean atualizarPosicao = false;
    boolean existeAinda = false;
    for (int j = 0; j < this.vetorEntradasGraficas.size(); j++) {
      entBusca = this.vetorEntradasGraficas.get(j);
      String nomeEstado1 = ((PText)entBusca.estadoEntrada.getChild(0)).getText();
      String nomeEstado2 = ((PText)entBusca.estadoSaida.getChild(0)).getText();
      if (nomeEstado1.equals(estadoEnt) && nomeEstado2.equals(estadoFin)) {
        if (entBusca.seqCriacao > seqCriacaoDeletar) {
          entBusca.seqCriacao--;
          this.vetorEntradasGraficas.set(j, entBusca);
          atualizarPosicao = true;
        } 
        existeAinda = true;
      } 
    } 
    if (atualizarPosicao) {
      for (int k = 0; k < this.vetorEstados.size(); k++) {
        EstadoGrafico eg = this.vetorEstados.get(k);
        if (eg.getNome().equals(estadoEnt))
          this.estadoEntrada = (PNode)eg.getBola(); 
        if (eg.getNome().equals(estadoFin))
          this.estadoSaida = (PNode)eg.getBola(); 
      } 
      atualizaLinhas();
    } 
    if (!existeAinda) {
      LinhaConexao lnBusca = null;
      for (int k = 0; k < this.vetorLinha.size(); k++) {
        lnBusca = this.vetorLinha.get(k);
        String nomeEstado1 = ((PText)lnBusca.estadoEntrada.getChild(0)).getText();
        String nomeEstado2 = ((PText)lnBusca.estadoSaida.getChild(0)).getText();
        if (nomeEstado1.equals(estadoEnt) && nomeEstado2.equals(estadoFin)) {
          deletaLinhaConexaoNoIndice(k);
          break;
        } 
      } 
    } 
  }
  
  public void alteraTransicao(String estadoEnt, String valorEnt, String estadoFin, String valorEntNovo) {
    EntradaGrafica entBusca = null;
    EntradaGrafica entAtual = null;
    EntradaGrafica entNova = null;
    int indEntAtual = -1;
    for (int i = 0; i < this.vetorEntradasGraficas.size() && (entAtual == null || entNova == null); i++) {
      entBusca = this.vetorEntradasGraficas.get(i);
      String nomeEstado1 = ((PText)entBusca.estadoEntrada.getChild(0)).getText();
      String nomeEstado2 = ((PText)entBusca.estadoSaida.getChild(0)).getText();
      String nomeEntrada = entBusca.valorGraficoEntrada.getText();
      if (nomeEstado1.equals(estadoEnt) && nomeEntrada.equals(valorEnt) && nomeEstado2.equals(estadoFin)) {
        entAtual = entBusca;
        indEntAtual = i;
      } 
      if (nomeEstado1.equals(estadoEnt) && nomeEntrada.equals(valorEntNovo) && nomeEstado2.equals(estadoFin))
        entNova = entBusca; 
    } 
    if (entAtual != null)
      if (entNova == null) {
        entAtual.valorGraficoEntrada.setText(valorEntNovo);
        entAtual.setNomeValorEntrada(valorEntNovo);
        this.vetorEntradasGraficas.set(indEntAtual, entAtual);
      } else {
        deletaTransicao(estadoEnt, valorEnt, estadoFin);
      }  
  }
  
  private void deletaEntradaGraficaNoIndice(int indice) {
    EntradaGrafica entBusca = this.vetorEntradasGraficas.get(indice);
    if (entBusca != null) {
      this.canvasAutomato.getLayer().removeChild((PNode)entBusca.valorGraficoEntrada);
      this.vetorEntradasGraficas.remove(indice);
    } 
  }
  
  private void deletaLinhaConexaoNoIndice(int indice) {
    LinhaConexao lnBusca = this.vetorLinha.get(indice);
    if (lnBusca != null) {
      this.canvasAutomato.getLayer().removeChild((PNode)lnBusca.linhaCimaSetinha);
      this.canvasAutomato.getLayer().removeChild((PNode)lnBusca.linhaBaixoSetinha);
      if (lnBusca.linha != null)
        this.canvasAutomato.getLayer().removeChild((PNode)lnBusca.linha); 
      if (lnBusca.arco != null)
        this.canvasAutomato.getLayer().removeChild((PNode)lnBusca.arco); 
      this.vetorLinha.remove(indice);
    } 
  }
  
  public void setEstadoReconhecedor(String nome) {
    for (int i = 0; i < this.vetorEstados.size(); i++) {
      EstadoGrafico estGrafAtual = this.vetorEstados.get(i);
      if (estGrafAtual.getNome().equals(nome)) {
        PClip bolaInterna = new PClip();
        PClip bolaAtual = estGrafAtual.getBola();
        bolaInterna.setPathToEllipse((float)(bolaAtual.getX() + (float)(this.LARGURA_ESTADO * 0.1D)), (float)bolaAtual.getY() + (float)(this.ALTURA_ESTADO * 0.1D), (float)(this.LARGURA_ESTADO * 0.8D), (float)(this.ALTURA_ESTADO * 0.8D));
        bolaAtual.addChild((PNode)bolaInterna);
      } 
    } 
  }
  
  public void removeEstadoReconhecedor(String nome) {
    for (int i = 0; i < this.vetorEstados.size(); i++) {
      EstadoGrafico estGrafAtual = this.vetorEstados.get(i);
      if (estGrafAtual.getNome().equals(nome)) {
        PClip bolaInterna = new PClip();
        PClip bolaAtual = estGrafAtual.getBola();
        bolaAtual.removeChild(1);
      } 
    } 
  }
  
  private void setaNomeEstados() {
    PNode filhoEntrada = this.estadoEntrada.getChild(0);
    PText nmEnt = (PText)filhoEntrada;
    this.nomeEstadoEntrada = nmEnt.getText();
    if (this.estadoSaida != null) {
      PNode filhoSaida = this.estadoSaida.getChild(0);
      PText nmSai = (PText)filhoSaida;
      this.nomeEstadoSaida = nmSai.getText();
    } 
  }
  
  public void atualizaLinhas() {
    boolean duploSentido = false;
    setaNomeEstados();
    for (int i = 0; i < this.vetorLinha.size(); i++) {
      LinhaConexao ln = this.vetorLinha.get(i);
      PLine linha = ln.linha;
      PLine linhaCimaSeta = ln.linhaCimaSetinha;
      PLine linhaBaixoSeta = ln.linhaBaixoSetinha;
      PNode bola1 = ln.estadoEntrada;
      PNode bola2 = ln.estadoSaida;
      int posicaoLinha = ln.posicaoLinha;
      int seqCriacao = ln.seqCriacao;
      PNode filho1 = bola1.getChild(0);
      PNode filho2 = bola2.getChild(0);
      PText nm1 = (PText)filho1;
      PText nm2 = (PText)filho2;
      String nome1 = nm1.getText();
      String nome2 = nm2.getText();
      if (nome1.equals(this.nomeEstadoEntrada) || nome2.equals(this.nomeEstadoSaida)) {
        PNode bolaEnt = bola1;
        PNode bolaSai = bola2;
        if (nome1.equals(this.nomeEstadoEntrada))
          bolaEnt = this.estadoEntrada; 
        if (nome2.equals(this.nomeEstadoSaida))
          bolaSai = this.estadoSaida; 
        if (bolaEnt != bolaSai) {
          if (bola1.getX() == bola2.getX() && bola1.getY() == bola2.getY())
            return; 
          double h = 0.0D, coseno = 0.0D, seno = 0.0D, angulo_princ = 0.0D;
          double deslocamentoTextoY = 0.0D;
          double x1 = bolaEnt.getX() + bolaEnt.getWidth() / 2.0D;
          double y1 = bolaEnt.getY() + bolaEnt.getHeight() / 2.0D;
          double x2 = bolaSai.getX() + bolaSai.getWidth() / 2.0D;
          double y2 = bolaSai.getY() + bolaSai.getHeight() / 2.0D;
          double r = bolaEnt.getWidth() / 2.0D;
          for (int n = 0; n < 2; n++) {
            double catadj = x2 - x1;
            double catop = y2 - y1;
            h = Math.sqrt(catop * catop + catadj * catadj);
            coseno = catadj / h;
            seno = catop / h;
            h -= r;
            double h_seta = h - r / 2.0D;
            catop = seno * h;
            catadj = coseno * h;
            double catop_seta = seno * h_seta;
            double catadj_seta = coseno * h_seta;
            if (n == 0) {
              x1 = x2 - catadj;
              y1 = y2 - catop;
              if ((bola1.getX() != bola2.getX() || bola1.getY() != bola2.getY()) && 
                seqCriacao == 1)
                linha.setPoint(0, (float)x1, (float)y1); 
            } else {
              x2 = catadj + x1;
              y2 = catop + y1;
              if (existeTransicaoDuploSentido(nome1, nome2)) {
                double x1p = bolaEnt.getX() + bolaEnt.getWidth() / 2.0D;
                double y1p = bolaEnt.getY() + bolaEnt.getHeight() / 2.0D;
                double x2p = bolaSai.getX() + bolaSai.getWidth() / 2.0D;
                double y2p = bolaSai.getY() + bolaSai.getHeight() / 2.0D;
                double x1dif = x1 - x1p;
                double y1dif = y1 - y1p;
                double x2dif = x2 - x2p;
                double y2dif = y2 - y2p;
                double x1_novo = Math.cos(358.5D) * x1dif + Math.sin(358.5D) * y1dif;
                double y1_novo = -Math.sin(358.5D) * x1dif + Math.cos(358.5D) * y1dif;
                x1 = bolaEnt.getX() + bolaEnt.getWidth() / 2.0D;
                y1 = bolaEnt.getY() + bolaEnt.getHeight() / 2.0D;
                x1 += x1_novo;
                y1 += y1_novo;
                double x2_novo = Math.cos(-358.5D) * x2dif + Math.sin(-358.5D) * y2dif;
                double y2_novo = -Math.sin(-358.5D) * x2dif + Math.cos(-358.5D) * y2dif;
                x2 = bolaSai.getX() + bolaSai.getWidth() / 2.0D;
                y2 = bolaSai.getY() + bolaSai.getHeight() / 2.0D;
                x2 += x2_novo;
                y2 += y2_novo;
                duploSentido = true;
              } else {
                duploSentido = false;
              } 
              double tangente = catop / catadj;
              angulo_princ = Math.toDegrees(Math.atan(tangente));
              double x1_seta = x2;
              double y1_seta = y2;
              double x2_seta = x1 + catadj_seta;
              double y2_seta = y1 + catop_seta;
              double ANGULO = -120.0D;
              double xdif_seta = x2_seta - x1_seta;
              double ydif_seta = y2_seta - y1_seta;
              double x3_seta = Math.cos(ANGULO) * xdif_seta + Math.sin(ANGULO) * ydif_seta;
              double y3_seta = -Math.sin(ANGULO) * xdif_seta + Math.cos(ANGULO) * ydif_seta;
              x3_seta += x1_seta;
              y3_seta += y1_seta;
              ANGULO = 120.0D;
              double x4_seta = Math.cos(ANGULO) * xdif_seta + Math.sin(ANGULO) * ydif_seta;
              double y4_seta = -Math.sin(ANGULO) * xdif_seta + Math.cos(ANGULO) * ydif_seta;
              x4_seta += x1_seta;
              y4_seta += y1_seta;
              if (bola1.getX() != bola2.getX() || bola1.getY() != bola2.getY())
                if (seqCriacao == 1) {
                  linha.setPoint(0, (float)x1, (float)y1);
                  linha.setPoint(1, (float)x2, (float)y2);
                  linhaCimaSeta.setPoint(0, (float)x1_seta, (float)y1_seta);
                  linhaCimaSeta.setPoint(1, (float)x3_seta, (float)y3_seta);
                  linhaBaixoSeta.setPoint(0, (float)x1_seta, (float)y1_seta);
                  linhaBaixoSeta.setPoint(1, (float)x4_seta, (float)y4_seta);
                }  
            } 
          } 
          for (int e = 0; e < this.vetorEntradasGraficas.size(); e++) {
            EntradaGrafica eg = this.vetorEntradasGraficas.get(e);
            bola1 = eg.estadoEntrada;
            bola2 = eg.estadoSaida;
            posicaoLinha = eg.posicaoLinha;
            seqCriacao = eg.seqCriacao;
            if (bola1 == bolaEnt && bola2 == bolaSai) {
              PText valorGrafEnt = eg.valorGraficoEntrada;
              double anguloValorEnt = eg.anguloValorEntrada;
              double xValorEnt = eg.xValorEnt;
              double yValorEnt = eg.yValorEnt;
              if (duploSentido) {
                deslocamentoTextoY = (valorGrafEnt.getHeight() + 2.0D) * seqCriacao + 10.0D;
              } else if (seqCriacao == 1) {
                deslocamentoTextoY = valorGrafEnt.getHeight() + 2.0D;
              } else {
                deslocamentoTextoY = (valorGrafEnt.getHeight() + 2.0D) * seqCriacao;
              } 
              double centro_X = bolaEnt.getX() + bolaEnt.getWidth() / 2.0D;
              double centro_Y = bolaEnt.getY() + bolaEnt.getHeight() / 2.0D;
              double meioX = h / 2.0D + bolaEnt.getX() + bolaEnt.getWidth();
              double meioY = centro_Y - deslocamentoTextoY;
              if (x2 < x1) {
                centro_X = bolaSai.getX() + bolaSai.getWidth() / 2.0D;
                centro_Y = bolaSai.getY() + bolaSai.getHeight() / 2.0D;
                meioX = h / 2.0D + bolaSai.getX() + bolaSai.getWidth();
                if (duploSentido) {
                  deslocamentoTextoY = valorGrafEnt.getHeight() * seqCriacao - 4.0D;
                } else {
                  deslocamentoTextoY = valorGrafEnt.getHeight() * seqCriacao - 10.0D;
                } 
                meioY = centro_Y + deslocamentoTextoY;
              } 
              valorGrafEnt.rotateAboutPoint(Math.toRadians(-anguloValorEnt), xValorEnt, yValorEnt);
              valorGrafEnt.setBounds((float)(meioX - valorGrafEnt.getWidth() / 2.0D), (float)meioY, valorGrafEnt.getWidth(), valorGrafEnt.getHeight());
              valorGrafEnt.rotateAboutPoint(Math.toRadians(angulo_princ), centro_X, centro_Y);
              anguloValorEnt = angulo_princ;
              xValorEnt = centro_X;
              yValorEnt = centro_Y;
              eg.anguloValorEntrada = anguloValorEnt;
              eg.xValorEnt = xValorEnt;
              eg.yValorEnt = yValorEnt;
              this.vetorEntradasGraficas.set(e, eg);
            } 
          } 
        } else {
          if (seqCriacao == 1) {
            PPath linhaArco = ln.arco;
            float larg = (this.ALTURA_ESTADO / 2);
            float alt = (this.ALTURA_ESTADO + 10);
            linhaArco.setX(((float)bolaEnt.getX() + 10.0F));
            linhaArco.setY(((float)bolaEnt.getY() - 22.0F));
            double xOrigemSetinha = bolaEnt.getX() + 10.0D;
            double yOrigemSetinha = bolaEnt.getY() + 3.0D;
            linhaCimaSeta.setPoint(0, xOrigemSetinha, yOrigemSetinha);
            linhaCimaSeta.setPoint(1, xOrigemSetinha - 6.0D, yOrigemSetinha - 9.0D);
            linhaBaixoSeta.setPoint(0, xOrigemSetinha, yOrigemSetinha);
            linhaBaixoSeta.setPoint(1, xOrigemSetinha + 7.0D, yOrigemSetinha - 9.0D);
          } 
          for (int e = 0; e < this.vetorEntradasGraficas.size(); e++) {
            EntradaGrafica eg = this.vetorEntradasGraficas.get(e);
            bola1 = eg.estadoEntrada;
            bola2 = eg.estadoSaida;
            posicaoLinha = eg.posicaoLinha;
            seqCriacao = eg.seqCriacao;
            if (bola1 == bolaEnt && bola2 == bolaSai) {
              PText valorGrafEnt = eg.valorGraficoEntrada;
              double centroX = bolaEnt.getX() + bolaEnt.getWidth() / 2.0D;
              double meioX = centroX - valorGrafEnt.getWidth() / 2.0D;
              double distCentroY = bolaEnt.getY() - 40.0D;
              double meioY = distCentroY - (seqCriacao - 1) * valorGrafEnt.getHeight();
              valorGrafEnt.setX((float)meioX);
              valorGrafEnt.setY((float)meioY);
            } 
          } 
        } 
      } 
    } 
  }
  
  public int getNrEstados() {
    return this.vetorEstados.size();
  }
  
  public ArrayList<String> getEstados() {
    ArrayList<String> tmp = new ArrayList<String>();
    for (int i = 0; i < this.vetorEstados.size(); i++) {
      EstadoGrafico eg = this.vetorEstados.get(i);
      tmp.add(eg.getNome());
    } 
    return tmp;
  }
  
  public void reposicionaEstadosAleatoriamente() {
    for (int i = 0; i < this.vetorEstados.size(); i++) {
      EstadoGrafico eg = this.vetorEstados.get(i);
      double xr = Math.random() * (this.canvasAutomato.getWidth() - this.LARGURA_ESTADO);
      double yr = Math.random() * (this.canvasAutomato.getHeight() - this.ALTURA_ESTADO);
      eg.getBola().setX(xr);
      eg.getBola().setY(yr);
      this.estadoEntrada = (PNode)eg.getBola();
      this.estadoSaida = (PNode)eg.getBola();
      atualizaLinhas();
      posicionaNomeEstado((PNode)eg.getBola());
      if (this.automatoAtual.isEstadoReconhecedor(eg.getNome()))
        posicionaBolaInterna((PNode)eg.getBola()); 
      if (this.automatoAtual.isEstadoInicial(eg.getNome()))
        atualizaSetaEstadoInicial(); 
    } 
    if (this.vetorEstados.size() > 0)
      this.automatoAtual.setStatus(1); 
  }
  
  public void deletaEstado(String nome) {
    boolean existeEstado = true;
    int contEstado = 0;
    if (this.automatoAtual.isEstadoInicial(nome)) {
      this.canvasAutomato.getLayer().removeChild((PNode)this.setaEstadoInicial);
      this.setaEstadoInicial = null;
      this.bolaEstadoInicial = null;
    } 
    while (existeEstado && contEstado < this.vetorEstados.size()) {
      EstadoGrafico eg = this.vetorEstados.get(contEstado);
      if (eg.getNome().equals(nome)) {
        int contLinha = 0;
        while (contLinha < this.vetorLinha.size()) {
          LinhaConexao lc = this.vetorLinha.get(contLinha);
          PText f1 = (PText)lc.estadoEntrada.getChild(0);
          PText f2 = (PText)lc.estadoSaida.getChild(0);
          String nmF1 = f1.getText();
          String nmF2 = f2.getText();
          if (nmF1.equals(nome) || nmF2.equals(nome)) {
            deletaLinhaConexaoNoIndice(contLinha);
            continue;
          } 
          contLinha++;
        } 
        int contEntrada = 0;
        while (contEntrada < this.vetorEntradasGraficas.size()) {
          EntradaGrafica entGraf = this.vetorEntradasGraficas.get(contEntrada);
          PText textoEntrada = entGraf.valorGraficoEntrada;
          PText pText1 = (PText)entGraf.estadoEntrada.getChild(0);
          PText pText2 = (PText)entGraf.estadoSaida.getChild(0);
          String str1 = pText1.getText();
          String str2 = pText2.getText();
          if (str1.equals(nome) || str2.equals(nome)) {
            deletaEntradaGraficaNoIndice(contEntrada);
            continue;
          } 
          contEntrada++;
        } 
        this.objetoAgrupador.removeChild((PNode)eg.getBola());
        this.vetorEstados.remove(contEstado);
        existeEstado = false;
      } 
      contEstado++;
    } 
  }
  
  private boolean existeTransicaoDuploSentido(String nomeEstadoEnt, String nomeEstadoSai) {
    boolean sentido1 = false, sentido2 = false;
    for (int i = 0; i < this.vetorLinha.size(); i++) {
      LinhaConexao ln = this.vetorLinha.get(i);
      PNode bola1 = ln.estadoEntrada;
      PNode bola2 = ln.estadoSaida;
      PNode filho1 = bola1.getChild(0);
      PNode filho2 = bola2.getChild(0);
      PText nm1 = (PText)filho1;
      PText nm2 = (PText)filho2;
      String nome1 = nm1.getText();
      String nome2 = nm2.getText();
      if (nome1.equals(nomeEstadoEnt) && nome2.equals(nomeEstadoSai))
        sentido1 = true; 
      if (nome1.equals(nomeEstadoSai) && nome2.equals(nomeEstadoEnt))
        sentido2 = true; 
      if (sentido1 && sentido2)
        return true; 
    } 
    return false;
  }
  
  public VersionadorListener getVersionador() {
    return this.versionador;
  }
  
  public void setCanvas(PCanvas canvasAutomato) {
    this.canvasAutomato = canvasAutomato;
  }
}
