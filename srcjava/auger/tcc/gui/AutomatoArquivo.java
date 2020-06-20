
package auger.tcc.gui;

import auger.tcc.Automato;
import auger.tcc.EstadoGrafico;
import java.util.ArrayList;

class AutomatoArquivo extends Automato {
  ArrayList posicaoEstados = new ArrayList();
  
  public AutomatoArquivo(Automato atm) {
    this.matrizTransicao = atm.getMatrizTransicoes();
    this.grafoAutomato = atm.getGrafoAutomato();
    this.estadoInicial = atm.getEstadoInicial();
    this.estadosReconhecedores = atm.getEstadosReconhecedores();
    alteraCorFundoDiagramaEstados(atm.getCorFundoDiagramaEstados());
    setSituacao(atm.getSituacao());
    for (int i = 0; i < this.grafoAutomato.vetorEstados.size(); i++) {
      EstadoGrafico estadoAtual = this.grafoAutomato.vetorEstados.get(i);
      Object[] obj = new Object[3];
      obj[0] = estadoAtual.getNome();
      obj[1] = (estadoAtual.getBola().getX() + estadoAtual.getBola().getXOffset()) + "";
      obj[2] = (estadoAtual.getBola().getY() + estadoAtual.getBola().getYOffset()) + "";
      this.posicaoEstados.add(obj);
    } 
  }
}
