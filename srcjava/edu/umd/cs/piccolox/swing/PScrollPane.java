package edu.umd.cs.piccolox.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.plaf.ScrollPaneUI;

public class PScrollPane extends JScrollPane {
  protected PNullAction nullAction = null;
  
  protected boolean disableKeyActions = false;
  
  public PScrollPane(Component paramComponent, int paramInt1, int paramInt2) {
    super(paramComponent, paramInt1, paramInt2);
    PScrollPaneLayout.UIResource uIResource = new PScrollPaneLayout.UIResource();
    setLayout(uIResource);
    uIResource.syncWithScrollPane(this);
  }
  
  public PScrollPane(Component paramComponent) {
    this(paramComponent, 20, 30);
  }
  
  public PScrollPane(int paramInt1, int paramInt2) {
    this((Component)null, paramInt1, paramInt2);
  }
  
  public PScrollPane() {
    this((Component)null, 20, 30);
  }
  
  public void setKeyActionsDisabled(boolean paramBoolean) {
    if (paramBoolean && this.disableKeyActions != paramBoolean) {
      this.disableKeyActions = paramBoolean;
      disableKeyActions();
    } else if (!paramBoolean && this.disableKeyActions != paramBoolean) {
      this.disableKeyActions = paramBoolean;
      installCustomKeyActions();
    } 
  }
  
  public void setUI(ScrollPaneUI paramScrollPaneUI) {
    super.setUI(paramScrollPaneUI);
    if (!this.disableKeyActions) {
      installCustomKeyActions();
    } else {
      disableKeyActions();
    } 
  }
  
  protected void installCustomKeyActions() {
    ActionMap actionMap = getActionMap();
    actionMap.put("scrollUp", new PScrollAction("scrollUp", 1, -1, true));
    actionMap.put("scrollDown", new PScrollAction("scrollDown", 1, 1, true));
    actionMap.put("scrollLeft", new PScrollAction("scrollLeft", 0, -1, true));
    actionMap.put("scrollRight", new PScrollAction("ScrollRight", 0, 1, true));
    actionMap.put("unitScrollRight", new PScrollAction("UnitScrollRight", 0, 1, false));
    actionMap.put("unitScrollLeft", new PScrollAction("UnitScrollLeft", 0, -1, false));
    actionMap.put("unitScrollUp", new PScrollAction("UnitScrollUp", 1, -1, false));
    actionMap.put("unitScrollDown", new PScrollAction("UnitScrollDown", 1, 1, false));
    actionMap.put("scrollEnd", new PScrollEndAction("ScrollEnd"));
    actionMap.put("scrollHome", new PScrollHomeAction("ScrollHome"));
  }
  
  protected void disableKeyActions() {
    ActionMap actionMap = getActionMap();
    if (this.nullAction == null)
      this.nullAction = new PNullAction(); 
    actionMap.put("scrollUp", this.nullAction);
    actionMap.put("scrollDown", this.nullAction);
    actionMap.put("scrollLeft", this.nullAction);
    actionMap.put("scrollRight", this.nullAction);
    actionMap.put("unitScrollRight", this.nullAction);
    actionMap.put("unitScrollLeft", this.nullAction);
    actionMap.put("unitScrollUp", this.nullAction);
    actionMap.put("unitScrollDown", this.nullAction);
    actionMap.put("scrollEnd", this.nullAction);
    actionMap.put("scrollHome", this.nullAction);
  }
  
  protected JViewport createViewport() {
    return new PViewport();
  }
  
  protected static class PNullAction extends AbstractAction {
    public void actionPerformed(ActionEvent param1ActionEvent) {}
  }
  
  protected static class PScrollEndAction extends AbstractAction {
    protected PScrollEndAction(String param1String) {
      super(param1String);
    }
    
    public void actionPerformed(ActionEvent param1ActionEvent) {
      JScrollPane jScrollPane = (JScrollPane)param1ActionEvent.getSource();
      if (jScrollPane.getVerticalScrollBar().isShowing() || jScrollPane.getHorizontalScrollBar().isShowing()) {
        JViewport jViewport = jScrollPane.getViewport();
        if (jViewport != null && jViewport.getView() != null) {
          Rectangle rectangle = jViewport.getViewRect();
          Dimension dimension = jViewport.getViewSize();
          jViewport.setViewPosition(new Point(dimension.width - rectangle.width, dimension.height - rectangle.height));
        } 
      } 
    }
  }
  
  private static class PScrollHomeAction extends AbstractAction {
    protected PScrollHomeAction(String param1String) {
      super(param1String);
    }
    
    public void actionPerformed(ActionEvent param1ActionEvent) {
      JScrollPane jScrollPane = (JScrollPane)param1ActionEvent.getSource();
      if (jScrollPane.getVerticalScrollBar().isShowing() || jScrollPane.getHorizontalScrollBar().isShowing()) {
        JViewport jViewport = jScrollPane.getViewport();
        if (jViewport != null && jViewport.getView() != null)
          jViewport.setViewPosition(new Point(0, 0)); 
      } 
    }
  }
  
  protected static class PScrollAction extends AbstractAction {
    protected int orientation;
    
    protected int direction;
    
    private boolean block;
    
    protected PScrollAction(String param1String, int param1Int1, int param1Int2, boolean param1Boolean) {
      super(param1String);
      this.orientation = param1Int1;
      this.direction = param1Int2;
      this.block = param1Boolean;
    }
    
    public void actionPerformed(ActionEvent param1ActionEvent) {
      JScrollPane jScrollPane = (JScrollPane)param1ActionEvent.getSource();
      if ((this.orientation == 1 && jScrollPane.getVerticalScrollBar().isShowing()) || (this.orientation == 0 && jScrollPane.getHorizontalScrollBar().isShowing())) {
        JViewport jViewport = jScrollPane.getViewport();
        Component component;
        if (jViewport != null && (component = jViewport.getView()) != null) {
          byte b;
          Rectangle rectangle = jViewport.getViewRect();
          Dimension dimension = jViewport.getViewSize();
          if (component instanceof Scrollable) {
            if (this.block) {
              b = ((Scrollable)component).getScrollableBlockIncrement(rectangle, this.orientation, this.direction);
            } else {
              b = ((Scrollable)component).getScrollableUnitIncrement(rectangle, this.orientation, this.direction);
            } 
          } else if (this.block) {
            if (this.orientation == 1) {
              b = rectangle.height;
            } else {
              b = rectangle.width;
            } 
          } else {
            b = 10;
          } 
          if (this.orientation == 1) {
            rectangle.y += b * this.direction;
            if (rectangle.y + rectangle.height > dimension.height) {
              rectangle.y = Math.max(0, dimension.height - rectangle.height);
            } else if (rectangle.y < 0) {
              rectangle.y = 0;
            } 
          } else {
            rectangle.x += b * this.direction;
            if (rectangle.x + rectangle.width > dimension.width) {
              rectangle.x = Math.max(0, dimension.width - rectangle.width);
            } else if (rectangle.x < 0) {
              rectangle.x = 0;
            } 
          } 
          jViewport.setViewPosition(rectangle.getLocation());
        } 
      } 
    }
  }
}
