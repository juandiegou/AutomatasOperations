package auger.tcc;

import auger.tcc.gui.Editor;
import auger.tcc.gui.UIManagerPut;
import java.awt.Component;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Auger {
  public static void main(String[] args) {
    Editor princ = new Editor();
    try {
      UIManagerPut.putValoresTraduzidos();
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      SwingUtilities.updateComponentTreeUI((Component)princ);
    } catch (UnsupportedLookAndFeelException ex1) {
      JOptionPane.showMessageDialog(null, "Skin nsuportado!\n\nDescrido erro:\n" + ex1, "Erro", 0);
    } catch (IllegalAccessException e) {
      JOptionPane.showMessageDialog(null, "Skin nsuportado!\n\nDescrido erro:\n" + e, "Erro", 0);
    } catch (ClassNotFoundException e) {
      JOptionPane.showMessageDialog(null, "Skin nsuportado!\n\nDescrido erro:\n" + e, "Erro", 0);
    } catch (InstantiationException e) {
      JOptionPane.showMessageDialog(null, "Skin nsuportado!\n\nDescrido erro:\n" + e, "Erro", 0);
    } 
    princ.setVisible(true);
  }
  
  public static byte[] getImagem(String endereco) {
    try {
      InputStream in = getClassLoader().getResourceAsStream(endereco);
      if (in != null) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
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
  
  public static byte[] objectToByteArray(Object obj) {
    if (obj == null)
      return null; 
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(obj);
      return baos.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public static Object byteArrayToObject(byte[] vetorBytes) {
    try {
      ByteArrayInputStream bais = new ByteArrayInputStream(vetorBytes, 0, vetorBytes.length);
      ObjectInputStream oos = new ObjectInputStream(bais);
      return oos.readObject();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } 
    return null;
  }
  
  public static ClassLoader getClassLoader() {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader == null)
      classLoader = ClassLoader.getSystemClassLoader(); 
    return classLoader;
  }
}