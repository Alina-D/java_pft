package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest {

  @Test
  public void testDistancePositive () {
    Point p1 = new Point(3, 6);
    Point p2 = new Point(7, 5);
    Assert.assertEquals(p1.distance(p2), 4.12);
  }

  @Test
  public void testDistanceNegative () {
    Point p1 = new Point(-2, 6);
    Point p2 = new Point(7, -5);
    Assert.assertEquals(p1.distance(p2), 14.21);
  }
}
