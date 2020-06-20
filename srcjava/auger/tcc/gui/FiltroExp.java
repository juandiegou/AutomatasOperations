package auger.tcc.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FiltroExp extends FileFilter {
  public boolean accept(File f) {
    if (f.isDirectory())
      return true; 
    String extensao = f.getPath();
    if (extensao != null) {
      extensao = extensao.toLowerCase().substring(extensao.length() - 3, extensao.length());
      if (extensao.equals("exp"))
        return true; 
      return false;
    } 
    return false;
  }
  
  public String getDescription() {
    return "Arquivos de expressregular (*.exp)";
  }
  
  public String toString() {
    return "Filtro EXP (*.exp)";
  }
}