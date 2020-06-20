package edu.umd.cs.piccolo;

import edu.umd.cs.piccolo.activities.PActivity;
import edu.umd.cs.piccolo.activities.PActivityScheduler;
import edu.umd.cs.piccolo.util.PDebug;
import edu.umd.cs.piccolo.util.PNodeFilter;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class PRoot extends PNode {
  public static final String PROPERTY_INPUT_SOURCES = "inputSources";
  
  public static final int PROPERTY_CODE_INPUT_SOURCES = 16384;
  
  protected transient boolean processingInputs;
  
  protected transient boolean processInputsScheduled;
  
  private PInputManager defaultInputManager;
  
  private transient List inputSources = new ArrayList();
  
  private transient long globalTime = System.currentTimeMillis();
  
  private PActivityScheduler activityScheduler = new PActivityScheduler(this);
  
  public boolean addActivity(PActivity paramPActivity) {
    getActivityScheduler().addActivity(paramPActivity);
    return true;
  }
  
  public PActivityScheduler getActivityScheduler() {
    return this.activityScheduler;
  }
  
  public void waitForActivities() {
    PNodeFilter pNodeFilter = new PNodeFilter(this) {
        private final PRoot this$0;
        
        public boolean accept(PNode param1PNode) {
          return (param1PNode instanceof PCamera && ((PCamera)param1PNode).getComponent() != null);
        }
        
        public boolean acceptChildrenOf(PNode param1PNode) {
          return true;
        }
      };
    while (this.activityScheduler.getActivitiesReference().size() > 0) {
      processInputs();
      for (PCamera pCamera : getAllNodes(pNodeFilter, null))
        pCamera.getComponent().paintImmediately(); 
    } 
  }
  
  public PRoot getRoot() {
    return this;
  }
  
  public PInputManager getDefaultInputManager() {
    if (this.defaultInputManager == null) {
      this.defaultInputManager = new PInputManager();
      addInputSource(this.defaultInputManager);
    } 
    return this.defaultInputManager;
  }
  
  public void addInputSource(InputSource paramInputSource) {
    this.inputSources.add(paramInputSource);
    firePropertyChange(16384, "inputSources", null, this.inputSources);
  }
  
  public void removeInputSource(InputSource paramInputSource) {
    this.inputSources.remove(paramInputSource);
    firePropertyChange(16384, "inputSources", null, this.inputSources);
  }
  
  public Timer createTimer(int paramInt, ActionListener paramActionListener) {
    return new Timer(paramInt, paramActionListener);
  }
  
  public long getGlobalTime() {
    return this.globalTime;
  }
  
  public void processInputs() {
    PDebug.startProcessingInput();
    this.processingInputs = true;
    this.globalTime = System.currentTimeMillis();
    byte b1 = (this.inputSources == null) ? 0 : this.inputSources.size();
    for (byte b2 = 0; b2 < b1; b2++) {
      InputSource inputSource = this.inputSources.get(b2);
      inputSource.processInput();
    } 
    this.activityScheduler.processActivities(this.globalTime);
    validateFullBounds();
    validateFullPaint();
    this.processingInputs = false;
    PDebug.endProcessingInput();
  }
  
  public void setFullBoundsInvalid(boolean paramBoolean) {
    super.setFullBoundsInvalid(paramBoolean);
    scheduleProcessInputsIfNeeded();
  }
  
  public void setChildBoundsInvalid(boolean paramBoolean) {
    super.setChildBoundsInvalid(paramBoolean);
    scheduleProcessInputsIfNeeded();
  }
  
  public void setPaintInvalid(boolean paramBoolean) {
    super.setPaintInvalid(paramBoolean);
    scheduleProcessInputsIfNeeded();
  }
  
  public void setChildPaintInvalid(boolean paramBoolean) {
    super.setChildPaintInvalid(paramBoolean);
    scheduleProcessInputsIfNeeded();
  }
  
  public void scheduleProcessInputsIfNeeded() {
    if (!SwingUtilities.isEventDispatchThread())
      return; 
    PDebug.scheduleProcessInputs();
    if (!this.processInputsScheduled && !this.processingInputs && (getFullBoundsInvalid() || getChildBoundsInvalid() || getPaintInvalid() || getChildPaintInvalid())) {
      this.processInputsScheduled = true;
      SwingUtilities.invokeLater(new Runnable(this) {
            private final PRoot this$0;
            
            public void run() {
              this.this$0.processInputs();
              this.this$0.processInputsScheduled = false;
            }
          });
    } 
  }
  
  public static interface InputSource {
    void processInput();
  }
}
