package auger.tcc;

import java.io.Serializable;

public class Transicao implements Serializable {
  private String estadoEntrada;
  
  private String valorEntrada;
  
  private String estadoSaida;
  
  private static final long serialVersionUID = 1L;
  
  public Transicao() {
    this.estadoEntrada = this.valorEntrada = this.estadoSaida = "";
  }
  
  public Transicao(String estadoEnt, String valorEnt, String estadoSai) {
    this.estadoEntrada = estadoEnt;
    this.valorEntrada = valorEnt;
    this.estadoSaida = estadoSai;
  }
  
  public String getEstadoEntrada() {
    return this.estadoEntrada;
  }
  
  public String getValorEntrada() {
    return this.valorEntrada;
  }
  
  public String getEstadoSaida() {
    return this.estadoSaida;
  }
  
  public void setEstadoEntrada(String nomeEstado) {
    this.estadoEntrada = nomeEstado;
  }
  
  public void setEstadoSaida(String nomeEstado) {
    this.estadoSaida = nomeEstado;
  }
}
