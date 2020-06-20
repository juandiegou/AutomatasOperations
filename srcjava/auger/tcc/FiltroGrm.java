package auger.tcc;

import java.io.File;
import javax.swing.filechooser.FileFilter;

class FiltroGrm extends FileFilter {
  public boolean accept(File f) {
    if (f.isDirectory())
      return true; 
    String extensao = f.getPath();
    if (extensao != null) {
      extensao = extensao.toLowerCase().substring(extensao.length() - 3, extensao.length());
      if (extensao.equals("grm"))
        return true; 
      return false;
    } 
    return false;
  }
  
  public String getDescription() {
    return "*.grm";
  }
}
