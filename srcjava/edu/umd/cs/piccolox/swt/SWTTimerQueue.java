package edu.umd.cs.piccolox.swt;

import org.eclipse.swt.widgets.Display;

public class SWTTimerQueue implements Runnable {
  static SWTTimerQueue instance;
  
  Display display = null;
  
  SWTTimer firstTimer;
  
  boolean running;
  
  public SWTTimerQueue(Display paramDisplay) {
    this.display = paramDisplay;
    start();
  }
  
  public static SWTTimerQueue sharedInstance(Display paramDisplay) {
    if (instance == null)
      instance = new SWTTimerQueue(paramDisplay); 
    return instance;
  }
  
  synchronized void start() {
    if (this.running)
      throw new RuntimeException("Can't start a TimerQueue that is already running"); 
    Display.getDefault().asyncExec(new Runnable(this) {
          private final SWTTimerQueue this$0;
          
          public void run() {
            Thread thread = new Thread(this.this$0, "TimerQueue");
            thread.setDaemon(true);
            thread.setPriority(5);
            thread.start();
          }
        });
    this.running = true;
  }
  
  synchronized void stop() {
    this.running = false;
    notify();
  }
  
  synchronized void addTimer(SWTTimer paramSWTTimer, long paramLong) {
    if (paramSWTTimer.running)
      return; 
    SWTTimer sWTTimer1 = null;
    SWTTimer sWTTimer2;
    for (sWTTimer2 = this.firstTimer; sWTTimer2 != null && sWTTimer2.expirationTime <= paramLong; sWTTimer2 = sWTTimer2.nextTimer)
      sWTTimer1 = sWTTimer2; 
    if (sWTTimer1 == null) {
      this.firstTimer = paramSWTTimer;
    } else {
      sWTTimer1.nextTimer = paramSWTTimer;
    } 
    paramSWTTimer.expirationTime = paramLong;
    paramSWTTimer.nextTimer = sWTTimer2;
    paramSWTTimer.running = true;
    notify();
  }
  
  synchronized void removeTimer(SWTTimer paramSWTTimer) {
    if (!paramSWTTimer.running)
      return; 
    SWTTimer sWTTimer1 = null;
    SWTTimer sWTTimer2 = this.firstTimer;
    boolean bool = false;
    while (sWTTimer2 != null) {
      if (sWTTimer2 == paramSWTTimer) {
        bool = true;
        break;
      } 
      sWTTimer1 = sWTTimer2;
      sWTTimer2 = sWTTimer2.nextTimer;
    } 
    if (!bool)
      return; 
    if (sWTTimer1 == null) {
      this.firstTimer = paramSWTTimer.nextTimer;
    } else {
      sWTTimer1.nextTimer = paramSWTTimer.nextTimer;
    } 
    paramSWTTimer.expirationTime = 0L;
    paramSWTTimer.nextTimer = null;
    paramSWTTimer.running = false;
  }
  
  synchronized boolean containsTimer(SWTTimer paramSWTTimer) {
    return paramSWTTimer.running;
  }
  
  synchronized long postExpiredTimers() {
    while (true) {
      SWTTimer sWTTimer = this.firstTimer;
      if (sWTTimer == null)
        return 0L; 
      long l1 = System.currentTimeMillis();
      long l2 = sWTTimer.expirationTime - l1;
      if (l2 <= 0L) {
        try {
          sWTTimer.postOverride();
        } catch (SecurityException securityException) {}
        removeTimer(sWTTimer);
        if (sWTTimer.isRepeats())
          addTimer(sWTTimer, l1 + sWTTimer.getDelay()); 
        try {
          wait(1L);
        } catch (InterruptedException interruptedException) {}
      } 
      if (l2 > 0L)
        return l2; 
    } 
  }
  
  public synchronized void run() {
    try {
      while (this.running) {
        long l = postExpiredTimers();
        try {
          wait(l);
        } catch (InterruptedException interruptedException) {}
      } 
    } catch (ThreadDeath threadDeath) {
      this.running = false;
      for (SWTTimer sWTTimer = this.firstTimer; sWTTimer != null; sWTTimer = sWTTimer.nextTimer)
        sWTTimer.cancelEventOverride(); 
      this.display.asyncExec(new SWTTimerQueueRestart(this.display));
      throw threadDeath;
    } 
  }
  
  public synchronized String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("TimerQueue (");
    SWTTimer sWTTimer = this.firstTimer;
    while (sWTTimer != null) {
      stringBuffer.append(sWTTimer.toString());
      sWTTimer = sWTTimer.nextTimer;
      if (sWTTimer != null)
        stringBuffer.append(", "); 
    } 
    stringBuffer.append(")");
    return stringBuffer.toString();
  }
  
  protected static class SWTTimerQueueRestart implements Runnable {
    boolean attemptedStart;
    
    Display display = null;
    
    public SWTTimerQueueRestart(Display param1Display) {
      this.display = param1Display;
    }
    
    public synchronized void run() {
      if (!this.attemptedStart) {
        SWTTimerQueue sWTTimerQueue = SWTTimerQueue.sharedInstance(this.display);
        synchronized (sWTTimerQueue) {
          if (!sWTTimerQueue.running)
            sWTTimerQueue.start(); 
        } 
        this.attemptedStart = true;
      } 
    }
  }
}