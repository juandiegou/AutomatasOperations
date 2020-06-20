package auger.tcc;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolox.nodes.PLine;
import java.io.Serializable;

class LinhaConexao implements Serializable {
  public PNode estadoEntrada;
  
  public PNode estadoSaida;
  
  public PLine linha;
  
  public PPath arco;
  
  public PLine linhaCimaSetinha;
  
  public PLine linhaBaixoSetinha;
  
  public int posicaoLinha;
  
  public int seqCriacao;
  
  private static final long serialVersionUID = 1L;
  
  public LinhaConexao(PNode estadoEnt, PLine l, PLine linhaCimaSeta, PLine linhaBaixoSeta, PNode estadoSai, int posLn, int seqLn) {
    this.estadoEntrada = estadoEnt;
    this.estadoSaida = estadoSai;
    this.linha = l;
    this.linhaCimaSetinha = linhaCimaSeta;
    this.linhaBaixoSetinha = linhaBaixoSeta;
    this.posicaoLinha = posLn;
    this.seqCriacao = seqLn;
  }
  
  public LinhaConexao(PNode estadoEnt, PPath p, PLine linhaCimaSeta, PLine linhaBaixoSeta, PNode estadoSai, int seqLn) {
    this.estadoEntrada = estadoEnt;
    this.estadoSaida = estadoSai;
    this.arco = p;
    this.linhaCimaSetinha = linhaCimaSeta;
    this.linhaBaixoSetinha = linhaBaixoSeta;
    this.seqCriacao = seqLn;
  }
}