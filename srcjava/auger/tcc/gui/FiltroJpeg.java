package auger.tcc.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FiltroJava extends FileFilter {
  public boolean accept(File f) {
    if (f.isDirectory())
      return true; 
    String extensao = f.getPath();
    if (extensao != null) {
      extensao = extensao.toLowerCase().substring(extensao.length() - 4, extensao.length());
      if (extensao.equals("java"))
        return true; 
      return false;
    } 
    return false;
  }
  
  public String getDescription() {
    return "Arquivos Java (*.java)";
  }
  
  public String toString() {
    return "Filtro JAVA (*.java)";
  }
}