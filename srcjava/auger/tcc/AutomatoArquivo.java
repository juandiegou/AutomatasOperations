package auger.tcc;

import java.util.ArrayList;

class AutomatoArquivo extends Automato {
  ArrayList posicaoEstados = new ArrayList();
  
  public AutomatoArquivo(Automato atm) {
    this.matrizTransicao = atm.matrizTransicao;
    this.grafoAutomato = atm.grafoAutomato;
    this.estadoInicial = atm.estadoInicial;
    this.estadosReconhecedores = atm.estadosReconhecedores;
    this.grafoAutomato = atm.grafoAutomato;
    for (int i = 0; i < this.grafoAutomato.vetorEstados.size(); i++) {
      EstadoGrafico estadoAtual = this.grafoAutomato.vetorEstados.get(i);
      Object[] obj = new Object[3];
      obj[0] = estadoAtual.getNome();
      obj[1] = String.valueOf(estadoAtual.getBola().getX() + estadoAtual.getBola().getXOffset()) + "";
      obj[2] = String.valueOf(estadoAtual.getBola().getY() + estadoAtual.getBola().getYOffset()) + "";
      this.posicaoEstados.add(obj);
    } 
    zeraGrafo();
  }
  
  public void zeraGrafo() {
    this.grafoAutomato = null;
  }
}
