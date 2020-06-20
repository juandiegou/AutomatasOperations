package auger.tcc;

import java.util.HashSet;
import java.util.Iterator;

public class Conjuntos {
  public HashSet diferenca(HashSet primeiro, HashSet segundo) {
    HashSet<String> saida = new HashSet();
    Iterator<String> navegaPrimeiro = primeiro.iterator();
    while (navegaPrimeiro.hasNext()) {
      String item1 = navegaPrimeiro.next();
      if (!segundo.contains(item1))
        saida.add(item1); 
    } 
    return saida;
  }
}
