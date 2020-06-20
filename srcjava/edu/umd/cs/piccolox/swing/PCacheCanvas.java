package edu.umd.cs.piccolox.swing;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.PRoot;
import edu.umd.cs.piccolox.nodes.PCacheCamera;

public class PCacheCanvas extends PCanvas {
  protected PCamera createDefaultCamera() {
    PRoot pRoot = new PRoot();
    PLayer pLayer = new PLayer();
    PCacheCamera pCacheCamera = new PCacheCamera();
    pRoot.addChild((PNode)pCacheCamera);
    pRoot.addChild((PNode)pLayer);
    pCacheCamera.addLayer(pLayer);
    return (PCamera)pCacheCamera;
  }
}
