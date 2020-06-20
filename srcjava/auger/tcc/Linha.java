package auger.tcc;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;

class Linha {
  public PNode org;
  
  public PNode dest;
  
  public PPath linha;
  
  public Linha(PNode e1, PPath l, PNode e2) {
    this.org = e1;
    this.dest = e2;
    this.linha = l;
  }
}
