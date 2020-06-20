package edu.umd.cs.piccolox.activities;

import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PPositionPathActivity extends PPathActivity {
  protected Point2D[] positions;
  
  protected Target target;
  
  public PPositionPathActivity(long paramLong1, long paramLong2, Target paramTarget) {
    this(paramLong1, paramLong2, paramTarget, null, null);
  }
  
  public PPositionPathActivity(long paramLong1, long paramLong2, Target paramTarget, float[] paramArrayOffloat, Point2D[] paramArrayOfPoint2D) {
    this(paramLong1, paramLong2, 1, 1, paramTarget, paramArrayOffloat, paramArrayOfPoint2D);
  }
  
  public PPositionPathActivity(long paramLong1, long paramLong2, int paramInt1, int paramInt2, Target paramTarget, float[] paramArrayOffloat, Point2D[] paramArrayOfPoint2D) {
    super(paramLong1, paramLong2, paramInt1, paramInt2, paramArrayOffloat);
    this.target = paramTarget;
    this.positions = paramArrayOfPoint2D;
  }
  
  protected boolean isAnimation() {
    return true;
  }
  
  public Point2D[] getPositions() {
    return this.positions;
  }
  
  public Point2D getPosition(int paramInt) {
    return this.positions[paramInt];
  }
  
  public void setPositions(Point2D[] paramArrayOfPoint2D) {
    this.positions = paramArrayOfPoint2D;
  }
  
  public void setPosition(int paramInt, Point2D paramPoint2D) {
    this.positions[paramInt] = paramPoint2D;
  }
  
  public void setPositions(GeneralPath paramGeneralPath) {
    PathIterator pathIterator = paramGeneralPath.getPathIterator(null, 1.0D);
    ArrayList arrayList = new ArrayList();
    float[] arrayOfFloat1 = new float[6];
    float f1 = 0.0F;
    float f2 = 0.0F;
    float f3 = 0.0F;
    while (!pathIterator.isDone()) {
      int j = pathIterator.currentSegment(arrayOfFloat1);
      switch (j) {
        case 0:
          arrayList.add(new Point2D.Float(arrayOfFloat1[0], arrayOfFloat1[1]));
          f2 = arrayOfFloat1[0];
          f3 = arrayOfFloat1[1];
          break;
        case 1:
          arrayList.add(new Point2D.Float(arrayOfFloat1[0], arrayOfFloat1[1]));
          break;
        case 4:
          arrayList.add(new Point2D.Float(f2, f3));
          break;
        case 2:
        case 3:
          throw new RuntimeException();
      } 
      if (arrayList.size() > 1) {
        Point2D point2D1 = arrayList.get(arrayList.size() - 2);
        Point2D point2D2 = arrayList.get(arrayList.size() - 1);
        f1 = (float)(f1 + point2D1.distance(point2D2));
      } 
      pathIterator.next();
    } 
    int i = arrayList.size();
    Point2D[] arrayOfPoint2D = new Point2D[i];
    float[] arrayOfFloat2 = new float[i];
    for (byte b = 0; b < i; b++) {
      arrayOfPoint2D[b] = arrayList.get(b);
      if (b > 0) {
        float f = (float)this.positions[b - 1].distance(arrayOfPoint2D[b]);
        arrayOfFloat2[b] = arrayOfFloat2[b - 1] + f / f1;
      } 
    } 
    setPositions(arrayOfPoint2D);
    setKnots(arrayOfFloat2);
  }
  
  public void setRelativeTargetValue(float paramFloat, int paramInt1, int paramInt2) {
    Point2D point2D1 = getPosition(paramInt1);
    Point2D point2D2 = getPosition(paramInt2);
    this.target.setPosition(point2D1.getX() + paramFloat * (point2D2.getX() - point2D1.getX()), point2D1.getY() + paramFloat * (point2D2.getY() - point2D1.getY()));
  }
  
  public static interface Target {
    void setPosition(double param1Double1, double param1Double2);
  }
}
