package auger.tcc.gui;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.nodes.PImage;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ExportaImagem {
  public static void salvaJPG(String local, PCanvas p) throws Exception {
    p.getCamera().setVisible(false);
    int largura = 1268;
    int altura = 1024;
    Image mapImage = p.getRoot().toImage(largura, altura, Color.white);
    BufferedImage mapBuffImage = PImage.toBufferedImage(mapImage, true);
    try {
      File file = new File(local);
      ImageIO.write(mapBuffImage, "jpg", file);
    } catch (Exception e) {
      throw new Exception("Erro ao exportar arquivo: " + e);
    } finally {
      p.getCamera().setVisible(true);
    } 
  }
}
