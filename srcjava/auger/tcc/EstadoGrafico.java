package auger.tcc;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.nodes.PClip;
import java.io.Serializable;

public class EstadoGrafico implements Serializable {
  private PClip bolaEstado;
  
  private String nomeEstado;
  
  private static final long serialVersionUID = 1L;
  
  public EstadoGrafico(PClip estado, String nm) {
    this.bolaEstado = estado;
    this.nomeEstado = nm;
  }
  
  public String getNome() {
    return this.nomeEstado;
  }
  
  public void setNome(String nm) {
    this.nomeEstado = nm;
    PNode filhoNome = this.bolaEstado.getChild(0);
    PText nomeTexto = (PText)filhoNome;
    nomeTexto.setText(nm);
  }
  
  public PClip getBola() {
    return this.bolaEstado;
  }
}
