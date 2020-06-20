package edu.umd.cs.piccolo.activities;

import edu.umd.cs.piccolo.util.PUtil;

public class PActivity {
  public static final int TERMINATE_WITHOUT_FINISHING = 0;
  
  public static final int TERMINATE_AND_FINISH = 1;
  
  public static final int TERMINATE_AND_FINISH_IF_STEPPING = 2;
  
  private PActivityScheduler scheduler;
  
  private long startTime;
  
  private long duration;
  
  private long stepRate;
  
  private PActivityDelegate delegate;
  
  private boolean stepping;
  
  private long nextStepTime;
  
  public PActivity(long paramLong) {
    this(paramLong, PUtil.DEFAULT_ACTIVITY_STEP_RATE);
  }
  
  public PActivity(long paramLong1, long paramLong2) {
    this(paramLong1, paramLong2, System.currentTimeMillis());
  }
  
  public PActivity(long paramLong1, long paramLong2, long paramLong3) {
    this.duration = paramLong1;
    this.stepRate = paramLong2;
    this.startTime = paramLong3;
    this.nextStepTime = paramLong3;
    this.stepping = false;
  }
  
  public long getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(long paramLong) {
    this.startTime = paramLong;
  }
  
  public long getStepRate() {
    return this.stepRate;
  }
  
  public void setStepRate(long paramLong) {
    this.stepRate = paramLong;
  }
  
  public long getNextStepTime() {
    return this.nextStepTime;
  }
  
  public long getDuration() {
    return this.duration;
  }
  
  public void setDuration(long paramLong) {
    this.duration = paramLong;
  }
  
  public PActivityScheduler getActivityScheduler() {
    return this.scheduler;
  }
  
  public void setActivityScheduler(PActivityScheduler paramPActivityScheduler) {
    this.scheduler = paramPActivityScheduler;
  }
  
  public boolean isStepping() {
    return this.stepping;
  }
  
  protected boolean isAnimation() {
    return false;
  }
  
  protected void activityStarted() {
    if (this.delegate != null)
      this.delegate.activityStarted(this); 
  }
  
  protected void activityStep(long paramLong) {
    if (this.delegate != null)
      this.delegate.activityStepped(this); 
  }
  
  protected void activityFinished() {
    if (this.delegate != null)
      this.delegate.activityFinished(this); 
  }
  
  public PActivityDelegate getDelegate() {
    return this.delegate;
  }
  
  public void setDelegate(PActivityDelegate paramPActivityDelegate) {
    this.delegate = paramPActivityDelegate;
  }
  
  public void startAfter(PActivity paramPActivity) {
    setStartTime(paramPActivity.getStartTime() + paramPActivity.getDuration());
  }
  
  public void terminate() {
    terminate(2);
  }
  
  public void terminate(int paramInt) {
    if (this.scheduler != null)
      this.scheduler.removeActivity(this); 
    switch (paramInt) {
      case 0:
        this.stepping = false;
        break;
      case 1:
        if (this.stepping) {
          this.stepping = false;
          activityFinished();
          break;
        } 
        activityStarted();
        activityFinished();
        break;
      case 2:
        if (this.stepping) {
          this.stepping = false;
          activityFinished();
        } 
        break;
    } 
  }
  
  public long processStep(long paramLong) {
    if (paramLong < this.startTime)
      return this.startTime - paramLong; 
    if (paramLong > getStopTime()) {
      if (this.stepping) {
        this.stepping = false;
        this.scheduler.removeActivity(this);
        activityFinished();
      } else {
        activityStarted();
        this.scheduler.removeActivity(this);
        activityFinished();
      } 
      return -1L;
    } 
    if (!this.stepping) {
      activityStarted();
      this.stepping = true;
    } 
    if (paramLong >= this.nextStepTime) {
      activityStep(paramLong - this.startTime);
      this.nextStepTime = paramLong + this.stepRate;
    } 
    return this.stepRate;
  }
  
  public long getStopTime() {
    return (this.duration == -1L) ? Long.MAX_VALUE : (this.startTime + this.duration);
  }
  
  public String toString() {
    String str = super.toString().replaceAll(".*\\.", "");
    return str + "[" + paramString() + "]";
  }
  
  protected String paramString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("startTime=" + this.startTime);
    stringBuffer.append(",duration=" + this.duration);
    stringBuffer.append(",stepRate=" + this.stepRate);
    if (this.stepping)
      stringBuffer.append(",stepping"); 
    stringBuffer.append(",nextStepTime=" + this.nextStepTime);
    return stringBuffer.toString();
  }
  
  public static interface PActivityDelegate {
    void activityStarted(PActivity param1PActivity);
    
    void activityStepped(PActivity param1PActivity);
    
    void activityFinished(PActivity param1PActivity);
  }
}
