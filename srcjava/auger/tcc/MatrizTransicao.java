package auger.tcc;

import java.io.Serializable;
import java.util.ArrayList;

public class MatrizTransicao implements Serializable {
  private ArrayList matrizTransicao = new ArrayList();
  
  private static final long serialVersionUID = 1L;
  
  public boolean criaTransicao(String estadoEnt, String valorEnt, String estadoFin) {
    if (procuraTransicao(estadoEnt, valorEnt, estadoFin) == -1) {
      this.matrizTransicao.add(new Transicao(estadoEnt, valorEnt, estadoFin));
      return true;
    } 
    return false;
  }
  
  public void deletaTransicao(String estadoEnt, String valorEnt, String estadoFin) {
    int indDeletar = procuraTransicao(estadoEnt, valorEnt, estadoFin);
    if (indDeletar != -1)
      this.matrizTransicao.remove(indDeletar); 
  }
  
  public void alteraTransicao(String estadoEnt, String valorEntAtual, String estadoFin, String valorEntNovo) {
    int indAlterar = procuraTransicao(estadoEnt, valorEntAtual, estadoFin);
    if (indAlterar != -1) {
      int indNova = procuraTransicao(estadoEnt, valorEntNovo, estadoFin);
      if (indNova == -1) {
        Transicao t = new Transicao(estadoEnt, valorEntNovo, estadoFin);
        this.matrizTransicao.set(indAlterar, t);
      } else {
        this.matrizTransicao.remove(indAlterar);
      } 
    } 
  }
  
  public int procuraTransicao(String estadoEnt, String valorEnt, String estadoFin) {
    for (int i = 0; i < this.matrizTransicao.size(); i++) {
      Transicao ta = this.matrizTransicao.get(i);
      if (ta.getEstadoEntrada().equals(estadoEnt) && ta.getValorEntrada().equals(valorEnt) && ta.getEstadoSaida().equals(estadoFin))
        return i; 
    } 
    return -1;
  }
  
  public ArrayList getTransicoes() {
    return this.matrizTransicao;
  }
  
  public void renomeiaEstado(String nomeAntigo, String novoNome) {
    for (int i = 0; i < this.matrizTransicao.size(); i++) {
      Transicao ta = this.matrizTransicao.get(i);
      if (ta.getEstadoEntrada().equals(nomeAntigo))
        ta.setEstadoEntrada(novoNome); 
      if (ta.getEstadoSaida().equals(nomeAntigo))
        ta.setEstadoSaida(novoNome); 
      this.matrizTransicao.set(i, ta);
    } 
  }
}
