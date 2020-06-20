package edu.umd.cs.piccolo.util;

import java.util.ArrayList;

public class PStack extends ArrayList {
  public void push(Object paramObject) {
    add((E)paramObject);
  }
  
  public Object peek() {
    int i = size();
    return (i == 0) ? null : get(i - 1);
  }
  
  public Object pop() {
    return remove(size() - 1);
  }
}
