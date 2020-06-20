package edu.umd.cs.piccolo.util;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.PRoot;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;

public class PUtil {
  public static Iterator NULL_ITERATOR = Collections.EMPTY_LIST.iterator();
  
  public static Enumeration NULL_ENUMERATION = new Enumeration() {
      public boolean hasMoreElements() {
        return false;
      }
      
      public Object nextElement() {
        return null;
      }
    };
  
  public static long DEFAULT_ACTIVITY_STEP_RATE = 20L;
  
  public static int ACTIVITY_SCHEDULER_FRAME_DELAY = 10;
  
  public static OutputStream NULL_OUTPUT_STREAM = new OutputStream() {
      public void close() {}
      
      public void flush() {}
      
      public void write(byte[] param1ArrayOfbyte) {}
      
      public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {}
      
      public void write(int param1Int) {}
    };
  
  private static final int PATH_IS_DONE = -1;
  
  public static PCamera createBasicScenegraph() {
    PRoot pRoot = new PRoot();
    PLayer pLayer = new PLayer();
    PCamera pCamera = new PCamera();
    pRoot.addChild((PNode)pCamera);
    pRoot.addChild((PNode)pLayer);
    pCamera.addLayer(pLayer);
    return pCamera;
  }
  
  public static void writeStroke(Stroke paramStroke, ObjectOutputStream paramObjectOutputStream) throws IOException {
    if (paramStroke instanceof java.io.Serializable) {
      paramObjectOutputStream.writeBoolean(true);
      paramObjectOutputStream.writeBoolean(true);
      paramObjectOutputStream.writeObject(paramStroke);
    } else if (paramStroke instanceof BasicStroke) {
      paramObjectOutputStream.writeBoolean(true);
      paramObjectOutputStream.writeBoolean(false);
      BasicStroke basicStroke = (BasicStroke)paramStroke;
      float[] arrayOfFloat = basicStroke.getDashArray();
      if (arrayOfFloat == null) {
        paramObjectOutputStream.write(0);
      } else {
        paramObjectOutputStream.write(arrayOfFloat.length);
        for (byte b = 0; b < arrayOfFloat.length; b++)
          paramObjectOutputStream.writeFloat(arrayOfFloat[b]); 
      } 
      paramObjectOutputStream.writeFloat(basicStroke.getLineWidth());
      paramObjectOutputStream.writeInt(basicStroke.getEndCap());
      paramObjectOutputStream.writeInt(basicStroke.getLineJoin());
      paramObjectOutputStream.writeFloat(basicStroke.getMiterLimit());
      paramObjectOutputStream.writeFloat(basicStroke.getDashPhase());
    } else {
      paramObjectOutputStream.writeBoolean(false);
    } 
  }
  
  public static Stroke readStroke(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    boolean bool = paramObjectInputStream.readBoolean();
    if (bool) {
      boolean bool1 = paramObjectInputStream.readBoolean();
      if (bool1)
        return (Stroke)paramObjectInputStream.readObject(); 
      float[] arrayOfFloat = null;
      int i = paramObjectInputStream.read();
      if (i != 0) {
        arrayOfFloat = new float[i];
        for (byte b = 0; b < i; b++)
          arrayOfFloat[b] = paramObjectInputStream.readFloat(); 
      } 
      float f1 = paramObjectInputStream.readFloat();
      int j = paramObjectInputStream.readInt();
      int k = paramObjectInputStream.readInt();
      float f2 = paramObjectInputStream.readFloat();
      float f3 = paramObjectInputStream.readFloat();
      return new BasicStroke(f1, j, k, f2, arrayOfFloat, f3);
    } 
    return null;
  }
  
  public static GeneralPath readPath(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
    GeneralPath generalPath = new GeneralPath();
    while (true) {
      int i = paramObjectInputStream.readInt();
      switch (i) {
        case 0:
          generalPath.moveTo(paramObjectInputStream.readFloat(), paramObjectInputStream.readFloat());
          continue;
        case 1:
          generalPath.lineTo(paramObjectInputStream.readFloat(), paramObjectInputStream.readFloat());
          continue;
        case 2:
          generalPath.quadTo(paramObjectInputStream.readFloat(), paramObjectInputStream.readFloat(), paramObjectInputStream.readFloat(), paramObjectInputStream.readFloat());
          continue;
        case 3:
          generalPath.curveTo(paramObjectInputStream.readFloat(), paramObjectInputStream.readFloat(), paramObjectInputStream.readFloat(), paramObjectInputStream.readFloat(), paramObjectInputStream.readFloat(), paramObjectInputStream.readFloat());
          continue;
        case 4:
          generalPath.closePath();
          continue;
        case -1:
          return generalPath;
      } 
      throw new IOException();
    } 
  }
  
  public static void writePath(GeneralPath paramGeneralPath, ObjectOutputStream paramObjectOutputStream) throws IOException {
    PathIterator pathIterator = paramGeneralPath.getPathIterator((AffineTransform)null);
    float[] arrayOfFloat = new float[6];
    while (!pathIterator.isDone()) {
      switch (pathIterator.currentSegment(arrayOfFloat)) {
        case 0:
          paramObjectOutputStream.writeInt(0);
          paramObjectOutputStream.writeFloat(arrayOfFloat[0]);
          paramObjectOutputStream.writeFloat(arrayOfFloat[1]);
          break;
        case 1:
          paramObjectOutputStream.writeInt(1);
          paramObjectOutputStream.writeFloat(arrayOfFloat[0]);
          paramObjectOutputStream.writeFloat(arrayOfFloat[1]);
          break;
        case 2:
          paramObjectOutputStream.writeInt(2);
          paramObjectOutputStream.writeFloat(arrayOfFloat[0]);
          paramObjectOutputStream.writeFloat(arrayOfFloat[1]);
          paramObjectOutputStream.writeFloat(arrayOfFloat[2]);
          paramObjectOutputStream.writeFloat(arrayOfFloat[3]);
          break;
        case 3:
          paramObjectOutputStream.writeInt(3);
          paramObjectOutputStream.writeFloat(arrayOfFloat[0]);
          paramObjectOutputStream.writeFloat(arrayOfFloat[1]);
          paramObjectOutputStream.writeFloat(arrayOfFloat[2]);
          paramObjectOutputStream.writeFloat(arrayOfFloat[3]);
          paramObjectOutputStream.writeFloat(arrayOfFloat[4]);
          paramObjectOutputStream.writeFloat(arrayOfFloat[5]);
          break;
        case 4:
          paramObjectOutputStream.writeInt(4);
          break;
        default:
          throw new IOException();
      } 
      pathIterator.next();
    } 
    paramObjectOutputStream.writeInt(-1);
  }
}