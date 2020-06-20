package auger.tcc;

class ClasseEstado {
  public String nomeEstado;
  
  public String nomeClasseAnterior;
  
  public String nomeClasseAtual;
  
  public boolean verificada;
  
  public ClasseEstado(String est, String nomeDaClasseAnterior, String nomeDaClasseAtual) {
    this.nomeEstado = est;
    this.nomeClasseAnterior = nomeDaClasseAnterior;
    this.nomeClasseAtual = nomeDaClasseAtual;
    this.verificada = false;
  }
}
