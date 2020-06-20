package edu.umd.cs.piccolox.swt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.eclipse.swt.widgets.Display;

public class SWTTimer extends Timer {
  private boolean notify = false;
  
  int initialDelay;
  
  int delay;
  
  boolean repeats = true;
  
  boolean coalesce = true;
  
  Runnable doPostEvent = null;
  
  Display display = null;
  
  long expirationTime;
  
  SWTTimer nextTimer;
  
  boolean running;
  
  public SWTTimer(Display paramDisplay, int paramInt, ActionListener paramActionListener) {
    super(paramInt, paramActionListener);
    this.delay = paramInt;
    this.initialDelay = paramInt;
    this.doPostEvent = new SWTDoPostEvent(this);
    this.display = paramDisplay;
  }
  
  protected void fireActionPerformed(ActionEvent paramActionEvent) {
    Object[] arrayOfObject = this.listenerList.getListenerList();
    for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
      if (arrayOfObject[i] == ActionListener.class)
        ((ActionListener)arrayOfObject[i + 1]).actionPerformed(paramActionEvent); 
    } 
  }
  
  SWTTimerQueue timerQueue() {
    return SWTTimerQueue.sharedInstance(this.display);
  }
  
  public void setDelay(int paramInt) {
    if (paramInt < 0)
      throw new IllegalArgumentException("Invalid delay: " + paramInt); 
    this.delay = paramInt;
  }
  
  public int getDelay() {
    return this.delay;
  }
  
  public void setInitialDelay(int paramInt) {
    if (paramInt < 0)
      throw new IllegalArgumentException("Invalid initial delay: " + paramInt); 
    this.initialDelay = paramInt;
  }
  
  public int getInitialDelay() {
    return this.initialDelay;
  }
  
  public void setRepeats(boolean paramBoolean) {
    this.repeats = paramBoolean;
  }
  
  public boolean isRepeats() {
    return this.repeats;
  }
  
  public void setCoalesce(boolean paramBoolean) {
    boolean bool = this.coalesce;
    this.coalesce = paramBoolean;
    if (!bool && this.coalesce)
      cancelEventOverride(); 
  }
  
  public boolean isCoalesce() {
    return this.coalesce;
  }
  
  public void start() {
    timerQueue().addTimer(this, System.currentTimeMillis() + getInitialDelay());
  }
  
  public boolean isRunning() {
    return timerQueue().containsTimer(this);
  }
  
  public void stop() {
    timerQueue().removeTimer(this);
    cancelEventOverride();
  }
  
  public void restart() {
    stop();
    start();
  }
  
  synchronized void cancelEventOverride() {
    this.notify = false;
  }
  
  synchronized void postOverride() {
    if (!this.notify || !this.coalesce) {
      this.notify = true;
      this.display.asyncExec(this.doPostEvent);
    } 
  }
  
  class SWTDoPostEvent implements Runnable {
    private final SWTTimer this$0;
    
    SWTDoPostEvent(SWTTimer this$0) {
      this.this$0 = this$0;
    }
    
    public void run() {
      if (this.this$0.notify) {
        this.this$0.fireActionPerformed(new ActionEvent(this.this$0, 0, null, System.currentTimeMillis(), 0));
        if (this.this$0.coalesce)
          this.this$0.cancelEventOverride(); 
      } 
    }
    
    SWTTimer getTimer() {
      return this.this$0;
    }
  }
}