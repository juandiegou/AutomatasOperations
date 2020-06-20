package edu.umd.cs.piccolo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class PObjectOutputStream extends ObjectOutputStream {
  private boolean writingRoot;
  
  private HashMap unconditionallyWritten = new HashMap();
  
  public static byte[] toByteArray(Object paramObject) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PObjectOutputStream pObjectOutputStream = new PObjectOutputStream(byteArrayOutputStream);
    pObjectOutputStream.writeObjectTree(paramObject);
    return byteArrayOutputStream.toByteArray();
  }
  
  public PObjectOutputStream(OutputStream paramOutputStream) throws IOException {
    super(paramOutputStream);
  }
  
  public void writeObjectTree(Object paramObject) throws IOException {
    this.writingRoot = true;
    recordUnconditionallyWritten(paramObject);
    writeObject(paramObject);
    this.writingRoot = false;
  }
  
  public void writeConditionalObject(Object paramObject) throws IOException {
    if (!this.writingRoot)
      throw new RuntimeException("writeConditionalObject() may only be called when a root object has been written."); 
    if (this.unconditionallyWritten.containsKey(paramObject)) {
      writeObject(paramObject);
    } else {
      writeObject(null);
    } 
  }
  
  public void reset() throws IOException {
    super.reset();
    this.unconditionallyWritten.clear();
  }
  
  protected void recordUnconditionallyWritten(Object paramObject) throws IOException {
    class ZMarkObjectOutputStream extends PObjectOutputStream {
      private final PObjectOutputStream this$0;
      
      public ZMarkObjectOutputStream(PObjectOutputStream this$0) throws IOException {
        super(PUtil.NULL_OUTPUT_STREAM);
        this.this$0 = this$0;
        enableReplaceObject(true);
      }
      
      public Object replaceObject(Object param1Object) {
        this.this$0.unconditionallyWritten.put(param1Object, Boolean.TRUE);
        return param1Object;
      }
      
      public void writeConditionalObject(Object param1Object) throws IOException {}
    };
    (new ZMarkObjectOutputStream(this)).writeObject(paramObject);
  }
}
