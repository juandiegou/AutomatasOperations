package auger.tcc;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventListener;
import edu.umd.cs.piccolox.event.PSelectionEventHandler;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;

class SelecaoGrupo extends PSelectionEventHandler {
  GrafoAutomato grafoChamou;
  
  ArrayList vetorSelecionados;
  
  public SelecaoGrupo(PNode lr, List al, GrafoAutomato ga) {
    super(lr, al);
    this.grafoChamou = ga;
  }
  
  public void decorateSelectedNode(PNode node) {
    super.decorateSelectedNode(node);
    int nrFilhos = node.getChildrenCount();
    for (int i = 0; i < nrFilhos; i++) {
      PNode noFilho = node.getChild(i);
      EventListenerList listaEventos = noFilho.getListenerList();
      if (listaEventos != null) {
        Object[] eventos = listaEventos.getListenerList();
        Object[] arrayOfObject1;
        byte b;
        for (arrayOfObject1 = eventos, b = 0; b < arrayOfObject1.length; ) {
          Object objEvento = arrayOfObject1[b];
          if (objEvento instanceof PInputEventListener)
            noFilho.removeInputEventListener((PInputEventListener)objEvento); 
          b++;
        } 
      } 
    } 
  }
  
  public void startDrag(PInputEvent e) {
    if (this.grafoChamou.automatoAtual.getSituacao() == 0 && 
      e.getButton() == 1 && 
      this.grafoChamou.getVersionador() != null)
      if (e.getPickedNode().getClass().getName().equalsIgnoreCase("edu.umd.cs.piccolox.nodes.PClip") || e.getPickedNode().getClass().getName().equalsIgnoreCase("edu.umd.cs.piccolo.nodes.PText"))
        this.grafoChamou.getVersionador().guardaVersaoAutomato();  
    super.startDrag(e);
  }
  
  public void mouseDragged(PInputEvent e) {
    if (this.grafoChamou.automatoAtual.getSituacao() == 0) {
      super.mouseDragged(e);
      this.vetorSelecionados = (ArrayList)getSelection();
      if (this.vetorSelecionados.size() > 0) {
        int tamVetor = this.vetorSelecionados.size();
        for (int i = 0; i < tamVetor; i++) {
          PNode pn = this.vetorSelecionados.get(i);
          double ox = pn.getXOffset();
          double oy = pn.getYOffset();
          pn.setOffset(0.0D, 0.0D);
          pn.setX(pn.getX() + ox);
          pn.setY(pn.getY() + oy);
          this.grafoChamou.estadoEntrada = pn;
          this.grafoChamou.estadoSaida = pn;
          this.grafoChamou.posicionaNomeEstado(pn);
          if (pn == this.grafoChamou.bolaEstadoInicial)
            this.grafoChamou.atualizaSetaEstadoInicial(); 
          if (pn.getChildrenCount() == 10)
            this.grafoChamou.posicionaBolaInterna(pn); 
          this.grafoChamou.atualizaLinhas();
        } 
      } 
    } 
  }
}
