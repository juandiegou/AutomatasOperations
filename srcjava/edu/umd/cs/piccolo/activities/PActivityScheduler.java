package edu.umd.cs.piccolo.activities;

import edu.umd.cs.piccolo.PRoot;
import edu.umd.cs.piccolo.util.PUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class PActivityScheduler {
  private PRoot root;
  
  private List activities;
  
  private Timer activityTimer;
  
  private boolean activitiesChanged;
  
  private boolean animating;
  
  private ArrayList processingActivities;
  
  public PActivityScheduler(PRoot paramPRoot) {
    this.root = paramPRoot;
    this.activities = new ArrayList();
    this.processingActivities = new ArrayList();
  }
  
  public PRoot getRoot() {
    return this.root;
  }
  
  public void addActivity(PActivity paramPActivity) {
    addActivity(paramPActivity, false);
  }
  
  public void addActivity(PActivity paramPActivity, boolean paramBoolean) {
    if (this.activities.contains(paramPActivity))
      return; 
    this.activitiesChanged = true;
    if (paramBoolean) {
      this.activities.add(0, paramPActivity);
    } else {
      this.activities.add(paramPActivity);
    } 
    paramPActivity.setActivityScheduler(this);
    if (!getActivityTimer().isRunning())
      startActivityTimer(); 
  }
  
  public void removeActivity(PActivity paramPActivity) {
    if (!this.activities.contains(paramPActivity))
      return; 
    this.activitiesChanged = true;
    this.activities.remove(paramPActivity);
    if (this.activities.size() == 0)
      stopActivityTimer(); 
  }
  
  public void removeAllActivities() {
    this.activitiesChanged = true;
    this.activities.clear();
    stopActivityTimer();
  }
  
  public List getActivitiesReference() {
    return this.activities;
  }
  
  public void processActivities(long paramLong) {
    int i = this.activities.size();
    if (i > 0) {
      this.processingActivities.clear();
      this.processingActivities.addAll(this.activities);
      for (int j = i - 1; j >= 0; j--) {
        PActivity pActivity = this.processingActivities.get(j);
        pActivity.processStep(paramLong);
      } 
    } 
  }
  
  public boolean getAnimating() {
    if (this.activitiesChanged) {
      this.animating = false;
      for (byte b = 0; b < this.activities.size(); b++) {
        PActivity pActivity = this.activities.get(b);
        this.animating |= pActivity.isAnimation();
      } 
      this.activitiesChanged = false;
    } 
    return this.animating;
  }
  
  protected void startActivityTimer() {
    getActivityTimer().start();
  }
  
  protected void stopActivityTimer() {
    getActivityTimer().stop();
  }
  
  protected Timer getActivityTimer() {
    if (this.activityTimer == null)
      this.activityTimer = this.root.createTimer(PUtil.ACTIVITY_SCHEDULER_FRAME_DELAY, new ActionListener(this) {
            private final PActivityScheduler this$0;
            
            public void actionPerformed(ActionEvent param1ActionEvent) {
              this.this$0.root.processInputs();
            }
          }); 
    return this.activityTimer;
  }
}
