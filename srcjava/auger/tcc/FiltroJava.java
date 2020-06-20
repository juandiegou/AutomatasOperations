package auger.tcc;

import java.io.File;
import javax.swing.filechooser.FileFilter;

class FiltroJava extends FileFilter {
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
    return "*.java";
  }
}
