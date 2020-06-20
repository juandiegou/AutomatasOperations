package auger.tcc;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventListener;
import edu.umd.cs.piccolo.nodes.PText;
import java.io.Serializable;
import javax.swing.JOptionPane;

class EntradaGrafica implements Serializable {
  public PNode estadoEntrada;
  
  public PNode estadoSaida;
  
  public PText valorGraficoEntrada;
  
  public double anguloValorEntrada;
  
  public double xValorEnt;
  
  public double yValorEnt;
  
  public int posicaoLinha;
  
  public int seqCriacao;
  
  public Automato automatoChamou;
  
  private static final long serialVersionUID = 1L;
  
  private String nomeEstadoEntrada;
  
  private String nomeValorEntrada;
  
  private String nomeEstadoSaida;
  
  static String ra$nomeEstadoEntrada(EntradaGrafica paramEntradaGrafica) {
    return paramEntradaGrafica.nomeEstadoEntrada;
  }
  
  static String ra$nomeValorEntrada(EntradaGrafica paramEntradaGrafica) {
    return paramEntradaGrafica.nomeValorEntrada;
  }
  
  static String ra$nomeEstadoSaida(EntradaGrafica paramEntradaGrafica) {
    return paramEntradaGrafica.nomeEstadoSaida;
  }
  
  public EntradaGrafica(PNode estadoEnt, PText valorGrafEnt, PNode estadoSai, int posLn, int seqLn, Automato automatoAtual) {
    this.estadoEntrada = estadoEnt;
    this.estadoSaida = estadoSai;
    this.valorGraficoEntrada = valorGrafEnt;
    this.posicaoLinha = posLn;
    this.seqCriacao = seqLn;
    this.anguloValorEntrada = 0.0D;
    this.xValorEnt = 0.0D;
    this.yValorEnt = 0.0D;
    this.automatoChamou = automatoAtual;
    this.nomeEstadoEntrada = ((PText)this.estadoEntrada.getChild(0)).getText();
    this.nomeValorEntrada = this.valorGraficoEntrada.getText();
    this.nomeEstadoSaida = ((PText)this.estadoSaida.getChild(0)).getText();
    this.valorGraficoEntrada.addInputEventListener((PInputEventListener)new PBasicInputEventHandler() {
          public void mousePressed(PInputEvent ev) {
            if (EntradaGrafica.this.automatoChamou.getSituacao() == 3) {
              EntradaGrafica.this.automatoChamou.deletaTransicao(EntradaGrafica.ra$nomeEstadoEntrada(EntradaGrafica.this), EntradaGrafica.ra$nomeValorEntrada(EntradaGrafica.this), EntradaGrafica.ra$nomeEstadoSaida(EntradaGrafica.this));
            } else if (EntradaGrafica.this.automatoChamou.getSituacao() == 0 && 
              ev.getClickCount() == 2) {
              String nomeValorEntradaNovo = JOptionPane.showInputDialog(null, "Informe a entrada:", "Alterando transi", 3);
              if (nomeValorEntradaNovo != null && 
                nomeValorEntradaNovo.length() > 0) {
                nomeValorEntradaNovo = nomeValorEntradaNovo.charAt(0) + "";
                EntradaGrafica.this.automatoChamou.alteraTransicao(EntradaGrafica.ra$nomeEstadoEntrada(EntradaGrafica.this), EntradaGrafica.ra$nomeValorEntrada(EntradaGrafica.this), EntradaGrafica.ra$nomeEstadoSaida(EntradaGrafica.this), nomeValorEntradaNovo);
              } 
            } 
          }
        });
  }
  
  public String getNomeEstadoEntrada() {
    return this.nomeEstadoEntrada;
  }
  
  public String getNomeValorEntrada() {
    return this.nomeValorEntrada;
  }
  
  public String getNomeEstadoSaida() {
    return this.nomeEstadoSaida;
  }
  
  public void setNomeEstadoEntrada(String nome) {
    this.nomeEstadoEntrada = nome;
  }
  
  public void setNomeEstadoSaida(String nome) {
    this.nomeEstadoSaida = nome;
  }
  
  public void setNomeValorEntrada(String nome) {
    this.nomeValorEntrada = nome;
  }
}
