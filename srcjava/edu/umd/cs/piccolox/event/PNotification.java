package edu.umd.cs.piccolox.event;

import java.util.Map;

public class PNotification {
  protected String name;
  
  protected Object source;
  
  protected Map properties;
  
  public PNotification(String paramString, Object paramObject, Map paramMap) {
    this.name = paramString;
    this.source = paramObject;
    this.properties = paramMap;
  }
  
  public String getName() {
    return this.name;
  }
  
  public Object getObject() {
    return this.source;
  }
  
  public Object getProperty(Object paramObject) {
    return (this.properties != null) ? this.properties.get(paramObject) : null;
  }
}
