package auger.tcc;

class ParenteseExcluir {
  public int indiceAbertura = -1;
  
  public int indiceFechamento = -1;
  
  public ParenteseExcluir(int indiceAbertura, int indiceFechamento) {
    this.indiceAbertura = indiceAbertura;
    this.indiceFechamento = indiceFechamento;
  }
  
  public String toString() {
    return "Abertura: " + this.indiceAbertura + "\tFechamento: " + this.indiceFechamento;
  }
}