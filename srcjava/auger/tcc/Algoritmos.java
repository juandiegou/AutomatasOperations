package auger.tcc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Algoritmos {
  private Automato automatoAtual;
  
  private Conjuntos operacoesConjuntos = new Conjuntos();
  
  private String expressaoRegularSimplificacao = null;
  
  private ArrayList<ParenteseExcluir> listaParentesesExcluir = null;
  
  public Algoritmos(Automato pai) {
    setAutomato(pai);
  }
  
  public void setAutomato(Automato pai) {
    this.automatoAtual = pai;
  }
  
  public Automato getAutomato() {
    return this.automatoAtual;
  }
  
  public int eliminaEstadosMortos() {
    int nrEliminados = 0;
    HashSet<String> estadosVivos = new HashSet();
    HashSet<String> vivosAtual = new HashSet();
    HashSet estadosMortos = new HashSet();
    HashSet estadosTodos = new HashSet();
    ArrayList<Transicao> transicoes = this.automatoAtual.getMatrizTransicoes().getTransicoes();
    HashSet estadosReconhecedores = this.automatoAtual.getEstadosReconhecedores();
    ArrayList<String> estados = this.automatoAtual.getEstados();
    boolean atualizadosVivos = true;
    Iterator<String> navegaReconhecedores = estadosReconhecedores.iterator();
    while (navegaReconhecedores.hasNext()) {
      String estadoVivoAtual = navegaReconhecedores.next();
      estadosVivos.add(estadoVivoAtual);
      vivosAtual.add(estadoVivoAtual);
    } 
    while (vivosAtual.size() > 0) {
      HashSet<String> vivosTemp = new HashSet();
      Iterator<String> navegaVivosAtual = vivosAtual.iterator();
      while (navegaVivosAtual.hasNext()) {
        String str = navegaVivosAtual.next();
        for (int j = 0; j < transicoes.size(); j++) {
          Transicao tr = transicoes.get(j);
          if (tr.getEstadoSaida().equals(str))
            if (!tr.getEstadoEntrada().equals(str)) {
              String estadoAtual = tr.getEstadoEntrada();
              if (estadosVivos.add(estadoAtual))
                vivosTemp.add(estadoAtual); 
            }  
        } 
      } 
      vivosAtual = vivosTemp;
      System.gc();
    } 
    for (int i = 0; i < estados.size(); i++)
      estadosTodos.add(estados.get(i)); 
    estadosMortos = this.operacoesConjuntos.diferenca(estadosTodos, estadosVivos);
    Iterator<String> navegaMortos = estadosMortos.iterator();
    while (navegaMortos.hasNext()) {
      String str = navegaMortos.next();
      this.automatoAtual.deletaEstado(str);
      nrEliminados++;
    } 
    return nrEliminados;
  }
  
  public int eliminaEstadosInalcancaveis() {
    HashSet<String> estadosAlcancaveis = new HashSet();
    HashSet estadosInalcancaveis = new HashSet();
    HashSet estadosTodos = new HashSet();
    ArrayList transicoes = this.automatoAtual.getMatrizTransicoes().getTransicoes();
    ArrayList<String> estados = this.automatoAtual.getEstados();
    estadosAlcancaveis.add(this.automatoAtual.getEstadoInicial());
    ArrayList<String> alcancaveisAtual = new ArrayList();
    alcancaveisAtual.add(this.automatoAtual.getEstadoInicial());
    while (alcancaveisAtual.size() > 0) {
      ArrayList<String> alcancaveisTemp = new ArrayList();
      for (int j = 0; j < alcancaveisAtual.size(); j++) {
        String estadoAtual = alcancaveisAtual.get(j);
        ArrayList<Transicao> transicoesEstadoAtual = retornaTransicoesEstado(transicoes, estadoAtual, null);
        for (int k = 0; k < transicoesEstadoAtual.size(); k++) {
          Transicao tr = transicoesEstadoAtual.get(k);
          if (estadosAlcancaveis.add(tr.getEstadoSaida()))
            alcancaveisTemp.add(tr.getEstadoSaida()); 
        } 
      } 
      alcancaveisAtual = alcancaveisTemp;
    } 
    for (int i = 0; i < estados.size(); i++)
      estadosTodos.add(estados.get(i)); 
    estadosInalcancaveis = this.operacoesConjuntos.diferenca(estadosTodos, estadosAlcancaveis);
    Iterator<String> navegaInalcancaveis = estadosInalcancaveis.iterator();
    int nrEstadosEliminados = 0;
    int nrTransicoesOrigem = 0;
    while (navegaInalcancaveis.hasNext()) {
      String str = navegaInalcancaveis.next();
      RelatorioEstadoEliminado rel = this.automatoAtual.deletaEstado(str);
      nrTransicoesOrigem += rel.nrTransicoesOrigem;
      nrEstadosEliminados++;
    } 
    RelatorioEstadoEliminado retornoRecursivo = null;
    if (nrTransicoesOrigem > 0)
      nrEstadosEliminados += eliminaEstadosInalcancaveis(); 
    return nrEstadosEliminados;
  }
  
  public int eliminaTransicoesVazias() {
    ArrayList<Transicao> transicoes = this.automatoAtual.getMatrizTransicoes().getTransicoes();
    ArrayList<Transicao> transicoesVazias = new ArrayList();
    int totalTransicoesInicial = -1;
    do {
      procuraTransicoesVazias(transicoes, transicoesVazias);
      if (totalTransicoesInicial == -1)
        totalTransicoesInicial = transicoesVazias.size(); 
      LinkedHashSet<String> estadosTransVazias = new LinkedHashSet();
      String primeiroEstado = "";
      for (int i = 0; i < transicoesVazias.size(); i++) {
        Transicao tr = transicoesVazias.get(i);
        estadosTransVazias.add(tr.getEstadoEntrada());
      } 
      ArrayList<LinkedHashSet> ciclosVazios = new ArrayList();
      Iterator<String> nav = estadosTransVazias.iterator();
      while (nav.hasNext()) {
        LinkedHashSet estadosCiclo = new LinkedHashSet();
        primeiroEstado = nav.next();
        procuraCicloVazio(ciclosVazios, estadosCiclo, transicoesVazias, primeiroEstado, primeiroEstado);
      } 
      eliminaCiclosVaziosDesnecessarios(ciclosVazios);
      for (int j = 0; j < ciclosVazios.size(); j++) {
        LinkedHashSet<String> cicloAtual = ciclosVazios.get(j);
        nav = cicloAtual.iterator();
        String estadoPermanente = nav.next();
        while (nav.hasNext()) {
          String estadoEliminar = nav.next();
          int nrT = transicoes.size();
          for (int m = 0; m < nrT; m++) {
            Transicao transicao = transicoes.get(m);
            if (transicao.getEstadoEntrada().equals(estadoEliminar))
              this.automatoAtual.criaTransicao(estadoPermanente, transicao.getValorEntrada(), transicao.getEstadoSaida()); 
            if (transicao.getEstadoSaida().equals(estadoEliminar))
              this.automatoAtual.criaTransicao(transicao.getEstadoEntrada(), transicao.getValorEntrada(), estadoPermanente); 
            if (this.automatoAtual.isEstadoReconhecedor(estadoEliminar))
              this.automatoAtual.adicionaEstadoReconhecedor(estadoPermanente); 
          } 
          this.automatoAtual.deletaEstado(estadoEliminar);
        } 
      } 
      procuraTransicoesVazias(transicoes, transicoesVazias);
      ArrayList<Transicao> transicoesEstadoSaida = new ArrayList();
      for (int k = 0; k < transicoesVazias.size(); k++) {
        Transicao transicao = transicoesVazias.get(k);
        String estadoEntrada = transicao.getEstadoEntrada();
        String estadoSaida = transicao.getEstadoSaida();
        if (this.automatoAtual.isEstadoReconhecedor(estadoSaida))
          this.automatoAtual.adicionaEstadoReconhecedor(estadoEntrada); 
        transicoesEstadoSaida.clear();
        for (int m = 0; m < transicoes.size(); m++) {
          Transicao tr2 = transicoes.get(m);
          if (tr2.getEstadoEntrada().equals(estadoSaida))
            transicoesEstadoSaida.add(tr2); 
        } 
        for (int n = 0; n < transicoesEstadoSaida.size(); n++) {
          Transicao tr2 = transicoesEstadoSaida.get(n);
          this.automatoAtual.criaTransicao(estadoEntrada, tr2.getValorEntrada(), tr2.getEstadoSaida());
        } 
        this.automatoAtual.deletaTransicao(transicao.getEstadoEntrada(), transicao.getValorEntrada(), transicao.getEstadoSaida());
      } 
      System.gc();
    } while (transicoesVazias.size() > 0);
    return totalTransicoesInicial;
  }
  
  private void procuraCicloVazio(ArrayList<LinkedHashSet<String>> ciclosVazios, LinkedHashSet<String> estadosCiclo, ArrayList<Transicao> vetorTransicoesVazias, String estadoAtual, String primeiroEstadoCiclo) {
    if (estadosCiclo.add(estadoAtual)) {
      ArrayList<String> estadosDestino = new ArrayList();
      for (int i = 0; i < vetorTransicoesVazias.size(); i++) {
        Transicao t = vetorTransicoesVazias.get(i);
        if (t.getEstadoEntrada().equals(estadoAtual))
          estadosDestino.add(t.getEstadoSaida()); 
      } 
      for (int j = 0; j < estadosDestino.size(); j++) {
        String est = estadosDestino.get(j);
        procuraCicloVazio(ciclosVazios, estadosCiclo, vetorTransicoesVazias, est, primeiroEstadoCiclo);
      } 
      estadosCiclo.remove(estadoAtual);
    } else if (estadoAtual.equals(primeiroEstadoCiclo)) {
      LinkedHashSet<String> copiaEstadosCiclo = new LinkedHashSet();
      Iterator<String> nav2 = estadosCiclo.iterator();
      while (nav2.hasNext()) {
        String estAdd = nav2.next();
        copiaEstadosCiclo.add(estAdd);
      } 
      ciclosVazios.add(copiaEstadosCiclo);
    } 
  }
  
  private void eliminaCiclosVaziosDesnecessarios(ArrayList<LinkedHashSet> ciclosVazios) {
    LinkedHashSet<String> ciclosDesnecessarios = new LinkedHashSet();
    for (int i = 0; i < ciclosVazios.size(); i++) {
      LinkedHashSet sequenciaPrincipal = ciclosVazios.get(i);
      for (int k = i + 1; k < ciclosVazios.size(); k++) {
        LinkedHashSet sequenciaSecundaria = ciclosVazios.get(k);
        boolean diferente = false;
        if (sequenciaPrincipal.size() == sequenciaSecundaria.size()) {
          Iterator<String> iterator = sequenciaPrincipal.iterator();
          while (iterator.hasNext()) {
            String estadoBusca = iterator.next();
            if (!sequenciaSecundaria.contains(estadoBusca))
              diferente = true; 
          } 
        } else {
          diferente = true;
        } 
        if (!diferente)
          ciclosDesnecessarios.add("" + k); 
      } 
    } 
    Iterator<String> nav = ciclosDesnecessarios.iterator();
    int j = 0;
    while (nav.hasNext()) {
      int indice = Integer.parseInt(nav.next());
      indice -= j;
      ciclosVazios.remove(indice);
      j++;
    } 
  }
  
  private void procuraTransicoesVazias(ArrayList<Transicao> transicoes, ArrayList<Transicao> transicoesVazias) {
    transicoesVazias.clear();
    for (int i = 0; i < transicoes.size(); i++) {
      Transicao tr = transicoes.get(i);
      if (tr.getValorEntrada().equals(Constantes.STRING_VAZIA))
        transicoesVazias.add(tr); 
    } 
  }
  
  public RelatorioAFNDParaAFD transformaEmAFD(ArrayList<ArrayList> vetorNovosEstadosAnterior, int indChamada) {
    RelatorioAFNDParaAFD relatorio = new RelatorioAFNDParaAFD();
    int nrEliminados = 0;
    ArrayList<String> estadosIndeterminismo = new ArrayList();
    ArrayList<LinkedHashSet<String>> entradasIndeterminismo = new ArrayList();
    LinkedHashSet novosEstadosCriados = new LinkedHashSet();
    if (indChamada == 0) {
      relatorio.nrTransicoesEliminadas = eliminaTransicoesVazias();
      eliminaEstadosInalcancaveis();
    } 
    ArrayList<Transicao> transicoes = this.automatoAtual.getMatrizTransicoes().getTransicoes();
    ArrayList<String> estados = this.automatoAtual.getEstados();
    for (int i = 0; i < estados.size(); i++) {
      String estadoAtual = estados.get(i);
      ArrayList<Transicao> transicoesEstadoAtual = retornaTransicoesEstado(transicoes, estadoAtual, null);
      boolean deterministico = true;
      int m = 0;
      LinkedHashSet<String> entradasAtual = new LinkedHashSet();
      while (m < transicoesEstadoAtual.size()) {
        Transicao tr = transicoesEstadoAtual.get(m);
        int n = m + 1;
        while (n < transicoesEstadoAtual.size()) {
          Transicao tr2 = transicoesEstadoAtual.get(n);
          if (tr.getValorEntrada().equals(tr2.getValorEntrada())) {
            deterministico = false;
            entradasAtual.add(tr.getValorEntrada());
          } 
          n++;
        } 
        m++;
      } 
      if (!deterministico) {
        estadosIndeterminismo.add(estadoAtual);
        entradasIndeterminismo.add(entradasAtual);
      } 
    } 
    boolean criadoNovoEstado = false;
    if (vetorNovosEstadosAnterior == null)
      vetorNovosEstadosAnterior = new ArrayList(); 
    ArrayList<ArrayList<String>> vetorNovosEstados = new ArrayList();
    for (int j = 0; j < estadosIndeterminismo.size(); j++) {
      String str = estadosIndeterminismo.get(j);
      LinkedHashSet vetorEntradasAtual = entradasIndeterminismo.get(j);
      Iterator<String> nav = vetorEntradasAtual.iterator();
      while (nav.hasNext()) {
        boolean isReconhecedor = false;
        ArrayList<String> vetorEstadosNovoEstado = new ArrayList();
        String entradaAtual = nav.next();
        int m = 0;
        boolean adicionado = false;
        while (m < transicoes.size()) {
          Transicao transicao = transicoes.get(m);
          if (transicao.getEstadoEntrada().equals(str) && transicao.getValorEntrada().equals(entradaAtual)) {
            vetorEstadosNovoEstado.add(transicao.getEstadoSaida());
            if (this.automatoAtual.isEstadoReconhecedor(transicao.getEstadoSaida()))
              isReconhecedor = true; 
          } 
          m++;
        } 
        if (!existeVetorNovo(vetorNovosEstados, vetorNovosEstadosAnterior, vetorEstadosNovoEstado)) {
          ArrayList<String> vAux = new ArrayList();
          String nome = this.automatoAtual.criaEstado(50.0D, 50.0D);
          vAux.add(nome);
          vAux.add(vetorEstadosNovoEstado);
          vetorNovosEstados.add(vAux);
          if (isReconhecedor)
            this.automatoAtual.adicionaEstadoReconhecedor(nome); 
          criadoNovoEstado = true;
        } 
      } 
    } 
    for (int k = 0; k < estadosIndeterminismo.size(); k++) {
      String str = estadosIndeterminismo.get(k);
      LinkedHashSet entradasAtual = entradasIndeterminismo.get(k);
      Iterator<String> nav = entradasAtual.iterator();
      while (nav.hasNext()) {
        String valorEntradaAtual = nav.next();
        ArrayList<String> vetorIdPesquisa = new ArrayList();
        for (int m = 0; m < transicoes.size(); m++) {
          Transicao transicao = transicoes.get(m);
          if (transicao.getEstadoEntrada().equals(str) && transicao.getValorEntrada().equals(valorEntradaAtual))
            vetorIdPesquisa.add(transicao.getEstadoSaida()); 
        } 
        String nomeEstado = null;
        for (int n = 0; n < vetorNovosEstados.size(); n++) {
          ArrayList<ArrayList> vAux = (ArrayList)vetorNovosEstados.get(n);
          ArrayList vetorIdAtual = vAux.get(1);
          if (comparaVetores(vetorIdAtual, vetorIdPesquisa) == 0) {
            nomeEstado = (String)vAux.get(0);
            break;
          } 
        } 
        if (nomeEstado == null)
          for (int i1 = 0; i1 < vetorNovosEstadosAnterior.size(); i1++) {
            ArrayList<ArrayList> vAux = vetorNovosEstadosAnterior.get(i1);
            ArrayList vetorIdAtual = vAux.get(1);
            if (comparaVetores(vetorIdAtual, vetorIdPesquisa) == 0) {
              nomeEstado = (String)vAux.get(0);
              break;
            } 
          }  
        if (nomeEstado != null) {
          int nrTr = transicoes.size();
          int i1 = 0;
          while (i1 < nrTr) {
            Transicao transicao = transicoes.get(i1);
            if (transicao.getEstadoEntrada().equals(str) && transicao.getValorEntrada().equals(valorEntradaAtual)) {
              this.automatoAtual.deletaTransicao(transicao.getEstadoEntrada(), transicao.getValorEntrada(), transicao.getEstadoSaida());
              nrTr--;
              continue;
            } 
            i1++;
          } 
          this.automatoAtual.criaTransicao(str, valorEntradaAtual, nomeEstado);
          continue;
        } 
        System.out.println("ATENUm novo estado foi gerado mas no momento de substituir a referantiga pelo nome do novo estado gerado o estado nfoi encontrado!");
      } 
    } 
    if (criadoNovoEstado)
      for (int m = 0; m < vetorNovosEstados.size(); m++) {
        ArrayList<String> novoAtual = vetorNovosEstados.get(m);
        String nome = novoAtual.get(0);
        ArrayList<String> estadosContidos = (ArrayList)novoAtual.get(1);
        for (int n = 0; n < estadosContidos.size(); n++) {
          String estadoContidoAtual = estadosContidos.get(n);
          for (int i1 = 0; i1 < transicoes.size(); i1++) {
            Transicao transicao = transicoes.get(i1);
            if (transicao.getEstadoEntrada().equals(estadoContidoAtual))
              this.automatoAtual.criaTransicao(nome, transicao.getValorEntrada(), transicao.getEstadoSaida()); 
          } 
        } 
      }  
    nrEliminados += estadosIndeterminismo.size();
    if (estadosIndeterminismo.size() > 0) {
      for (int m = 0; m < vetorNovosEstados.size(); m++) {
        ArrayList vetorNovoAtual = vetorNovosEstados.get(m);
        vetorNovosEstadosAnterior.add(vetorNovoAtual);
      } 
      nrEliminados += (transformaEmAFD(vetorNovosEstadosAnterior, indChamada + 1)).nrEstadosAlterados;
    } 
    if (indChamada == 0)
      renomeiaEstados(this.automatoAtual); 
    relatorio.nrEstadosAlterados = nrEliminados;
    return relatorio;
  }
  
  public static ArrayList retornaTransicoesEstado(ArrayList<Transicao> transicoes, String estadoAtual, String valorEnt) {
    ArrayList<Transicao> transicoesEstadoAtual = new ArrayList();
    for (int i = 0; i < transicoes.size(); i++) {
      Transicao tr = transicoes.get(i);
      if (tr.getEstadoEntrada().equals(estadoAtual))
        if (valorEnt != null) {
          if (tr.getValorEntrada().equals(valorEnt))
            transicoesEstadoAtual.add(tr); 
        } else {
          transicoesEstadoAtual.add(tr);
        }  
    } 
    return transicoesEstadoAtual;
  }
  
  private boolean existeVetorNovo(ArrayList<ArrayList> vetorNovosEstadosAtual, ArrayList<ArrayList> vetorNovosEstadosAnterior, ArrayList vetorNovo) {
    for (int i = 0; i < vetorNovosEstadosAtual.size(); i++) {
      ArrayList<ArrayList> vetorCompleto = vetorNovosEstadosAtual.get(i);
      ArrayList vetorCompara = vetorCompleto.get(1);
      if (comparaVetores(vetorCompara, vetorNovo) == 0)
        return true; 
    } 
    for (int j = 0; j < vetorNovosEstadosAnterior.size(); j++) {
      ArrayList<ArrayList> vetorCompleto = vetorNovosEstadosAnterior.get(j);
      ArrayList vetorCompara = vetorCompleto.get(1);
      if (comparaVetores(vetorCompara, vetorNovo) == 0)
        return true; 
    } 
    return false;
  }
  
  private int comparaVetores(ArrayList<String> primeiro, ArrayList segundo) {
    if (primeiro.size() == segundo.size()) {
      for (int i = 0; i < primeiro.size(); i++) {
        String elementoAtual = primeiro.get(i);
        if (!segundo.contains(elementoAtual))
          return -5; 
      } 
      return 0;
    } 
    if (primeiro.size() > segundo.size())
      return 1; 
    return -1;
  }
  
  public int minimiza() {
    ArrayList<ClasseEstado> classesEquivalencia = new ArrayList();
    eliminaEstadosMortos();
    eliminaEstadosInalcancaveis();
    int retAfd = (transformaEmAFD(null, 0)).nrEstadosAlterados;
    if (retAfd > 0) {
      eliminaEstadosMortos();
      eliminaEstadosInalcancaveis();
    } 
    ArrayList transicoes = this.automatoAtual.getMatrizTransicoes().getTransicoes();
    ArrayList<String> estados = this.automatoAtual.getEstados();
    for (int i = 0; i < estados.size(); i++) {
      ClasseEstado classeEstado;
      String estadoAtual = estados.get(i);
      if (this.automatoAtual.isEstadoReconhecedor(estadoAtual)) {
        classeEstado = new ClasseEstado(estadoAtual, "r", "r");
      } else {
        classeEstado = new ClasseEstado(estadoAtual, "n", "n");
      } 
      classesEquivalencia.add(classeEstado);
    } 
    boolean modificado = true;
    while (modificado) {
      modificado = false;
      for (int j = 0; j < classesEquivalencia.size(); j++) {
        ClasseEstado classeEstadoAtual = classesEquivalencia.get(j);
        classeEstadoAtual.verificada = false;
        classesEquivalencia.set(j, classeEstadoAtual);
      } 
      for (int k = 0; k < classesEquivalencia.size(); k++) {
        ClasseEstado primClasseEstado = classesEquivalencia.get(k);
        if (!primClasseEstado.verificada) {
          ArrayList<Transicao> transicoesPrimEst = retornaTransicoesEstado(transicoes, primClasseEstado.nomeEstado, null);
          String classeAntEstadoAtual = primClasseEstado.nomeClasseAtual;
          primClasseEstado.nomeClasseAnterior = primClasseEstado.nomeClasseAtual;
          primClasseEstado.nomeClasseAtual = primClasseEstado.nomeEstado;
          primClasseEstado.verificada = true;
          classesEquivalencia.set(k, primClasseEstado);
          for (int m = k + 1; m < classesEquivalencia.size(); m++) {
            ClasseEstado segClasseEstado = classesEquivalencia.get(m);
            if (!segClasseEstado.verificada) {
              ArrayList<Transicao> transicoesSegEst = retornaTransicoesEstado(transicoes, segClasseEstado.nomeEstado, null);
              if (primClasseEstado.nomeClasseAnterior.equals(segClasseEstado.nomeClasseAtual))
                if (transicoesSegEst.size() == transicoesPrimEst.size()) {
                  boolean classesIguais = true;
                  for (int t1 = 0; t1 < transicoesPrimEst.size(); t1++) {
                    Transicao tr1 = transicoesPrimEst.get(t1);
                    Transicao tr2 = null;
                    boolean transicao_encontrada = false;
                    int indT2 = 0;
                    while (!transicao_encontrada && indT2 < transicoesSegEst.size()) {
                      tr2 = transicoesSegEst.get(indT2);
                      if (tr2.getValorEntrada().equals(tr1.getValorEntrada())) {
                        transicao_encontrada = true;
                        continue;
                      } 
                      indT2++;
                    } 
                    if (transicao_encontrada) {
                      String str1;
                      String str2;
                      if (isAlocadoEmClasse(classesEquivalencia, tr1.getEstadoSaida(), primClasseEstado.nomeClasseAtual)) {
                        str1 = retornaClasseEquivalenciaAnterior(classesEquivalencia, tr1.getEstadoSaida());
                      } else {
                        str1 = retornaClasseEquivalenciaAtual(classesEquivalencia, tr1.getEstadoSaida());
                      } 
                      if (isAlocadoEmClasse(classesEquivalencia, tr2.getEstadoSaida(), primClasseEstado.nomeClasseAtual)) {
                        str2 = retornaClasseEquivalenciaAnterior(classesEquivalencia, tr2.getEstadoSaida());
                      } else {
                        str2 = retornaClasseEquivalenciaAtual(classesEquivalencia, tr2.getEstadoSaida());
                      } 
                      if (!str2.equals(str1))
                        classesIguais = false; 
                    } else {
                      classesIguais = false;
                    } 
                  } 
                  if (classesIguais) {
                    if (!segClasseEstado.nomeClasseAtual.equals(primClasseEstado.nomeClasseAtual)) {
                      segClasseEstado.nomeClasseAnterior = segClasseEstado.nomeClasseAtual;
                      segClasseEstado.nomeClasseAtual = primClasseEstado.nomeClasseAtual;
                      segClasseEstado.verificada = true;
                      modificado = true;
                    } else {
                      segClasseEstado.nomeClasseAnterior = segClasseEstado.nomeClasseAtual;
                      segClasseEstado.verificada = true;
                    } 
                    classesEquivalencia.set(m, segClasseEstado);
                  } else {
                    modificado = true;
                  } 
                } else {
                  modificado = true;
                }  
            } 
          } 
          primClasseEstado.nomeClasseAnterior = classeAntEstadoAtual;
          classesEquivalencia.set(k, primClasseEstado);
        } 
      } 
    } 
    int nrEliminados = reduzEstadosEquivalentes(classesEquivalencia, transicoes);
    renomeiaEstados(this.automatoAtual);
    return nrEliminados;
  }
  
  private void mostraClasses(ArrayList<ClasseEstado> classesEquivalencia) {
    String saida = "";
    for (int i = 0; i < classesEquivalencia.size(); i++) {
      ClasseEstado classeEstadoSaida = classesEquivalencia.get(i);
      saida = saida + "Estado: " + classeEstadoSaida.nomeEstado + "\n";
      saida = saida + "Classe anterior: " + classeEstadoSaida.nomeClasseAnterior + "\n";
      saida = saida + "Classe atual: " + classeEstadoSaida.nomeClasseAtual + "\n";
      saida = saida + "\n";
    } 
    JOptionPane.showMessageDialog(null, new JTextArea(saida));
  }
  
  private int reduzEstadosEquivalentes(ArrayList<ClasseEstado> classesEquivalencia, ArrayList<Transicao> transicoes) {
    int nrEliminados = 0;
    for (int i = 0; i < classesEquivalencia.size(); i++) {
      ClasseEstado classeEstadoAtual = classesEquivalencia.get(i);
      classeEstadoAtual.verificada = false;
      classesEquivalencia.set(i, classeEstadoAtual);
    } 
    for (int j = 0; j < classesEquivalencia.size(); j++) {
      ClasseEstado ce1 = classesEquivalencia.get(j);
      if (!ce1.verificada)
        for (int k = j + 1; k < classesEquivalencia.size(); k++) {
          ClasseEstado ce2 = classesEquivalencia.get(k);
          if (ce2.nomeClasseAtual.equals(ce1.nomeClasseAtual)) {
            for (int t = 0; t < transicoes.size(); t++) {
              Transicao tr = transicoes.get(t);
              String orig = tr.getEstadoEntrada();
              String dest = tr.getEstadoSaida();
              if (ce2.nomeEstado.equals(tr.getEstadoEntrada()))
                orig = ce1.nomeClasseAtual; 
              if (ce2.nomeEstado.equals(tr.getEstadoSaida()))
                dest = ce1.nomeClasseAtual; 
              this.automatoAtual.criaTransicao(orig, tr.getValorEntrada(), dest);
            } 
            ce2.verificada = true;
            classesEquivalencia.set(k, ce2);
            this.automatoAtual.deletaEstado(ce2.nomeEstado);
            nrEliminados++;
          } 
        }  
    } 
    return nrEliminados;
  }
  
  private boolean isAlocadoEmClasse(ArrayList<ClasseEstado> vetorClasses, String estadoAtual, String nomeClasseAlocado) {
    for (int i = 0; i < vetorClasses.size(); i++) {
      ClasseEstado ca = vetorClasses.get(i);
      if (ca.nomeEstado.equals(estadoAtual))
        return ca.verificada; 
    } 
    return false;
  }
  
  private String retornaClasseEquivalenciaAtual(ArrayList<ClasseEstado> vetorClasses, String estadoAtual) {
    for (int i = 0; i < vetorClasses.size(); i++) {
      ClasseEstado ca = vetorClasses.get(i);
      if (ca.nomeEstado.equals(estadoAtual))
        return ca.nomeClasseAtual; 
    } 
    return null;
  }
  
  private String retornaClasseEquivalenciaAnterior(ArrayList<ClasseEstado> vetorClasses, String estadoAtual) {
    for (int i = 0; i < vetorClasses.size(); i++) {
      ClasseEstado ca = vetorClasses.get(i);
      if (ca.nomeEstado.equals(estadoAtual))
        return ca.nomeClasseAnterior; 
    } 
    return null;
  }
  
  public String validaExpressaoRegular(String expressao) {
    if (expressao == null)
      return "Expressem branco"; 
    if (expressao.length() <= 0)
      return "Expressem branco"; 
    int nrAbre = 0;
    int nrFecha = 0;
    for (int i = 0; i < expressao.length(); i++) {
      if (expressao.charAt(i) == '(')
        nrAbre++; 
      if (expressao.charAt(i) == ')')
        nrFecha++; 
    } 
    if (nrAbre < nrFecha) {
      int dif = nrFecha - nrAbre;
      if (dif == 1)
        return "Falta abrir 1 par"; 
      return "Falta abrir " + dif + " par";
    } 
    if (nrFecha < nrAbre) {
      int dif = nrAbre - nrFecha;
      if (dif == 1)
        return "Falta fechar 1 par"; 
      return "Falta fechar " + dif + " par";
    } 
    return null;
  }
  
  public String validaGramaticaRegular() {
    return null;
  }
  
  public Automato importaExpressaoRegular(String expressaoPosFixa) {
    Stack<Automato> pilhaAutomatos = new Stack();
    for (int i = 0; i < expressaoPosFixa.length(); i++) {
      String elemAtual = expressaoPosFixa.charAt(i) + "";
      if (isOperador(elemAtual)) {
        if (isOperadorBinario(elemAtual)) {
          Automato atm2 = pilhaAutomatos.pop();
          Automato atm1 = pilhaAutomatos.pop();
          Automato atmNovo = null;
          if (elemAtual.equals("|")) {
            atmNovo = uneAutomatosEmParalelo(i, atm1, atm2);
          } else if (elemAtual.equals(Constantes.OPERADOR_CONCATENACAO)) {
            atmNovo = uneAutomatosEmSerie(i, atm1, atm2);
          } 
          pilhaAutomatos.push(atmNovo);
        } else {
          Automato automato = pilhaAutomatos.pop();
          if (elemAtual.equals("*")) {
            Automato atmNovo = null;
            atmNovo = criaAutomatoEmCiclo(i, automato);
            pilhaAutomatos.push(atmNovo);
          } 
        } 
      } else {
        Automato atmTmp = new Automato(0, null, false);
        atmTmp.criaEstado(i + "1", 0.0D, 0.0D);
        atmTmp.criaEstado(i + "2", 10.0D, 10.0D);
        atmTmp.adicionaEstadoReconhecedor(i + "2");
        atmTmp.criaTransicao(i + "1", elemAtual, i + "2");
        pilhaAutomatos.push(atmTmp);
      } 
    } 
    if (pilhaAutomatos.size() > 0) {
      Automato autRet = pilhaAutomatos.pop();
      autRet.setResolucaoGrafo(1);
      renomeiaEstados(autRet);
      return autRet;
    } 
    return null;
  }
  
  private boolean isOperador(String caracter) {
    return !(!caracter.equals("|") && !caracter.equals("*") && !caracter.equals(Constantes.OPERADOR_CONCATENACAO) && !caracter.equals("(") && !caracter.equals(")"));
  }
  
  private boolean isOperadorBinario(String caracter) {
    return caracter.equals("*") ^ true;
  }
  
  private Automato uneAutomatosEmParalelo(int indiceNumericoNomeEstado, Automato atm1, Automato atm2) {
    Automato atmNovo = new Automato(0, null, false);
    int indNome = indiceNumericoNomeEstado;
    atmNovo.criaEstado(indNome + "1", 20.0D, 20.0D);
    atmNovo.criaEstado(indNome + "2", 30.0D, 30.0D);
    atmNovo.adicionaEstadoReconhecedor(indNome + "2");
    ArrayList<String> vetorEstados = atm1.getEstados();
    for (int i = 0; i < vetorEstados.size(); i++) {
      String nomeEstado = vetorEstados.get(i);
      atmNovo.criaEstado(nomeEstado, 40.0D, (40 + i));
    } 
    atmNovo.criaTransicao(atmNovo.getEstadoInicial(), Constantes.STRING_VAZIA, atm1.getEstadoInicial());
    HashSet<String> hashRec = atm1.getEstadosReconhecedores();
    Iterator<String> itNaveg = hashRec.iterator();
    String estadoRec = itNaveg.next();
    atmNovo.criaTransicao(estadoRec, Constantes.STRING_VAZIA, indNome + "2");
    ArrayList<Transicao> transicoes = atm1.getMatrizTransicoes().getTransicoes();
    for (int j = 0; j < transicoes.size(); j++) {
      Transicao tr = transicoes.get(j);
      atmNovo.criaTransicao(tr.getEstadoEntrada(), tr.getValorEntrada(), tr.getEstadoSaida());
    } 
    vetorEstados = atm2.getEstados();
    for (int k = 0; k < vetorEstados.size(); k++) {
      String nomeEstado = vetorEstados.get(k);
      atmNovo.criaEstado(nomeEstado, 50.0D, (50 + k));
    } 
    atmNovo.criaTransicao(atmNovo.getEstadoInicial(), Constantes.STRING_VAZIA, atm2.getEstadoInicial());
    hashRec = atm2.getEstadosReconhecedores();
    itNaveg = hashRec.iterator();
    estadoRec = itNaveg.next();
    atmNovo.criaTransicao(estadoRec, Constantes.STRING_VAZIA, indNome + "2");
    transicoes = atm2.getMatrizTransicoes().getTransicoes();
    for (int m = 0; m < transicoes.size(); m++) {
      Transicao tr = transicoes.get(m);
      atmNovo.criaTransicao(tr.getEstadoEntrada(), tr.getValorEntrada(), tr.getEstadoSaida());
    } 
    return atmNovo;
  }
  
  private Automato uneAutomatosEmSerie(int indiceNumericoNomeEstado, Automato atm1, Automato atm2) {
    Automato atmNovo = new Automato(0, null, false);
    int indNome = indiceNumericoNomeEstado;
    atmNovo.criaEstado(indNome + "1", 60.0D, 60.0D);
    atmNovo.criaEstado(indNome + "2", 70.0D, 70.0D);
    atmNovo.criaEstado(indNome + "3", 80.0D, 80.0D);
    atmNovo.adicionaEstadoReconhecedor(indNome + "3");
    ArrayList<String> vetorEstados = atm1.getEstados();
    for (int i = 0; i < vetorEstados.size(); i++) {
      String nomeEstado = vetorEstados.get(i);
      atmNovo.criaEstado(nomeEstado, 90.0D, (90 + i));
    } 
    atmNovo.criaTransicao(atmNovo.getEstadoInicial(), Constantes.STRING_VAZIA, atm1.getEstadoInicial());
    HashSet<String> hashRec = atm1.getEstadosReconhecedores();
    Iterator<String> itNaveg = hashRec.iterator();
    String estadoRec = itNaveg.next();
    atmNovo.criaTransicao(estadoRec, Constantes.STRING_VAZIA, indNome + "2");
    ArrayList<Transicao> transicoes = atm1.getMatrizTransicoes().getTransicoes();
    for (int j = 0; j < transicoes.size(); j++) {
      Transicao tr = transicoes.get(j);
      atmNovo.criaTransicao(tr.getEstadoEntrada(), tr.getValorEntrada(), tr.getEstadoSaida());
    } 
    vetorEstados = atm2.getEstados();
    for (int k = 0; k < vetorEstados.size(); k++) {
      String nomeEstado = vetorEstados.get(k);
      atmNovo.criaEstado(nomeEstado, 100.0D, (100 + k));
    } 
    atmNovo.criaTransicao(indNome + "2", Constantes.STRING_VAZIA, atm2.getEstadoInicial());
    hashRec = atm2.getEstadosReconhecedores();
    itNaveg = hashRec.iterator();
    estadoRec = itNaveg.next();
    atmNovo.criaTransicao(estadoRec, Constantes.STRING_VAZIA, indNome + "3");
    transicoes = atm2.getMatrizTransicoes().getTransicoes();
    for (int m = 0; m < transicoes.size(); m++) {
      Transicao tr = transicoes.get(m);
      atmNovo.criaTransicao(tr.getEstadoEntrada(), tr.getValorEntrada(), tr.getEstadoSaida());
    } 
    return atmNovo;
  }
  
  private Automato criaAutomatoEmCiclo(int indiceNumericoNomeEstado, Automato atm1) {
    Automato atmNovo = new Automato(0, null, false);
    int indNome = indiceNumericoNomeEstado;
    atmNovo.criaEstado(indNome + "1", 110.0D, 110.0D);
    atmNovo.criaEstado(indNome + "2", 120.0D, 120.0D);
    atmNovo.criaEstado(indNome + "3", 130.0D, 130.0D);
    atmNovo.adicionaEstadoReconhecedor(indNome + "3");
    atmNovo.criaTransicao(indNome + "1", Constantes.STRING_VAZIA, indNome + "2");
    atmNovo.criaTransicao(indNome + "2", Constantes.STRING_VAZIA, indNome + "3");
    ArrayList<String> vetorEstados = atm1.getEstados();
    for (int i = 0; i < vetorEstados.size(); i++) {
      String nomeEstado = vetorEstados.get(i);
      atmNovo.criaEstado(nomeEstado, 140.0D, (140 + i));
    } 
    atmNovo.criaTransicao(indNome + "2", Constantes.STRING_VAZIA, atm1.getEstadoInicial());
    HashSet hashRec = atm1.getEstadosReconhecedores();
    Iterator<String> itNaveg = hashRec.iterator();
    String estadoRec = itNaveg.next();
    atmNovo.criaTransicao(estadoRec, Constantes.STRING_VAZIA, indNome + "2");
    ArrayList<Transicao> transicoes = atm1.getMatrizTransicoes().getTransicoes();
    for (int j = 0; j < transicoes.size(); j++) {
      Transicao tr = transicoes.get(j);
      atmNovo.criaTransicao(tr.getEstadoEntrada(), tr.getValorEntrada(), tr.getEstadoSaida());
    } 
    return atmNovo;
  }
  
  private void renomeiaEstados(Automato atm) {
    ArrayList<String> estadosAnt = atm.getEstados();
    for (int i = 0; i < estadosAnt.size(); i++) {
      String nomeAtual = estadosAnt.get(i);
      String novoNome = "q" + i;
      if (!novoNome.equals(nomeAtual)) {
        if (atm.existeEstado(novoNome))
          atm.renomeiaEstado(novoNome, "x" + i); 
        atm.renomeiaEstado(nomeAtual, novoNome);
      } 
    } 
  }
  
  public Automato importaGramaticaReglar(ArrayList<String> linhasArquivo) {
    Automato automatoResultante = new Automato();
    ArrayList<Transicao> transicoesGramatica = new ArrayList();
    String nomeNovoEstado = "_N";
    for (int i = 0; i < linhasArquivo.size(); i++) {
      String str1, str2, ln = linhasArquivo.get(i);
      int posSep = ln.indexOf("::=");
      String ladoEsquerdo = ln.substring(0, posSep);
      ladoEsquerdo = ladoEsquerdo.trim();
      String ladoDireito = ln.substring(posSep + 3, ln.length());
      ladoDireito = ladoDireito.trim();
      ladoEsquerdo = ladoEsquerdo.substring(1, ladoEsquerdo.length() - 1);
      String estadoEntrada = ladoEsquerdo;
      if (ladoDireito.length() == 1) {
        if (ladoDireito.equals(")") {
          str1 = ladoDireito;
          str2 = null;
        } else {
          transicoesGramatica.add(new Transicao(estadoEntrada, ladoDireito, nomeNovoEstado));
          estadoEntrada = nomeNovoEstado;
          str1 = ";";
          str2 = null;
        } 
      } else {
        posSep = ladoDireito.indexOf("|");
        if (posSep != -1) {
          String novaLinha = "<" + ladoEsquerdo + "> ::= " + ladoDireito.substring(posSep + 1, ladoDireito.length());
          linhasArquivo.add(novaLinha);
          ladoDireito = ladoDireito.substring(0, posSep);
        } 
        str1 = "" + ladoDireito.charAt(0);
        ladoDireito = ladoDireito.substring(2, ladoDireito.length() - 1);
        str2 = ladoDireito;
      } 
      transicoesGramatica.add(new Transicao(estadoEntrada, str1, str2));
    } 
    for (int j = 0; j < transicoesGramatica.size(); j++) {
      Transicao tg = transicoesGramatica.get(j);
      automatoResultante.criaEstado(tg.getEstadoEntrada(), 50.0D, 50.0D);
      if (tg.getEstadoSaida() != null) {
        automatoResultante.criaEstado(tg.getEstadoSaida(), 50.0D, 50.0D);
        automatoResultante.criaTransicao(tg.getEstadoEntrada(), tg.getValorEntrada(), tg.getEstadoSaida());
      } else {
        automatoResultante.adicionaEstadoReconhecedor(tg.getEstadoEntrada());
      } 
    } 
    return automatoResultante;
  }
  
  public String geraExpressaoRegular() {
    ArrayList<String> estados = this.automatoAtual.getEstados();
    ArrayList<Object[]> transicoesChegada = new ArrayList();
    ArrayList<String> transicoesLaco = new ArrayList();
    ArrayList<Object[]> transicoesSaida = new ArrayList();
    String expAtual = "";
    String expSaida = "";
    String estIniAnt = this.automatoAtual.getEstadoInicial();
    String nomeNovoIni = this.automatoAtual.criaEstado(10.0D, 100.0D);
    this.automatoAtual.setEstadoInicial(nomeNovoIni);
    this.automatoAtual.criaTransicao(nomeNovoIni, Constantes.STRING_VAZIA, estIniAnt);
    String nomeNovoRec = this.automatoAtual.criaEstado(10.0D, 100.0D);
    HashSet vetorEstRecAnt = this.automatoAtual.getEstadosReconhecedores();
    Iterator<String> nav = vetorEstRecAnt.iterator();
    String[] recsRemover = new String[vetorEstRecAnt.size()];
    int cr = 0;
    while (nav.hasNext()) {
      String nomeEst = nav.next();
      this.automatoAtual.criaTransicao(nomeEst, Constantes.STRING_VAZIA, nomeNovoRec);
      recsRemover[cr] = nomeEst;
      cr++;
    } 
    for (cr = 0; cr < recsRemover.length; cr++)
      this.automatoAtual.removeEstadoReconhecedor(recsRemover[cr]); 
    this.automatoAtual.adicionaEstadoReconhecedor(nomeNovoRec);
    int i = 0;
    while (i < estados.size()) {
      String es = estados.get(i);
      if (!this.automatoAtual.isEstadoReconhecedor(es) && !this.automatoAtual.isEstadoInicial(es)) {
        ArrayList<Transicao> arrayList1 = this.automatoAtual.getMatrizTransicoes().getTransicoes();
        transicoesChegada.clear();
        transicoesLaco.clear();
        transicoesSaida.clear();
        for (int j = 0; j < arrayList1.size(); j++) {
          Transicao tr = arrayList1.get(j);
          if (tr.getEstadoSaida().equals(es))
            if (tr.getEstadoEntrada().equals(es)) {
              transicoesLaco.add(tr.getValorEntrada());
            } else {
              adicionaTransicaoEstado(transicoesChegada, tr.getValorEntrada(), tr.getEstadoEntrada());
            }  
        } 
        ArrayList<Transicao> transicoesSaidaIndividuais = retornaTransicoesEstado(arrayList1, es, null);
        if (transicoesSaidaIndividuais.size() > 0)
          for (int k = 0; k < transicoesSaidaIndividuais.size(); k++) {
            Transicao tr = transicoesSaidaIndividuais.get(k);
            adicionaTransicaoEstado(transicoesSaida, tr.getValorEntrada(), tr.getEstadoSaida());
          }  
        for (int n = 0; n < transicoesChegada.size(); n++) {
          Object[] obi = transicoesChegada.get(n);
          ArrayList<String> entradas = (ArrayList)obi[0];
          String nomeEstadoEntrada = (String)obi[1];
          if (entradas.size() > 0) {
            expAtual = "(";
            int x = 0;
            for (; x < entradas.size() - 1; x++)
              expAtual = expAtual + (String)entradas.get(x) + "+"; 
            expAtual = expAtual + (String)entradas.get(x);
            expAtual = expAtual + ")";
          } 
          expSaida = expAtual;
          if (transicoesLaco.size() > 0) {
            expAtual = "(";
            int x = 0;
            for (; x < transicoesLaco.size() - 1; x++)
              expAtual = expAtual + (String)transicoesLaco.get(x) + "+"; 
            expAtual = expAtual + (String)transicoesLaco.get(x);
            expAtual = expAtual + ")*";
            expSaida = expSaida + expAtual;
          } 
          for (int w = 0; w < transicoesSaida.size(); w++) {
            Object[] obi_sai = transicoesSaida.get(w);
            ArrayList<String> entradas_sai = (ArrayList)obi_sai[0];
            String nomeEstadoSaida = (String)obi_sai[1];
            expAtual = "(";
            int x = 0;
            for (; x < entradas_sai.size() - 1; x++)
              expAtual = expAtual + (String)entradas_sai.get(x) + "+"; 
            expAtual = expAtual + (String)entradas_sai.get(x);
            expAtual = expAtual + ")";
            String expFinal = expSaida + expAtual;
            this.automatoAtual.criaTransicao(nomeEstadoEntrada, expFinal, nomeEstadoSaida);
          } 
        } 
        this.automatoAtual.deletaEstado(es);
        estados = this.automatoAtual.getEstados();
        continue;
      } 
      i++;
    } 
    expSaida = "";
    String lacoEstIni = montaExpressaoLacoEstado(this.automatoAtual.getEstadoInicial());
    if (lacoEstIni.length() > 0)
      expSaida = expSaida + lacoEstIni + "*("; 
    ArrayList<Transicao> transicoes = this.automatoAtual.getMatrizTransicoes().getTransicoes();
    ArrayList transicoesInicialParaReconhecedor = new ArrayList();
    i = 0;
    for (; i < transicoes.size() - 1; i++) {
      Transicao tr = transicoes.get(i);
      if (tr.getEstadoEntrada().equals(this.automatoAtual.getEstadoInicial()) && 
        !tr.getEstadoSaida().equals(this.automatoAtual.getEstadoInicial()))
        expSaida = expSaida + tr.getValorEntrada() + "+"; 
    } 
    Transicao transicao = transicoes.get(i);
    if (transicao.getEstadoEntrada().equals(this.automatoAtual.getEstadoInicial()) && 
      !transicao.getEstadoSaida().equals(this.automatoAtual.getEstadoInicial()))
      expSaida = expSaida + transicao.getValorEntrada(); 
    if (lacoEstIni.length() > 0)
      expSaida = expSaida + ")"; 
    expSaida = expSaida.trim();
    boolean modificado = true;
    while (modificado) {
      modificado = false;
      int contDif = 0;
      char[] vetorExp = expSaida.toCharArray();
      for (int e = 0; e < vetorExp.length; e++) {
        if (vetorExp[e] != ')') {
          if (vetorExp[e] == '(') {
            contDif = 0;
          } else {
            contDif++;
          } 
        } else if (contDif < 2) {
          expSaida = "";
          for (int j = 0; j < vetorExp.length; j++) {
            if (j != e && j != e - contDif - 1)
              expSaida = expSaida + vetorExp[j] + ""; 
          } 
          modificado = true;
          break;
        } 
      } 
    } 
    return expSaida;
  }
  
  private String montaExpressaoLacoEstado(String nomeEstado) {
    String expCompleta = "";
    String expInterna = "";
    ArrayList transicoes = this.automatoAtual.getMatrizTransicoes().getTransicoes();
    ArrayList<Transicao> transEstado = retornaTransicoesEstado(transicoes, nomeEstado, null);
    for (int e = 0; e < transEstado.size(); e++) {
      Transicao tr = transEstado.get(e);
      if (tr.getEstadoSaida().equals(nomeEstado))
        if (expInterna.length() == 0) {
          expInterna = tr.getValorEntrada();
        } else {
          expInterna = expInterna + "+" + tr.getValorEntrada();
        }  
    } 
    if (expInterna.length() > 0)
      expCompleta = "(" + expInterna + ")"; 
    return expCompleta;
  }
  
  private void adicionaTransicaoEstado(ArrayList<Object[]> transicoesEntrada, String entradaAtual, String nomeEstado) {
    for (int i = 0; i < transicoesEntrada.size(); i++) {
      Object[] arrayOfObject = transicoesEntrada.get(i);
      String nomeBusca = (String)arrayOfObject[1];
      if (nomeBusca.equals(nomeEstado)) {
        ArrayList<String> transEst = (ArrayList)arrayOfObject[0];
        transEst.add(entradaAtual);
        transicoesEntrada.set(i, arrayOfObject);
        return;
      } 
    } 
    Object[] obi = new Object[2];
    ArrayList<String> a = new ArrayList();
    a.add(entradaAtual);
    obi[0] = a;
    obi[1] = nomeEstado;
    transicoesEntrada.add(obi);
  }
  
  public String geraGramaticaRegular() {
    ArrayList transicoes = this.automatoAtual.getMatrizTransicoes().getTransicoes();
    ArrayList<String> estados = this.automatoAtual.getEstados();
    String gramSaida = "";
    for (int i = 0; i < estados.size(); i++) {
      String es = estados.get(i);
      ArrayList<Transicao> transEst = retornaTransicoesEstado(transicoes, es, null);
      for (int j = 0; j < transEst.size(); j++) {
        Transicao tr = transEst.get(j);
        gramSaida = gramSaida + "<" + tr.getEstadoEntrada() + "> ::= " + tr.getValorEntrada() + "<" + tr.getEstadoSaida() + ">\n";
      } 
      if (this.automatoAtual.isEstadoReconhecedor(es))
        gramSaida = gramSaida + "<" + es + "> ::= " + Constantes.STRING_VAZIA + "\n"; 
    } 
    return gramSaida;
  }
  
  public String geraProgramaReconhecedor(String nomeClasse) {
    ArrayList<Transicao> transicoes = this.automatoAtual.getMatrizTransicoes().getTransicoes();
    ArrayList<String> estados = this.automatoAtual.getEstados();
    HashSet reconhecedores = this.automatoAtual.getEstadosReconhecedores();
    String progGerado = "";
    progGerado = progGerado + "import java.awt.Dimension;\n";
    progGerado = progGerado + "import java.awt.Rectangle;\n";
    progGerado = progGerado + "import java.awt.event.ActionEvent;\n";
    progGerado = progGerado + "import java.awt.event.ActionListener;\n";
    progGerado = progGerado + "import java.awt.event.WindowAdapter;\n";
    progGerado = progGerado + "import java.awt.event.WindowEvent;\n";
    progGerado = progGerado + "import javax.swing.JButton;\n";
    progGerado = progGerado + "import javax.swing.JFrame;\n";
    progGerado = progGerado + "import javax.swing.JLabel;\n";
    progGerado = progGerado + "import javax.swing.JOptionPane;\n";
    progGerado = progGerado + "import javax.swing.JTextField;\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "public class " + nomeClasse + " extends JFrame\n";
    progGerado = progGerado + "{\n";
    progGerado = progGerado + "\tprivate JButton btnVerificar = new JButton();\n";
    progGerado = progGerado + "\tprivate JLabel jLabel1 = new JLabel();\n";
    progGerado = progGerado + "\tprivate JTextField txfSentenca = new JTextField();\n";
    progGerado = progGerado + "\tprivate String[] alfabeto = {";
    LinkedHashSet<String> entradas = new LinkedHashSet();
    int nrGer = 0;
    for (int i = 0; i < transicoes.size(); i++) {
      Transicao tr = transicoes.get(i);
      if (entradas.add(tr.getValorEntrada())) {
        if (nrGer == 0) {
          progGerado = progGerado + "\"" + tr.getValorEntrada() + "\"";
        } else {
          progGerado = progGerado + ", \"" + tr.getValorEntrada() + "\"";
        } 
        nrGer++;
      } 
    } 
    progGerado = progGerado + "};\n";
    progGerado = progGerado + "\tprivate String[] estados = {";
    for (int j = 0; j < estados.size(); j++) {
      String est = estados.get(j);
      if (j == 0) {
        progGerado = progGerado + "\"" + est + "\"";
      } else {
        progGerado = progGerado + ", \"" + est + "\"";
      } 
    } 
    progGerado = progGerado + "};\n";
    progGerado = progGerado + "\tprivate String[] estadosReconhecedores = {";
    Iterator<String> nav = reconhecedores.iterator();
    nrGer = 0;
    while (nav.hasNext()) {
      String est = nav.next();
      if (nrGer == 0) {
        progGerado = progGerado + "\"" + est + "\"";
      } else {
        progGerado = progGerado + ", \"" + est + "\"";
      } 
      nrGer++;
    } 
    progGerado = progGerado + "};\n";
    progGerado = progGerado + "\tprivate String[][] matrizTransicao = new String[";
    progGerado = progGerado + entradas.size() + "";
    progGerado = progGerado + "][";
    progGerado = progGerado + estados.size() + "";
    progGerado = progGerado + "];\n";
    progGerado = progGerado + "\tprivate String estadoInicial = ";
    progGerado = progGerado + "\"" + this.automatoAtual.getEstadoInicial() + "\";\n";
    progGerado = progGerado + "\tprivate String estadoAtual;\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "\tprivate final int TRANSICAO_ENCONTRADA  = 0;\n";
    progGerado = progGerado + "\tprivate final int ENTRADA_INEXISTENTE   = 1;\n";
    progGerado = progGerado + "\tprivate final int TRANSICAO_INEXISTENTE = 2;\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "\tpublic " + nomeClasse + "()\n";
    progGerado = progGerado + "\t{\n";
    progGerado = progGerado + "\t\ttry\n";
    progGerado = progGerado + "\t\t{\n";
    progGerado = progGerado + "\t\t\tiniciaPrograma();\n";
    progGerado = progGerado + "\t\t}\n";
    progGerado = progGerado + "\t\tcatch(Exception e)\n";
    progGerado = progGerado + "\t\t{\n";
    progGerado = progGerado + "\t\t\te.printStackTrace();\n";
    progGerado = progGerado + "\t\t}\n";
    progGerado = progGerado + "\t}\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "\tprivate void iniciaPrograma() throws Exception\n";
    progGerado = progGerado + "\t{\n";
    progGerado = progGerado + "\t\tthis.getContentPane().setLayout(null);\n";
    progGerado = progGerado + "\t\tthis.setSize(new Dimension(400, 155));\n";
    progGerado = progGerado + "\t\tthis.setTitle(\"Programa reconhecedor\");\n";
    progGerado = progGerado + "\t\tthis.setResizable(false);\n";
    progGerado = progGerado + "\t\tbtnVerificar.setText(\"Verificar\");\n";
    progGerado = progGerado + "\t\tbtnVerificar.setBounds(new Rectangle(145, 80, 105, 35));\n";
    progGerado = progGerado + "\t\tbtnVerificar.setMnemonic('V');\n";
    progGerado = progGerado + "\t\tbtnVerificar.addActionListener(new ActionListener()\n";
    progGerado = progGerado + "\t\t\t{\n";
    progGerado = progGerado + "\t\t\t\tpublic void actionPerformed(ActionEvent e)\n";
    progGerado = progGerado + "\t\t\t\t{\n";
    progGerado = progGerado + "\t\t\t\tbtnVerificar_actionPerformed(e);\n";
    progGerado = progGerado + "\t\t\t\t}\n";
    progGerado = progGerado + "\t\t\t});\n";
    progGerado = progGerado + "\t\tjLabel1.setText(\"Senten\");\n";
    progGerado = progGerado + "\t\tjLabel1.setBounds(new Rectangle(15, 20, 195, 15));\n";
    progGerado = progGerado + "\t\ttxfSentenca.setBounds(new Rectangle(15, 35, 365, 20));\n";
    progGerado = progGerado + "\t\tthis.getContentPane().add(txfSentenca, null);\n";
    progGerado = progGerado + "\t\tthis.getContentPane().add(jLabel1, null);\n";
    progGerado = progGerado + "\t\tthis.getContentPane().add(btnVerificar, null);\n";
    progGerado = progGerado + "\t\tinicializaAutomato();\n";
    progGerado = progGerado + "\t}\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "\tprivate void inicializaAutomato()\n";
    progGerado = progGerado + "\t{\n";
    progGerado = progGerado + "\t\tfor (int i=0; i<alfabeto.length; i++)\n";
    progGerado = progGerado + "\t\t\tfor (int j=0; j<estados.length; j++)\n";
    progGerado = progGerado + "\t\t\t\tmatrizTransicao[i][j] = null;\n";
    nav = entradas.iterator();
    nrGer = 0;
    while (nav.hasNext()) {
      String entAtu = nav.next();
      for (int k = 0; k < estados.size(); k++) {
        String est = estados.get(k);
        ArrayList<Transicao> transEst = retornaTransicoesEstado(transicoes, est, entAtu);
        if (transEst.size() > 0) {
          Transicao tr = transEst.get(0);
          String estSaida = tr.getEstadoSaida();
          progGerado = progGerado + "\t\tmatrizTransicao[" + nrGer + "][" + k + "] = \"" + estSaida + "\";  //<" + est + "> ::= " + entAtu + "<" + estSaida + ">\n";
        } 
      } 
      nrGer++;
    } 
    progGerado = progGerado + "\t}\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "\tprivate int executaTransicao(String entrada)\n";
    progGerado = progGerado + "\t{\n";
    progGerado = progGerado + "\t\tint resultado = TRANSICAO_INEXISTENTE;\n";
    progGerado = progGerado + "\t\tint indEntrada, indEstado;\n";
    progGerado = progGerado + "\t\tindEntrada = getIndiceEntrada(entrada);\n";
    progGerado = progGerado + "\t\tif (indEntrada==-1)\n";
    progGerado = progGerado + "\t\t\treturn ENTRADA_INEXISTENTE;\n";
    progGerado = progGerado + "\t\tindEstado = getIndiceEstado(estadoAtual);\n";
    progGerado = progGerado + "\t\tString estadoSaida = matrizTransicao[indEntrada][indEstado];\n";
    progGerado = progGerado + "\t\tif (estadoSaida!=null)\n";
    progGerado = progGerado + "\t\t{\n";
    progGerado = progGerado + "\t\t\testadoAtual = estadoSaida;\n";
    progGerado = progGerado + "\t\t\tresultado = TRANSICAO_ENCONTRADA;\n";
    progGerado = progGerado + "\t\t}\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "\t\treturn resultado;\n";
    progGerado = progGerado + "\t}\n";
    progGerado = progGerado + "\tprivate int getIndiceEntrada(String entrada)\n";
    progGerado = progGerado + "\t{\n";
    progGerado = progGerado + "\t\tfor (int i=0; i<alfabeto.length; i++)\n";
    progGerado = progGerado + "\t\t\tif (alfabeto[i].equals(entrada))\n";
    progGerado = progGerado + "\t\t\treturn i;\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "\t\treturn -1;\n";
    progGerado = progGerado + "\t}\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "\tprivate int getIndiceEstado(String estado)\n";
    progGerado = progGerado + "\t{\n";
    progGerado = progGerado + "\t\tfor (int i=0; i<estados.length; i++)\n";
    progGerado = progGerado + "\t\t\tif (estados[i].equals(estado))\n";
    progGerado = progGerado + "\t\t\t\treturn i;\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "\t\treturn -1;\n";
    progGerado = progGerado + "\t}\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "\tprivate void btnVerificar_actionPerformed(ActionEvent e)\n";
    progGerado = progGerado + "\t{\n";
    progGerado = progGerado + "\t\tString valorEntrada = txfSentenca.getText();\n";
    progGerado = progGerado + "\t\tString entradaAtual = null;\n";
    progGerado = progGerado + "\t\tint resultado = TRANSICAO_ENCONTRADA;\n";
    progGerado = progGerado + "\t\tint i=0;\n";
    progGerado = progGerado + "\t\testadoAtual = estadoInicial;\n";
    progGerado = progGerado + "\t\twhile ( (i<valorEntrada.length()) && (resultado==TRANSICAO_ENCONTRADA) )\n";
    progGerado = progGerado + "\t\t{\n";
    progGerado = progGerado + "\t\t\tentradaAtual = valorEntrada.charAt(i) + \"\";\n";
    progGerado = progGerado + "\t\t\tresultado = executaTransicao(entradaAtual);\n";
    progGerado = progGerado + "\t\t\ti++;\n";
    progGerado = progGerado + "\t\t}\n";
    progGerado = progGerado + "\t\tswitch(resultado)\n";
    progGerado = progGerado + "\t\t{\n";
    progGerado = progGerado + "\t\t\tcase TRANSICAO_INEXISTENTE : JOptionPane.showMessageDialog(null, \"Nfoi possencontrar uma transicom a entrada \\\"\" + entradaAtual + \"\\\".\\nSentennreconhecida!\", \"Resultado\", JOptionPane.ERROR_MESSAGE);\n";
    progGerado = progGerado + "\t\t\t\t\t\t\t\t\t\tbreak;\n";
    progGerado = progGerado + "\t\t\tcase ENTRADA_INEXISTENTE :   JOptionPane.showMessageDialog(null, \"A entrada \\\"\" + entradaAtual + \"\\\" npertence ao alfabeto.\\nSentennreconhecida!\", \"Resultado\", JOptionPane.ERROR_MESSAGE);\n";
    progGerado = progGerado + "\t\t\t\t\t\t\t\t\t\tbreak;\n";
    progGerado = progGerado + "\t\t\tdefault : if ( isReconhecedor(estadoAtual) )\n";
    progGerado = progGerado + "\t\t\t\t\t\tJOptionPane.showMessageDialog(null, \"Sentenreconhecida!\", \"Resultado\", JOptionPane.INFORMATION_MESSAGE);\n";
    progGerado = progGerado + "\t\t\t\t\telse\n";
    progGerado = progGerado + "\t\t\t\t\t\tJOptionPane.showMessageDialog(null, \"Sentennreconhecida!\", \"Resultado\", JOptionPane.ERROR_MESSAGE);\n";
    progGerado = progGerado + "\t\t}\n";
    progGerado = progGerado + "\t}\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "\tprivate boolean isReconhecedor(String estado)\n";
    progGerado = progGerado + "\t{\n";
    progGerado = progGerado + "\t\tfor (int i=0; i<estadosReconhecedores.length; i++)\n";
    progGerado = progGerado + "\t\t{\n";
    progGerado = progGerado + "\t\t\tif (estadosReconhecedores[i].equals(estado))\n";
    progGerado = progGerado + "\t\t\t\treturn true;\n";
    progGerado = progGerado + "\t\t}\n";
    progGerado = progGerado + "\t\treturn false;\n";
    progGerado = progGerado + "\t}\n";
    progGerado = progGerado + "\n";
    progGerado = progGerado + "\tpublic static void main(String[] args)\n";
    progGerado = progGerado + "\t{\n";
    progGerado = progGerado + "\t\t" + nomeClasse + " f1 = new " + nomeClasse + "();\n";
    progGerado = progGerado + "\t\tf1.addWindowListener(new WindowAdapter()\n";
    progGerado = progGerado + "\t\t\t{\n";
    progGerado = progGerado + "\t\t\t\tpublic void windowClosing(WindowEvent e)\n";
    progGerado = progGerado + "\t\t\t\t{\n";
    progGerado = progGerado + "\t\t\t\t\tSystem.exit(0);\n";
    progGerado = progGerado + "\t\t\t\t}\n";
    progGerado = progGerado + "\t\t\t});\n";
    progGerado = progGerado + "\t\tf1.show();\n";
    progGerado = progGerado + "\t}\n";
    progGerado = progGerado + "}";
    return progGerado;
  }
  
  public String simplificaExpressaoRegular(String expressaoRegular) {
    int indVazio = -1;
    do {
      indVazio = expressaoRegular.indexOf(Constantes.STRING_VAZIA, indVazio + 1);
      if (indVazio == -1)
        continue; 
      if (indVazio > 0 && expressaoRegular.charAt(indVazio - 1) == '+')
        continue; 
      if (indVazio < expressaoRegular.length() - 1 && expressaoRegular.charAt(indVazio + 1) == '+')
        continue; 
      expressaoRegular = expressaoRegular.substring(0, indVazio) + expressaoRegular.substring(indVazio + 1);
    } while (indVazio != -1);
    this.expressaoRegularSimplificacao = expressaoRegular;
    this.listaParentesesExcluir = new ArrayList<ParenteseExcluir>();
    marcaParentesesDesnecessariosER(0);
    for (int i = 0; i < this.listaParentesesExcluir.size(); i++) {
      ParenteseExcluir parAtual = this.listaParentesesExcluir.get(i);
      expressaoRegular = expressaoRegular.substring(0, parAtual.indiceAbertura) + expressaoRegular.substring(parAtual.indiceAbertura + 1);
      expressaoRegular = expressaoRegular.substring(0, parAtual.indiceFechamento - 1) + expressaoRegular.substring(parAtual.indiceFechamento);
      for (int j = i + 1; j < this.listaParentesesExcluir.size(); j++) {
        ParenteseExcluir parBusca = this.listaParentesesExcluir.get(j);
        if (parBusca.indiceAbertura > parAtual.indiceAbertura)
          parBusca.indiceAbertura--; 
        if (parBusca.indiceAbertura > parAtual.indiceFechamento)
          parBusca.indiceAbertura--; 
        if (parBusca.indiceFechamento > parAtual.indiceAbertura)
          parBusca.indiceFechamento--; 
        if (parBusca.indiceFechamento > parAtual.indiceFechamento)
          parBusca.indiceFechamento--; 
      } 
    } 
    this.expressaoRegularSimplificacao = null;
    this.listaParentesesExcluir = null;
    return expressaoRegular;
  }
  
  private RelatorioParentese marcaParentesesDesnecessariosER(int indiceInicial) {
    RelatorioParentese relRetorno = new RelatorioParentese();
    for (int i = indiceInicial; i < this.expressaoRegularSimplificacao.length(); i++) {
      if (this.expressaoRegularSimplificacao.charAt(i) == '(') {
        RelatorioParentese rel = marcaParentesesDesnecessariosER(i + 1);
        if (rel.podeExcluir) {
          this.listaParentesesExcluir.add(new ParenteseExcluir(i, rel.indiceParenteseFechado));
          i = rel.indiceParenteseFechado;
        } else if (i > 0 && this.expressaoRegularSimplificacao.charAt(i - 1) == '(' && 
          rel.indiceParenteseFechado < this.expressaoRegularSimplificacao.length() - 1 && 
          this.expressaoRegularSimplificacao.charAt(rel.indiceParenteseFechado + 1) == ')') {
          this.listaParentesesExcluir.add(new ParenteseExcluir(i, rel.indiceParenteseFechado));
          i = rel.indiceParenteseFechado;
        } else {
          i = rel.indiceParenteseFechado;
        } 
      } else if (this.expressaoRegularSimplificacao.charAt(i) == '+') {
        relRetorno.possuiOperadorOU = true;
      } else if (this.expressaoRegularSimplificacao.charAt(i) == ')') {
        relRetorno.indiceParenteseFechado = i;
        int tamanhoExpressao = i - indiceInicial;
        if (tamanhoExpressao <= 1) {
          relRetorno.podeExcluir = true;
          break;
        } 
        relRetorno.possuiMaisDeUmSimbolo = false;
        break;
      } 
    } 
    return relRetorno;
  }
}
