package auger.tcc;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class Global {
  public byte[] getImagem(String endereco) {
    try {
      File f = new File(endereco);
      if (!f.exists())
        return new byte[0]; 
      InputStream in = getStream(endereco);
      if (in != null) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[2048];
        int n;
        while ((n = in.read(buffer)) > 0)
          out.write(buffer, 0, n); 
        in.close();
        out.flush();
        return out.toByteArray();
      } 
      return new byte[0];
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    } 
  }
  
  private InputStream getStream(String fileName) throws IOException {
    InputStream stream = ClassLoader.getSystemResourceAsStream(fileName);
    if (stream == null)
      stream = getClass().getResourceAsStream(fileName); 
    if (stream == null)
      stream = getClass().getClassLoader().getResourceAsStream(fileName); 
    return stream;
  }
  
  public static void centralizaFrame(JFrame frame, Dimension referencia) {
    int x = (referencia.width - (frame.getSize()).width) / 2;
    if (x < 0)
      x = 0; 
    int y = (referencia.height - (frame.getSize()).height) / 2;
    if (y < 0)
      y = 0; 
    frame.setLocation(x, y);
  }
  
  public static void centralizaFramePelaTela(JFrame frame) {
    Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
    centralizaFrame(frame, tela);
  }
  
  public static void centralizaFrame(JDialog frame, Dimension referencia) {
    int x = (referencia.width - (frame.getSize()).width) / 2;
    if (x < 0)
      x = 0; 
    int y = (referencia.height - (frame.getSize()).height) / 2;
    if (y < 0)
      y = 0; 
    frame.setLocation(x, y);
  }
  
  public static void centralizaFramePelaTela(JDialog frame) {
    Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
    centralizaFrame(frame, tela);
  }
}
