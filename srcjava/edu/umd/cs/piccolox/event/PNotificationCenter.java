package edu.umd.cs.piccolox.event;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PNotificationCenter {
  public static final Object NULL_MARKER = new Object();
  
  protected static PNotificationCenter DEFAULT_CENTER;
  
  protected HashMap listenersMap = new HashMap();
  
  protected ReferenceQueue keyQueue = new ReferenceQueue();
  
  public static PNotificationCenter defaultCenter() {
    if (DEFAULT_CENTER == null)
      DEFAULT_CENTER = new PNotificationCenter(); 
    return DEFAULT_CENTER;
  }
  
  public void addListener(Object paramObject1, String paramString1, String paramString2, Object paramObject2) {
    processKeyQueue();
    Object object = paramString2;
    Method method = null;
    try {
      method = paramObject1.getClass().getMethod(paramString1, new Class[] { PNotification.class });
    } catch (NoSuchMethodException noSuchMethodException) {
      noSuchMethodException.printStackTrace();
      return;
    } 
    if (object == null)
      object = NULL_MARKER; 
    if (paramObject2 == null)
      paramObject2 = NULL_MARKER; 
    CompoundKey compoundKey = new CompoundKey(object, paramObject2);
    CompoundValue compoundValue = new CompoundValue(paramObject1, method);
    List list = (List)this.listenersMap.get(compoundKey);
    if (list == null) {
      list = new ArrayList();
      this.listenersMap.put(new CompoundKey(object, paramObject2, this.keyQueue), list);
    } 
    if (!list.contains(compoundValue))
      list.add(compoundValue); 
  }
  
  public void removeListener(Object paramObject) {
    processKeyQueue();
    Iterator iterator = (new LinkedList(this.listenersMap.keySet())).iterator();
    while (iterator.hasNext())
      removeListener(paramObject, iterator.next()); 
  }
  
  public void removeListener(Object paramObject1, String paramString, Object paramObject2) {
    processKeyQueue();
    List list = matchingKeys(paramString, paramObject2);
    Iterator iterator = list.iterator();
    while (iterator.hasNext())
      removeListener(paramObject1, iterator.next()); 
  }
  
  public void postNotification(String paramString, Object paramObject) {
    postNotification(paramString, paramObject, null);
  }
  
  public void postNotification(String paramString, Object paramObject, Map paramMap) {
    postNotification(new PNotification(paramString, paramObject, paramMap));
  }
  
  public void postNotification(PNotification paramPNotification) {
    LinkedList linkedList = new LinkedList();
    String str = paramPNotification.getName();
    Object object = paramPNotification.getObject();
    if (str != null) {
      if (object != null) {
        List list1 = (List)this.listenersMap.get(new CompoundKey(str, object));
        if (list1 != null)
          linkedList.addAll(list1); 
        list1 = (List)this.listenersMap.get(new CompoundKey(str, NULL_MARKER));
        if (list1 != null)
          linkedList.addAll(list1); 
        list1 = (List)this.listenersMap.get(new CompoundKey(NULL_MARKER, object));
        if (list1 != null)
          linkedList.addAll(list1); 
      } else {
        List list1 = (List)this.listenersMap.get(new CompoundKey(str, NULL_MARKER));
        if (list1 != null)
          linkedList.addAll(list1); 
      } 
    } else if (object != null) {
      List list1 = (List)this.listenersMap.get(new CompoundKey(NULL_MARKER, object));
      if (list1 != null)
        linkedList.addAll(list1); 
    } 
    CompoundKey compoundKey = new CompoundKey(NULL_MARKER, NULL_MARKER);
    List list = (List)this.listenersMap.get(compoundKey);
    if (list != null)
      linkedList.addAll(list); 
    Iterator iterator = linkedList.iterator();
    while (iterator.hasNext()) {
      CompoundValue compoundValue = iterator.next();
      if (compoundValue.get() == null) {
        iterator.remove();
        continue;
      } 
      try {
        compoundValue.getMethod().invoke(compoundValue.get(), new Object[] { paramPNotification });
      } catch (IllegalAccessException illegalAccessException) {
        illegalAccessException.printStackTrace();
      } catch (InvocationTargetException invocationTargetException) {
        invocationTargetException.printStackTrace();
      } 
    } 
  }
  
  protected List matchingKeys(String paramString, Object paramObject) {
    LinkedList linkedList = new LinkedList();
    for (CompoundKey compoundKey : this.listenersMap.keySet()) {
      if ((paramString == null || paramString == compoundKey.name()) && (paramObject == null || paramObject == compoundKey.get()))
        linkedList.add(compoundKey); 
    } 
    return linkedList;
  }
  
  protected void removeListener(Object paramObject1, Object paramObject2) {
    if (paramObject1 == null) {
      this.listenersMap.remove(paramObject2);
      return;
    } 
    List list = (List)this.listenersMap.get(paramObject2);
    if (list == null)
      return; 
    Iterator iterator = list.iterator();
    while (iterator.hasNext()) {
      T t = ((CompoundValue)iterator.next()).get();
      if (t == null || paramObject1 == t)
        iterator.remove(); 
    } 
    if (list.size() == 0)
      this.listenersMap.remove(paramObject2); 
  }
  
  protected void processKeyQueue() {
    CompoundKey compoundKey;
    while ((compoundKey = (CompoundKey)this.keyQueue.poll()) != null)
      this.listenersMap.remove(compoundKey); 
  }
  
  protected static class CompoundValue extends WeakReference {
    protected int hashCode;
    
    protected Method method;
    
    public CompoundValue(Object param1Object, Method param1Method) {
      super((T)param1Object);
      this.hashCode = param1Object.hashCode();
      this.method = param1Method;
    }
    
    public Method getMethod() {
      return this.method;
    }
    
    public int hashCode() {
      return this.hashCode;
    }
    
    public boolean equals(Object param1Object) {
      if (this == param1Object)
        return true; 
      CompoundValue compoundValue = (CompoundValue)param1Object;
      if (this.method == compoundValue.method || (this.method != null && this.method.equals(compoundValue.method))) {
        T t = get();
        if (t != null && t == compoundValue.get())
          return true; 
      } 
      return false;
    }
    
    public String toString() {
      return "[CompoundValue:" + get() + ":" + getMethod().getName() + "]";
    }
  }
  
  protected static class CompoundKey extends WeakReference {
    private Object name;
    
    private int hashCode;
    
    public CompoundKey(Object param1Object1, Object param1Object2) {
      super((T)param1Object2);
      this.name = param1Object1;
      this.hashCode = param1Object1.hashCode() + param1Object2.hashCode();
    }
    
    public CompoundKey(Object param1Object1, Object param1Object2, ReferenceQueue param1ReferenceQueue) {
      super((T)param1Object2, param1ReferenceQueue);
      this.name = param1Object1;
      this.hashCode = param1Object1.hashCode() + param1Object2.hashCode();
    }
    
    public Object name() {
      return this.name;
    }
    
    public int hashCode() {
      return this.hashCode;
    }
    
    public boolean equals(Object param1Object) {
      if (this == param1Object)
        return true; 
      CompoundKey compoundKey = (CompoundKey)param1Object;
      if (this.name == compoundKey.name || (this.name != null && this.name.equals(compoundKey.name))) {
        T t = get();
        if (t != null && t == compoundKey.get())
          return true; 
      } 
      return false;
    }
    
    public String toString() {
      return "[CompoundKey:" + name() + ":" + get() + "]";
    }
  }
}
