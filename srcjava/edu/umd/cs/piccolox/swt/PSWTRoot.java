package edu.umd.cs.piccolox.swt;

import edu.umd.cs.piccolo.PRoot;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.eclipse.swt.widgets.Composite;

public class PSWTRoot extends PRoot {
  private Composite composite;
  
  public PSWTRoot(Composite paramComposite) {
    this.composite = paramComposite;
  }
  
  public Timer createTimer(int paramInt, ActionListener paramActionListener) {
    return new SWTTimer(this.composite.getDisplay(), paramInt, paramActionListener);
  }
  
  public void scheduleProcessInputsIfNeeded() {
    if (!Thread.currentThread().equals(this.composite.getDisplay().getThread()))
      return; 
    if (!this.processInputsScheduled && !this.processingInputs && (getFullBoundsInvalid() || getChildBoundsInvalid() || getPaintInvalid() || getChildPaintInvalid())) {
      this.processInputsScheduled = true;
      this.composite.getDisplay().asyncExec(new Runnable(this) {
            private final PSWTRoot this$0;
            
            public void run() {
              this.this$0.processInputs();
              this.this$0.processInputsScheduled = false;
            }
          });
    } 
  }
}
