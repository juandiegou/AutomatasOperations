package edu.umd.cs.piccolox.swt;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import org.eclipse.swt.graphics.Rectangle;

public class SWTShapeManager {
  static AffineTransform IDENTITY_XFORM = new AffineTransform();
  
  static Point2D aPoint = new Point2D.Double();
  
  static ArrayList segList = new ArrayList();
  
  static double[] pts = new double[8];
  
  public static void transform(Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform) {
    pts[0] = paramRectangle2D.getX();
    pts[1] = paramRectangle2D.getY();
    pts[2] = paramRectangle2D.getX() + paramRectangle2D.getWidth();
    pts[3] = paramRectangle2D.getY();
    pts[4] = paramRectangle2D.getX() + paramRectangle2D.getWidth();
    pts[5] = paramRectangle2D.getY() + paramRectangle2D.getHeight();
    pts[6] = paramRectangle2D.getX();
    pts[7] = paramRectangle2D.getY() + paramRectangle2D.getHeight();
    paramAffineTransform.transform(pts, 0, pts, 0, 4);
    double d1 = pts[0];
    double d2 = pts[1];
    double d3 = pts[0];
    double d4 = pts[1];
    for (byte b = 1; b < 4; b++) {
      if (pts[2 * b] < d1)
        d1 = pts[2 * b]; 
      if (pts[2 * b + 1] < d2)
        d2 = pts[2 * b + 1]; 
      if (pts[2 * b] > d3)
        d3 = pts[2 * b]; 
      if (pts[2 * b + 1] > d4)
        d4 = pts[2 * b + 1]; 
    } 
    paramRectangle2D.setRect(d1, d2, d3 - d1, d4 - d2);
  }
  
  public static void awtToSWT(Rectangle2D paramRectangle2D, Rectangle paramRectangle) {
    paramRectangle.x = (int)(paramRectangle2D.getX() + 0.5D);
    paramRectangle.y = (int)(paramRectangle2D.getY() + 0.5D);
    paramRectangle.width = (int)(paramRectangle2D.getWidth() + 0.5D);
    paramRectangle.height = (int)(paramRectangle2D.getHeight() + 0.5D);
  }
  
  public static double[] shapeToPolyline(Shape paramShape) {
    segList.clear();
    aPoint.setLocation(0.0D, 0.0D);
    PathIterator pathIterator = paramShape.getPathIterator(IDENTITY_XFORM, 1.0E-9D);
    while (!pathIterator.isDone()) {
      int i = pathIterator.currentSegment(pts);
      switch (i) {
        case 0:
          aPoint.setLocation(pts[0], pts[1]);
          segList.add(new Point2D.Double(pts[0], pts[1]));
          break;
        case 1:
          segList.add(new Point2D.Double(pts[0], pts[1]));
          break;
        case 4:
          segList.add(new Point2D.Double(aPoint.getX(), aPoint.getY()));
          break;
      } 
      pathIterator.next();
    } 
    double[] arrayOfDouble = new double[2 * segList.size()];
    for (byte b = 0; b < segList.size(); b++) {
      Point2D point2D = segList.get(b);
      arrayOfDouble[2 * b] = (int)(point2D.getX() + 0.5D);
      arrayOfDouble[2 * b + 1] = (int)(point2D.getY() + 0.5D);
    } 
    return arrayOfDouble;
  }
  
  public static int[] transform(double[] paramArrayOfdouble, AffineTransform paramAffineTransform) {
    int[] arrayOfInt = new int[paramArrayOfdouble.length];
    for (byte b = 0; b < paramArrayOfdouble.length / 2; b++) {
      aPoint.setLocation(paramArrayOfdouble[2 * b], paramArrayOfdouble[2 * b + 1]);
      paramAffineTransform.transform(aPoint, aPoint);
      arrayOfInt[2 * b] = (int)(aPoint.getX() + 0.5D);
      arrayOfInt[2 * b + 1] = (int)(aPoint.getY() + 0.5D);
    } 
    return arrayOfInt;
  }
}
