package edu.umd.cs.piccolo.util;

import edu.umd.cs.piccolo.PNode;

public interface PNodeFilter {
  boolean accept(PNode paramPNode);
  
  boolean acceptChildrenOf(PNode paramPNode);
}
