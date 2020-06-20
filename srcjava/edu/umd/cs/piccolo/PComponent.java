package edu.umd.cs.piccolo;

import edu.umd.cs.piccolo.util.PBounds;
import java.awt.Cursor;

public interface PComponent {
  void repaint(PBounds paramPBounds);
  
  void paintImmediately();
  
  void pushCursor(Cursor paramCursor);
  
  void popCursor();
  
  void setInteracting(boolean paramBoolean);
}
