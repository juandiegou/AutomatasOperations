package auger.tcc;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Automato implements Serializable {
  protected GrafoAutomato grafoAutomato;
  
  protected MatrizTransicao matrizTransicao;
  
  private int situacao;
  
  private static final long serialVersionUID = 1L;
  
  protected String estadoInicial;
  
  protected HashSet estadosReconhecedores;
  
  protected int status;
  
  private boolean armazenarVersoes = true;
  
  private VersionadorListener versionador;
  
  private Color corFundo;
  
  public Automato() {
    this.grafoAutomato = new GrafoAutomato(null);
    this.matrizTransicao = new MatrizTransicao();
    this.grafoAutomato.setaAutomato(this);
    this.grafoAutomato.getCanvas().setDefaultRenderQuality(1);
    this.estadoInicial = null;
    this.estadosReconhecedores = new HashSet();
    this.situacao = 0;
    this.status = 0;
    this.corFundo = new Color(188, 248, 255);
    this.grafoAutomato.getCanvas().setBackground(this.corFundo);
    this.versionador = null;
    setArmazenarVersoes(false);
  }
  
  public Automato(int resolucaoGrafo, VersionadorListener versionador, boolean armazenarVersoes) {
    this.grafoAutomato = new GrafoAutomato(versionador);
    this.matrizTransicao = new MatrizTransicao();
    this.grafoAutomato.setaAutomato(this);
    this.grafoAutomato.getCanvas().setDefaultRenderQuality(resolucaoGrafo);
    this.estadoInicial = null;
    this.estadosReconhecedores = new HashSet();
    this.situacao = 0;
    this.status = 0;
    this.corFundo = new Color(188, 248, 255);
    this.grafoAutomato.getCanvas().setBackground(this.corFundo);
    this.versionador = versionador;
    setArmazenarVersoes(armazenarVersoes);
  }
  
  public void setSituacao(int sit) {
    if (this.situacao == 5)
      this.grafoAutomato.reset(); 
    this.situacao = sit;
  }
  
  public int getSituacao() {
    return this.situacao;
  }
  
  public void setStatus(int novoStatus) {
    this.status = novoStatus;
  }
  
  public int getStatus() {
    return this.status;
  }
  
  public void setResolucaoGrafo(int resolucaoGrafo) {
    this.grafoAutomato.getCanvas().setDefaultRenderQuality(resolucaoGrafo);
    setStatus(1);
  }
  
  public int getNrEstados() {
    return this.grafoAutomato.getNrEstados();
  }
  
  public String criaEstado(double x, double y) {
    if (isArmazenarVersoes() && this.versionador != null)
      this.versionador.guardaVersaoAutomato(); 
    String nomeEstadoGerado = this.grafoAutomato.criaEstado(x, y);
    if (this.estadoInicial == null) {
      boolean armazenarVersoesAux = this.armazenarVersoes;
      this.armazenarVersoes = false;
      String nomePrimeiroEstado = this.grafoAutomato.getEstados().get(0);
      setEstadoInicial(nomePrimeiroEstado);
      this.armazenarVersoes = armazenarVersoesAux;
    } 
    setStatus(1);
    return nomeEstadoGerado;
  }
  
  public void criaEstado(String nome, double x, double y) {
    criaEstado(nome, x, y, true);
  }
  
  public void criaEstado(String nome, double x, double y, boolean posicionamentoXYCentral) {
    if (!existeEstado(nome)) {
      if (isArmazenarVersoes() && this.versionador != null)
        this.versionador.guardaVersaoAutomato(); 
      this.grafoAutomato.criaEstado(nome, x, y, posicionamentoXYCentral);
      if (this.estadoInicial == null) {
        boolean armazenarVersoesAux = this.armazenarVersoes;
        this.armazenarVersoes = false;
        setEstadoInicial(nome);
        this.armazenarVersoes = armazenarVersoesAux;
      } 
      setStatus(1);
    } 
  }
  
  public boolean criaTransicao(String estadoEnt, String valorEnt, String estadoFin) {
    if (estadoEnt.equals(estadoFin) && valorEnt.equals("" + 'Ø'))
      return false; 
    if (isArmazenarVersoes() && this.versionador != null)
      this.versionador.guardaVersaoAutomato(); 
    if (this.matrizTransicao.criaTransicao(estadoEnt, valorEnt, estadoFin)) {
      this.grafoAutomato.criaTransicao(estadoEnt, valorEnt, estadoFin);
      setStatus(1);
      return true;
    } 
    if (isArmazenarVersoes() && this.versionador != null)
      this.versionador.descartaUltimaVersaoAutomato(); 
    return false;
  }
  
  public void deletaTransicao(String estadoEnt, String valorEnt, String estadoFin) {
    if (isArmazenarVersoes() && this.versionador != null)
      this.versionador.guardaVersaoAutomato(); 
    this.matrizTransicao.deletaTransicao(estadoEnt, valorEnt, estadoFin);
    this.grafoAutomato.deletaTransicao(estadoEnt, valorEnt, estadoFin);
    setStatus(1);
  }
  
  public void alteraTransicao(String estadoEnt, String valorEntAtual, String estadoFin, String valorEntNovo) {
    if (isArmazenarVersoes() && this.versionador != null)
      this.versionador.guardaVersaoAutomato(); 
    this.matrizTransicao.alteraTransicao(estadoEnt, valorEntAtual, estadoFin, valorEntNovo);
    this.grafoAutomato.alteraTransicao(estadoEnt, valorEntAtual, estadoFin, valorEntNovo);
    setStatus(1);
  }
  
  public GrafoAutomato getGrafoAutomato() {
    return this.grafoAutomato;
  }
  
  public MatrizTransicao getMatrizTransicoes() {
    return this.matrizTransicao;
  }
  
  public ArrayList<String> getEstados() {
    return this.grafoAutomato.getEstados();
  }
  
  public boolean existeEstado(String est) {
    ArrayList<String> vetorEstados = getEstados();
    for (int i = 0; i < vetorEstados.size(); i++) {
      String e = vetorEstados.get(i);
      if (e.equals(est))
        return true; 
    } 
    return false;
  }
  
  public void setEstadoInicial(String est) {
    if (this.armazenarVersoes && this.versionador != null)
      this.versionador.guardaVersaoAutomato(); 
    this.estadoInicial = est;
    if (est != null && 
      est.length() > 0) {
      this.grafoAutomato.setEstadoInicial(this.estadoInicial);
      setStatus(1);
    } 
  }
  
  public String getEstadoInicial() {
    return this.estadoInicial;
  }
  
  public boolean adicionaEstadoReconhecedor(String est) {
    if (isArmazenarVersoes() && this.versionador != null)
      this.versionador.guardaVersaoAutomato(); 
    if (this.estadosReconhecedores.add(est)) {
      this.grafoAutomato.setEstadoReconhecedor(est);
      setStatus(1);
      return true;
    } 
    if (isArmazenarVersoes() && this.versionador != null)
      this.versionador.descartaUltimaVersaoAutomato(); 
    return false;
  }
  
  public void removeEstadoReconhecedor(String est) {
    if (isArmazenarVersoes() && this.versionador != null)
      this.versionador.guardaVersaoAutomato(); 
    if (this.estadosReconhecedores.remove(est)) {
      this.grafoAutomato.removeEstadoReconhecedor(est);
      setStatus(1);
    } else if (isArmazenarVersoes() && this.versionador != null) {
      this.versionador.descartaUltimaVersaoAutomato();
    } 
  }
  
  public HashSet getEstadosReconhecedores() {
    return this.estadosReconhecedores;
  }
  
  public boolean isEstadoReconhecedor(String nome) {
    return this.estadosReconhecedores.contains(nome);
  }
  
  public boolean isEstadoInicial(String nome) {
    return this.estadoInicial.equals(nome);
  }
  
  public RelatorioEstadoEliminado deletaEstado(String est) {
    if (isArmazenarVersoes() && this.versionador != null)
      this.versionador.guardaVersaoAutomato(); 
    RelatorioEstadoEliminado retorno = new RelatorioEstadoEliminado(0, 0);
    ArrayList<Transicao> transicoes = this.matrizTransicao.getTransicoes();
    removeEstadoReconhecedor(est);
    this.grafoAutomato.deletaEstado(est);
    int indTrans = 0;
    boolean tinhaTransicao = false;
    while (indTrans < transicoes.size()) {
      Transicao tr = transicoes.get(indTrans);
      if (tr.getEstadoEntrada().equals(est)) {
        transicoes.remove(indTrans);
        retorno.nrTransicoesOrigem++;
        continue;
      } 
      if (tr.getEstadoSaida().equals(est)) {
        transicoes.remove(indTrans);
        retorno.nrTransicoesDestino++;
        continue;
      } 
      indTrans++;
    } 
    if (isEstadoInicial(est))
      setEstadoInicial(""); 
    return retorno;
  }
  
  public void deletaTodosEstados() {
    ArrayList<String> estados = getEstados();
    for (String estadoAtual : estados)
      deletaEstado(estadoAtual); 
  }
  
  public void renomeiaEstado(String nomeAntigo, String novoNome) {
    if (isArmazenarVersoes() && this.versionador != null)
      this.versionador.guardaVersaoAutomato(); 
    this.matrizTransicao.renomeiaEstado(nomeAntigo, novoNome);
    this.grafoAutomato.renomeiaEstado(nomeAntigo, novoNome);
    if (getEstadoInicial().equals(nomeAntigo))
      setEstadoInicial(novoNome); 
    if (isEstadoReconhecedor(nomeAntigo)) {
      this.estadosReconhecedores.remove(nomeAntigo);
      this.estadosReconhecedores.add(novoNome);
    } 
    setStatus(1);
  }
  
  public boolean isArmazenarVersoes() {
    return this.armazenarVersoes;
  }
  
  public void setArmazenarVersoes(boolean armazenarVersoes) {
    this.armazenarVersoes = armazenarVersoes;
  }
  
  public void alteraCorFundoDiagramaEstados(Color novaCor) {
    if (isArmazenarVersoes() && this.versionador != null)
      this.versionador.guardaVersaoAutomato(); 
    this.grafoAutomato.getCanvas().setBackground(novaCor);
    this.corFundo = novaCor;
    setStatus(1);
  }
  
  public Color getCorFundoDiagramaEstados() {
    return this.corFundo;
  }
}
