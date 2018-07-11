package ru.stqa.pft.sandbox;

public class Point {

  public double a;
  public double b;

  public Point(double a, double b) {
    this.a = a;
    this.b = b;
  }

  public double distance (double a, double b) {
    double da = Math.pow(this.a - a, 2);
    double db = Math.pow(this.b - b, 2);
    return Math.round(Math.sqrt(da + db) * 100.0)/100.0;
  }

  public double distance(Point p) {
    return distance(p.a, p.b);
  }
}
